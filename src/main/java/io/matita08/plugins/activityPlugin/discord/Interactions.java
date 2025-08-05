package io.matita08.plugins.activityPlugin.discord;

import io.matita08.plugins.activityPlugin.data.Memory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.*;

public class Interactions {
   public static void activity(SlashCommandInteractionEvent e) {
      OptionMapping om = e.getOption("name");
      if(om == null || om.getType() != OptionType.STRING) {
         e.reply("Parameter \"name\" was null or not a string").setEphemeral(true).queue();
         return;
      }
      e.reply(e.getUser().getAsMention() + " requested data for " + om.getAsString()).addEmbeds(new EmbedBuilder().setDescription(Memory.getTime(om.getAsString())).build()).queue();
   }
}
