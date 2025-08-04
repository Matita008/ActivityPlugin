package io.matita08.plugins.activityPlugin.data;

import io.matita08.plugins.activityPlugin.ActivityPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
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
      long time = data.getLong(getPath(player), 0);
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
         data.set(getPath(id), time);
      });
      try {
         data.save(real);
      } catch (IOException e) {
         if(ActivityPlugin.instance.saveTask != null) ActivityPlugin.instance.saveTask.cancel();
         throw new RuntimeException(e);
      }
   }
   
   private static String getPath(UUID id) {
      return "data." + id.toString() + ".time";
   }
}
