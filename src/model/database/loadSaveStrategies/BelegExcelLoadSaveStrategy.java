package model.database.loadSaveStrategies;

import model.BelegSoort;
import utilities.ExcelLoadSaveTemplate;

public class BelegExcelLoadSaveStrategy extends ExcelLoadSaveTemplate implements LoadSaveStrategy {
    @Override
    public BelegSoort maakObject(String[] tokens) {
        BelegSoort belegSoort = new BelegSoort(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return belegSoort;
    }

    @Override
    public String getKey(String[] tokens) {
        return tokens[0];
    }
}
