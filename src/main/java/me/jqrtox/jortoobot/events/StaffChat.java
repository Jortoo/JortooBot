package me.jqrtox.jortoobot.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.jqrtox.jortoobot.BotCreation;
import me.jqrtox.jortoobot.JortooBot;
import me.jqrtox.jortoobot.commands.StaffChatToggle;
import net.dv8tion.jda.api.EmbedBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;

public class StaffChat implements Listener {

    @EventHandler
    public void onStaffChat(AsyncChatEvent event) {

        Player player = event.getPlayer();

        if (!StaffChatToggle.scToggled.contains(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        Component message = event.message();
        EmbedBuilder eb = new EmbedBuilder();
        MiniMessage mm = MiniMessage.miniMessage();
        eb.setAuthor(event.getPlayer().getName() + " ▸ " + mm.serialize(message), null, "http://cravatar.eu/avatar/" + player.getUniqueId());
        eb.setColor(Color.decode(JortooBot.mainColor));
        JortooBot.staffChatChannel.sendMessageEmbeds(eb.build()).queue();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            if (!onlinePlayer.hasPermission("jortoo.staff.chat")) {
                continue;
            }

            onlinePlayer.sendRichMessage(StaffChatToggle.scPrefix + player.getName() + ": <yellow>" + mm.serialize(message));

        }

    }

}
