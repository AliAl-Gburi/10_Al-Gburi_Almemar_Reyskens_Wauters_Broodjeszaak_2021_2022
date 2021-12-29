package utilities;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import utilities.ExcelPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public abstract class ExcelLoadSaveTemplate<K,V>{

    private final ExcelPlugin excelPlugin = new ExcelPlugin();

    public final Map<K,V> load(File file) throws BiffException, IOException {
        ArrayList<ArrayList<String>> arrayLists = excelPlugin.read(file);
        Map<K,V> resMap = new TreeMap<K,V>();
        for (ArrayList<String> row: arrayLists) {
            String[] tokens = row.toArray(new String[0]);
            V element = maakObject(tokens);
            K key = getKey(tokens);
            resMap.put(key, element);
        }
        return resMap;
    }

    public void save(File file, Map<K,V> db) {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (K key: db.keySet()) {
            String dataset = db.get(key).toString();
            String[] list = dataset.split(",");
            ArrayList<String> arraylist = new ArrayList<>(Arrays.asList(list));
            res.add(arraylist);
        }try {
            excelPlugin.write(file, res);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract V maakObject(String[] tokens);

    public abstract K getKey(String[] tokens);

}
