package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.KitchenView;

import java.util.List;

public class KitchenViewController implements Observer {
    private KitchenView view;
    private BestelFacade facade;

    public KitchenViewController(BestelFacade facade) {
        this.facade = facade;
        facade.registerObserver(this);
    }

    public void initialize() {
        volgendeBestelling();
        bestellingAfgewerkt();
    }

    public void setView(KitchenView view) {
        this.view = view;
    }

    public void bestellingAfgewerkt() {
        view.getBestellingAfgewerkt().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                facade.getBestellingWachtRij().remove(0);
                view.getBestellingAfgewerkt().setDisable(true);
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


            }
        });
    }

    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {

    }
}
