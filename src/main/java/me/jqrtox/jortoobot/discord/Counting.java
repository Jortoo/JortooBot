package me.jqrtox.jortoobot.discord;

import lombok.SneakyThrows;
import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import poa.poalib.webooks.DiscordWebhook;

import java.io.IOException;


public class Counting extends ListenerAdapter {

    private static final String webhookUrl = JortooBot.plugin.getConfig().getString("bot.webhook-url");

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getChannel() != JortooBot.countChannel)
            return;

        User author = event.getAuthor();
        if (author.isBot() || event.isWebhookMessage())
            return;

        String message = event.getMessage().getContentRaw();

        if (!message.equalsIgnoreCase((DiscordYml.number + 1) + "")) {
            event.getMessage().delete().queue();
            return;
        }

        DiscordYml.number++;
        final DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
        webhook.setUsername(author.getEffectiveName());
        webhook.setAvatarUrl(author.getAvatarUrl());
        webhook.setContent(event.getMessage().getContentRaw());
        webhook.execute();


        event.getMessage().delete().queue();


    }

}
