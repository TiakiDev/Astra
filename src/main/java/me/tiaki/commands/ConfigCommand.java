package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import me.tiaki.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.util.List;

public class ConfigCommand implements ICommand {

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public String getDescription() {
        return "Konfiguracja bota";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.ROLE, "admin-role", "Rola z uprawnieniami administracyjnymi", false),
                new OptionData(OptionType.CHANNEL, "welcome-channel", "Kanał powitalny", false).setChannelTypes(ChannelType.TEXT),
                new OptionData(OptionType.CHANNEL, "rules-channel", "Kanał z regulaminem", false).setChannelTypes(ChannelType.TEXT),
                new OptionData(OptionType.CHANNEL, "order-category", "Kategoria dla zamówień", false).setChannelTypes(ChannelType.CATEGORY),
                new OptionData(OptionType.CHANNEL, "proposal-channel" , "kanał z propozycjami", false).setChannelTypes(ChannelType.TEXT)

        );
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            EmbedUtils.replyWithError(event, BotConstants.PERMISSION_ERROR);
            return;
        }

        String guildId = event.getGuild().getId();

        if (event.getOption("admin-role") != null) {
            Role role = event.getOption("admin-role").getAsRole();
            ConfigUtils.saveConfig(guildId, "admin-role", role.getId());
        }

        if (event.getOption("welcome-channel") != null) {
            TextChannel channel = event.getOption("welcome-channel").getAsChannel().asTextChannel();
            ConfigUtils.saveConfig(guildId, "welcome-channel", channel.getId());
        }

        if (event.getOption("rules-channel") != null) {
            TextChannel channel = event.getOption("rules-channel").getAsChannel().asTextChannel();
            ConfigUtils.saveConfig(guildId, "rules-channel", channel.getId());
        }

        if (event.getOption("order-category") != null) {
            Category category = event.getOption("order-category").getAsChannel().asCategory();
            ConfigUtils.saveConfig(guildId, "order-category", category.getId());
        }

        if (event.getOption("proposal-channel") != null) {
            TextChannel channel = event.getOption("proposal-channel").getAsChannel().asTextChannel();
            ConfigUtils.saveConfig(guildId, "proposal-channel", channel.getId());
        }

        EmbedUtils.replyWithSuccess(event, "Konfiguracja bota zaktualizowana.", "");
    }
}