package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class DiscordEventListener extends ListenerAdapter {

    BotMessenger botMessenger;

    public DiscordEventListener(){
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        if (message.equals("!ram")) {
            runRamCommand();
        }
        if (message.equals("!playerlist")) {
            runListCommand();
        }
        if (message.equals("!plugins")) {
            runPluginCommand();
        }
        if (message.contains("!whitelist")) {
            runWhitelistCommand(message);
        }

    }

    public void setBotMessenger (BotMessenger botMessenger){
        this.botMessenger = botMessenger;
    }

    private void runWhitelistCommand(String message){
        String playerName = message.substring(message.lastIndexOf(" ") + 1);
        String command = "whitelist add " + playerName;
        Bukkit.getScheduler().runTask(MinecraftBot.plugin, () ->{
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        });
        botMessenger.sendMessage("Adding " + playerName + " to the whitelist.");
    }

    private void runRamCommand(){
        Runtime r = Runtime.getRuntime();
        StringBuilder message = new StringBuilder("Total: " + r.totalMemory()/1048576 + "MB\n");
        message.append("Used: ").append((r.totalMemory() - r.freeMemory()) / 1048576).append("MB\n");
        message.append("Free: ").append(r.freeMemory() / 1048576).append("MB");
        botMessenger.sendMessage(message.toString());
    }

    private void runListCommand(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        StringBuilder message = new StringBuilder("Players online: ");
        for (Player player : players){
            message.append(player.getDisplayName()).append(", ");
        }
        message.substring(0, message.length()-4);
        botMessenger.sendMessage(message.toString());
    }

    private void runPluginCommand(){
        Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
        StringBuilder message = new StringBuilder("Plugins: ");
        for (Plugin plugin : plugins){
            message.append(plugin.getName()).append(", ");
        }
        message.substring(0, message.length()-4);
        botMessenger.sendMessage(message.toString());
    }

}
