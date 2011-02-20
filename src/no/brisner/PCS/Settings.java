package no.brisner.PCS;

import java.io.File;

//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.logging.Level;

//import org.bukkit.Server;

public class Settings {

	public static boolean commands;
	public static boolean chat;

	public static boolean mysql;
	public static boolean flatLog;
	public static String mysqlUser = "root";
	public static String mysqlPass = "root";
	public static String mysqlDB = "jdbc:mysql://localhost:3306/minecraft";
	public static String mysqlTable = "pcsdata";
	public static String sqlEngine;
	public static int sendDelay;


	public static void initialize(File dataFolder) {
		loadPropertiesFiles(dataFolder);
	}

	public static void onDisable(File dataFolder) {

	}

	private static void loadPropertiesFiles(File dataFolder) {
	    //Use Configuration once it's finished.
	    PropertiesFile pf = new PropertiesFile(new File(dataFolder, "PCS.properties"));
		mysql = pf.getBoolean("MySQL", true, "If true, uses MySQL. If false, uses Sqlite");
		flatLog = pf.getBoolean("flatFileLogs", false, "If true, will also log actions to .logs (one for each player)");
		mysqlUser = pf.getString("mysqlUser", "root", "Username for MySQL db (if applicable)");
		mysqlPass = pf.getString("mysqlPass", "root", "Password for MySQL db (if applicable)");
		mysqlDB = pf.getString("mysqlDB", "jdbc:mysql://localhost:3306/minecraft", "DB for MySQL (if applicable)");
        mysqlTable = pf.getString("mysqlTable", "pcsdata", "What table name to use in DB");
	    sendDelay = pf.getInt("sendDelay", 4, "Delay to batch send updates to database (4-5 recommended)");
		pf.save();
	}



}