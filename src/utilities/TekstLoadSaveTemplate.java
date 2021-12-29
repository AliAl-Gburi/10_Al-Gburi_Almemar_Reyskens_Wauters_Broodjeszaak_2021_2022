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

    public void save(File file, Map<K,V> db) {
        try {
            FileWriter myWriter = new FileWriter(file);
            for (V value: db.values()) {
                myWriter.write(value.toString());
                myWriter.write("\n");
            }
            myWriter.close();

        } catch (IOException ignored) {

        }

    }

    public abstract V maakObject(String[] tokens);

    public abstract K getKey(String[] tokens);
}



