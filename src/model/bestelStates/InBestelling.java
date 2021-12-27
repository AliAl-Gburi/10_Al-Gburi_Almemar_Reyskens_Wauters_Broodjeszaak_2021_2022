package model.bestelStates;

import model.Bestellijn;
import model.Bestelling;

public class InBestelling extends BestellingState {
    private Bestelling bestelling;

    public InBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public void voegBestelLijnToe(String naamBroodje) {
        Bestellijn lijn = new Bestellijn(naamBroodje);
        bestelling.getBestellijnList().add(lijn);
    }
}
