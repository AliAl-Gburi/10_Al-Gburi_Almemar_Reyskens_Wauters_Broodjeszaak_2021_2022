package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import model.BelegSoort;
import model.BestelFacade;
import model.Broodje;
import model.Observer;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.AdminView;

import java.io.*;
import java.util.*;

public class AdminController implements Observer {
    private AdminView view;
    private BestelFacade facade;
    private ObservableList<Broodje> observableBroodjeList;
    private ObservableList<BelegSoort> observableBelegsoortenList;

    public AdminController(BestelFacade facade) {
        this.facade = facade;
        facade.registerObserver(this);
    }

    public void setView(AdminView view) {
        this.view = view;
    }

    public void initialize() {
        facade.notifyObservers();
        loadSelectedSettings();
        loadStatistiek();
        loadKortingsLijstEnDBFormatLijst();
        saveSetting();
    }

    private void loadKortingsLijstEnDBFormatLijst() {
        view.getAdminMainPane().getSettingsPane().getFileFormat().getItems().addAll("excel", "text");
        view.getAdminMainPane().getSettingsPane().getKortingsList().getItems().addAll("Geen korting", "Goedkoopste broodje gratis", "10% korting op bestelling");
    }

    private void loadSelectedSettings() {
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/settings.properties");
            properties.load(is);
            Object format = properties.getProperty("databaseFormat");
            String dbFormat = (String) format;
            Object korting = properties.getProperty("defaultKorting");
            String kortingString = (String) korting;
            view.getAdminMainPane().getSettingsPane().getFileFormat().getSelectionModel().select(dbFormat);
            view.getAdminMainPane().getSettingsPane().getKortingsList().getSelectionModel().select(kortingString);
            facade.setDefaultKorting(kortingString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSetting() {
        view.getAdminMainPane().getSettingsPane().getSaveBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Properties properties = new Properties();
                try {
                    String value = view.getAdminMainPane().getSettingsPane().getFileFormat().getValue();
                    String defaultKoting = view.getAdminMainPane().getSettingsPane().getKortingsList().getValue();
                    FileOutputStream os = new FileOutputStream("src/bestanden/settings.properties");
                    if (value != null && defaultKoting != null) {

                        properties.setProperty("databaseFormat", value);
                        properties.setProperty("defaultKorting", defaultKoting);
                        facade.setDefaultKorting(defaultKoting);
                        properties.store(os, "done");

                    }

                    os.close();
                    view.getAdminMainPane().getSettingsPane().getSaveLabel().setText("Your setting have been saved");
                    facade.notifyObservers();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void loadStatistiek() {
        List<Broodje> broodjes = facade.getBroodjesList();
        List<BelegSoort> belegen = facade.getBelegenList();
        List<String> broodjesEnBelegen = new ArrayList<>();
        for (Broodje b: broodjes) {
            broodjesEnBelegen.add(b.getBroodjesnaam());
        }
        for (BelegSoort b: belegen) {
            broodjesEnBelegen.add(b.getBelegnaam());
        }
        view.getAdminMainPane().getStatistiekPane().getXaxis().setCategories(FXCollections.<String>observableArrayList(broodjesEnBelegen));

        for (String naam:broodjesEnBelegen) {
            if(facade.getVerkochtLijstBroodjes().containsKey(naam)) {
                view.getAdminMainPane().getStatistiekPane().getSeries().getData().add(new XYChart.Data<>(naam, facade.getVerkochtLijstBroodjes().get(naam)));
            }
            if(facade.getVerkochtLijstBelegen().containsKey(naam)) {
                view.getAdminMainPane().getStatistiekPane().getSeries().getData().add(new XYChart.Data<>(naam, facade.getVerkochtLijstBelegen().get(naam)));
            }
        }

    }

    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {
        observableBroodjeList = FXCollections.observableArrayList(broodjeDatabase.getBroodjesList());
        view.getAdminMainPane().getSandwichOverviewPane().getBroodjestable().setItems(observableBroodjeList);
        view.getAdminMainPane().getSandwichOverviewPane().getBroodjestable().refresh();

        observableBelegsoortenList = FXCollections.observableArrayList(belegDatabase.getBelegen());
        view.getAdminMainPane().getSandwichOverviewPane().getBelegtable().setItems(observableBelegsoortenList);
        view.getAdminMainPane().getSandwichOverviewPane().getBelegtable().refresh();
    }
}
