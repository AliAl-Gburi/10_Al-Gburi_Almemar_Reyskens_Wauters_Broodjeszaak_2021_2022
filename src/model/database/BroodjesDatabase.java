package model.database;

import jxl.read.biff.BiffException;
import model.Broodjes;
import model.database.loadSaveStrategies.BroodjesTekstLoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BroodjesDatabase{
    private Map<String, Broodjes> broodjesMap;
    private List<Broodjes> broodjesList;
    private static BroodjesDatabase database;
    private LoadSaveStrategy strategy;

    private BroodjesDatabase(File file, LoadSaveStrategyEnum loadSaveStrategyEnum) throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        strategy = LoadSaveStrategyFactory.createStrategy(loadSaveStrategyEnum);
        broodjesMap = strategy.load(file);
        broodjesList = new ArrayList<Broodjes>(broodjesMap.values());
    }

    public List<Broodjes> getBroodjesList() {
        return this.broodjesList;
    }

    public static BroodjesDatabase getInstance(File file, LoadSaveStrategyEnum loadSaveStrategyEnum) throws BiffException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(database == null) {
            database = new BroodjesDatabase(file, loadSaveStrategyEnum);
        }
        return database;
    }

    public static BroodjesDatabase getBroodjesDatabase() {
        return database;
    }






}
