package model;

import connection.SQLServerConnection;
import ultis.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class BaseRepository<T> {
    protected String table = "";
    protected String primaryKey = "";
    protected String[] fillable;
    protected static Connection msqlConnection;
    PreparedStatement ps = null;


    protected BaseRepository() {
        try {
            if (msqlConnection == null) {
                System.out.println("1.init database connection");
                msqlConnection = SQLServerConnection.getConnection();
                System.out.println(msqlConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static HashMap<String, String> convertHashMapToColumnAndValues(HashMap<String, Object> keysValues) {
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
        return new HashMap<String,String>() {{
            put("columns", String.valueOf(keysString));
            put("values", String.valueOf(valuesString));
        }};
    }

    protected int sqlUpdateExecutor(String sqlQuery) {

        int rowsAffected;
        System.out.println(sqlQuery);
        try {
            ps = msqlConnection.prepareStatement(sqlQuery);
            rowsAffected = ps.executeUpdate();
            System.out.println(sqlQuery);
            if (rowsAffected > 0) {
                System.out.println("Update successful. Rows affected: " + rowsAffected);
                return rowsAffected;
            } else {
                System.out.println("No rows were affected by the update.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the PreparedStatement
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    protected ResultSet sqlQueryExecutor(String sqlQuery) {
        ResultSet rs;

        System.out.println(sqlQuery);
        try {
            ps = msqlConnection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

        return rs;
    }

    protected static String getInsertSql(String tableName, HashMap<String, Object> keysValues) {
        HashMap<String, String> columnAndValues = convertHashMapToColumnAndValues(keysValues);
        return "INSERT INTO " + "[" + tableName + "]" + "(" + columnAndValues.get("columns") + ")"
                + " VALUES(" + columnAndValues.get("values") + ")";
    }

    public String getWhereSql(String condition, List<String> columns) {
        String joinedString = String.join(", ", columns);
        return "SELECT " + joinedString + " FROM" + "[" + this.table + "]" + "WHERE " + condition;
    }

    public String getSqlUpdate(String tableName, HashMap<String, Object> keysValues, String updateCondition) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> entry : keysValues.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                sb.append(key).append(" = '").append(value).append("', ");
            } else {
                sb.append(key).append(" = ").append(value).append(", ");
            }
        }
        String udatedFieldsAndValues = sb.toString();
        return String.format("UPDATE [%s] SET %s WHERE %s", tableName, udatedFieldsAndValues, updateCondition);
    }

    public T save(T data) {
        HashMap<String, Object> objData = Helper.convertToHashMap(data, fillable);
        String sql = getInsertSql(this.table, objData);
        if (sqlUpdateExecutor(sql) != 0) {
            return data;
        }
        return null;
    }


    public ArrayList<T> getRecords(String sql, Function<ResultSet, T> mapper) {
        ArrayList<T> records = new ArrayList<>();
        try (ResultSet rs = sqlQueryExecutor(sql)) {
            while (rs.next()) {
                T temp = mapper.apply(rs);
                records.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return records;
    }

    public int update(T updatedData, String updateCondition) {
        HashMap<String, Object> objData = Helper.convertToHashMap(updatedData, fillable);
        String sql = getSqlUpdate(this.table, objData, updateCondition);
        return sqlUpdateExecutor(sql);
    }
}