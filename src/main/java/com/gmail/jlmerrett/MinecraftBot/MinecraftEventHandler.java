package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MinecraftEventHandler implements Listener {

    Bot bot;

    public MinecraftEventHandler(Bot bot){
        this.bot = bot;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent){
        Player player = playerJoinEvent.getPlayer();
        bot.botMessenger.sendMessage(player.getDisplayName() + " has joined the server.");
    }
}
