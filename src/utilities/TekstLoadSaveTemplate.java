package utilities;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public abstract class TekstLoadSaveTemplate <K,V>{

    public Map<K,V> load(File file) throws IOException {
        Map<K,V> returnMap = new TreeMap<K,V>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                String[] tokens = line.split(",");
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key,element);
                line = reader.readLine();
            }
        }
        return returnMap;
    }

    public void save(File file, Map db) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Object value: db.values()) {
            writer.write(value.toString());
            writer.newLine();
        }
    }

    public abstract V maakObject(String[] tokens);

    public abstract K getKey(String[] tokens);
}



