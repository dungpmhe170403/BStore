package ultis;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class Helper {

    public static String getValueFromAppProperties(String property) {
        Properties properties = new Properties();
        try ( InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream("config/app.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(e);
        }
        return properties.getProperty(property);
    }

    public static HashMap<String, Object> convertToHashMap(Object obj, String... fillable) {
        HashMap<String, Object> hashMap = new HashMap<>();

        // Get all fields of the object
        Field[] fields = obj.getClass().getDeclaredFields();

        // Iterate over the fields
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                // Get the value of the field
                Object value = field.get(obj);
                if (Arrays.asList(fillable).contains(field.getName())) {
                    // Put the field name and value into the HashMap
                    hashMap.put(field.getName(), value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return hashMap;
    }

    public static HashMap<String, Object> convertToHashMap(Object obj, ArrayList<String> fillable) {
        HashMap<String, Object> hashMap = new HashMap<>();

        // Get all fields of the object
        Field[] fields = obj.getClass().getDeclaredFields();

        // Iterate over the fields
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                // Get the value of the field
                Object value = field.get(obj);
                if (Arrays.asList(fillable).contains(field.getName())) {
                    // Put the field name and value into the HashMap
                    hashMap.put(field.getName(), value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return hashMap;
    }

    public static boolean isEmptyParamsInRequest(HttpServletRequest request, ArrayList<String> parameterNames) {
        System.out.println(parameterNames);
        for (String paramName : parameterNames) {
            String paramValue = request.getParameter(paramName);
            if (paramValue == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyParamsInRequest(HttpServletRequest request, String... parameterNames) {
        for (String paramName : parameterNames) {
            String paramValue = request.getParameter(paramName);
            if (paramValue == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyParamsInRequest(HttpServletRequest request, boolean quoteCheck, String... parameterNames) {
        for (String paramName : parameterNames) {
            String paramValue = request.getParameter(paramName);
            if (paramValue == null || paramValue.equals("")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyParamsWithExceptInRequest(HttpServletRequest request, ArrayList<String> parameterNames, String except) {
        System.out.println(parameterNames);
        for (String paramName : parameterNames) {
            String paramValue = request.getParameter(paramName);
            if (paramValue == null || paramValue.equals(except)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T mergeObjects(T obj1, T obj2) {
        Class<?> clazz = obj1.getClass();
        try {
            T mergedObj = (T) clazz.getDeclaredConstructor().newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);

                if (value2 != null && value2 instanceof List) {
                    List<Object> mergedList = new ArrayList<>((List<Object>) value1);
                    mergedList.addAll((List<Object>) value2);
                    field.set(mergedObj, mergedList);
                } else {
                    field.set(mergedObj, value2 != null ? value2 : value1);
                }
            }

            return mergedObj;
        } catch (Exception e) {
            throw new RuntimeException("Error merging objects: " + e.getMessage(), e);
        }
    }

    public static HashMap<String, String> getParamsAndConvertToHashMap(HttpServletRequest request, ArrayList<String> params) {
        HashMap<String, String> paramsAndItsValue = new HashMap<String, String>();
        for (String param : params) {
            if (request.getParameter(param) != null) {
                paramsAndItsValue.put(param, request.getParameter(param));
            }
        }
        return paramsAndItsValue;
    }

    public static HashMap<String, String> convertHashMapToColumnAndValues(HashMap<String, Object> keysValues) {
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
        return new HashMap<String, String>() {
            {
                put("columns", String.valueOf(keysString));
                put("values", String.valueOf(valuesString));
            }
        };
    }

    public static String wrapInQuote(String value) {
        return String.format("'%s'", value);
    }
}
