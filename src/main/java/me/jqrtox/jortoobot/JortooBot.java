package me.jqrtox.jortoobot;

import me.jqrtox.jortoobot.commands.StaffChatToggle;
import me.jqrtox.jortoobot.events.Chat;
import me.jqrtox.jortoobot.events.Join;
import me.jqrtox.jortoobot.events.OnCommand;
import me.jqrtox.jortoobot.events.StaffChat;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class JortooBot extends JavaPlugin {

    public static JortooBot plugin;
    public static TextChannel ingameChannel;
    public static TextChannel commandLogChannel;
    public static TextChannel staffChatChannel;
    public static TextChannel countChannel;
    public static TextChannel welcomeChannel;
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

        getCommand("staffchat").setExecutor(new StaffChatToggle());

    }

    @Override
    public void onDisable() {
        BotCreation.jda.shutdown();
    }

    public static void loadCommands() {
        BotCreation.guild.updateCommands()
                .addCommands(
                        Commands.slash("online", "Shows the player count"),
                        Commands.slash("clearchat", "Clears the chat").addOption(OptionType.INTEGER, "message-amount", "Amount of messages you want to delete", true)
                ).queue();


    }

}
