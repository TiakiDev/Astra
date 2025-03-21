package me.tiaki.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ModalListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String modalId = event.getModalId();

        if (modalId.startsWith("form_")) {
            // Obsługa formularza zamówienia
            String category = modalId.split("_")[1];
            String data = event.getValue("dane").getAsString();

            // Usuń wiadomość powitalną
            event.getChannel().getHistory().retrievePast(10).queue(messages -> {
                for (Message message : messages) {
                    if (message.getEmbeds().size() > 0 &&
                            message.getEmbeds().get(0).getTitle() != null &&
                            message.getEmbeds().get(0).getTitle().contains("Witaj w systemie zamówień!")) {
                        message.delete().queue();
                        break;
                    }
                }
            });

            // Stwórz embed dla administracji
            EmbedBuilder orderEmbed = new EmbedBuilder()
                    .setTitle("<:box:1351555386452475954> NOWE ZAMÓWIENIE!")
                    .setColor(new Color(161, 22, 196))
                    .addField("Kategoria", category.toUpperCase() , false)
                    .addField("Szczegóły", "```\n" + data + "\n```", false)
                    .setImage("https://i.imgur.com/lHkRUg1.png")
                    .setFooter("Zamówienie od: " + event.getUser().getName());

            // Wyślij embed do kanału ticketowego
            event.getChannel().sendMessageEmbeds(orderEmbed.build())
                    .addActionRow(
                            Button.danger("close_order", "❌ Anuluj zamówienie"),
                            Button.primary("edit_order", "📝 Edytuj zamówienie")
                    )
                    .queue();

            // Potwierdź przyjęcie zamówienia
            EmbedBuilder userEmbed = new EmbedBuilder()
                    .setTitle("📩 Dziękujemy za zgłoszenie!")
                    .setColor(0x00FF00)
                    .setDescription("""
                    Twoje zamówienie zostało przyjęte!
                    Administrator skontaktuje się z Tobą w ciągu 24h
                    """)
                    .setFooter("ID zgłoszenia: " + event.getInteraction().getId());

            event.replyEmbeds(userEmbed.build())
                    .setEphemeral(true)
                    .queue();
        } else if (modalId.equals("edit_order_modal")) {
            // Obsługa edycji zamówienia
            String newDetails = event.getValue("new_details").getAsString();

            // Zaktualizuj embed z zamówieniem
            Message message = event.getMessage();
            if (message.getEmbeds().isEmpty()) return;

            // Pobierz istniejący embed
            EmbedBuilder updatedEmbed = new EmbedBuilder(message.getEmbeds().get(0));

            // Wyczyść pola i dodaj je ponownie z nowymi szczegółami
            updatedEmbed.clearFields()
                    .addField("Kategoria", message.getEmbeds().get(0).getFields().get(0).getValue(), false) // Zachowaj kategorię
                    .addField("Szczegóły", "```\n" + newDetails + "\n```", false); // Zaktualizuj szczegóły

            message.editMessageEmbeds(updatedEmbed.build()).queue();

            EmbedBuilder confirmationEmbed = new EmbedBuilder()
                    .setTitle("✅ Szczegóły zamówienia zostały zaktualizowane!")
                    .setColor(0x00FF00)
                    .setDescription("");
            // Potwierdź edycję
            event.replyEmbeds(confirmationEmbed.build())
                    .setEphemeral(true)
                    .queue();
        }
    }
}