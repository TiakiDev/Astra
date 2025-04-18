package me.tiaki.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.awt.*;
import java.time.Instant;

public class BotConstants {

    // Colors
    public static final Color PRIMARY_COLOR = new Color(161, 22, 196);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color ERROR_COLOR = new Color(231, 76, 60);
    public static final Color WARNING_COLOR = new Color(241, 196, 15);

    // Emojis
    public static final String WALLET_EMOJI = "<:wallet:1360228383002198169>";
    public static final String PAYPAL_EMOJI = "<:PayPal:1360223553118802070>";
    public static final String BLIK_EMOJI = "<:Blik:1360223577919590460>";
    public static final String BTC_EMOJI = "<:btc:1360223816101658624>";
    public static final String LTC_EMOJI = "<:ltc:1360224171954671806>";
    public static final String ETH_EMOJI = "<:eth:1360223794660245693>";
    public static final String CHECKMARK_EMOJI = "<:checkmark:1351969481978806383>";
    public static final String QUESTION_MARK_EMOJI = "<:questionMark:1352397897810837649>";
    public static final String SHIELD_EMOJI = "<:shield:1351550697912664084>";
    public static final String BOOK_EMOJI = "<:book:1351949486519488532>";
    public static final String LAPTOP_EMOJI = "<:laptop:1360227944202768616>";
    public static final String MIDDLE_EMOJI = "<:middle:1351969466916798625>";
    public static final String CROSS_EMOJI = "<:cross:1351969501541040159>";

    // Footer Text
    public static final String FOOTER_TEXT = "© 2025 Astra Shop | %s";

    // Payment Methods
    public static final String PAYPAL_LINK = "https://paypal.me/AstraPP";
    public static final String BLIK_LINK = "https://tipply.pl/@AstraShop";

    // Crypto Addresses
    public static final String BTC_ADDRESS = "bc1qejclvfcxa8cpdxps3na3avyxj6kgpwwq454aw5";
    public static final String LTC_ADDRESS = "LiJWnjN6vnPz7LScPWSGdzUnnp6UgjWWEd";
    public static final String ETH_ADDRESS = "0xF0b62Ecc9977E0E2Ef5Be16e433fFB8e0db3Ae00";

    // Images
    public static  final String SEPARATOR_IMAGE = "https://i.imgur.com/lHkRUg1.png";

    // Error Messages
    public static final String PERMISSION_ERROR = "Nie masz uprawnień do wykonania tej akcji!";
    public static final String TICKET_ERROR = "Wystąpił błąd podczas przetwarzania ticketu. Spróbuj ponownie później.";
    public static final String COMMAND_ERROR = "Wystąpił błąd podczas wykonywania komendy. Spróbuj ponownie później.";
    public static final String CONFIG_ERROR = "Wystąpił błąd podczas ładowania konfiguracji.";


    //All embeds

    //* SHOP EMBED ---------------------------------------------------------

    public static final EmbedBuilder SHOP_EMBED = new EmbedBuilder()
            .setTitle(" <:astra:1351298307875668119>  **SKLEP PREMIUM**")
            .setColor(BotConstants.PRIMARY_COLOR)
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
                    "```diff\n+ 100% bezpieczeństwa\n+ Support 24/7\n+ Zwroty do 24h\n```", false)
            .setImage("https://s6.gifyu.com/images/bzbai.gif")
            .setFooter("© 2025 Astra Shop | Wersja 1.0.0")
            .setTimestamp(Instant.now());

    //* RULES EMBED ---------------------------------------------------------

    public static final EmbedBuilder RULES_EMBED = new EmbedBuilder()
            .setTitle("<:book:1351949486519488532> Regulamin Serwera Astra Shop")
            .setColor(BotConstants.PRIMARY_COLOR)
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

    //* FAQ EMBED ---------------------------------------------------------

    public static final EmbedBuilder FAQ_EMBED = new EmbedBuilder()

            .setTitle( BotConstants.QUESTION_MARK_EMOJI + " FAQ: Role za Zaproszenia")
            .setColor(BotConstants.PRIMARY_COLOR)
            .addField("**Jak zdobyć rolę?**",
                                      "Zaproś znajomych swoim linkiem.\n" +
                                      "Role dostajesz automatycznie po osiągnięciu progu (np. 3 zaproszeń).", false)
            .addField("**Gdzie sprawdzić statystyki?**",
                                      "Użyj `/invites` – pokaże twoje zaproszenia i aktualne nagrody.", false)

            .addField("**Co dają role?**",
                                      """
                            ▸ Dostęp do giveawayów
                            ▸ Większe szanse w `/daily`
                            ▸ masz jeszcze jakieś pomysły? Pisz w #「🔧」propozycje]""", false)

            .addField("**Uwaga!**",
                                      """
                            ❗ Nadużycia (fake konta) = **ban i utrata rang**
                            ❗ Zaproszenie traci ważność jeśli osoba wyjdzie z serwera""", false)

            .addField("**Komendy:**",
                                      """
                            • `/invites` – twoje statystyki""", false)

            .addField("**Dostępne role:**",
                                      """
                            **🎖️ Zapraszający LV1** - 3 Zaproszenia
                            **🥈 Zapraszający LV2** - 6 Zaproszeń
                            **🏆 Mistrz Zaproszeń** - 12 Zaproszeń
                            **🔗 Ambasador Serwera** - 20 Zaproszeń""", false)
            .setFooter("Masz więcej pytań? Zwróć się do administracji!");

    //* SHOP RULES EMBED ---------------------------------------------------------

    public static final EmbedBuilder SHOP_RULES_EMBED = new EmbedBuilder()
            .setTitle(BotConstants.BOOK_EMOJI + " Regulamin Sklepu")
            .setColor(BotConstants.PRIMARY_COLOR)
            .setDescription("""
        **1. Postanowienia Ogólne**
        1.1. Sklep Astra Shop oferuje usługi związane z zakupem kont gamingowych, skinów oraz innych produktów cyfrowych.
        1.2. Korzystanie z usług sklepu jest równoznaczne z akceptacją niniejszego regulaminu.
        1.3. Wszystkie transakcje są przeprowadzane zgodnie z zasadami określonymi w regulaminie.


        **2. Zamówienia**
        2.1. Zamówienia można składać **wyłącznie** poprzez oficjalny system ticketów na serwerze Discord.
        2.2. Każde zamówienie musi zawierać:
        - Kategorię produktu (np. Konto Steam, Konto Riot Games),
        - Szczegóły zamówienia (np. budżet, typ konta, preferowane gry/skiny).
        2.3. Po złożeniu zamówienia klient otrzyma potwierdzenie i zostanie poinformowany o dalszych krokach.


        **3. Płatności**
        3.1. **Akceptowane metody płatności**: PayPal, Kryptowaluty, Blik, Karta płatnicza.
        3.2. Płatność musi zostać uregulowana **w ciągu 24 godzin** od potwierdzenia zamówienia.
        3.3. W przypadku braku płatności w wyznaczonym terminie, zamówienie zostanie anulowane.


        **4. Dostawa**
        4.1. Czas realizacji zamówienia wynosi **do 24 godzin** od momentu potwierdzenia płatności.
        4.2. Po zrealizowaniu zamówienia klient otrzyma:
        - Dane do konta,
        - Lub kod aktywacyjny
        4.3. W przypadku opóźnień klient zostanie poinformowany o nowym przewidywanym czasie dostawy.


        **5. Gwarancja i Zwroty**
        5.1. Na produkty obowiązuje **domyślnie** gwarancja 24h po zakupie.
        **Sprzedawcy mają prawo modyfikować warunki gwarancji** (np. skrócić/wydłużyć jej okres) w opisie produktu lub podczas finalizacji transakcji.
        5.2. **Gwarancja automatycznie przepada w przypadku**:
        - Zmiany hasła do zakupionego konta,
        - Zmiany adresu email powiązanego z kontem,
        - Naruszenia zasad platformy, na której konto zostało zarejestrowane.
        5.3. Zwrot pieniędzy lub wymiana produktu są możliwe **wyłącznie jeśli klient nie naruszył warunków z pkt 5.2**.
        5.4. Zwroty należy zgłaszać poprzez system ticketów **w terminie obowiązywania gwarancji**.


        **6. Odpowiedzialność**
        6.1. Sklep Astra Shop **nie ponosi odpowiedzialności** za:
        - Nieprawidłowe użycie produktu przez klienta,
        - Bany nałożone przez platformy gamingowe (np. Steam/Riot),
        - Utratę gwarancji z powodu działań klienta (np. zmiany danych konta).
        6.2. **Klient zobowiązuje się do**:
        - Niezmieniania hasła lub emaila konta w okresie gwarancji,
        - Nieudostępniania danych konta osobom trzecim.


        **7. Prywatność**
        7.1. Wszystkie dane klientów są przechowywane zgodnie z **RODO**.
        7.2. Sklep nie udostępnia danych klientów osobom trzecim bez ich zgody.


        **8. Postanowienia Końcowe**
        8.1. Sklep zastrzega sobie prawo do zmiany regulaminu. Aktualizacje będą publikowane na serwerze Discord.
        8.2. W przypadku sporów decydujące są zapisy niniejszego regulaminu.
        8.3. Kontakt z administracją: **system ticketów na serwerze Discord**.
        """)
            .setFooter("© 2025 Astra Shop | Wszystkie prawa zastrzeżone");

    //* LEGIT CHECK EMBED ---------------------------------------------------------

    public static final EmbedBuilder LEGIT_CHECK_EMBED = new EmbedBuilder()
            .setTitle(BotConstants.CHECKMARK_EMOJI + " Formułka Legit Check")
            .setColor(BotConstants.PRIMARY_COLOR)
            .setDescription("""
                        Użyj poniższej formułki, aby potwierdzić, że transakcja była **bezpieczna**:

                        ```diff
                        +rep [@ping] [Kategoria] : [Gra]  [Kwota]
                        ```

                        <:questionMark:1352397897810837649> Przykład użycia:
                        ```md
                        +rep @Tiaki Steam Account : The Forest 20 PLN
                        ```

                        <:shield:1351550697912664084> *Upewnij się, że wpisujesz wszystko poprawnie – to ważne przy rozwiązywaniu ewentualnych sporów.*
                        """)
            .setFooter("© 2025 Astra Shop | Sprawdź przed zakupem!");

    //* PAYMENTS EMBED ---------------------------------------------------------

    public static final EmbedBuilder PAYMENTS_EMBED = new EmbedBuilder()
            .setTitle(BotConstants.WALLET_EMOJI + " Metody Płatności")
            .setDescription("Poniżej znajdziesz listę dostępnych metod płatności. Wybierz swoją ulubioną opcję! \n ‎")
            .setColor(BotConstants.PRIMARY_COLOR)

            .addField(
                    BotConstants.PAYPAL_EMOJI + " PayPal",
                    "[Kliknij tutaj](https://paypal.me/AstraPP) \n ‎",
                    true
                    )
            .addField(
                    BotConstants.BLIK_EMOJI + " BLIK/Karta",
                    "[Kliknij tutaj](https://tipply.pl/@AstraShop) \n ‎ ",
                    true
                    )

            .addField(BotConstants.LAPTOP_EMOJI + " Kryptowaluty",                     """
                            ‎\s
                            <:btc:1360223816101658624>:  `bc1qejclvfcxa8cpdxps3na3avyxj6kgpwwq454aw5`
                            <:ltc:1360224171954671806>:  `LiJWnjN6vnPz7LScPWSGdzUnnp6UgjWWEd`
                            <:eth:1360223794660245693>:  `0xF0b62Ecc9977E0E2Ef5Be16e433fFB8e0db3Ae00`
                            """, false)
            .setFooter("© 2025 Astra Shop | Bezpieczne transakcje");

    //*  EMBED ---------------------------------------------------------
}
