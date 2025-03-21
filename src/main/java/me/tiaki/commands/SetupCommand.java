package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.ConfigUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.awt.*;
import java.time.Instant;
import java.util.List;

public class SetupCommand implements ICommand {

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Inicjuje panel zamówieniowy sklepu";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of(
                new SubcommandData("shop", "Inicjuje panel zamówieniowy sklepu"),
                new SubcommandData("rules", "Inicjuje panel z regulaminem sklepu")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String adminRoleId = ConfigUtils.getConfig(event.getGuild().getId(), "admin-role");
        Role adminRole = event.getGuild().getRoleById(adminRoleId);

        if (!event.getMember().getRoles().contains(adminRole)) {
            event.reply("❌ Nie masz uprawnień do użycia tej komendy.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if(event.getSubcommandName().equals("shop")) {

            try {
                EmbedBuilder shopEmbed = new EmbedBuilder()
                        .setTitle(" <:astra:1351298307875668119>  **SKLEP PREMIUM**")
                        .setColor(new Color(161, 22, 196))
                        .setDescription("""
                         ```ansi
                         [0;35m▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                         [0;35m           🛒 WYBIERZ SWÓJ PRODUKT
                         [0;35m▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                         ```""")
                        .addField("<:steam:1351298003566334056> **Konta Steam**",
                                "```• Pełny dostęp\n• Dowolne gry\n• Dostawa w ciągu 24h```", true)
                        .addField("<:Riot:1351551585242845236> **Konta Riot games**",
                                "```• Pełny dostęp\n• Wilele skinów\n• Dostawa w ciągu 24h```", true)
                        .addField("<:shield:1351550697912664084> Gwarancje",
                                "```diff\n+ 100% bezpieczeństwa\n+ Support 24/7\n+ Zwroty do 14 dni\n```", false)
                        .setImage("https://s6.gifyu.com/images/bzbai.gif")
                        .setFooter("© 2025 Astra Shop | Wersja 1.0.0", event.getGuild().getIconUrl())
                        .setTimestamp(Instant.now());

                event.replyEmbeds(shopEmbed.build())
                        .addActionRow(
                                Button.success("create_order", "🛒 Rozpocznij zamówienie")
                                        .withEmoji(Emoji.fromUnicode("📦")),
                                Button.link("https://pastebin.com/raw/tRSUac5S", "📜 Regulamin sklepu")
                        )
                        .queue();

            } catch (Exception e) {
                EmbedBuilder errorEmbed = new EmbedBuilder()
                        .setTitle("⚠️ Błąd systemu zamówień")
                        .setColor(new Color(220, 20, 60))
                        .setDescription("```prolog\n" + e.getMessage() + "\n```")
                        .addField("Naprawa w toku", "⚙ Zespół techniczny został powiadomiony", false)
                        .setFooter("ID Błędu: " + event.getInteraction().getId());

                event.replyEmbeds(errorEmbed.build())
                        .setEphemeral(true)
                        .queue();
            }

        }
        if (event.getSubcommandName().equals("rules")) {

            EmbedBuilder rulesEmbed = new EmbedBuilder();

            rulesEmbed
                    .setTitle("<:book:1351949486519488532> Regulamin Serwera Astra Shop")
                    .setColor(new Color(161, 22, 196))
                    .setDescription("Poniżej znajdziesz zasady obowiązujące na naszym serwerze. Przestrzegaj ich, aby zapewnić wszystkim przyjemną atmosferę!")
                    .addField("1. Postanowienia Ogólne", """
                            1.1. Niniejszy regulamin określa zasady korzystania z serwera Discord.
                            1.2. Dołączając do serwera, użytkownik zobowiązuje się do przestrzegania niniejszego regulaminu.
                            1.3. Administracja zastrzega sobie prawo do zmiany regulaminu w dowolnym momencie.
                            """, false)
                    .addField("2. Zasady Ogólne", """
                            2.1. Szanuj wszystkich użytkowników serwera.
                            2.2. Zakazane jest spamowanie, floodowanie oraz wysyłanie treści niezwiązanych z tematem kanału.
                            2.3. Zakazane jest udostępnianie treści NSFW, wulgarnych lub niezgodnych z prawem.
                            """, false)
                    .addField("3. Kanały Głosowe i Tekstowe", """
                            3.1. Używaj odpowiednich kanałów do odpowiednich tematów.
                            3.2. Zakazane jest nagrywanie lub transmitowanie rozmów bez zgody uczestników.
                            """, false)
                    .addField("4. Prywatność i Bezpieczeństwo", """
                            4.1. Zakazane jest udostępnianie danych osobowych.
                            4.2. Zakazane jest wysyłanie linków do phishingowych lub niebezpiecznych stron.
                            """, false)
                    .addField("5. Administracja i Moderacja", """
                            5.1. Decyzje administracji i moderatorów są ostateczne.
                            5.2. W przypadku problemów, skontaktuj się z administracją.
                            """, false)
                    .addField("6. Zasady Dotyczące Reklam", """
                            6.1. Zakazane jest wysyłanie niechcianych reklam.
                            6.2. Reklamowanie własnych treści jest dozwolone tylko za zgodą administracji.
                            """, false)
                    .addField("7. Postanowienia Końcowe", """
                            7.1. Nieznajomość regulaminu nie zwalnia z jego przestrzegania.
                            7.2. W przypadku poważnych naruszeń, administracja zastrzega sobie prawo do natychmiastowego usunięcia użytkownika.
                            """, false)
                    .setThumbnail("https://s6.gifyu.com/images/bzKiw.gif")
                    .setFooter("© 2025 Astra Shop | Ostatnia aktualizacja: " ).setTimestamp(Instant.now());

            event.replyEmbeds(rulesEmbed.build()).queue();
        }
    }
}