package no.brisner.PCS.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.brisner.PCS.Settings;

public class ConnectionManager {

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(Settings.mysqlDB,Settings.mysqlUser, Settings.mysqlPass);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "[PrikkSystem] Error getting connection", e);
            e.printStackTrace();
            return null;
        }
    }

    public static Connection createConnection() {
    	try {
    		 //Settings.mysqlDB, Settings.mysqlUser, Settings.mysqlPass);
            Connection ret = DriverManager.getConnection(Settings.mysqlDB,Settings.mysqlUser, Settings.mysqlPass);
            ret.setAutoCommit(false);
            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}