package ultis.DBHelper.convert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Convert {
    public static HashMap<String, String> convertKeyValueToColumnAndValues(HashMap<String, Object> keysValues) {
        StringBuilder keysString = new StringBuilder();
        StringBuilder valuesString = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Object> entry : keysValues.entrySet()) {
            if (count > 0) {
                keysString.append(", ");
                valuesString.append(", ");
            }
            keysString.append(entry.getKey());
            if (entry.getValue() instanceof String) {
                valuesString.append("'").append(entry.getValue()).append("'");
            } else {
                valuesString.append(entry.getValue());
            }
            count++;
        }
        return new HashMap<String, String>() {{
            put("columns", String.valueOf(keysString));
            put("values", String.valueOf(valuesString));
        }};
    }

    public static ArrayList<String> keysValuesJoin(HashMap<String, Object> keysValues, String delimiter) {
        ArrayList<String> storeJoin = new ArrayList<>();

        for (Map.Entry<String, Object> entry : keysValues.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            ArrayList<String> helpJoin = new ArrayList<>();
            helpJoin.add(String.valueOf(key));
            helpJoin.add(delimiter);
            Object val = value instanceof String ? String.format("'%s'", value) : value;
            helpJoin.add(String.valueOf(val));
            String afterJoin = String.join(" ", helpJoin);
            storeJoin.add(afterJoin);
        }
        return storeJoin;
    }

    public static HashMap<String, Object> convertEntityToHashMap(Object obj, ArrayList<String> fillable) {
        HashMap<String, Object> hashMap = new HashMap<>();

        // Get all fields of the object
        Field[] fields = obj.getClass().getDeclaredFields();

        // Iterate over the fields
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                // Get the value of the field
                Object value = field.get(obj);
                if (fillable.contains(field.getName())) {
                    // Put the field name and value into the HashMap
                    hashMap.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return hashMap;
    }
}
