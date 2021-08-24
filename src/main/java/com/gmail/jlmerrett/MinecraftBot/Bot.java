package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot{

    BotMessenger botMessenger;
    DiscordEventListener discordEventListener;

    public Bot() throws LoginException{
        discordEventListener = new DiscordEventListener();
        String token = ConfigFile.getString("bot_token");

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(discordEventListener);
        JDA bot = builder.build();
        try {
            bot.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        botMessenger = new BotMessenger(bot);
        discordEventListener.setBotMessenger(botMessenger);
    }


}
