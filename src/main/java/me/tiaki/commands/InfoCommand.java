package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import me.tiaki.utils.RewardConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.awt.*;
import java.util.List;

public class InfoCommand implements ICommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "shows some info";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild().getId();

        // Sprawdź uprawnienia
        String adminRoleId = ConfigUtils.getConfig(guildId, "admin-role");
        Role adminRole = event.getGuild().getRoleById(adminRoleId);

        if (!event.getMember().getRoles().contains(adminRole)) {
            event.reply("❌ Nie masz uprawnień do użycia tej komendy.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        // Pobierz kanał
        String welcomeChannelID = ConfigUtils.getConfig(guildId, "welcome-channel");
        TextChannel welcomeChannel = welcomeChannelID != null
                ? event.getGuild().getTextChannelById(welcomeChannelID)
                : null;

        // Pobierz kanał
        String rulesChannelID = ConfigUtils.getConfig(guildId, "rules-channel");
        TextChannel rulesChannel = rulesChannelID != null
                ? event.getGuild().getTextChannelById(rulesChannelID)
                : null;

        // Pobierz kategorię
        String categoryId = ConfigUtils.getConfig(guildId, "order-category");
        Category orderCategory = categoryId != null
                ? event.getGuild().getCategoryById(categoryId)
                : null;

        //pobierz kanał
        String proposalChannelId = ConfigUtils.getConfig(guildId, "proposal-channel");
        TextChannel proposalChannel = proposalChannelId != null
                ? event.getGuild().getTextChannelById(proposalChannelId)
                : null;

        //pobierz kanał
        String rewardChannelId = RewardConfig.getGuildConfig(guildId).channelId;
        TextChannel rewardChannel = rewardChannelId != null
                ? event.getGuild().getTextChannelById(rewardChannelId)
                : null;

        // Stwórz embed
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("⚙️ Aktualna konfiguracja")
                .setColor(BotConstants.PRIMARY_COLOR)
                .addField("Rola administracyjna",
                        adminRole != null ? adminRole.getAsMention() : "Nie ustawiono",
                        false)
                .addField("Kanał wiadomości powitalnych",
                        welcomeChannel != null ? welcomeChannel.getAsMention() : "Nie ustawiono",
                        false)
                .addField("Kanał z regulaminem",
                        rulesChannel != null ? rulesChannel.getAsMention() : "Nie ustawiono",
                        false)
                .addField("Kanał propozycji",
                        proposalChannel != null ? proposalChannel.getAsMention() : "Nie ustawiono",
                        false)
                .addField("Kanał dziennych nagród",
                        rewardChannel != null ? rewardChannel.getAsMention() : "Nie ustawiono",
                        false)
                .addField("Kategoria zamówień",
                        orderCategory != null ? "`" + orderCategory.getName() + "`" : "Nie ustawiono",
                        false)
                .setFooter("Ustawienia bota | " + event.getGuild().getName())
                .setImage("https://i.imgur.com/lHkRUg1.png");

        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
    }
}
