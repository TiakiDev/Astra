package me.tiaki.utils;

import me.tiaki.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private List<ICommand> commands = new ArrayList<>();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            for (ICommand command : commands) {
                SlashCommandData commandData = Commands.slash(command.getName(), command.getDescription())
                        .addOptions(command.getOptions())
                        .addSubcommands(command.getSubcommands());

                guild.upsertCommand(commandData).queue();
            }
        }
    }


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for(ICommand command : commands){
            if(command.getName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }

    }

    public void add(ICommand command){
        commands.add(command);
    }

}
