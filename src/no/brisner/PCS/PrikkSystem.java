package no.brisner.PCS;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import no.brisner.PCS.sqlhandler;
import no.brisner.PCS.datasource.ConnectionManager;
import no.brisner.PCS.datasource.MyData;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;


/**
 * PrikkSystem for Bukkit
 *
 * @author arensirb
 */
public class PrikkSystem extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
    private final PrikkSystemPlayerListener playerListener = new PrikkSystemPlayerListener(this);
    private final PrikkSystemBlockListener blockListener = new PrikkSystemBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public final String name = this.getDescription().getName();
    public final String version = this.getDescription().getVersion();
    public final static String premessage = ChatColor.AQUA + "[PrikkSystem]: " + ChatColor.WHITE;
    
    public PrikkSystem(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        // TODO: Place any custom initialisation code here
        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }

    public void onEnable() {
    
        PluginManager pm = getServer().getPluginManager();

        if (new File("PrikkSystem").exists()) {
            updateSettings(getDataFolder()); // getDataFolder() returns PrikkSystem
        } else if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        Settings.initialize(getDataFolder());
        
        
        Connection conn2 = ConnectionManager.createConnection();
        if (conn2 == null) {
            log.log(Level.SEVERE, "[PrikkSystem] Could not establish SQL conn2ection. Disabling PrikkSystem");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            try {
                conn2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        registerEvents();
        Permission.setupPermissions(getServer());
        sqlhandler.initialize();
          
    	log.info(name + " " + version + " initialized");
    }
    private void updateSettings(File dataFolder) {
        File oldDirectory = new File("PrikkSystem");
        dataFolder.getParentFile().mkdirs();
        oldDirectory.renameTo(dataFolder);
    }
    private void registerEvents() {
        //getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Monitor, this);
        //getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Monitor, this);
    }
    public void onDisable() {
    	Settings.onDisable(getDataFolder());
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	String[] split = args;
        String commandName = command.getName().toLowerCase();
        String sql;
        
        
        if (sender instanceof Player) {
            //Player player = (Player) sender;
            if (commandName.equals("prikk")) {
                if (split.length == 0) {
                	sender.sendMessage(premessage +"This is the help :)");
                    return true;
                }
                if (split[0].equalsIgnoreCase("add")) {
                	if (split.length >= 2) {
                		List<Player> targets = getServer().matchPlayer(split[1]);
                		Player rawplayer = null;
                		String player = "";
                		if(targets.size() == 0 ) {
                			player = split[1];                			
                		} else if( targets.size() == 1) {
                			rawplayer = targets.get(0);
                			player = rawplayer.getName();
                		} else {
                			sender.sendMessage(premessage + "Too many players matched!");
                			return false;
                		}
                			String reason = "";
                			for (int i = 2; i < split.length; i++) {
                				reason += split[i] + " ";
		                    }
                			sender.sendMessage(premessage +"New comment for " + player + " with reason " + reason);
                			if(sqlhandler.insertPrikk(player, reason)) {
                				sender.sendMessage(premessage +"Comment added!"); 
                			}
                	
                	} else {
                		sender.sendMessage(premessage + "Usage is : /prikk add <player> <reason>");
                		return false;
                	}
                }
                if (split[0].equalsIgnoreCase("list")) {
                	if(split.length == 2) {
                		List<String> list;
                		String player = split[1];
                		
                		sender.sendMessage(premessage + "Listing comments about " + player);
                		
                		ArrayList<MyData> x = sqlhandler.listPrikk(player);             
                		if(x.size() >=1) {
                			for(MyData object : x) {
                				Long date = object.date;
                				String data = object.data;
                				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");                     			
                				Date cDate = new Date( date * 1000 );                		
                				sender.sendMessage(premessage + format.format(cDate) + " : " + data);
                			}
                		} else {
                			sender.sendMessage(premessage + "Didnt find any comments on this player!");
                		}
                	}
                		
                    return true;
                    }
                	if (split[0].equalsIgnoreCase("rem")) {
                    return true;
                    }
               }
        }
        return false;
    }
    
    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

