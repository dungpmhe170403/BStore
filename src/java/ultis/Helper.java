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

    public static boolean isEmptyParamsInRequest(HttpServletRequest request, String... parameterNames) {
        for (String paramName : parameterNames) {
            String paramValue = request.getParameter(paramName);
            if (paramValue == null) {
                return true;
            }
        }
        return false;
    }

}
