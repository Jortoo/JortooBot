package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class DiscordToMinecraft extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        FileConfiguration config = JortooBot.plugin.getConfig();
        String channelId = config.getString("bot.ingame-channel-id");

        if (!event.getMessage().getChannelId().equals(channelId)) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        String user = event.getAuthor().getName();
        MiniMessage mm = MiniMessage.miniMessage();
        Bukkit.broadcast(mm.deserialize("<blue><bold>DISCORD</bold> <white>" + user + ":<gray> " + message));

    }

}