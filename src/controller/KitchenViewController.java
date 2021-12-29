package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.KitchenView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class KitchenViewController implements Observer {
    private KitchenView view;
    private BestelFacade facade;
    private int ordersInQueue;

    public KitchenViewController(BestelFacade facade) {
        this.facade = facade;
        facade.registerObserver(this);
    }

    public void initialize() {
        volgendeBestelling();
        disableAllButtons();
        bestellingAfgewerkt();
    }

    private void disableAllButtons() {
        //todo disable next order button if no orders in queue
        if (facade.getBestellingWachtRij().size() == 0) {
            view.getVolgendeBestelling().setDisable(true);
        }
    }

    public void setView(KitchenView view) {
        this.view = view;
    }

    public void bestellingAfgewerkt() {
        view.getBestellingAfgewerkt().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //todo removing orders from queue will happen after going to next order anyway so no need for it here
//                facade.getBestellingWachtRij().remove(0);
                view.getBestellingAfgewerkt().setDisable(true);
                view.getOrderDescription().setText("Geen bestelingen te maken");
                disableAllButtons();
            }
        });
    }


    public void volgendeBestelling() {
        view.getVolgendeBestelling().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Bestelling> bestellingenWachtrij = facade.getBestellingWachtRij();
                List<Bestellijn> bestellijnList = bestellingenWachtrij.get(0).getBestellijnList();
                String value = "Volgende bestelling: " + bestellingenWachtrij.get(0).getVolgnr() + " - Aantal broodjes: " + bestellijnList.size();

                //Todo for real!

            }
        });
    }


    //todo Observer update Will be invoked when "Naar keuken" button is pressed in the main stage
    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {
        if (facade.getEvent().equals(BestellingEvents.NAAR_KEUKEN)){
            updateQueue();
        }
    }

    private void updateQueue() {
        view.getQueue().setText("Number of orders in queue: " + facade.getBestellingWachtRij().size());
        disableAllButtons();
    }
}
