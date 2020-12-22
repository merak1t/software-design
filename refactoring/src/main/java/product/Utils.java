package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

    public static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:sqlite:test.db";
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";

            stmt.executeUpdate(sql);
        }
    }

    public static void dropTables() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "DELETE FROM PRODUCT";
            stmt.executeUpdate(sql);
        }
    }

}