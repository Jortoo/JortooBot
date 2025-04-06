package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DiscordYml {

    public static final File countFile = new File(JortooBot.plugin.getDataFolder(), "DiscordBotStats.yml");

    public static final FileConfiguration discordYml;

    public static long number = 0;
    public static long suggestionCount = 0;

    static {

        try {

            countFile.createNewFile();
            discordYml = YamlConfiguration.loadConfiguration(countFile);

            if (discordYml.isSet("count-number")) {
                number = discordYml.getInt("count-number");
            }
            if (discordYml.isSet("suggestion-count")) {
                suggestionCount = discordYml.getInt("suggestion-count");
            }

            Bukkit.getScheduler().runTaskTimerAsynchronously(JortooBot.plugin, () -> {

                discordYml.set("count-number", number);
                discordYml.set("suggestion-count", suggestionCount);

                try {
                    discordYml.save(countFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }, 6000L, 6000L);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
