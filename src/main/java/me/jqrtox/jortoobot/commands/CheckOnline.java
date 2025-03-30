package me.jqrtox.jortoobot.commands;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CheckOnline extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {

            case "online" -> {
                if (event.getChannelId().equals(JortooBot.plugin.getConfig().getString("bot.ingame-channel-id"))) {
                    event.reply("Please use this outside the ingame channel!").setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Total players online on Generik Box");
                eb.setColor(Color.decode(JortooBot.mainColor));
                eb.addField("Online:", "Total online: (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers() + ")", true);
                eb.setDescription("Join using GenerikBox.minehut.gg");
                event.replyEmbeds(eb.build()).queue();
            }
            case "clearchat" -> {
                final int amount = event.getOption("message-amount").getAsInt();

                event.getChannel().getHistory().retrievePast(amount).queue(messages -> {


                    for (Message message : messages) {
                        message.delete().queue();
                    }

                });

                event.reply("Successfully deleted " + amount + " message").setEphemeral(true).queue();

            }
        }


    }

}
