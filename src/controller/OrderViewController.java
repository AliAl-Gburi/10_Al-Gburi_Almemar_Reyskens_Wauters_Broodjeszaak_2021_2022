package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.BestelFacade;
import model.Bestellijn;
import model.Bestelling;
import model.Observer;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.OrderView;

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
        broodjeListener();
        belegListener();
        voegZelfdeBestelling();
        verwijderBroodje();
        annuleerBestelling();
    }

    public void nieuweBestelling() {
        view.getNieuwBestellingBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getNieuwBestellingBtn().setDisable(true);
                facade.nieuwBestelling();
                voorraadChecker();

            }
        });
    }

    public void deactivateAllBroodjesEnBelegen() {
        for (String broodje: view.getBroojebuttons().keySet()) {
            view.getBroojebuttons().get(broodje).setDisable(true);
        }
        for (String beleg: view.getBelegbuttons().keySet()) {
            view.getBelegbuttons().get(beleg).setDisable(true);
        }
    }

    public void voorraadChecker() {
        for (String broodje: view.getBroojebuttons().keySet()) {
            view.getBroojebuttons().get(broodje).setDisable(!availableBroodjeVoorraad(broodje));
        }

        for (String beleg: view.getBelegbuttons().keySet()) {
            view.getBelegbuttons().get(beleg).setDisable(!availableBelegVoorraad(beleg));
        }
    }

    public void broodjeListener() {
        for (String broodje: view.getBroojebuttons().keySet()) {
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
        for(String beleg: view.getBelegbuttons().keySet()) {
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
                if(facade.getLijstBestellijnen().size() < 1) {
                    a.setContentText("Er moet minimaal 1 broodje zijn voordat je het kunt dupliceren");
                    a.show();
                } else if(!dupeBestelling()) {
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
                if(facade.getLijstBestellijnen().size() < 1) {
                    a.setContentText("Er zijn geen broodjes in de bestelling");
                    a.show();
                } else {
                    facade.verwijderLaatsteToegevoegdBroodje();
                }
            }
        });
    }

    public void annuleerBestelling() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        view.getAnnuleerBestelling().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!view.getNieuwBestellingBtn().isDisabled()) {
                    a.setContentText("Je moet een bestelling hebben voordat je deze kunt annuleren");
                    a.show();
                } else {
                    facade.annuleerBestelling();
                    view.getNieuwBestellingBtn().setDisable(false);
                    deactivateAllBroodjesEnBelegen();
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

    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {
            bestellijnObservableList = FXCollections.observableList(facade.getLijstBestellijnen());
            view.getBestellijnTableView().setItems(bestellijnObservableList);
            view.getBestellijnTableView().refresh();

            view.getAantalBroodjes().setText("Aantal broodjes: " + bestellijnObservableList.size());

    }
}
