package com.gmail.jlmerrett.MinecraftBot;

import org.bukkit.configuration.file.FileConfiguration;

public final class ConfigFile {

    private static FileConfiguration configFile;

    private ConfigFile(){
    }

    public static void addConfig(FileConfiguration configurationFile){
        configFile = configurationFile;
    }

    public static String getString(String messageKey){
        return (String) configFile.get(messageKey);
    }

}
