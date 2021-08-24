package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;

public class BotMessenger {

    JDA bot;
    Guild guild;
    TextChannel textChannel;
    String guildId;
    String textChannelID;

    public BotMessenger(JDA bot){
        this.bot = bot;
        this.guildId = ConfigFile.getString("guild_id");
        this.textChannelID = ConfigFile.getString("text_channel_id");
        this.guild = bot.getGuildById(guildId);
        if (guild == null){
            Bukkit.broadcastMessage("Could Not Find Guild with ID: " + guildId);
        }
        if (guild != null) {
            this.textChannel = guild.getTextChannelById(textChannelID);
        }
        if (textChannel == null){
            Bukkit.broadcastMessage("Could Not Find Text Channel with ID: " + textChannelID);
        }
    }

    public void sendMessage(String message){
        textChannel.sendMessage(message).queue();
    }

}
