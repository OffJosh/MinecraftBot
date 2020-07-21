package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class MinecraftBot extends JavaPlugin {

    BotMessenger botMessenger;
    MinecraftEventHandler eventHandler;
    FileConfiguration configFile;

    @Override
    public void onEnable() {
        initPlugin();
        botMessenger.sendMessage("Server is now online.");
    }

    @Override
    public void onDisable() {
        if(eventHandler.isReloading){
            botMessenger.sendMessage("Server is reloading.\nThere may be some brief lag.");
        }
        else{
            botMessenger.sendMessage("Server is now offline.");
        }
    }

    private void initPlugin() {
        this.saveDefaultConfig();
        configFile = this.getConfig();
        Bot bot = null;
        try {
            bot = new Bot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        eventHandler = new MinecraftEventHandler(bot);
        botMessenger = bot.botMessenger;
        getServer().getPluginManager().registerEvents(eventHandler, this);
    }
}