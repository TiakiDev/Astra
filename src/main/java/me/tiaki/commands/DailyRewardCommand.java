package me.tiaki.commands;

import me.tiaki.ICommand;
import me.tiaki.utils.RewardConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class DailyRewardCommand implements ICommand {

    private static final Color EMBED_COLOR = new Color(161, 22, 196);
    private static final String BANNER_IMAGE = "https://s6.gifyu.com/images/bzELo.gif";
    private static final String CLOCK_EMOJI = "<:alarmclock:1352407829830565918>";

    @Override
    public String getName() { return "daily"; }

    @Override
    public String getDescription() { return "Odbierz swoją dzienną nagrodę 🎁"; }

    @Override
    public List<OptionData> getOptions() { return Collections.emptyList(); }

    @Override
    public List<SubcommandData> getSubcommands() { return Collections.emptyList(); }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild().getId();
        String userId = event.getUser().getId();

        RewardConfig.GuildConfig config = RewardConfig.getGuildConfig(guildId);

        if (!checkChannel(event, config)) return;
        if (!checkCooldown(event, config, userId)) return;

        String reward = drawReward(config);
        updateCooldown(config, userId, guildId);
        sendResult(event, reward, config);
    }

    private boolean checkChannel(SlashCommandInteractionEvent event, RewardConfig.GuildConfig config) {
        if (config.channelId != null && !event.getChannel().getId().equals(config.channelId)) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(EMBED_COLOR)
                    .setTitle("🚫 Nieprawidłowy kanał")
                    .setDescription("**Poprawny kanał:**\n" + event.getGuild().getTextChannelById(config.channelId).getAsMention())
                    .addField("Dlaczego?", "Dzienną nagrodę można odbierać tylko na dedykowanym kanale", false)
                    .setFooter("Codzienne nagrody • " + event.getGuild().getName(), event.getGuild().getIconUrl());

            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return false;
        }
        return true;
    }

    private boolean checkCooldown(SlashCommandInteractionEvent event, RewardConfig.GuildConfig config, String userId) {
        long lastClaim = config.lastClaims.getOrDefault(userId, 0L);
        long currentTime = Instant.now().getEpochSecond();
        long remainingTime = 86400 - (currentTime - lastClaim);

        if (remainingTime > 0) {
            Duration duration = Duration.ofSeconds(remainingTime);
            String timeLeft = String.format(
                    "%d godzin %02d minut",
                    duration.toHours(),
                    duration.toMinutesPart()
            );

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(EMBED_COLOR)
                    .setTitle(CLOCK_EMOJI + " Zbyt szybko!")
                    .setDescription("**Czas do kolejnej nagrody:**\n" + timeLeft)
                    .addField("Następna próba", "<t:" + (lastClaim + 86400) + ":R>", false)
                    .setImage("https://i.imgur.com/lHkRUg1.png")
                    .setFooter(event.getUser().getName() + " • Codzienne nagrody", event.getUser().getAvatarUrl());

            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return false;
        }
        return true;
    }

    private String drawReward(RewardConfig.GuildConfig config) {
        if (config.chances.isEmpty()) return null;

        double total = config.chances.values().stream().mapToDouble(Double::doubleValue).sum();
        double random = new Random().nextDouble() * 100;

        double current = 0;
        for (Map.Entry<String, Double> entry : config.chances.entrySet()) {
            current += entry.getValue();
            if (random <= current) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void updateCooldown(RewardConfig.GuildConfig config, String userId, String guildId) {
        config.lastClaims.put(userId, Instant.now().getEpochSecond());
        RewardConfig.saveGuildConfig(guildId, config);
    }

    private void sendResult(SlashCommandInteractionEvent event, String reward, RewardConfig.GuildConfig config) {
        Instant nextClaim = Instant.now().plusSeconds(86400);
        String chanceInfo = getChanceInfo(reward, config);

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(EMBED_COLOR)
                .setImage(BANNER_IMAGE)
                .setFooter("Daily" + " • " + event.getGuild().getName());

        if (reward != null) {

            // Aktualizacja ekwipunku

            String guildId = event.getGuild().getId();

            Map<String, Integer> inventory = config.userInventories
                    .computeIfAbsent(event.getUser().getId(), k -> new HashMap<>());

            inventory.put(reward, inventory.getOrDefault(reward, 0) + 1);
            RewardConfig.saveGuildConfig(guildId, config);

            embed.setTitle("🎉 **WYGRANA!**")
                    .setDescription("Otrzymujesz specjalną nagrodę!")
                    .addField("Nagroda", reward, false)
                    .addField("Szansa na wygraną", chanceInfo, false)
                    .addField("Kolejna nagroda", "<t:" + nextClaim.getEpochSecond() + ":R>", true);
        } else {
            embed.setTitle("💔 Nic tym razem")
                    .setDescription("Nie trać nadziei!\nSpróbuj ponownie jutro")
                    .addField("Dostępne nagrody", getRewardsList(config), false)
                    .addField("Następna próba", "<t:" + nextClaim.getEpochSecond() + ":R>", true);
        }

        event.replyEmbeds(embed.build()).queue();
    }

    private String getChanceInfo(String reward, RewardConfig.GuildConfig config) {
        if (reward == null) return "";
        double chance = config.chances.get(reward);
        return String.format("%.2f%% szans\n(1 na %d prób)", chance, (int)(100/chance));
    }

    private String getRewardsList(RewardConfig.GuildConfig config) {
        if (config.chances.isEmpty()) return "Brak skonfigurowanych nagród";

        StringBuilder sb = new StringBuilder();
        config.chances.forEach((name, chance) ->
                sb.append(String.format("• %s - %.2f%%\n", name, chance))
        );
        return sb.toString();
    }
}