package model.database.loadSaveStrategies;

import java.lang.reflect.InvocationTargetException;

public class LoadSaveStrategyFactory {

    public static LoadSaveStrategy createStrategy(LoadSaveStrategyEnum strategy) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class strategyClass = Class.forName("model.database.loadSaveStrategies."+strategy.getStringvalue());
        Object loadSaveObject = strategyClass.getConstructor().newInstance();
        LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveObject;
        return loadSaveStrategy;
    }
}
