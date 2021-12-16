package model.database.loadSaveStrategies;

import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface LoadSaveStrategy<K,V> {
    Map<K,V> load(File file) throws BiffException, IOException;
    void save(File file, Map<K,V> db) throws IOException;
}
