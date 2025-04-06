package me.jqrtox.jortoobot.commands;

import me.jqrtox.jortoobot.JortooBot;
import me.jqrtox.jortoobot.Linking;
import me.jqrtox.jortoobot.discord.DiscordYml;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class LinkCommand implements CommandExecutor {

    public static Map<Integer, UUID> linkCodes = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        UUID uniqueId = player.getUniqueId();

        if (Linking.checkIfPlayerLinked(uniqueId)) {
            player.sendRichMessage(Linking.linkingPrefix + "You are already linked!");
            return true;
        }

        if (linkCodes.containsValue(uniqueId)) {
            player.sendRichMessage("<#FFCD47><bold>LINKING<bold:false>\n\n <#FFCD47>| <white>You already generated a code!\n <#FFCD47>| <white>Your link code is: <#FFCD47>" + linkCodes.get(player.getUniqueId()) + "\n\n <white>Run <yellow>/link <code> <white>in the channel: <dark_gray>#❖┇linking\n ");
            return true;
        }
        int code = returnLinkCode();
        player.sendRichMessage("<#FFCD47><bold>LINKING<bold:false>\n\n <#FFCD47>| <white>You have generated a code!\n <#FFCD47>| <white>Your link code is: <#FFCD47>" + code + "\n\n <white>Run <yellow>/link <code> <white>in the channel: <dark_gray>#❖┇bot-commands\n ");

        linkCodes.put(code, uniqueId);

        return true;
    }

    private static int returnLinkCode() {

        Random random = new Random();
        int code = 1000 + random.nextInt(9000);

        if (linkCodes.containsKey(code))
            returnLinkCode();

        return code;

    }



}
