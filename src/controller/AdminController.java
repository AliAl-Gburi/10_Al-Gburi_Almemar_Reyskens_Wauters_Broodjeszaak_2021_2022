package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.BelegSoort;
import model.BestelFacade;
import model.Broodje;
import model.Observer;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.AdminView;

import java.io.*;
import java.util.Properties;

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
        saveSetting();
    }

    private void loadSelectedSettings() {
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/settings.properties");
            properties.load(is);
            Object format = properties.getProperty("databaseFormat");
            String dbFormat = (String) format;
            view.getAdminMainPane().getSettingsPane().getFileFormat().setPromptText(dbFormat);
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
                    if (value != null) {
                        FileOutputStream os = new FileOutputStream("src/bestanden/settings.properties");
                        properties.setProperty("databaseFormat", value);
                        properties.store(os, "done");
                        os.close();
                    }
                    view.getAdminMainPane().getSettingsPane().getSaveLabel().setText("Your setting have been saved");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
