package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class MinecraftBot extends JavaPlugin {

    Bot bot;
    MinecraftEventHandler eventHandler;

    @Override
    public void onEnable() {
        initPlugin();
        bot.botMessenger.sendMessage("Server is now online");
    }

    @Override
    public void onDisable() {
        bot.botMessenger.sendMessage("Server is now offline");
    }

    private void initPlugin() {
        try {
            bot = new Bot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        eventHandler = new MinecraftEventHandler(bot);
        getServer().getPluginManager().registerEvents(eventHandler, this);
    }
}