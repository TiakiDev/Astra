package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import me.tiaki.utils.RewardConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


public class InventoryCommand implements ICommand {


    @Override
    public String getName() { return "inventory"; }

    @Override
    public String getDescription() { return "Pokazuje twoje zebrane nagrody"; }

    @Override
    public List<OptionData> getOptions() { return Collections.emptyList(); }

    @Override
    public List<SubcommandData> getSubcommands() { return Collections.emptyList(); }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild().getId();
        String userId = event.getUser().getId();

        RewardConfig.GuildConfig config = RewardConfig.getGuildConfig(guildId);
        Map<String, Integer> inventory = config.userInventories.getOrDefault(userId, new HashMap<>());

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(BotConstants.PRIMARY_COLOR)
                .setTitle("🎒 Ekwipunek " + event.getUser().getName())
                .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                .setImage(BotConstants.SEPARATOR_IMAGE)
                .setFooter("Ekwipunek • " + event.getGuild().getName());

        if (inventory.isEmpty()) {
            embed.setDescription("Twój ekwipunek jest pusty!\nZdobywaj nagrody używając `/daily`");
        } else {
            StringBuilder content = new StringBuilder();
            inventory.forEach((reward, count) ->
                    content.append(String.format("▸ %s **x%d**\n", reward, count))
            );
            embed.setDescription("**Twoje nagrody:**\n" + content);
        }

        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
    }
}