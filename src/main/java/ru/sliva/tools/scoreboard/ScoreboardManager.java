package ru.sliva.tools.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardManager {

    private static final org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    /**
     * Shows the sidebar to the player
     *
     * @param player The player to set the sidebar for.
     * @param sidebar The sidebar to set.
     */
    public static void setSidebar(@NotNull Player player, @NotNull Sidebar sidebar) {
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy", sidebar.getTitle());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        String id = "d";
        String name = ChatColor.RESET.toString();
        int number = sidebar.getLines().size();
        for(Component line : sidebar.getLines()) {
            Team team = scoreboard.registerNewTeam(id);
            id += id;
            team.prefix(line);
            team.addEntry(name);
            Score score = objective.getScore(name);
            score.setScore(number);
            number--;
            name += name;
        }

        player.setScoreboard(scoreboard);
    }

    /**
     * It removes all the sidebars from the player
     *
     * @param player The player whose sidebars are being removed.
     */
    public static void removeSidebars(@NotNull Player player) {
        player.setScoreboard(scoreboardManager.getMainScoreboard());
    }

}
