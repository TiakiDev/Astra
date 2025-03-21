package me.tiaki;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.tiaki.commands.*;
import me.tiaki.listeners.*;
import me.tiaki.utils.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            // Wczytaj token z konfiguracji
            String token = loadToken();

            JDA jda = JDABuilder.createDefault(token)
                    .enableIntents(
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MODERATION
                    )
                    .build();

            CommandManager commandManager = new CommandManager();

            // Rejestracja komend
            commandManager.add(new SetupCommand());
            commandManager.add(new ConfigCommand());
            commandManager.add(new InfoCommand());
            commandManager.add(new GiveawayCommand());
            commandManager.add(new RewardConfigCommand());
            commandManager.add(new DailyRewardCommand());
            commandManager.add(new InventoryCommand());
            commandManager.add(new RewardsCommand());

            // Rejestracja listenerów
            jda.addEventListener(commandManager);
            jda.addEventListener(new TicketListener());
            jda.addEventListener(new ModalListener());
            jda.addEventListener(new InteractionListener());
            jda.addEventListener(new MemberJoinListener());
            jda.addEventListener(new ProposalListener());

        } catch (Exception e) {
            System.err.println("BŁĄD INICJALIZACJI BOTA:");
            e.printStackTrace();
        }
    }

    private static String loadToken() throws Exception {
        File configFile = new File("src/main/resources/config.json");

        if (!configFile.exists()) {
            throw new RuntimeException("Brak pliku config.json w folderze resources!");
        }

        JsonObject config = new Gson().fromJson(new FileReader(configFile), JsonObject.class);

        if (!config.has("token")) {
            throw new RuntimeException("Brak tokenu w pliku config.json!");
        }

        return config.get("token").getAsString();
    }
}