package me.tiaki.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.util.List;

public class EmbedUtils {


    public static void replyWithEmbed(SlashCommandInteractionEvent event, String title, String description, Color color, String footer, List<Button> buttons) {
        MessageEmbed embed = createEmbed(title, description, color, footer);
        MessageCreateData message = new MessageCreateBuilder()
                .setEmbeds(embed)
                .setActionRow(buttons)
                .build();

        event.reply(message).queue();
    }

    public static void replyWithError(SlashCommandInteractionEvent event, String errorMessage) {
        MessageEmbed embed = createEmbed("❌ Błąd", errorMessage, BotConstants.ERROR_COLOR, null);
        event.replyEmbeds(embed).setEphemeral(true).setEphemeral(true).queue();
    }

    public static void replyWithSuccess(SlashCommandInteractionEvent event, String title, String message) {
        MessageEmbed embed = createEmbed("✅ " + title, message, BotConstants.SUCCESS_COLOR, null);
        event.replyEmbeds(embed).setEphemeral(true).queue();
    }

    public static void replyWithWarning(SlashCommandInteractionEvent event, String title, String message) {
        MessageEmbed embed = createEmbed("⚠️ " + title, message, BotConstants.WARNING_COLOR, null);
        event.replyEmbeds(embed).setEphemeral(true).queue();
    }

    public static MessageEmbed createEmbed(String title, String description, Color color, String footer) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setImage(BotConstants.SEPARATOR_IMAGE);

        if (footer != null) {
            embed.setFooter("© 2025 Astra Shop | " + footer);
        }
        else {
            embed.setFooter("© 2025 Astra Shop");
        }

        return embed.build();
    }
}