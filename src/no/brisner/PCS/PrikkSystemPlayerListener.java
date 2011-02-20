package no.brisner.PCS;


import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.brisner.PCS.Permission;
import no.brisner.PCS.PrikkSystem;
import no.brisner.PCS.Settings;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;


/**
 * Handle events for all Player related events
 * @author arensirb
 */
public class PrikkSystemPlayerListener extends PlayerListener {
    private final PrikkSystem plugin;

    public PrikkSystemPlayerListener(PrikkSystem instance) {
        plugin = instance;
    }
    public void onPlayerCommand(PlayerChatEvent event) {
        Player player = event.getPlayer();
      
    }
   
}

