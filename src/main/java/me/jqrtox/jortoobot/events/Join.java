package me.jqrtox.jortoobot.events;

import me.jqrtox.jortoobot.BotCreation;
import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        TextChannel channel = JortooBot.ingameChannel;
        Player player = event.getPlayer();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode(JortooBot.joinColor));
        embedBuilder.setAuthor(player.getName() + " has joined the server", null, "http://cravatar.eu/avatar/" + player.getUniqueId());

        channel.sendMessageEmbeds(embedBuilder.build()).queue();

    }

}