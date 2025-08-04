package io.matita08.plugins.activityPlugin.listeners;

import io.matita08.plugins.activityPlugin.data.Memory;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import java.time.*;
import java.util.HashMap;
import java.util.logging.Logger;

public class ActivityListener implements Listener {
   
   private final HashMap<Player, Instant> mem = new HashMap<>();
   private final Logger LOG;
   
   public ActivityListener(Logger log) {
      LOG = log;
   }
   
   @EventHandler
   public void onPlayerJoinEvent(PlayerJoinEvent pje) {
      Player p = pje.getPlayer();
      LOG.info(p.getName() + " è entrato");
      mem.put(p, Instant.now());
   }
   
   @EventHandler
   public void onPlayerQuitEvent(PlayerQuitEvent pqe) {
      Player p = pqe.getPlayer();
      Instant now = Instant.now();
      LOG.info(p.getName() + " è uscito");
      if(now.minus(Duration.ofMinutes(2)).compareTo(mem.get(p)) < 0) {
         LOG.info(p.getName() + " è rimasto per troppo poco tempo nel server");
         return;
      }
      long time = now.toEpochMilli() - mem.get(p).toEpochMilli();
      LOG.info("Added " + time/1000.0 + " to " + p);
      Memory.saveTime(p.getUniqueId(), time);
      
   }
   
   @EventHandler
   public void onPlayerKickEvent(PlayerKickEvent pke) {
      Player p = pke.getPlayer();
      LOG.info(p.getName() + " è stato espulso");
      Instant now = Instant.now();
      //TODO no hardcode
      if(now.minus(Duration.ofMinutes(2)).compareTo(mem.get(p)) < 0) {
         LOG.info(p.getName() + " non ha eseguito l'accesso");
         return;
      }
      long time = now.toEpochMilli() - mem.get(p).toEpochMilli();
      LOG.info("Added " + time/1000.0 + " to " + p);
      Memory.saveTime(p.getUniqueId(), time);
   }
   
   public void stop() {
      Instant now = Instant.now();
      mem.forEach((p, time) -> {
         long timeL = now.toEpochMilli() - mem.get(p).toEpochMilli();
         LOG.info("Added " + timeL/1000.0 + " to " + p);
         Memory.saveTime(p.getUniqueId(), timeL);
      });
   }
}
