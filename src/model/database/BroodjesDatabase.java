package model.database;

import jxl.read.biff.BiffException;
import model.Broodje;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BroodjesDatabase{
    private Map<String, Broodje> broodjesMap;
    private List<Broodje> broodjeList;
    private static BroodjesDatabase database;
    private LoadSaveStrategy strategy;

    private BroodjesDatabase(File file, LoadSaveStrategyEnum loadSaveStrategyEnum) throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        strategy = LoadSaveStrategyFactory.createStrategy(loadSaveStrategyEnum);
        broodjesMap = strategy.load(file);
        broodjeList = new ArrayList<Broodje>(broodjesMap.values());
    }

    public List<Broodje> getBroodjesList() {
        return this.broodjeList;
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

    public Broodje getBroodje(String naamBroodje) {
        return broodjesMap.get(naamBroodje);
    }

    public Map<String, Integer> getVoorraadLijstBroodjes() {
        Map<String, Integer> res = new TreeMap<>();
        for (Broodje broodje: broodjeList) {
            res.put(broodje.getBroodjesnaam(), broodje.getVoorraad());
        }
        return res;
    }







}
