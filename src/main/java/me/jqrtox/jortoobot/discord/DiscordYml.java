package me.jqrtox.jortoobot.discord;

import me.jqrtox.jortoobot.JortooBot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DiscordYml {

    public static final File countFile = new File(JortooBot.plugin.getDataFolder(), "DiscordBotStats.yml");

    public static final FileConfiguration yml;

    public static long number = 0;

    static {

        try {

            countFile.createNewFile();
            yml = YamlConfiguration.loadConfiguration(countFile);

            if (yml.isSet("count-number")) {
                number = yml.getInt("count-number");
            }

            Bukkit.getScheduler().runTaskTimerAsynchronously(JortooBot.plugin, () -> {

                yml.set("count-number", number);

                try {
                    yml.save(countFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }, 6000L, 6000L);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
