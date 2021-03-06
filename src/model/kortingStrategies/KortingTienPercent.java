package model.kortingStrategies;

import model.Bestelling;

public class KortingTienPercent implements KortingStrategy {
    @Override
    public double prijsMetKorting(Bestelling bestelling) {
        return bestelling.berekenPrijs()*0.9;
    }
}
