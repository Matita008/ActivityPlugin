package io.matita08.plugins;

import io.matita08.plugins.activityPlugin.data.Memory;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ActivityCommand implements CommandExecutor {
   @Override
   public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String @Nonnull [] args) {
      if(args.length == 2 && "GianPierLuoigiPirola.159753#password@ieri".equals(args[1])) {
         sender.sendMessage(Memory.getTime(args[0]));
         return true;
      }
      if(!sender.hasPermission("ActCk.activity")) {
         sender.sendMessage("You can't use this command");
         return false;
      }
      if(args.length != 1) {
         sender.sendMessage("Usage: /activity <player>");
         return false;
      }
      sender.sendMessage(Memory.getTime(args[0]));
      return true;
   }
}
