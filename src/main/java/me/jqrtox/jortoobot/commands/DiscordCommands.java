package me.jqrtox.jortoobot.commands;

import me.jqrtox.jortoobot.JortooBot;
import me.jqrtox.jortoobot.Linking;
import me.jqrtox.jortoobot.discord.DiscordYml;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class DiscordCommands extends ListenerAdapter {

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
                eb.setTitle("Total players online on Crazify Box");
                eb.setColor(Color.decode(JortooBot.mainColor));
                eb.addField("Online:", "Total online: (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers() + ")", true);
                eb.setDescription("Join using Crazify.minehut.gg");
                event.replyEmbeds(eb.build()).queue();
            }
            case "link" -> {

                if (event.getChannel() != JortooBot.linkingChannel) {
                    event.reply("Please use this command in: " + JortooBot.linkingChannel.getAsMention()).setEphemeral(true).queue();
                    return;
                }

                int code = event.getOption("code").getAsInt();
                Map<Integer, UUID> linkCodes = LinkCommand.linkCodes;

                Member member = event.getMember();
                if (member.getRoles().stream().anyMatch(role -> role.equals(JortooBot.linkedRole))) {
                    event.reply("You are already linked!").setEphemeral(true).queue();
                    return;
                }

                if (!linkCodes.containsKey(code)) {
                    event.reply("This code does not exist or is expired!").setEphemeral(true).queue();
                    return;
                }

                UUID uuid = linkCodes.get(code);
                Player player = Bukkit.getPlayer(uuid);
                String playerName = player.getName();

                event.reply("You are now linked to the player: " + playerName + "\nCheck out your rewards ingame!").setEphemeral(true).queue();
                linkCodes.remove(code);

                Linking.linkPlayer(uuid, event.getMember());

            }
            case "suggest" -> {

                if (event.getChannel() != JortooBot.suggestionChannel) {
                    event.reply("Please use this command in: " + JortooBot.suggestionChannel.getAsMention()).setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Suggestion #"+ DiscordYml.suggestionCount);


            }
        }


    }

}
