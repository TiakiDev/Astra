package me.tiaki.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {
    private static final String CONFIG_FILE = "botConfig.json";  // Zmieniono ścieżkę
    private static final Gson gson = new Gson();

    public static void saveConfig(String guildId, String key, String value) {
        Map<String, Map<String, String>> config = loadConfig();
        config.computeIfAbsent(guildId, k -> new HashMap<>()).put(key, value);

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String guildId, String key) {
        Map<String, Map<String, String>> config = loadConfig();
        return config.getOrDefault(guildId, new HashMap<>()).get(key);
    }

    private static Map<String, Map<String, String>> loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, new TypeToken<Map<String, Map<String, String>>>() {}.getType());
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}