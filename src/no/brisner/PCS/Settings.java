package no.brisner.PCS;

import java.io.File;

//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.logging.Level;

//import org.bukkit.Server;

public class Settings {

	public static String mysqlUser = "root";
	public static String mysqlPass = "root";
	public static String mysqlDB = "jdbc:mysql://localhost:3306/minecraft";
	public static String mysqlTable = "pcsdata";
	public static String sqlEngine;


	public static void initialize(File dataFolder) {
		loadPropertiesFiles(dataFolder);
	}

	public static void onDisable(File dataFolder) {

	}

	private static void loadPropertiesFiles(File dataFolder) {
	    //Use Configuration once it's finished.
	    PropertiesFile pf = new PropertiesFile(new File(dataFolder, "PCS.properties"));
		mysqlUser = pf.getString("mysqlUser", "root", "Username for MySQL db (if applicable)");
		mysqlPass = pf.getString("mysqlPass", "root", "Password for MySQL db (if applicable)");
		mysqlDB = pf.getString("mysqlDB", "jdbc:mysql://localhost:3306/minecraft", "DB for MySQL (if applicable)");
        mysqlTable = pf.getString("mysqlTable", "pcsdata", "What table name to use in DB");
		pf.save();
	}



}