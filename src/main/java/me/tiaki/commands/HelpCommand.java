package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.util.List;

public class HelpCommand implements ICommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Komenda pomocnicza";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of(
                new SubcommandData("payments", "Pokazuje dostępne metody płatności"),
                new SubcommandData("legitcheck", "Pokazuje informacje o formułce na legit check"),
                new SubcommandData("shop", "Pokazuje regulamin sklepu")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event.getSubcommandName() == null) return;

        if (event.getSubcommandName().equals("payments")) {

            event.replyEmbeds(BotConstants.PAYMENTS_EMBED.build()).queue();
        }
        if(event.getSubcommandName().equals("legitcheck")) {

            event.replyEmbeds(BotConstants.LEGIT_CHECK_EMBED.build()).queue();
        }

        if (event.getSubcommandName().equals("shop")) {

            event.replyEmbeds(BotConstants.SHOP_RULES_EMBED.build()).queue();
        }
    }
}
