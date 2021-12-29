package model;

import controller.AdminController;
import controller.OrderViewController;
import jxl.read.biff.BiffException;
import model.bestelStates.BestellingState;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;
import model.kortingStrategies.KortingStrategy;
import model.kortingStrategies.KortingStrategyEnum;
import model.kortingStrategies.KortingStrategyFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BestelFacade implements Subject {
    private Bestelling bestelling;
    private List<Bestelling> bestellingWachtRij;
    private final List<Observer> observers;
    private BroodjesDatabase broodjesDatabase;
    private BelegDatabase belegDatabase;
    private BestellingEvents event;
    private String defaultKorting;
    private int volgnr;
    private File broodjesFile;
    private File belegenFile;


    public BestelFacade() {

        loadDatabase();
        observers = new ArrayList<>();
        bestellingWachtRij = new ArrayList<>();
        event = BestellingEvents.LOAD_DATABASE;
        nieuwBestelling();
        volgnr = 0;

    }

    public void loadDatabase() {
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/settings.properties");
            properties.load(is);
            Object format = properties.getProperty("databaseFormat");
            String dbFormat = (String) format;
            if (dbFormat.equals("text")) {
                broodjesFile = new File("src/bestanden/broodjes.txt");
                belegenFile = new File("src/bestanden/beleg.txt");
                broodjesDatabase = BroodjesDatabase.getInstance(broodjesFile, LoadSaveStrategyEnum.BROODJESTEKSTLOADSAVESTRATEGY);
                belegDatabase = BelegDatabase.getInstance(belegenFile, LoadSaveStrategyEnum.BELEGTEKSTLOADSAVESTRATEGY);
            } else if (dbFormat.equals("excel")) {
                broodjesFile = new File("src/bestanden/broodjes.xls");
                belegenFile = new File("src/bestanden/beleg.xls");
                broodjesDatabase = BroodjesDatabase.getInstance(broodjesFile, LoadSaveStrategyEnum.BROODJESEXCELLOADSAVESTRATEGY);
                belegDatabase = BelegDatabase.getInstance(belegenFile, LoadSaveStrategyEnum.BELEGEXCELLOADSAVESTRATEGY);
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

    public void save() {
        try {

            broodjesDatabase.save(broodjesFile, broodjesDatabase.broodjesMap);
            belegDatabase.save(belegenFile, belegDatabase.belegMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void nieuwBestelling() {
        bestelling = new Bestelling();
        volgnr++;
        bestelling.setVolgnr(volgnr);
    }

    public int getVolgnr() {
        return this.volgnr;
    }
    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public void setBestellingState(BestellingState state) {
        bestelling.setState(state);
    }

    public BestellingState getInWachtState(){
        return bestelling.getInWacht();
    }
    public BestellingState getInBestellingState(){
        return bestelling.getInBestelling();
    }



    public boolean enoughtoDupe() {
        boolean res = true;
        Bestellijn lastBestelling = bestelling.getLastToegevoegdBestelling();
        if (broodjesDatabase.getVoorraadLijstBroodjes().get(lastBestelling.getNaamBroodje()) < 1) res = false;
        String[] belegLijst = lastBestelling.getBelegsoorten().split(",");
        Map<String, Integer> itemsinOrder = new TreeMap<>();
        for (int i = 0; i < belegLijst.length; i++) {
            if (!itemsinOrder.containsKey(belegLijst[i].trim())) {
                itemsinOrder.put(belegLijst[i].trim(), 1);
            } else {
                int xd = itemsinOrder.get(belegLijst[i].trim());
                xd++;
                itemsinOrder.put(belegLijst[i].trim(), xd);
            }
        }

        for (String beleg : itemsinOrder.keySet()) {
            if (belegDatabase.getVoorraadLijstBelegen().get(beleg) < itemsinOrder.get(beleg)) res = false;
        }
        if (res) {
            voegBestellijnToe(lastBestelling.getNaamBroodje());
            for (int i = 0; i < belegLijst.length; i++) {
                voegBelegenToe(belegLijst[i].trim());
            }
        }


        return res;

    }
    public void setVerkochtVoorAlleBestelLijnen() {
        for (Bestellijn lijn: bestelling.getBestellijnList()) {
            broodjesDatabase.getBroodje(lijn.getNaamBroodje()).setVerkocht(broodjesDatabase.getBroodje(lijn.getNaamBroodje()).getVerkocht()+1);
            if(lijn.getBelegsoorten() != null && !lijn.getBelegsoorten().equals("")) {


            String[] belegLijst = lijn.getBelegsoorten().split(",");
            Map<String, Integer> itemsinOrder = new TreeMap<>();
            for (int i = 0; i < belegLijst.length; i++) {
                if (!itemsinOrder.containsKey(belegLijst[i].trim())) {
                    itemsinOrder.put(belegLijst[i].trim(), 1);
                } else {
                    int xd = itemsinOrder.get(belegLijst[i].trim());
                    xd++;
                    itemsinOrder.put(belegLijst[i].trim(), xd);
                }
            }
            for (String beleg: itemsinOrder.keySet()) {
                belegDatabase.getBeleg(beleg).setVerkocht(belegDatabase.getBeleg(beleg).getVerkocht() + itemsinOrder.get(beleg));
            }

            }



        }

        event = BestellingEvents.NAAR_KEUKEN;

    }

    public void verwijderLaatsteToegevoegdBroodje() {
        Bestellijn lastBestelling = bestelling.getLastToegevoegdBestelling();
        broodjesDatabase.getBroodje(lastBestelling.getNaamBroodje()).setVoorraad(broodjesDatabase.getBroodje(lastBestelling.getNaamBroodje()).getVoorraad() + 1);

        if(lastBestelling.getBelegsoorten() != null && !lastBestelling.getBelegsoorten().equals("")){
            String[] belegLijst = lastBestelling.getBelegsoorten().split(",");
            Map<String, Integer> itemsinOrder = new TreeMap<>();
            for (int i = 0; i < belegLijst.length; i++) {
                if (!itemsinOrder.containsKey(belegLijst[i].trim())) {
                    itemsinOrder.put(belegLijst[i].trim(), 1);
                } else {
                    int xd = itemsinOrder.get(belegLijst[i].trim());
                    xd++;
                    itemsinOrder.put(belegLijst[i].trim(), xd);
                }
            }
            for (String beleg : itemsinOrder.keySet()) {
                belegDatabase.getBeleg(beleg).setVoorraad(itemsinOrder.get(beleg) + belegDatabase.getBeleg(beleg).getVoorraad());
            }
        }

        bestelling.getBestellijnList().remove(bestelling.getBestellijnList().size() - 1);
        notifyObservers();

    }

    public void annuleerBestelling() {
        while (bestelling.getBestellijnList().size() > 0) {
            verwijderLaatsteToegevoegdBroodje();
        }
        volgnr--;
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

    public boolean isEvent(BestellingEvents event) {
        return this.event == event;
    }

    public BestellingEvents getEvent() {
        return this.event;
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

    public Map<String, Integer> getVerkochtLijstBroodjes() {
        return BroodjesDatabase.getBroodjesDatabase().getVerkochtLijstBroodjes();
    }

    public Map<String, Integer> getVerkochtLijstBelegen() {
        return BelegDatabase.getDatabase().getVerkochtLijstBelegen();
    }

    public void berekenPrijsBestellijn(Bestellijn bestellijn) {
        double prijs = 0;
        double prijsBroodje = broodjesDatabase.getBroodje(bestellijn.getNaamBroodje()).getVerkoopprijs();
        double prijsBeleg = 0;
        if (bestellijn.getBelegsoorten() != null && !bestellijn.getBelegsoorten().isEmpty()) {
            for (String belegsoort : bestellijn.getBelegSoortenList()) {
                prijsBeleg += belegDatabase.getBeleg(belegsoort.trim()).getVerkoopprijs();
            }
        }
        prijs += prijsBroodje + prijsBeleg;

        bestellijn.setPrijs(prijs);
    }

    public double getPrijsBestelling(KortingStrategyEnum kortingStrategyEnum) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Bestellijn bestellijn : bestelling.getBestellijnList()) {
            berekenPrijsBestellijn(bestellijn);
        }
        //double prijs = bestelling.berekenPrijs();
        KortingStrategy kortingStrategy = KortingStrategyFactory.createStrategy(kortingStrategyEnum);
        return kortingStrategy.prijsMetKorting(bestelling);
    }

    public void addBestellingToBestelRij(Bestelling bestelling) {
        bestellingWachtRij.add(bestelling);
    }

    public Bestelling getBestelling() {
        return this.bestelling;
    }
    public List<Bestelling> getBestellingWachtRij() {
        return this.bestellingWachtRij;
    }



    public void setDefaultKorting(String defaultKorting) {
        this.defaultKorting = defaultKorting;
    }
    public String getDefaultKorting() {
        return this.defaultKorting;
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
        for (Observer o : observers
        ) {
            switch (event) {
                case TOEVOEGEN_BROODJE:
                case LOAD_DATABASE:
                    if (o instanceof AdminController || o instanceof OrderViewController) {
                        o.update(broodjesDatabase, belegDatabase);
                    }
                    break;
                case NAAR_KEUKEN:
                        o.update(broodjesDatabase, belegDatabase);
                    break;
            }


        }
    }
}
