package me.jqrtox.jortoobot;

import me.jqrtox.jortoobot.commands.CheckOnline;
import me.jqrtox.jortoobot.discord.Counting;
import me.jqrtox.jortoobot.discord.DiscordJoin;
import me.jqrtox.jortoobot.discord.DiscordToMinecraft;
import me.jqrtox.jortoobot.discord.DiscordToMinecraftStaffChat;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.configuration.file.FileConfiguration;

import javax.security.auth.login.LoginException;


public class BotCreation {

    public static JDA jda;
    public static Guild guild;
    public static String guildId;

    public static void initBot() throws LoginException {

        JortooBot plugin = JortooBot.plugin;
        FileConfiguration configuration = plugin.getConfig();
        String token = configuration.getString("bot.token");

        if (jda != null) {
            System.out.println("Bot is already running!");
            return;
        }

        try {

            JDABuilder jdaBuilder = JDABuilder.createDefault(token).setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .setActivity(Activity.watching("GenerikBox.minehut.gg"))
                    .addEventListeners(new DiscordToMinecraft())
                    .addEventListeners(new CheckOnline())
                    .addEventListeners(new DiscordToMinecraftStaffChat())
                    .addEventListeners(new Counting())
                    .addEventListeners(new DiscordJoin());

            for (GatewayIntent value : GatewayIntent.values())
                jdaBuilder.enableIntents(value);

            jda = jdaBuilder
                    .build()
                    .awaitReady();

            FileConfiguration config = JortooBot.plugin.getConfig();

            JortooBot.ingameChannel = jda.getTextChannelById(config.getString("bot.ingame-channel-id"));
            JortooBot.commandLogChannel = jda.getTextChannelById(config.getString("bot.command-log-channel-id"));
            JortooBot.staffChatChannel = jda.getTextChannelById(config.getString("bot.staffchat-channel-id"));
            JortooBot.countChannel = jda.getTextChannelById(config.getString("bot.count-channel-id"));
            JortooBot.welcomeChannel = jda.getTextChannelById(config.getString("bot.welcome-channel-id"));

            guildId = config.getString("bot.guild-id");
            guild = BotCreation.jda.getGuildById(guildId);

            JortooBot.loadCommands();

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }


}