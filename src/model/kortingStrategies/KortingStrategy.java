package model.kortingStrategies;

import model.Bestelling;

public interface KortingStrategy {

    double prijsMetKorting(Bestelling bestelling);
}
