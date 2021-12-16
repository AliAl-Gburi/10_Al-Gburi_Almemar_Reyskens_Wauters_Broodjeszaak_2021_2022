package model.database.loadSaveStrategies;

import model.BelegSoort;
import utilities.TekstLoadSaveTemplate;

public class BelegTekstLoadSaveStrategy extends TekstLoadSaveTemplate {


    @Override
    public BelegSoort maakObject(String[] tokens) {
        BelegSoort beleg = new BelegSoort(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return beleg;
    }

    @Override
    public String getKey(String[] tokens) {
        return tokens[0];
    }
}
