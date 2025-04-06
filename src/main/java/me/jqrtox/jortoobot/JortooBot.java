package me.jqrtox.jortoobot;

import me.jqrtox.jortoobot.commands.LinkCommand;
import me.jqrtox.jortoobot.commands.StaffChatToggle;
import me.jqrtox.jortoobot.discord.DiscordYml;
import me.jqrtox.jortoobot.events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public final class JortooBot extends JavaPlugin {

    public static JortooBot plugin;
    public static TextChannel ingameChannel;
    public static TextChannel commandLogChannel;
    public static TextChannel staffChatChannel;
    public static TextChannel countChannel;
    public static TextChannel welcomeChannel;
    public static TextChannel killLogChannel;
    public static TextChannel linkingLogChannel;
    public static TextChannel linkingChannel;
    public static TextChannel suggestionChannel;
    public static Role linkedRole;
    public static String mainColor;
    public static String joinColor;
    public static String leaveColor;

    @Override
    public void onEnable() {

        plugin = this;
        mainColor = plugin.getConfig().getString("bot.hexcolor");
        joinColor = plugin.getConfig().getString("bot.joincolor");
        leaveColor = plugin.getConfig().getString("bot.leavecolor");

        saveDefaultConfig();

        try {
            BotCreation.initBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new OnCommand(), this);
        getServer().getPluginManager().registerEvents(new StaffChat(), this);
        getServer().getPluginManager().registerEvents(new KillLogs(), this);

        getCommand("staffchat").setExecutor(new StaffChatToggle());
        getCommand("link").setExecutor(new LinkCommand());

    }

    @Override
    public void onDisable() {
        BotCreation.jda.shutdown();

        try {
            Linking.linkedYml.save(Linking.linkFile);
            DiscordYml.discordYml.save(DiscordYml.countFile);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static void loadCommands() {
        BotCreation.guild.updateCommands()
                .addCommands(
                        Commands.slash("online", "Shows the player count"),
                        Commands.slash("link", "Link your discord account to your minecraft account").addOption(OptionType.INTEGER, "code", "linking code", true)
                ).queue();


    }

}
