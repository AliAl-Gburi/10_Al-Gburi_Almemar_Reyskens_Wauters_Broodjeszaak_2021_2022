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

    public double berekenPrijs() {
        double prijs = 0;

        for (Bestellijn bestellijn : bestellijnList ) {
            prijs+= bestellijn.getPrijs();
        }
        return Math.round(prijs);
    }

    public Bestellijn getGoedkoopsteBroodje() {
        Bestellijn goedkoopsteBroodje = bestellijnList.get(0);
        for (Bestellijn bestellijn : bestellijnList ) {
            if (bestellijn.getBelegsoorten() != null && !bestellijn.getBelegsoorten().isEmpty() && bestellijn.getPrijs() < goedkoopsteBroodje.getPrijs()) {
                goedkoopsteBroodje = bestellijn;
            }
        } return goedkoopsteBroodje;
    }


}
