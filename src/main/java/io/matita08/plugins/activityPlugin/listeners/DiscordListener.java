package io.matita08.plugins.activityPlugin.listeners;

import io.matita08.plugins.activityPlugin.Log;
import io.matita08.plugins.activityPlugin.discord.Interactions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
   private final JDA jda;
   private static final Log LOG = new Log("Bot");
   public DiscordListener(JDA bot) {
      jda = bot;
   }
   
   @Override
   public void onReady(@Nonnull ReadyEvent e) {
      jda.updateCommands().addCommands(
          Commands.slash("activity","Get ").addOptions(
              new OptionData(OptionType.STRING, "name", "", true)
          ).setDefaultPermissions(DefaultMemberPermissions.enabledFor(536870912)).setGuildOnly(true))
         .queue();
      LOG.info("Reloaded commands correctly");
   }
   
   @Override
   public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
      switch(e.getName()) {
         case "activity" -> Interactions.activity(e);
         default -> {
            LOG.severe("Invalid slash command received (" + e.getFullCommandName() + "): " + e);
            e.reply(" ").setEphemeral(true).queue();
         }
      }
   }
}
