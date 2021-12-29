package model;

import model.bestelStates.BestellingState;
import model.bestelStates.InBestelling;
import model.bestelStates.InWacht;

import java.util.ArrayList;
import java.util.List;

public class Bestelling {
    private List<Bestellijn> bestellijnList;
    private int volgnr;
    private BestellingState inWacht;
    private BestellingState inBestelling;

    private BestellingState state;

    public Bestelling() {
        inWacht = new InWacht(this);
        inBestelling = new InBestelling(this);
        state = inWacht;
        bestellijnList = new ArrayList<>();
    }

    public void setVolgnr(int volgnr) {
        this.volgnr = volgnr;
    }

    public int getVolgnr() {
        return this.volgnr;
    }

    public void setState(BestellingState state) {
        this.state = state;
    }

    public BestellingState getInBestelling() {
        return this.inBestelling;
    }

    public BestellingState getInWacht() {
        return this.inWacht;
    }

    public BestellingState getState() {
        return this.state;
    }

    public void voegBestelLijnToe(String naamBroodje) {
        state.voegBestelLijnToe(naamBroodje);
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
        return Math.round(prijs * 100.0) / 100.0;
    }

    public Bestellijn getGoedkoopsteBroodje() {
        Bestellijn goedkoopsteBroodje = bestellijnList.get(0);
        for (Bestellijn bestellijn : bestellijnList ) {
            if ((bestellijn.getBelegsoorten() != null && !bestellijn.getBelegsoorten().isEmpty()) && bestellijn.getPrijs() < goedkoopsteBroodje.getPrijs()) {
                goedkoopsteBroodje = bestellijn;
            }
        } return goedkoopsteBroodje;
    }


}
