package me.tiaki.listeners;

import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import me.tiaki.utils.EmbedUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import java.util.EnumSet;
import net.dv8tion.jda.api.EmbedBuilder;

public class TicketListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("create_order")) {
            Member member = event.getMember();
            if (member == null) return;

            String categoryId = ConfigUtils.getConfig(event.getGuild().getId(), "order-category");

            Category category = categoryId != null
                    ? event.getGuild().getCategoryById(categoryId)
                    : null;

            event.getGuild().createTextChannel("📦" + member.getEffectiveName())
                    .setParent(category)
                    .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                    .addPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .queue(channel -> setupTicketChannel(event, channel));
        }

        if (event.getComponentId().equals("close_order")) {
            event.getChannel().delete().queue();
        }

        if (event.getComponentId().equals("edit_order")) {
            // Pobierz aktualne szczegóły zamówienia z embeda
            Message message = event.getMessage();
            if (message.getEmbeds().isEmpty()) return;

            String currentDetails = message.getEmbeds().get(0).getFields().get(1).getValue(); // Pobierz szczegóły z embeda

            // Stwórz modal do edycji zamówienia
            TextInput details = TextInput.create("new_details", "Nowe szczegóły zamówienia", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Wprowadź nowe szczegóły...")
                    .setValue(currentDetails.replace("```", "").trim()) // Usuń formatowanie kodu
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("edit_order_modal", "Edytuj zamówienie")
                    .addActionRow(details)
                    .build();

            event.replyModal(modal).queue();
        }
    }

    private void setupTicketChannel(ButtonInteractionEvent event, TextChannel channel) {
        // Tworzenie embeda powitalnego
        EmbedBuilder welcomeEmbed = new EmbedBuilder()
                .setTitle("<:astra:1351298307875668119> Witaj w systemie zamówień!")
                .setColor(BotConstants.PRIMARY_COLOR)
                .setDescription("Wybierz kategorię poniżej aby kontynuować:")
                .addField("Dostępne opcje", "• Konto Steam\n• Konto Riot Games\n• Inne", false)
                .setFooter("System zamówień • v1.0")
                .setThumbnail("https://i.imgur.com/hcEYuz1.png");

        StringSelectMenu menu = StringSelectMenu.create("select_category")
                .addOptions(
                        SelectOption.of("Konto Steam", "steam")
                                .withEmoji(Emoji.fromFormatted("<:steam:1351298003566334056>")),
                        SelectOption.of("Konto Riot Games", "riot")
                                .withEmoji(Emoji.fromFormatted("<:Riot:1351551585242845236>")),
                        SelectOption.of("Inne", "other")
                                .withEmoji(Emoji.fromFormatted("<:questionMark:1352397897810837649>"))
                ).build();

        channel.sendMessageEmbeds(welcomeEmbed.build())
                .addActionRow(menu)
                .queue();

        EmbedBuilder confirmationEmbed = new EmbedBuilder()
                .setTitle("✅ Zamówienie utworzone!")
                .setColor(BotConstants.SUCCESS_COLOR)
                .setDescription("Twoje zamówienie: " + channel.getAsMention())
                .addField("Co dalej?", "1. Wybierz kategorię z listy\n2. Wypełnij formularz\n3. Czekaj na kontakt administracji", false)
                .setImage(BotConstants.SEPARATOR_IMAGE);

        event.replyEmbeds(confirmationEmbed.build())
                .setEphemeral(true)
                .queue();
    }
}