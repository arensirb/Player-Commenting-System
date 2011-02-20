package no.brisner.PCS.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Driver;


import no.brisner.PCS.Settings;

public class ConnectionManager {

    public static Connection getConnection() {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Settings.mysqlDB,Settings.mysqlUser, Settings.mysqlPass);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "[PrikkSystem] Error getting connection", e);
            e.printStackTrace();
            return null;
        }catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection createConnection() {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		 //Settings.mysqlDB, Settings.mysqlUser, Settings.mysqlPass);
            Connection ret = DriverManager.getConnection(Settings.mysqlDB,Settings.mysqlUser, Settings.mysqlPass);
            ret.setAutoCommit(false);
            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}