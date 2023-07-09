package connection;

import ultis.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    public static final String HOSTNAME = "localhost";
    public static final String PORT = "1433";

    /**
     * Get connection to MSSQL Server
     *
     * @return Connection
     */
    public static Connection getConnection() {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";"
                + "databaseName=" +  Helper.getValueFromAppProperties("database.name") + ";encrypt=true;trustServerCertificate=true";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl,  Helper.getValueFromAppProperties("database.username"),  Helper.getValueFromAppProperties("database.password"));
        }
        // Handle any errors that may have occurred.
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
