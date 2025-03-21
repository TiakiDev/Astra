package me.tiaki.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RewardConfig {
    private static final String REWARD_FILE = "src/main/resources/rewards.json";
    private static final Gson gson = new Gson();
    private static final TypeToken<Map<String, GuildConfig>> typeToken =
            new TypeToken<Map<String, GuildConfig>>() {};

    public static class GuildConfig {
        public String channelId;
        public Map<String, Double> chances = new HashMap<>();
        public Map<String, Long> lastClaims = new HashMap<>();
        public Map<String, Map<String, Integer>> userInventories = new HashMap<>();
    }

    public static void saveGuildConfig(String guildId, GuildConfig config) {
        Map<String, GuildConfig> allConfigs = loadAllConfigs();
        allConfigs.put(guildId, config);

        try (FileWriter writer = new FileWriter(REWARD_FILE)) {
            gson.toJson(allConfigs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GuildConfig getGuildConfig(String guildId) {
        Map<String, GuildConfig> allConfigs = loadAllConfigs();
        return allConfigs.computeIfAbsent(guildId, k -> new GuildConfig());
    }

    private static Map<String, GuildConfig> loadAllConfigs() {
        try (FileReader reader = new FileReader(REWARD_FILE)) {
            return gson.fromJson(reader, typeToken.getType());
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}