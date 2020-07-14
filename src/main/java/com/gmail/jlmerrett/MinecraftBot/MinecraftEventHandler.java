package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class MinecraftEventHandler implements Listener {

    Bot bot;

    public MinecraftEventHandler(Bot bot){
        this.bot = bot;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent){
        Player player = playerDeathEvent.getEntity().getPlayer();
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("Deaths");
        Score score = objective.getScore(player.getName());
        bot.botMessenger.sendMessage(player.getDisplayName() + " has died. Total Deaths: " + Integer.toString(score.getScore()+1));
    }
}
