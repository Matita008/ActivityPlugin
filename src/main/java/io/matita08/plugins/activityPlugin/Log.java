package io.matita08.plugins.activityPlugin;

import java.util.logging.Logger;

public class Log {
   private static final Logger LOG = ActivityPlugin.logger();
   private final String prefix;
   
   public Log(String name) {
      prefix = "[" + name + "] ";
   }
   
   public void info(String s) {
      LOG.info(prefix + s);
   }
   
   public void warn(String s) {
      LOG.warning(prefix + s);
   }
   
   public void severe(String s) {
      LOG.severe(prefix + s);
   }
}
