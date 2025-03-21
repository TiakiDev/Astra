package me.tiaki;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.List;

public interface ICommand {

    String getName();

    String getDescription();

    List<OptionData> getOptions();

    List<SubcommandData> getSubcommands();


    void execute(SlashCommandInteractionEvent event);

}
