package model.database;

import jxl.read.biff.BiffException;
import model.BelegSoort;
import model.database.loadSaveStrategies.BelegTekstLoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BelegDatabase{

    private Map<String, BelegSoort> belegMap;
    private List<BelegSoort> belegen;
    private LoadSaveStrategy strategy;
    private static BelegDatabase database;

    private BelegDatabase(File file, LoadSaveStrategyEnum loadSaveStrategyEnum) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, BiffException {
        strategy = LoadSaveStrategyFactory.createStrategy(loadSaveStrategyEnum);
        belegMap = strategy.load(file);
        belegen = new ArrayList<BelegSoort>(belegMap.values());
    }

    public List<BelegSoort> getBelegen() {
        return this.belegen;
    }

    public static BelegDatabase getInstance(File file, LoadSaveStrategyEnum loadSaveStrategyEnum) throws BiffException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(database == null) {
            database = new BelegDatabase(file, loadSaveStrategyEnum);
        }
        return database;
    }

    public static BelegDatabase getDatabase() {
        return database;
    }


}
