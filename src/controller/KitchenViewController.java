package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import model.Observer;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import view.KitchenView;

import java.util.*;

public class KitchenViewController implements Observer {
    private KitchenView view;
    private BestelFacade facade;

    public KitchenViewController(BestelFacade facade) {
        this.facade = facade;
        facade.registerObserver(this);

    }

    public void initialize() {
        volgendeBestelling();
        disableKnopen();
        bestellingAfgewerkt();
    }

    public void setView(KitchenView view) {
        this.view = view;
    }

    private void updateWachtRij() {
        view.getWachtRij().setText("Aantal Bestellingen in de wachtrij: " + facade.getBestellingWachtRij().size());
        disableKnopen();
    }
    private void disableKnopen() {

            view.getVolgendeBestelling().setDisable(facade.getBestellingWachtRij().size() < 1);

    }

    private void bestellingAfgewerkt() {
        view.getBestellingAfgewerkt().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getBestellingBeschrijving().setText("Er is momenteel geen bestelling in het keuken\n\n\n");
                disableKnopen();
                view.getBestellingAfgewerkt().setDisable(true);
            }
        });
    }

    private void volgendeBestelling() {
        view.getVolgendeBestelling().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Bestellijn> bestellijnList = facade.getBestellingWachtRij().get(0).getBestellijnList();
                String bestellingBeschrijving = "Volgnummer bestelling: " + facade.getBestellingWachtRij().get(0).getVolgnr()+ " - Aantal broodjes: " + bestellijnList.size() + "\n";

                LinkedHashMap<Bestellijn, Integer> bestellijnen = new LinkedHashMap<>();

                List<Integer> foundIndexes = new ArrayList<>();

                for(int i = 0; i < bestellijnList.size(); i++) {
                    int aantal = 1;
                    boolean put = false;
                    for(int j = i; j < bestellijnList.size(); j++) {
                        if(bestellijnList.get(i).toString().equals(bestellijnList.get(j).toString()) && i != j && !foundIndexes.contains(i))  {
                            aantal++;
                            foundIndexes.add(j);
                            bestellijnen.put(bestellijnList.get(i), aantal);
                            put = true;
                        }

                    }
                    if(!put && !foundIndexes.contains(i)) {
                        bestellijnen.put(bestellijnList.get(i), 1);
                    }

                }


                System.out.println(bestellijnen);


                for (Bestellijn lijn: bestellijnen.keySet()) {
                    if(lijn.getBelegsoorten() != null && !lijn.getBelegsoorten().equals("")){
                    String[] belegLijst = lijn.getBelegsoorten().split(",");
                    LinkedHashMap<String, Integer> itemsinOrder = new LinkedHashMap<>();
                    for (int i = 0; i < belegLijst.length; i++) {
                        if (!itemsinOrder.containsKey(belegLijst[i].trim())) {
                            itemsinOrder.put(belegLijst[i].trim(), 1);
                        } else {
                            int xd = itemsinOrder.get(belegLijst[i].trim());
                            xd++;
                            itemsinOrder.put(belegLijst[i].trim(), xd);
                        }
                    }
                    bestellingBeschrijving += "\n" + bestellijnen.get(lijn) + " x " + lijn.getNaamBroodje() + ": ";
                    List<String> keys = new ArrayList<>(itemsinOrder.keySet());
                    for (String key: keys) {
                        if(keys.indexOf(key) != keys.size() - 1) {
                            if(itemsinOrder.get(key) == 1) {
                                bestellingBeschrijving += key + ", ";
                            } else {
                                bestellingBeschrijving += itemsinOrder.get(key) + " x " + key +", ";
                            }
                        } else {
                            if(itemsinOrder.get(key) == 1) {
                                bestellingBeschrijving += key;
                            } else {
                                bestellingBeschrijving += itemsinOrder.get(key) + " x " + key;
                            }
                        }





                    }
                    } else {
                        bestellingBeschrijving += "\n" + bestellijnen.get(lijn) + " x " + lijn.getNaamBroodje() + ": ";
                    }
                }

                view.getBestellingBeschrijving().setText(bestellingBeschrijving);
                facade.getBestellingWachtRij().remove(0);
                updateWachtRij();
                view.getVolgendeBestelling().setDisable(true);
                view.getBestellingAfgewerkt().setDisable(false);
            }
        });
    }

    @Override
    public void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase) {
        if(facade.isEvent(BestellingEvents.NAAR_KEUKEN)) {
            updateWachtRij();
        }

    }
}
