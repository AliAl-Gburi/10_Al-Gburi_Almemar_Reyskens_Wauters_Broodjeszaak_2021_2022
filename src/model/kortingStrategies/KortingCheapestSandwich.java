package model.kortingStrategies;

import model.Bestelling;

public class KortingCheapestSandwich implements KortingStrategy {
    @Override
    public double prijsMetKorting(Bestelling bestelling) {
        return bestelling.berekenPrijs() - bestelling.getGoedkoopsteBroodje().getPrijs();
    }
}
