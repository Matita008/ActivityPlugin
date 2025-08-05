package io.matita08.plugins.activityPlugin.data;

import io.matita08.plugins.activityPlugin.ActivityPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class Memory {
   static YamlConfiguration data;
   static File real;
   private static HashMap<UUID, Long> mem;
   
   public static void saveTime(UUID player, long timeToAdd) {
      mem.put(player, timeToAdd + getTime(player));
   }
   
   public static long getTime(UUID player) {
      if(mem.containsKey(player)) return mem.get(player);
      long time = data.getLong(getPath(player.toString()), 0);
      mem.put(player, time);
      return time;
   }
   
   public static void init(File fi) {
      mem = new HashMap<>();
      real = fi;
      data = YamlConfiguration.loadConfiguration(fi);
   }
   
   public static void save() {
      mem.forEach((id, time) -> {
         data.set(getPath(id.toString()), time);
         data.set("data." + id + ".name", Bukkit.getOfflinePlayer(id).getName());
      });
      try {
         data.save(real);
      } catch (IOException e) {
         if(ActivityPlugin.instance.saveTask != null) ActivityPlugin.instance.saveTask.cancel();
         throw new RuntimeException(e);
      }
   }
   
   public static String getTime(String playerName) {
      long time = 0;
      ConfigurationSection dataSection = data.getConfigurationSection("data");
      if(dataSection == null) return "The player was never online";
      for(String uuid : dataSection.getKeys(false)) {
         if(playerName.equals(dataSection.getString(uuid + ".name"))) {
            time = data.getLong(getPath(uuid), 0);
            break;
         }
      }
      if(time == 0) return "The player wasn't online long enough";
      Duration d = Duration.ofMillis(time);
      StringBuilder sb = new StringBuilder("The player was online for ");
      if(d.toDays() > 1) sb.append(d.toDays()).append(" days, ");
      else if(d.toDays() == 1) sb.append(" 1 day, ");
      if(d.toHours() > 1) sb.append(d.toHours() % 24).append(" hours, ");
      else if(d.toHours() == 1) sb.append(" 1 hour, ");
      if(d.toMinutes() > 1) sb.append(d.toMinutes() % 60).append(" minutes, ");
      else if(d.toMinutes() == 1) sb.append(" 1 minute, ");
      if(d.getSeconds() > 1) sb.append(d.getSeconds() % 60).append(" second");
      else if(d.getSeconds() == 1) sb.append(" 1 second");
      return sb.toString();
   }
   
   private static String getPath(String id) {
      return "data." + id + ".time";
   }
}
