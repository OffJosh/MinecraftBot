package com.gmail.jlmerrett.MinecraftBot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordEventListener extends ListenerAdapter {

    BotMessenger botMessenger;

    public DiscordEventListener(){
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ram")){
            Runtime r = Runtime.getRuntime();
            botMessenger.sendMessage("Total: " + r.totalMemory()/1048576 + "mB");
            botMessenger.sendMessage("Used: " + (r.totalMemory() - r.freeMemory())/1048576 + "mB");
            botMessenger.sendMessage("Free: " + r.freeMemory()/1048576 + "mB");
        }

    }

    public void setBotMessenger (BotMessenger botMessenger){
        this.botMessenger = botMessenger;
    }

}
