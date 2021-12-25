package model;

import java.util.ArrayList;
import java.util.List;

public class Bestelling {
    private List<Bestellijn> bestellijnList;

    public Bestelling() {
        bestellijnList = new ArrayList<>();
    }

    public void voegBestelLijnToe(String naamBroodje) {
        Bestellijn lijn = new Bestellijn(naamBroodje);
        bestellijnList.add(lijn);
    }
    public List<Bestellijn> getBestellijnList() {
        return this.bestellijnList;
    }
    public void voegBelegToe(String beleg) {
            getLastToegevoegdBestelling().voegBelegToe(beleg);
    }

    public Bestellijn getLastToegevoegdBestelling() {
        return bestellijnList.get(bestellijnList.size() - 1);
    }
}
