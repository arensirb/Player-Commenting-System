package no.brisner.PCS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.brisner.PCS.PrikkSystem;
import no.brisner.PCS.Settings;
import no.brisner.PCS.datasource.ConnectionManager;
import no.brisner.PCS.datasource.MyData;

public class sqlhandler {
	
	public final static String DATA_NAME = Settings.mysqlTable;
	public static String DATA_TABLE_MYSQL = "CREATE TABLE `" + Settings.mysqlTable + "` (" + "`id` INT NOT NULL AUTO_INCREMENT,"
    + "`date` INT UNSIGNED NOT NULL DEFAULT '0'," + "`player` varchar(32) NOT NULL DEFAULT 'Player'," 
    + "`data` varchar(150) NOT NULL DEFAULT ''," + "PRIMARY KEY (`id`),"
    + "INDEX(`player`)," +  "INDEX(`date`)" + ") ENGINE=INNODB;";

	public static void initialize() {
        if (!dataTableExists(!Settings.mysql)) {
            PrikkSystem.log.info("[PrikkSystem]: Generating prikkdata table");
            createDataTable(!Settings.mysql);
        }
    }
	
	private static boolean dataTableExists(boolean sqlite) {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, DATA_NAME, null);
            if (!rs.next())
                return false;
            return true;
        } catch (SQLException ex) {
            PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Table Check SQL Exception mysql" , ex);
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (conn != null) 
                    conn.close();
            } catch (SQLException ex) {
                PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Table Check SQL Exception (on closing)");
            }
        }
    }
	private static void createDataTable(boolean sqlite) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = ConnectionManager.getConnection();
            st = conn.createStatement();

            st.executeUpdate(DATA_TABLE_MYSQL);
            conn.commit();
        } catch (SQLException e) {
            PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Create Table SQL Exception mysql" + DATA_TABLE_MYSQL, e);
        } finally {
            try {
                if (st != null)
                    st.close();
                if (conn != null) 
                    conn.close();
            } catch (SQLException e) {
                PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Could not create the table (on close)");
            }
        }
    }
	public static boolean insertPrikk(String player, String reason) { // Legger til ny prikk på spiller
		Connection conn = null;
		PreparedStatement st = null;
		String mysqlTable = Settings.mysqlTable;
		Long epoch = System.currentTimeMillis()/1000;
		
		try {
			conn = ConnectionManager.getConnection();
			st = conn.prepareStatement("INSERT INTO `" + mysqlTable + "` (`date`,player,data) VALUES ("+ epoch + ",?,?)");
			st.setString(1, player);
			st.setString(2, reason);
			st.executeUpdate();
			conn.commit();
			System.out.println("SQL; " + st.toString());
			
		} catch(SQLException e) {
			PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Insert Prikk Exception" + st.toString(), e);
			return false;
		}
		finally {
            try {
                if (st != null)
                    st.close();
                if (conn != null) 
                    conn.close();
            } catch (SQLException e) {
                PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Could not insert prikk-data (on close)");
                return false;
            }
		}
		return true;
	}
	public static boolean removePrikk(String player, int id) { // Fjerner prikk fra spiller
		return false;
	}
	public static ArrayList<MyData> listPrikk(String player) { // Henter alle prikker på spiller
		Connection conn = null;
		PreparedStatement st = null;
		String mysqlTable = Settings.mysqlTable;
		ResultSet rs;
		ArrayList<MyData> obj = new ArrayList<MyData>();
		try {
			conn = ConnectionManager.getConnection();
			st = conn.prepareStatement("SELECT date,data FROM " + mysqlTable + " WHERE player = ?");
			st.setString(1, player);
			rs = st.executeQuery();
			while(rs.next()) {
				obj.add(new MyData(rs.getLong("date"), rs.getString("data")));
			}	
		} catch(SQLException e) {
			PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Retrieve prikk SQL Exception" + DATA_TABLE_MYSQL, e);
		}
		finally {
            try {
                if (st != null)
                    st.close();
                if (conn != null) 
                    conn.close();    
            } catch (SQLException e) {
                PrikkSystem.log.log(Level.SEVERE, "[PrikkSystem]: Could not retrieve prikk-data (on close)");
            }
		}	
		 return obj;
	}
}
