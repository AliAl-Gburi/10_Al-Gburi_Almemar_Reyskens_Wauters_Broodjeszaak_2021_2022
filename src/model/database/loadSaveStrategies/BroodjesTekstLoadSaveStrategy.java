package model.database.loadSaveStrategies;

import model.Broodje;
import utilities.TekstLoadSaveTemplate;

public class BroodjesTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy {



    @Override
    public Broodje maakObject(String[] tokens) {
        Broodje broodje = new Broodje(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return broodje;
    }

    @Override
    public String getKey(String[] tokens) {
        return tokens[0];
    }
}
