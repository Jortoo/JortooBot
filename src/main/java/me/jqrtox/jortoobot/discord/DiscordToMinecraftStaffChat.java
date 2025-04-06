package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import me.jqrtox.jortoobot.commands.StaffChatToggle;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DiscordToMinecraftStaffChat extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getChannel() != JortooBot.staffChatChannel) {
            return;
        }
        if (event.getAuthor().isBot()) {
            return;
        }
        String message = event.getMessage().getContentRaw();
        String user = event.getAuthor().getName();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("jortoo.staff.chat")) {
                continue;
            }

            onlinePlayer.sendRichMessage(StaffChatToggle.scPrefix + "<blue><bold>DISCORD</bold><white> " + user + ": <yellow>" + message);

        }

    }

}
