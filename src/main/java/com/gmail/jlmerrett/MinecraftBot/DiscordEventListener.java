package com.gmail.jlmerrett.MinecraftBot;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;;
import java.util.Collection;

public class DiscordEventListener extends ListenerAdapter {

    BotMessenger botMessenger;

    public DiscordEventListener(){
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (!event.getAuthor().getId().equals(ConfigFile.getString("bot_id"))) {
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
            if (message.contains("!unwhitelist")) {
                runUnWhitelistCommand(message);
            }
            if (message.contains("!link")) {
                runLinkCommand(event);
            }
        }
    }

    public void setBotMessenger (BotMessenger botMessenger){
        this.botMessenger = botMessenger;
    }

    private void runLinkCommand(MessageReceivedEvent event){
        if (event.getMessage().getMentionedUsers().size() == 1) {
            String discordUser = event.getMessage().getMentionedUsers().toString();
            String discordUserId = discordUser.substring(
                    discordUser.lastIndexOf("(")+1,
                    discordUser.lastIndexOf(")"));
            String message = event.getMessage().getContentRaw();
            String minecraftUser = message.substring(message.lastIndexOf(" ") + 1);
            String minecraftUUID = getUUIDFromUsername(minecraftUser);

            botMessenger.sendMessage("Linking Discord user **" + discordUser + "** with Minecraft user **" + minecraftUser + "**");
            botMessenger.sendMessage(discordUserId + " " + minecraftUUID);

            try{
                JSONHandler.addUserToFile(discordUserId, minecraftUUID);
            }
            catch (IOException ioException){
                botMessenger.sendMessage("File not found, tag @OffJosh");
            }
            catch (JsonException jsonException){
                botMessenger.sendMessage("Something broke with the JSON, tag @OffJosh");
            }
        }
        else{
            if (event.getMessage().getMentionedUsers().size() > 1) {
                botMessenger.sendMessage("More than one user was tagged, please do this one at a time");
            } else {
                botMessenger.sendMessage("No user was tagged, please tag a user");
            }

        }
    }

    private void runWhitelistCommand(String message){
        String playerName = message.substring(message.lastIndexOf(" ") + 1);
        String command = "whitelist add " + playerName;
        Bukkit.getScheduler().runTask(MinecraftBot.plugin, () ->{
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        });
        botMessenger.sendMessage("Adding " + playerName + " to the whitelist.");
    }

    private void runUnWhitelistCommand(String message){
        String playerName = message.substring(message.lastIndexOf(" ") + 1);
        String command = "whitelist remove " + playerName;
        Bukkit.getScheduler().runTask(MinecraftBot.plugin, () ->{
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        });
        botMessenger.sendMessage("Removing " + playerName + " from the whitelist.");
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

    private String getUUIDFromUsername(String minecraftUserName){
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + minecraftUserName);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            int responseCode = httpURLConnection.getResponseCode();
            InputStream inputStream;

            if (200 <= responseCode && responseCode <= 299) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            inputStream));

            StringBuilder response = new StringBuilder();
            String currentLine;

            while ((currentLine = in.readLine()) != null)
                response.append(currentLine);

            in.close();

            JsonObject jsonObject = Jsoner.deserialize(response.toString(), new JsonObject());

            String uuid = jsonObject.get("id").toString();

            return uuid;
        }
        catch (MalformedURLException malformedURLException){
            botMessenger.sendMessage("Unable to find Minecraft user: " + minecraftUserName);
        }
        catch (IOException e) {
            botMessenger.sendMessage("Unable to get data from API Reponse");
        }
        return null;
    }

}
