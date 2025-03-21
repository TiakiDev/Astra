package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.RewardConfig;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.util.*;
import me.tiaki.utils.RewardConfig;

public class RewardConfigCommand implements ICommand {

    @Override
    public String getName() { return "rewards-config"; }

    @Override
    public String getDescription() { return "Konfiguracja systemu nagród dziennych"; }

    @Override
    public List<OptionData> getOptions() {
        return Arrays.asList(
                new OptionData(OptionType.STRING, "action", "Akcja do wykonania", true)
                        .addChoice("set_channel", "set_channel")
                        .addChoice("add_reward", "add_reward")
                        .addChoice("remove_reward", "remove_reward"),
                new OptionData(OptionType.STRING, "nazwa", "Nazwa nagrody", false),
                new OptionData(OptionType.NUMBER, "szansa", "Szansa w procentach", false)
        );
    }

    @Override
    public List<SubcommandData> getSubcommands() { return Collections.emptyList(); }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("❌ Brak uprawnień!").setEphemeral(true).queue();
            return;
        }

        String action = event.getOption("action").getAsString();
        String guildId = event.getGuild().getId();
        RewardConfig.GuildConfig config = RewardConfig.getGuildConfig(guildId);

        switch (action) {
            case "set_channel": handleSetChannel(event, config, guildId); break;
            case "add_reward": handleAddReward(event, config, guildId); break;
            case "remove_reward": handleRemoveReward(event, config, guildId); break;
        }
    }

    private void handleSetChannel(SlashCommandInteractionEvent event, RewardConfig.GuildConfig config, String guildId) {
        TextChannel channel = event.getChannel().asTextChannel();
        config.channelId = channel.getId();
        RewardConfig.saveGuildConfig(guildId, config);
        event.reply("✅ Kanał nagród ustawiony na: " + channel.getAsMention()).queue();
    }

    private void handleAddReward(SlashCommandInteractionEvent event, RewardConfig.GuildConfig config, String guildId) {
        String name = event.getOption("nazwa").getAsString();
        double chance = event.getOption("szansa").getAsDouble();

        if (chance <= 0 || chance > 100) {
            event.reply("❌ Nieprawidłowa szansa (0-100)!").setEphemeral(true).queue();
            return;
        }

        config.chances.put(name, chance);
        RewardConfig.saveGuildConfig(guildId, config);
        event.reply("✅ Dodano nagrodę: **" + name + "** (" + chance + "%)").queue();
    }

    private void handleRemoveReward(SlashCommandInteractionEvent event, RewardConfig.GuildConfig config, String guildId) {
        String name = event.getOption("nazwa").getAsString();

        if (config.chances.containsKey(name)) {
            config.chances.remove(name);
            RewardConfig.saveGuildConfig(guildId, config);
            event.reply("✅ Usunięto nagrodę: **" + name + "**").queue();
        } else {
            event.reply("❌ Nagroda nie istnieje!").setEphemeral(true).queue();
        }
    }
}