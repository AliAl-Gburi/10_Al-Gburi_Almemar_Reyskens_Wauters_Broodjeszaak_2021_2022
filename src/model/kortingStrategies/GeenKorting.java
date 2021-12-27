package model.kortingStrategies;

import model.Bestelling;

public class GeenKorting implements KortingStrategy{
    @Override
    public double prijsMetKorting(Bestelling bestelling) {
        return bestelling.berekenPrijs();
    }
}
