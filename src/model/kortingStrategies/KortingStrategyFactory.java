package model.kortingStrategies;

import model.database.loadSaveStrategies.LoadSaveStrategy;

import java.lang.reflect.InvocationTargetException;

public class KortingStrategyFactory {
        public static KortingStrategy createStrategy(KortingStrategyEnum strategy) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Class strategyClass = Class.forName("model.kortingStrategies."+strategy.getStringvalue());
            Object kortingObject = strategyClass.getConstructor().newInstance();
            KortingStrategy kortingStrategy = (KortingStrategy) kortingObject;
            return kortingStrategy;
        }
}
