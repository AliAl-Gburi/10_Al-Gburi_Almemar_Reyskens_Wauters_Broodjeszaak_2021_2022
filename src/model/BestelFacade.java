package model;

import jxl.read.biff.BiffException;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BestelFacade implements Subject{
    private Bestelling bestelling;
    private final List<Observer> observers;
    private BroodjesDatabase broodjesDatabase;
    private BelegDatabase belegDatabase;


    public BestelFacade() throws BiffException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File broodjesfile = new File("src/bestanden/broodjes.xls");
        File belegfile = new File("src/bestanden/beleg.xls");
        broodjesDatabase = BroodjesDatabase.getInstance(broodjesfile, LoadSaveStrategyEnum.BROODJESEXCELLOADSAVESTRATEGY);
        belegDatabase = BelegDatabase.getInstance(belegfile, LoadSaveStrategyEnum.BELEGEXCELLOADSAVESTRATEGY);
        observers = new ArrayList<>();
        nieuwBestelling();
    }

    public void nieuwBestelling() {
        bestelling = new Bestelling();
    }

    public void voegBestellijnToe(String naamBroodje) {
        bestelling.voegBestelLijnToe(naamBroodje);
        broodjesDatabase.getBroodje(naamBroodje).aanpassenVoorraad();
        notifyObservers();
    }

    public void voegBelegenToe(String beleg) {
        bestelling.voegBelegToe(beleg);
        belegDatabase.getBeleg(beleg).aanpassenVoorraad();
        notifyObservers();
    }

    public List<Bestellijn> getLijstBestellijnen() {
        return bestelling.getBestellijnList();
    }

    public Map<String, Integer> getVoorraadLijstBroodjes() {
        return BroodjesDatabase.getBroodjesDatabase().getVoorraadLijstBroodjes();
    }

    public Map<String, Integer> getVoorraadLijstBelegen() {
        return BelegDatabase.getDatabase().getVoorraadLijstBelegen();
    }


    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o: observers
             ) {
            o.update(broodjesDatabase, belegDatabase);
        }
    }
}
