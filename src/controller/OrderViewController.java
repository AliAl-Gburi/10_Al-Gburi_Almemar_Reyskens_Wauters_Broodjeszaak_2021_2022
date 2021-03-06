package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.*;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.kortingStrategies.KortingStrategyEnum;
import view.OrderView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class OrderViewController implements Observer {
    private BestelFacade facade;
    private OrderView view;
    private ObservableList<Bestellijn> bestellijnObservableList;

    public OrderViewController(BestelFacade facade) {
        this.facade = facade;
        facade.registerObserver(this);
    }

    public void setView(OrderView view) {
        this.view = view;
    }

    public void initialize() {
        nieuweBestelling();
        loadBroodjeEnBelegKnopen();
        loadKortingsLijst();
        broodjeListener();
        belegListener();
        voegZelfdeBestelling();
        verwijderBroodje();
        annuleerBestelling();
        afsluitBestelling();
        betaal();
        naarKeuken();
        view.getVolgnr().setText("Volgnr: " + facade.getVolgnr());

    }

    private void loadKortingsLijst() {
        view.getKortings().getItems().addAll("Geen korting", "Goedkoopste broodje gratis", "10% korting op bestelling");
        view.getKortings().getSelectionModel().select(facade.getDefaultKorting());
    }

    public void nieuweBestelling() {
        view.getNieuwBestellingBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                facade.nieuwBestelling();
                view.getNieuwBestellingBtn().setDisable(true);
                view.getAfsluitBestelling().setDisable(false);
                view.getDuplicateOrderBtn().setDisable(false);
                view.getVerwijderBroodje().setDisable(false);
                facade.setBestellingState(facade.getInBestellingState());
                voorraadChecker();
                view.getVolgnr().setText("Volgnr: " + facade.getVolgnr());

            }
        });
    }

    public void deactivateAllBroodjesEnBelegen() {
        for (String broodje : view.getBroojebuttons().keySet()) {
            view.getBroojebuttons().get(broodje).setDisable(true);
        }
        for (String beleg : view.getBelegbuttons().keySet()) {
            view.getBelegbuttons().get(beleg).setDisable(true);
        }
    }

    public void voorraadChecker() {
        for (String broodje : view.getBroojebuttons().keySet()) {
            view.getBroojebuttons().get(broodje).setDisable(!availableBroodjeVoorraad(broodje));
        }

        for (String beleg : view.getBelegbuttons().keySet()) {
            view.getBelegbuttons().get(beleg).setDisable(!availableBelegVoorraad(beleg));
        }
    }

    public void broodjeListener() {
        for (String broodje : view.getBroojebuttons().keySet()) {
            view.getBroojebuttons().get(broodje).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    voegBestelLijnToe(broodje);
                    voorraadChecker();
                }
            });
        }
    }

    public void belegListener() {
        for (String beleg : view.getBelegbuttons().keySet()) {
            view.getBelegbuttons().get(beleg).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    voegBelegToe(beleg);
                    voorraadChecker();
                }
            });
        }
    }

    public void voegZelfdeBestelling() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        view.getDuplicateOrderBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (facade.getLijstBestellijnen().size() < 1) {
                    a.setContentText("Er moet minimaal 1 broodje zijn voordat je het kunt dupliceren");
                    a.show();
                } else if (!dupeBestelling()) {
                    a.setContentText("U bent niet op voorraad");
                    a.show();
                }
            }
        });
    }

    public void verwijderBroodje() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        view.getVerwijderBroodje().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (facade.getLijstBestellijnen().size() < 1) {
                    a.setContentText("Er zijn geen broodjes in de bestelling");
                    a.show();
                } else {
                    facade.verwijderLaatsteToegevoegdBroodje();
                    voorraadChecker();
                }
            }
        });
    }

    public void annuleerBestelling() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        view.getAnnuleerBestelling().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!view.getNieuwBestellingBtn().isDisabled()) {
                    a.setContentText("Je moet een bestelling hebben voordat je deze kunt annuleren");
                    a.show();
                } else {
                    facade.annuleerBestelling();
                    view.getNieuwBestellingBtn().setDisable(false);
                    view.getVolgnr().setText("Volgnr: " + facade.getVolgnr());
                    deactivateAllBroodjesEnBelegen();
                    view.getTeBetalen().setText("Te betalen: ???0");
                }
            }
        });
    }

    public void voegBestelLijnToe(String naamBroodje) {
        facade.voegBestellijnToe(naamBroodje);
    }

    public boolean dupeBestelling() {
        return facade.enoughtoDupe();
    }

    public void voegBelegToe(String naamBeleg) {
        try {
            facade.voegBelegenToe(naamBeleg);
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Aan een broodje hoeft geen beleg te worden toegevoegd.");
            a.show();
        }

    }

    private boolean availableBroodjeVoorraad(String item) {
        return facade.getVoorraadLijstBroodjes().get(item) > 0;
    }

    private boolean availableBelegVoorraad(String item) {
        return facade.getVoorraadLijstBelegen().get(item) > 0;
    }

    private void loadBroodjeEnBelegKnopen() {


        for (Broodje broodje : facade.getBroodjesList()
        ) {
            Button broodjebtn = new Button(broodje.getBroodjesnaam());
            view.getBroojebuttons().put(broodje.getBroodjesnaam(), broodjebtn);
            broodjebtn.setDisable(true);
            view.getBroodjesKnopen().getChildren().add(broodjebtn);
        }

        for (BelegSoort beleg : facade.getBelegenList()) {
            Button belegbtn = new Button(beleg.getBelegnaam());
            view.getBelegbuttons().put(beleg.getBelegnaam(), belegbtn);
            belegbtn.setDisable(true);
            view.getBelegenKnopen().getChildren().add(belegbtn);
        }
    }

    private void afsluitBestelling() {
        view.getAfsluitBestelling().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String korting = view.getKorting();
                if (korting == null || korting.equals("Geen korting") || korting.isEmpty()) {
                    try {
                        view.getTeBetalen().setText("Te betalen: ???" + facade.getPrijsBestelling(KortingStrategyEnum.GEENKORTING));
                    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (korting != null && korting.equals("Goedkoopste broodje gratis")) {
                    try {
                        view.getTeBetalen().setText("Te betalen: ???" + facade.getPrijsBestelling(KortingStrategyEnum.KORTINGCHEAPESTSANWICH));
                    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (korting != null && korting.equals("10% korting op bestelling")) {
                    try {
                        view.getTeBetalen().setText("Te betalen: ???" + facade.getPrijsBestelling(KortingStrategyEnum.KORTINGTIENPERCENT));
                    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                deactivateAllBroodjesEnBelegen();
                view.getVerwijderBroodje().setDisable(true);
                view.getDuplicateOrderBtn().setDisable(true);
                view.getAfsluitBestelling().setDisable(true);
                view.getBetaal().setDisable(false);

            }
        });

    }

    private void betaal() {
        view.getBetaal().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getNaarKeuken().setDisable(false);
                view.getTeBetalen().setText("Te betalen ???0");
                view.getBetaal().setDisable(true);
            }
        });
    }

    private void naarKeuken() {
        view.getNaarKeuken().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                facade.setVerkochtVoorAlleBestelLijnen();
                facade.addBestellingToBestelRij(facade.getBestelling());
                facade.setBestelling(new Bestelling());

                view.getNieuwBestellingBtn().setDisable(false);
                view.getNaarKeuken().setDisable(true);
                facade.notifyObservers();
                facade.save();


            }
        });
    }

    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {
        bestellijnObservableList = FXCollections.observableList(facade.getLijstBestellijnen());
        view.getBestellijnTableView().setItems(bestellijnObservableList);
        view.getBestellijnTableView().refresh();

        view.getAantalBroodjes().setText("Aantal broodjes: " + bestellijnObservableList.size());

    }
}
