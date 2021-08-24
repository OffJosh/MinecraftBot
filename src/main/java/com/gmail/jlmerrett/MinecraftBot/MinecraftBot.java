package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class MinecraftBot extends JavaPlugin {

    BotMessenger botMessenger;
    MinecraftEventHandler eventHandler;
    static Plugin plugin;

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
        plugin = this;
        plugin.saveDefaultConfig();
        ConfigFile.addConfig(plugin.getConfig());
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