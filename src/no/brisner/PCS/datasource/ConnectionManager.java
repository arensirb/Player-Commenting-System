package no.brisner.PCS.datasource;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import no.brisner.PCS.PCS;
import no.brisner.PCS.Settings;

public class ConnectionManager {

   /* public static Connection getConnection() {
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
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
    }*/

    public static Connection createConnection() {
    		try {	
    			Class.forName("com.mysql.jdbc.Driver"); 
    			PCS.DisplayJDBC();
            Connection conn = DriverManager.getConnection(Settings.mysqlDB,Settings.mysqlUser, Settings.mysqlPass);
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}