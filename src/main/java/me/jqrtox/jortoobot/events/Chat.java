package me.jqrtox.jortoobot.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.jqrtox.jortoobot.JortooBot;
import me.jqrtox.jortoobot.commands.StaffChatToggle;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;

public class Chat implements Listener {


    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        if (StaffChatToggle.scToggled.contains(event.getPlayer().getUniqueId())) {
            return;
        }

        TextChannel ingameChannel = JortooBot.ingameChannel;
        MiniMessage mm = MiniMessage.miniMessage();
        String message = mm.serialize(event.message());

        if (message.contains("@")) {
            message = message.replaceAll("@", "*");
        }
        if (message.charAt(0) == '!') return;

        if (ingameChannel != null) {
            Player player = event.getPlayer();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.decode(JortooBot.mainColor));
            eb.setAuthor("" + player.getName() + "  ‣ " + message, null, "http://cravatar.eu/avatar/" + player.getUniqueId());

            ingameChannel.sendMessageEmbeds(eb.build()).queue();
        }
    }

}