package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class MinecraftEventHandler implements Listener {

    BotMessenger botMessenger;
    boolean isReloading;

    public MinecraftEventHandler(Bot bot){
        this.botMessenger = bot.botMessenger;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent){
        Player player = playerDeathEvent.getEntity().getPlayer();
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("Deaths");
        Score score = objective.getScore(player.getName());
        botMessenger.sendMessage(playerDeathEvent.getDeathMessage() + ".\nTotal Deaths: " + (score.getScore() + 1));
    }

    @EventHandler
    public void onServerCommandEvent(ServerCommandEvent serverCommandEvent){
        if(serverCommandEvent.getCommand().equals("reload confirm")){
            isReloading = true;
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent playerCommandPreprocessEvent){
        if(playerCommandPreprocessEvent.getPlayer().isOp() && playerCommandPreprocessEvent.getMessage().equals("/reload confirm")){
            isReloading = true;
        }
    }
}
