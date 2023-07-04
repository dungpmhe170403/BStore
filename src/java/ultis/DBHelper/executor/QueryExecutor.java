package ultis.DBHelper.executor;

import connection.SQLServerConnection;
import ultis.DBHelper.bound.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class QueryExecutor<T> {
    private static Connection dbConnection;
    private PreparedStatement ps;

    public QueryExecutor() {
        if (dbConnection == null) {
            dbConnection = SQLServerConnection.getConnection();
        }
    }

    public int updateQuery(String sql) {
        int rowsAffected;
        //Log
        System.out.println("UPDATE QUERY:" + sql);
        try {
            ps = dbConnection.prepareStatement(sql);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + "ROW AFFECTED");
            } else {
                System.out.println("NO ROW AFFECTED");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return rowsAffected;
    }

    public ResultSet selectQuery(String sqlQuery) {
        ResultSet rs;
        System.out.println("SELECT QUERY:" + sqlQuery);
        try {
            ps = dbConnection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public Optional<ArrayList<T>> records(String sql, EntityMapper<T> mapper) {
        ArrayList<T> rows = new ArrayList<>();
        try (ResultSet rs = selectQuery(sql)) {
            while (rs.next()) {
                T temp = mapper.mapper(rs);
                rows.add(temp);
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
        if (rows.size() == 0) {
            rows = null;
        }
        return Optional.ofNullable(rows);
    }
}
