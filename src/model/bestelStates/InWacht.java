package model.bestelStates;

import model.Bestelling;

public class InWacht extends BestellingState {
    private Bestelling bestelling;

    public InWacht(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    @Override
    public void voegBestelLijnToe(String naamBroodje) {

    }
}
