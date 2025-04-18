package me.tiaki;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.tiaki.commands.*;
import me.tiaki.listeners.*;
import me.tiaki.utils.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class Main {
    private static JDA jda;
    private static HttpServer server;

    public static void main(String[] args) {
        try {
            // Wczytaj token z konfiguracji
            String token = loadToken();

            // Uruchom serwer HTTP
            startHttpServer();

            // Zbuduj bota
            jda = JDABuilder.createDefault(token)
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
            commandManager.add(new HelpCommand());

            // Rejestracja listenerów
            jda.addEventListener(commandManager);
            jda.addEventListener(new TicketListener());
            jda.addEventListener(new ModalListener());
            jda.addEventListener(new InteractionListener());
            jda.addEventListener(new MemberJoinListener());
            jda.addEventListener(new ProposalListener());

            // Dodaj hook do bezpiecznego zamykania
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (jda != null) jda.shutdown();
                if (server != null) server.stop(0);
            }));

        } catch (Exception e) {
            System.err.println("BŁĄD INICJALIZACJI BOTA:");
            e.printStackTrace();
            try {
                Thread.sleep(10000);
                main(args);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void startHttpServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            String response = "Bot działa!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        server.start();
        System.out.println("Serwer HTTP uruchomiony na porcie 8080");
    }

    private static String loadToken() throws Exception {
        // Użyj ClassLoader, aby wczytać plik jako zasób
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.json");

        if (inputStream == null) {
            throw new RuntimeException("Brak pliku config.json w folderze resources!");
        }

        // Wczytaj zawartość pliku
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JsonObject config = new Gson().fromJson(reader, JsonObject.class);

        if (!config.has("token")) {
            throw new RuntimeException("Brak tokenu w pliku config.json!");
        }

        return config.get("token").getAsString();
    }
}