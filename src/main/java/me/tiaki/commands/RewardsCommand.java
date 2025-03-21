package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.RewardConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class RewardsCommand implements ICommand {

    private static final Color EMBED_COLOR = new Color(161, 22, 196);

    @Override
    public String getName() {
        return "rewards";
    }

    @Override
    public String getDescription() {
        return "Pokazuje listę dostępnych nagród";
    }

    @Override
    public List<OptionData> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return Collections.emptyList();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild().getId();
        RewardConfig.GuildConfig config = RewardConfig.getGuildConfig(guildId);
        Map<String, Double> chances = config.chances;

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(EMBED_COLOR)
                .setTitle("🎁 Dostępne nagrody")
                .setImage("https://i.imgur.com/lHkRUg1.png")
                .setFooter("Szanse na wygraną • " + event.getGuild().getName());

        if (chances.isEmpty()) {
            embed.setDescription("Brak skonfigurowanych nagród!\nAdministrator może dodać je komendą `/rewards-config`");
        } else {
            double totalChance = chances.values().stream().mapToDouble(Double::doubleValue).sum();
            StringBuilder rewardsList = new StringBuilder();

            chances.forEach((reward, chance) ->
                    rewardsList.append(String.format("▸ **%s** - %.2f%%\n", reward, chance))
            );

            embed.addField("Lista nagród (" + chances.size() + ")", rewardsList.toString(), false)
                    .addField("Całkowita szansa", String.format("%.2f%%", totalChance), true)
                    .addField("Szansa na brak nagrody", String.format("%.2f%%", 100 - totalChance), true);
        }

        event.replyEmbeds(embed.build()).queue();
    }
}