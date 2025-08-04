package io.matita08.plugins.activityPlugin.discord;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
   public final JDA bot;
   
   public Bot(String token) {
      JDABuilder builder = JDABuilder.create(token, GatewayIntent.getIntents(5));
      bot = builder.build();
   }
   
   public void disable(){
      bot.shutdown();
   }
}
