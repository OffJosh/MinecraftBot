package com.gmail.jlmerrett.MinecraftBot;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.FileWriter;
import java.io.IOException;

public class MinecraftBot extends JavaPlugin {

    BotMessenger botMessenger;
    MinecraftEventHandler eventHandler;
    static Plugin plugin;

    @Override
    public void onEnable() {
        initPlugin();
        //botMessenger.sendMessage("Server is now online.");
    }

    @Override
    public void onDisable() {
        if(eventHandler.isReloading){
            //botMessenger.sendMessage("Server is reloading.\nThere may be some brief lag.");
        }
        else{
            //botMessenger.sendMessage("Server is now offline.");
        }
    }

    private void initPlugin() {
        plugin = this;
        plugin.saveDefaultConfig();
        ConfigFile.addConfig(plugin.getConfig());
        try {
            JsonArray objects = new JsonArray();

            JsonObject newUser = new JsonObject();
            newUser.put("discord_id", "");
            newUser.put("minecraft_uuid", "");

            objects.add(newUser);

            try( FileWriter fileWriter = new FileWriter(MinecraftBot.plugin.getDataFolder() + "/discord_id_player_uuid.json")) {
                Jsoner.serialize(objects, fileWriter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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