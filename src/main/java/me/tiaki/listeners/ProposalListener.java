package me.tiaki.listeners;

import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.time.Instant;

public class ProposalListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String guildId = event.getGuild().getId();

        String proposalChannelId = ConfigUtils.getConfig(guildId, "proposal-channel");
        TextChannel proposalChannel = proposalChannelId != null
                ? event.getGuild().getTextChannelById(proposalChannelId)
                : null;

        // Sprawdź, czy wiadomość pochodzi z odpowiedniego kanału i nie jest od bota
        if (event.getAuthor().isBot() || !event.getChannel().getId().equals(proposalChannelId)) return;

        Message message = event.getMessage();
        User author = event.getAuthor();

        // Tworzenie embeda
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(author.getName(), null, author.getAvatarUrl()) // Używamy getName() zamiast getAsMention()
                .setDescription(message.getContentRaw())
                .setColor(BotConstants.PRIMARY_COLOR)
                .setImage(BotConstants.SEPARATOR_IMAGE)
                .setFooter("Data publikacji ").setTimestamp(Instant.now())
                .build();

        // Wysyłanie embeda
        TextChannel channel = event.getChannel().asTextChannel();
        channel.sendMessage(MessageCreateData.fromEmbeds(embed)).queue(sentMessage -> {
            // Dodawanie reakcji
            sentMessage.addReaction(Emoji.fromFormatted(BotConstants.CHECKMARK_EMOJI)).queue();
            sentMessage.addReaction(Emoji.fromFormatted(BotConstants.MIDDLE_EMOJI)).queue();
            sentMessage.addReaction(Emoji.fromFormatted(BotConstants.CROSS_EMOJI)).queue();
        });

        message.delete().queue();
    }
}