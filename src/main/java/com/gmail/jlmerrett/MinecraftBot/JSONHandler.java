package com.gmail.jlmerrett.MinecraftBot;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONHandler {

    public static void addUserToFile(String discord_id, String minecraft_uuid) throws IOException, JsonException {
        JsonArray linkedIDs;

        try(FileReader fileReader = new FileReader(MinecraftBot.plugin.getDataFolder() + "/discord_id_player_uuid.json")){
            linkedIDs = Jsoner.deserializeMany(fileReader);
        }

        JsonObject newUser = new JsonObject();
        newUser.put("discord_id", discord_id);
        newUser.put("minecraft_uuid", minecraft_uuid);

        linkedIDs.add(newUser);

        try( FileWriter fileWriter = new FileWriter(MinecraftBot.plugin.getDataFolder() + "/discord_id_player_uuid.json")){
            Jsoner.serialize(linkedIDs, fileWriter);
        }
    }

}
