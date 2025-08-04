package io.matita08.plugins.activityPlugin;

import io.matita08.plugins.activityPlugin.data.Memory;
import io.matita08.plugins.activityPlugin.discord.Bot;
import io.matita08.plugins.activityPlugin.listeners.ActivityListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.logging.Logger;

public final class ActivityPlugin extends JavaPlugin {
   public static ActivityPlugin instance;
   public static Bot bot;
   
   public final Logger LOG = getLogger();
   
   public final File storage = new File(getDataFolder(), "times.yml");
   public YamlConfiguration configs;
   public BukkitTask saveTask;
   
   private final ActivityListener listener = new ActivityListener(LOG);
   
   private boolean botEnabled = false;
   
   public static Logger logger() {
      if(instance != null) return instance.LOG;
      throw new RuntimeException("The plugin isn't loaded yet, How?");
   }
   
   @Override
   public void onLoad() {
      instance = this;
      Memory.init(storage);
      LOG.info("Loaded!");
   }
   
   @Override
   public void onDisable() {
      if(botEnabled) bot.disable();
      if(saveTask != null) saveTask.cancel();
      
      listener.stop();
      Memory.save();
      
      LOG.info("Plugin disabled successfully");
   }
   
   @Override
   public void onEnable() {
      saveDefaultConfig();
      configs = (YamlConfiguration)getConfig();
      if(!storage.exists()) {
         try {
            storage.createNewFile();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
      
      botEnabled = configs.getBoolean("bot.enabled", false);
      if(botEnabled) {
         String token = configs.getString("bot.token");
         if(token == null || token.strip().isBlank() || token.trim().isBlank() || token.indexOf('.') < 10) {
            botEnabled = false;
            LOG.severe("The discord bot is set to enable while the token inserted is invalid");
            LOG.severe("Token inserted: \"" + token + "\"");
         } else {
            bot = new Bot(token.trim());
         }
      } else LOG.info("Skipping discord bot");
      
      Bukkit.getPluginManager().registerEvents(listener, this);
      if(configs.getBoolean("data.activity.autosave.enabled"))
         saveTask = Bukkit.getScheduler().runTaskTimer(this, Memory::save, configs.getInt("data.activity.autosave.delay") * 20L, configs.getInt("data.activity.autosave.interval") * 20L);
      
      LOG.info("Plugin enabled successfully");
   }
}
