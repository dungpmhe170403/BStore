package ultis.DBHelper.model;

import ultis.DBHelper.bound.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    private static String table;
    private static ArrayList<String> fillable;

    public void table(String tableName) {
        table = tableName;
    }

    public void fillable(String... cols) {
        fillable = new ArrayList<>(Arrays.asList(cols));
    }

    public static String tableName() {
        return table;
    }

    public static ArrayList<String> getFillable() {
        return fillable;
    }

}
