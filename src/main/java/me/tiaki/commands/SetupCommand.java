package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import me.tiaki.utils.EmbedUtils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.List;

public class SetupCommand implements ICommand {

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Inicjuje panele";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of(
                new SubcommandData("shop", "Inicjuje panel zamówieniowy sklepu"),
                new SubcommandData("faq", "Inicjuje panel z faq"),
                new SubcommandData("rules", "Inicjuje panel z regulaminem sklepu")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String adminRoleId = ConfigUtils.getConfig(event.getGuild().getId(), "admin-role");
        Role adminRole = event.getGuild().getRoleById(adminRoleId);

        if (!event.getMember().getRoles().contains(adminRole)) {
            EmbedUtils.replyWithError(event, BotConstants.PERMISSION_ERROR);
            return;
        }

        if(event.getSubcommandName().equals("shop")) {

            try {
                event.replyEmbeds(BotConstants.SHOP_EMBED.build())
                        .addActionRow(
                                Button.success("create_order", "🛒 Rozpocznij zamówienie")
                                        .withEmoji(Emoji.fromUnicode("📦")),
                                Button.link("https://pastebin.com/raw/WrEzD524", "📜 Regulamin sklepu")
                        )
                        .queue();

            } catch (Exception e) {
                EmbedUtils.replyWithError(event, e.getMessage());
            }
        }
        if (event.getSubcommandName().equals("rules")) {

            event.replyEmbeds(BotConstants.RULES_EMBED.build()).queue();
        }

        if (event.getSubcommandName().equals("faq")){

            event.replyEmbeds(BotConstants.FAQ_EMBED.build()).queue();

        }
    }
}