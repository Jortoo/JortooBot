package me.jqrtox.jortoobot;

import me.jqrtox.jortoobot.discord.DiscordYml;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class Linking {

    public static String linkingPrefix = "<dark_gray>[<#FFCD47><bold>LINKING<bold:false><dark_gray>] <gray>▸ <white>";
    public static final File linkFile = new File(JortooBot.plugin.getDataFolder(), "Linked-Accounts.yml");
    public static FileConfiguration linkedYml;

    static {

        try {

            linkFile.createNewFile();
            linkedYml = YamlConfiguration.loadConfiguration(linkFile);

            Bukkit.getScheduler().runTaskTimer(JortooBot.plugin, s -> {

                try {
                    linkedYml.save(linkFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }, 6000L, 6000L);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static boolean checkIfPlayerLinked(UUID uuid) {

        if (linkedYml.isSet("linked." + uuid))
            return true;

        return false;

    }
    public static void linkPlayer(UUID uuid, Member member) {

        TextChannel logChannel = JortooBot.linkingLogChannel;
        Role role = JortooBot.linkedRole;

        linkedYml.set("linked." + uuid, member.getId());
        Player player = Bukkit.getPlayer(uuid);
        BotCreation.guild.modifyNickname(member, "(✓) " + player.getName()).queue();

        BotCreation.guild.addRoleToMember(member, role).queue();

        EmbedBuilder logEb = new EmbedBuilder();
        logEb.setColor(Color.decode(JortooBot.mainColor));
        logEb.setThumbnail("http://cravatar.eu/avatar/" + player.getUniqueId());
        logEb.setAuthor(member.getNickname() + " Has linked their account to: " + player.getName());
        logEb.addField("Info", "\n"+ member.getAsMention() +"\nUUID: `" + uuid + "`" + "\nDiscord Id: `" + member.getId() + "`", false);
        logEb.setFooter("Date:" + LocalDate.now());

        logChannel.sendMessageEmbeds(logEb.build()).queue();

        Bukkit.getScheduler().runTask(JortooBot.plugin, () -> {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission set jortoo.daily true");
        });
        player.sendRichMessage(linkingPrefix + "You have successfully connected your discord account with your minecraft account!\n<gray>You can now use /daily!");
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(linkingPrefix + "<#FFCD47>" + player.getName() + "<white> Has linked their account using <#FFCD47>/link<white> and recieved rewards"));

    }


}
