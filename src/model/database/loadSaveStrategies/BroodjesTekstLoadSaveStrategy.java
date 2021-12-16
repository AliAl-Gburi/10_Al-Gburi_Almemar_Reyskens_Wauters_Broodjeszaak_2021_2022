package model.database.loadSaveStrategies;

import model.Broodjes;
import utilities.TekstLoadSaveTemplate;

public class BroodjesTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy {



    @Override
    public Broodjes maakObject(String[] tokens) {
        Broodjes broodjes = new Broodjes(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return broodjes;
    }

    @Override
    public String getKey(String[] tokens) {
        return tokens[0];
    }
}
