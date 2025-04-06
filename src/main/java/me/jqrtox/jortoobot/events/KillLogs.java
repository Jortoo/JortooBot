package me.jqrtox.jortoobot.events;

import me.jqrtox.jortoobot.JortooBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class KillLogs implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.isCancelled()) return;

        Player victim = event.getPlayer();

        if (victim.getKiller().getType() != EntityType.PLAYER) return;

        Player attacker = victim.getKiller();
        TextChannel killLogs = JortooBot.killLogChannel;

        if (killLogs == null) return;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(attacker.getName() + " Has killed: " + victim.getName(), null, "http://cravatar.eu/avatar/" + attacker.getUniqueId());
        eb.setColor(Color.decode(JortooBot.mainColor));
        killLogs.sendMessageEmbeds(eb.build()).queue();

    }

}
