package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {

    BotMessenger botMessenger;

    public Bot() throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NzMyNTc0Mjk1NTQ4NjI0ODk2.Xw2pEg.GqmK0ufMqH-rxus2i_hIdQXqNdg";
        builder.setToken(token);
        JDA bot = builder.build();
        try {
            bot.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        botMessenger = new BotMessenger(bot);
    }
}
