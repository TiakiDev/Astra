package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.BotConstants;
import me.tiaki.utils.ConfigUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GiveawayCommand implements ICommand {

    @Override
    public String getName() {
        return "giveaway";
    }

    @Override
    public String getDescription() {
        return "Tworzy giveaway";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public List<SubcommandData> getSubcommands() {
        return List.of(
                new SubcommandData("create", "Tworzy nowy giveaway")
                        .addOption(OptionType.STRING, "nagroda", "Nagroda w giveawayu", true)
                        .addOption(OptionType.INTEGER, "czas", "Czas trwania giveawayu w minutach", true)
                        .addOption(OptionType.ROLE, "rola", "Rola, która może brać udział w giveawayu", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event.getSubcommandName() == null) return;

        String guildId = event.getGuild().getId();

        String adminRoleId = ConfigUtils.getConfig(guildId, "admin-role");
        Role adminRole = event.getGuild().getRoleById(adminRoleId);

        if (!event.getMember().getRoles().contains(adminRole)) {
            event.reply("❌ Nie masz uprawnień do użycia tej komendy.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (event.getSubcommandName().equals("create")) {
            String prize = event.getOption("nagroda").getAsString();
            int duration = event.getOption("czas").getAsInt();
            Role role = event.getOption("rola") != null ? event.getOption("rola").getAsRole() : null;

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("🎊 **Giveaway** 🎊")
                    .setDescription("Nagroda: **" + prize + "**\n\nKliknij reakcję 🎉, aby dołączyć!")
                    .setColor(BotConstants.PRIMARY_COLOR)
                    .setFooter("Giveaway zakończy się")
                    .setThumbnail("https://i.imgur.com/8k0wCWA.png")
                    .setTimestamp(Instant.now().plus(duration, ChronoUnit.MINUTES));

            if (role != null) {
                embed.addField("Rola uprawniona do udziału", role.getAsMention(), false);
            }

            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(embed.build())
                    .build();

            event.reply("Giveaway został utworzony!").setEphemeral(true).queue();
            event.getChannel().sendMessage(message).queue(msg -> {
                msg.addReaction(Emoji.fromUnicode("🎉")).queue(); // Dodaj reakcję 🎉 do wiadomości
                scheduleGiveawayEnd(msg, duration, prize, role);
            });
        }
    }

    private void scheduleGiveawayEnd(net.dv8tion.jda.api.entities.Message message, int duration, String prize, Role role) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            message.retrieveReactionUsers(Emoji.fromUnicode("🎉")).queue(users -> {
                List<net.dv8tion.jda.api.entities.User> participants = users.stream()
                        .filter(user -> !user.isBot()) // Ignoruj bota
                        .toList();

                if (role != null) {
                    participants = participants.stream()
                            .filter(user -> message.getGuild().getMember(user).getRoles().contains(role))
                            .toList();
                }

                if (participants.isEmpty()) {
                    message.reply("Nikt nie dołączył do giveawayu 😢").queue();
                    return;
                }

                net.dv8tion.jda.api.entities.User winner = participants.get((int) (Math.random() * participants.size()));

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("🎊 **Giveaway zakończony** 🎊")
                        .setDescription("Nagroda: **" + prize + "**\n\n**Zwycięzca:** " + winner.getAsMention())
                        .setColor(BotConstants.PRIMARY_COLOR)
                        .setFooter("Giveaway zakończył się")
                        .setThumbnail("https://i.imgur.com/8k0wCWA.png")
                        .setTimestamp(Instant.now());

                message.replyEmbeds(embed.build()).queue();
            });
        }, duration, TimeUnit.MINUTES);
    }
}