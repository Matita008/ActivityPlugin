package io.matita08.plugins.activityPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class ActivityPlugin extends JavaPlugin {
   public static ActivityPlugin instance;
   public final Logger LOG = getLogger();
   @Override
   public void onEnable() {
      LOG.info("I've been enabled!");
   }
   
   @Override
   public void onDisable() {
      LOG.info("I've been disabled!");
   }
   
   @Override
   public void onLoad() {
      instance = this;
      
   }
   public static Logger logger(){
      if(instance != null) return instance.LOG;
      throw new RuntimeException("The plugin isn't loaded yet, How?");
   }
}
