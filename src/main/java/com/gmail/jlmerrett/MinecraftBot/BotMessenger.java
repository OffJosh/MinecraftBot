package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.JDA;

public class BotMessenger {

    JDA bot;

    public BotMessenger(JDA bot){
        this.bot = bot;

    }

    public void sendMessage(String message){
        bot.getGuildById("709178638624620574").getTextChannelById("709178638624620577").sendMessage(message).queue();
    }

}
