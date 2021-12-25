package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BelegSoort;
import model.BestelFacade;
import model.Broodje;
import model.Observer;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.AdminView;

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
