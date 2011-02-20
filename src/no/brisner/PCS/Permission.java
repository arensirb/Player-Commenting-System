package no.brisner.PCS;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Server;


import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Permission {

	private static Permissions permissionsPlugin;
	public static final Logger log = Logger.getLogger("Minecraft");
	private static boolean permissionsEnabled = false;
	
    public static void setupPermissions(Server server) { 	
    	Plugin test = server.getPluginManager().getPlugin("Permissions");
    		if(test != null) {
    			permissionsPlugin = ((Permissions)test);
    			permissionsEnabled = true;
    			log.log(Level.INFO, "[PCS] Permissions activated!");
    		} else {
    			log.log(Level.SEVERE, "[PCS] Permission not installed! PrikkSystem disabled.");
    			server.getPluginManager().disablePlugin(server.getPluginManager().getPlugin("PrikkSystem"));
    		}

    }
    private static boolean permission(Player player, String string) {
        return permissionsPlugin.Security.permission(player, string);
    }

    public static boolean isAdmin(Player player) {
        if (permissionsEnabled) {
            return permission(player, "pcs.admin");
        } else {
            return player.isOp();
        }
    }
    public static boolean canLook(Player player) {
        if (permissionsEnabled) {
            return permission(player, "pcs.view");
        } else {
            return player.isOp();
        }
    }
    
}