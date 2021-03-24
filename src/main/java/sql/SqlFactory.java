package sql;

import java.sql.*;

public class SqlFactory {

    private static final String DB_URL = "jdbc:sqlserver://someIpAndPort";
    protected static final int timeoutInMinutesForQueries = 15;
    protected Connection connection;

    public void connectToDataBase(String user, String password) {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    enum Status {
        FOR_REALIZATION("FOR_REALIZATION"),
        IN_PROGRESS("IN_PROGRESS");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getDescription() {
            return status;
        }
    }
}