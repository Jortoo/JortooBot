package me.jqrtox.jortoobot.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StaffChatToggle implements CommandExecutor {

    public static List<UUID> scToggled = new ArrayList<>();
    public static final String scPrefix = "<dark_gray>[<light_purple><bold>STAFF<light_purple>CHAT</bold><dark_gray>] <gray>▸ <white>";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            if (scToggled.contains(player.getUniqueId())) {
                scToggled.remove(player.getUniqueId());
                player.sendRichMessage(scPrefix + "You have <red>Disabled<white> staff chat");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return true;
            } else {
                scToggled.add(player.getUniqueId());
                player.sendRichMessage(scPrefix + "You have <green>Enabled <white>staff chat");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return true;
            }
        }
        else {

            String join = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
            MiniMessage.miniMessage().deserialize(join);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("jortoo.staff.chat")) continue;

                onlinePlayer.sendRichMessage(StaffChatToggle.scPrefix + player.getName() + ": <yellow>" + join);

            }
        }
        return true;
    }
}
