package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LinkChannelManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getChannel() != JortooBot.linkingChannel)
            return;

        if (event.getAuthor().isBot())
            return;

        event.getMessage().delete().queue();
    }

}
