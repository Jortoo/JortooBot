package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Counting extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getChannel() != JortooBot.countChannel) {
            return;
        }
        if (event.getAuthor().isBot() || event.isWebhookMessage())
            return;

        event.getChannel().getHistory().retrievePast(2).queue(messages -> {
            if (messages.size() > 1) {
                Message prevMessage = messages.get(1);
                User prevUser = prevMessage.getAuthor();
                if (prevUser.equals(event.getAuthor())) {
                    event.getMessage().delete().queue();
                }
            }
        });


        String message = event.getMessage().getContentRaw();

        if (!message.equalsIgnoreCase((DiscordYml.number + 1) + "")) {
            event.getMessage().delete().queue();
            return;
        }
        DiscordYml.number++;

    }

}
