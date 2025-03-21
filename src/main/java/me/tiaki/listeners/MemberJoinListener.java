package me.tiaki.listeners;

import me.tiaki.utils.ConfigUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MemberJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String guildId = event.getGuild().getId();

        // Konfiguracja kanału powitalnego
        String welcomeChannelId = ConfigUtils.getConfig(guildId, "welcome-channel");
        TextChannel welcomeChannel = welcomeChannelId != null
                ? event.getGuild().getTextChannelById(welcomeChannelId)
                : null;

        if (welcomeChannel == null) return;

        // Konfiguracja kanału z regulaminem
        String rulesChannelId = ConfigUtils.getConfig(guildId, "rules-channel");
        TextChannel rulesChannel = rulesChannelId != null
                ? event.getGuild().getTextChannelById(rulesChannelId)
                : null;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("👋 Witaj w naszym sklepie!")
                .setDescription(event.getUser().getAsMention() + " właśnie dołączył/a na serwer!")
                .setColor(new Color(161, 22, 196))
                .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                .addField("Zasady", "Przeczytaj regulamin w " +
                        (rulesChannel != null ? rulesChannel.getAsMention() : "#regulamin"), false)
                .setFooter("© 2025 Astra Shop");

        welcomeChannel.sendMessageEmbeds(embed.build()).queue();
    }
}
