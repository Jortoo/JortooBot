package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.BotCreation;
import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;

public class DiscordJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        FileConfiguration config = JortooBot.plugin.getConfig();
        String welcomeChannelId = config.getString("bot.welcome-channel-id");
        if (welcomeChannelId == null || welcomeChannelId.isEmpty()) {
            System.out.println("No welcome channel found!");
            return;
        }
        JDA jda = BotCreation.jda;
        EmbedBuilder eb = new EmbedBuilder();
        String avatarUrl = event.getMember().getEffectiveAvatarUrl();
        eb.setColor(Color.decode(JortooBot.mainColor));
        eb.setAuthor("New member!");
        eb.addField("Welcome to Generik Box" , event.getMember().getAsMention() + "\n", false);
        eb.addField("Check out:", " - " + jda.getTextChannelById("1354977090641985647").getAsMention() + "\n - " + jda.getTextChannelById("1354977130831679639").getAsMention() + "\n - " + jda.getTextChannelById("1354977445303947496").getAsMention() + "\n - " + jda.getTextChannelById("1354978075397459979").getAsMention() + "\n\n Enjoy your stay! (IP: GenerikBox.minehut.gg)", false);
        eb.setThumbnail(avatarUrl);
        jda.getTextChannelById(welcomeChannelId).sendMessageEmbeds(eb.build()).queue();

    }

}
