package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Bot{

    BotMessenger botMessenger;
    DiscordEventListener discordEventListener;

    public Bot() throws LoginException{
        discordEventListener = new DiscordEventListener();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NzMyNTc0Mjk1NTQ4NjI0ODk2.Xw2pEg.GqmK0ufMqH-rxus2i_hIdQXqNdg";
        builder.setToken(token);
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
