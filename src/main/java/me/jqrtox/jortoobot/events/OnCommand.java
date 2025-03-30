package me.jqrtox.jortoobot.events;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.awt.*;

public class OnCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {

        String command = event.getMessage();
        MiniMessage mm = MiniMessage.miniMessage();
        Player player = event.getPlayer();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(event.getPlayer().getName() + " ▸ " + command, null, "http://cravatar.eu/avatar/" + player.getUniqueId());
        eb.setColor(Color.decode(JortooBot.mainColor));
        JortooBot.commandLogChannel.sendMessageEmbeds(eb.build()).queue();

    }
}