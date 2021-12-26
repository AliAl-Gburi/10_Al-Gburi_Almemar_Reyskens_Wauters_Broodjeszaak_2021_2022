package model;

import controller.AdminController;
import controller.OrderViewController;
import jxl.read.biff.BiffException;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BestelFacade implements Subject{
    private Bestelling bestelling;
    private final List<Observer> observers;
    private BroodjesDatabase broodjesDatabase;
    private BelegDatabase belegDatabase;
    private BestellingEvents event;


    public BestelFacade() throws BiffException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        loadDatabase();
        observers = new ArrayList<>();
        event = BestellingEvents.LOAD_DATABASE;
        nieuwBestelling();
    }

    public void loadDatabase() {
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/settings.properties");
            properties.load(is);
            Object format = properties.getProperty("databaseFormat");
            String dbFormat = (String) format;
            if(dbFormat.equals("text")) {
                File broodjesfile = new File("src/bestanden/broodjes.txt");
                File belegfile = new File("src/bestanden/beleg.txt");
                broodjesDatabase = BroodjesDatabase.getInstance(broodjesfile, LoadSaveStrategyEnum.BROODJESTEKSTLOADSAVESTRATEGY);
                belegDatabase = BelegDatabase.getInstance(belegfile, LoadSaveStrategyEnum.BELEGTEKSTLOADSAVESTRATEGY);
            } else if(dbFormat.equals("excel")) {
                File broodjesfile = new File("src/bestanden/broodjes.xls");
                File belegfile = new File("src/bestanden/beleg.xls");
                broodjesDatabase = BroodjesDatabase.getInstance(broodjesfile, LoadSaveStrategyEnum.BROODJESEXCELLOADSAVESTRATEGY);
                belegDatabase = BelegDatabase.getInstance(belegfile, LoadSaveStrategyEnum.BELEGEXCELLOADSAVESTRATEGY);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void nieuwBestelling() {
        bestelling = new Bestelling();
    }
    public boolean enoughtoDupe() {
        boolean res = true;
        Bestellijn lastBestelling = bestelling.getLastToegevoegdBestelling();
        if(broodjesDatabase.getVoorraadLijstBroodjes().get(lastBestelling.getNaamBroodje()) < 1) res = false;
        String[] belegLijst = lastBestelling.getBelegsoorten().split(",");
        Map<String, Integer> itemsinOrder = new TreeMap<>();
        for(int i = 0; i < belegLijst.length; i++) {
            if(!itemsinOrder.containsKey(belegLijst[i].trim())){
                itemsinOrder.put(belegLijst[i].trim(), 1);
            } else {
                int xd = itemsinOrder.get(belegLijst[i].trim());
                xd++;
                itemsinOrder.put(belegLijst[i].trim() ,xd);
            }
        }

        for (String beleg: itemsinOrder.keySet()) {
            if(belegDatabase.getVoorraadLijstBelegen().get(beleg) < itemsinOrder.get(beleg)) res = false;
        }
        if(res) {
            voegBestellijnToe(lastBestelling.getNaamBroodje());
            for(int i = 0; i < belegLijst.length; i++) {
                voegBelegenToe(belegLijst[i].trim());
            }
        }


        return res;

    }

    public void verwijderLaatsteToegevoegdBroodje() {
        Bestellijn lastBestelling = bestelling.getLastToegevoegdBestelling();
        broodjesDatabase.getBroodje(lastBestelling.getNaamBroodje()).setVoorraad(broodjesDatabase.getBroodje(lastBestelling.getNaamBroodje()).getVoorraad() + 1);

        String[] belegLijst = lastBestelling.getBelegsoorten().split(",");
        Map<String, Integer> itemsinOrder = new TreeMap<>();
        for(int i = 0; i < belegLijst.length; i++) {
            if(!itemsinOrder.containsKey(belegLijst[i].trim())){
                itemsinOrder.put(belegLijst[i].trim(), 1);
            } else {
                int xd = itemsinOrder.get(belegLijst[i].trim());
                xd++;
                itemsinOrder.put(belegLijst[i].trim() ,xd);
            }
        }
        for(String beleg: itemsinOrder.keySet()) {
            belegDatabase.getBeleg(beleg).setVoorraad(itemsinOrder.get(beleg) + belegDatabase.getBeleg(beleg).getVoorraad());
        }
        bestelling.getBestellijnList().remove(bestelling.getBestellijnList().size() - 1);
        notifyObservers();

    }

    public void annuleerBestelling() {
        while (bestelling.getBestellijnList().size() > 0) {
            verwijderLaatsteToegevoegdBroodje();
        }
    }

    public void voegBestellijnToe(String naamBroodje) {
        bestelling.voegBestelLijnToe(naamBroodje);
        broodjesDatabase.getBroodje(naamBroodje).aanpassenVoorraad();
        event = BestellingEvents.TOEVOEGEN_BROODJE;
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
    public List<Broodje> getBroodjesList() {
        return broodjesDatabase.getBroodjesList();
    }
    public List<BelegSoort> getBelegenList() {
        return belegDatabase.getBelegen();
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
            switch (event) {
                case TOEVOEGEN_BROODJE:
                    if(o instanceof AdminController || o instanceof OrderViewController){
                        o.update(broodjesDatabase, belegDatabase);
                    }
                    break;
                case LOAD_DATABASE:
                    if (o instanceof AdminController) {
                        o.update(broodjesDatabase, belegDatabase);
                    }
                    break;
            }


        }
    }
}
