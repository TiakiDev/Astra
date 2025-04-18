
package me.tiaki.listeners;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

public class InteractionListener extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        try {
            if (!event.getComponentId().equals("select_category")) return;

            String category = event.getValues().get(0);

            // Poprawiona nazwa pola i placeholder
            TextInput input = TextInput.create("dane", "Informacje", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("""
                    Budżet: ...
                    Posiadane gry/skiny: ...
                    Metoda płatnosci: ...
                    """)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("form_" + category, "Zamówienie - " + category.toUpperCase())
                    .addActionRow(input)
                    .build();

            event.replyModal(modal).queue();

        } catch (Exception e) {
            event.reply("❌ Wystąpił błąd: " + e.getMessage())
                    .setEphemeral(true)
                    .queue();
        }
    }
}