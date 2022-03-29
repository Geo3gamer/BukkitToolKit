package ru.sliva.tools.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class Scheduler implements Listener {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    /**
     * Runs the task
     *
     * @param task The Task that you want to run.
     * @return The task ID.
     */
    @Contract(pure = true)
    public static int runTask(@NotNull Task task) {
        Plugin plugin = task.getPlugin();
        Runnable runnable = task.getRunnable();
        if(task.isAsync()) {
            if(task.isRepeating()) {
                return scheduler.runTaskTimerAsynchronously(plugin, runnable, task.getDelay(), task.getPeriod()).getTaskId();
            } else {
                if(task.getDelay() > 0) {
                    return scheduler.runTaskAsynchronously(plugin, runnable).getTaskId();
                } else {
                    return scheduler.runTaskLaterAsynchronously(plugin, runnable, task.getDelay()).getTaskId();
                }
            }
        } else {
            if(task.isRepeating()) {
                return scheduler.runTaskTimer(plugin, runnable, task.getDelay(), task.getPeriod()).getTaskId();
            } else {
                if(task.getDelay() > 0) {
                    return scheduler.runTask(plugin, runnable).getTaskId();
                } else {
                    return scheduler.runTaskLater(plugin, runnable, task.getDelay()).getTaskId();
                }
            }
        }
    }

    /**
     * This function cancels a task with the given taskID
     *
     * @param taskID The ID of the task to be cancelled.
     */
    public static void stopTask(int taskID) {
        scheduler.cancelTask(taskID);
    }

    /**
     * It stops all tasks that are owned by the plugin
     *
     * @param plugin The plugin that owns the tasks.
     */
    private static void stop(@NotNull Plugin plugin) {
        List<BukkitTask> tasks = scheduler.getPendingTasks().stream().filter(bukkitTask -> Objects.equals(bukkitTask.getOwner(), plugin)).toList();
        tasks.forEach(bukkitTask -> stopTask(bukkitTask.getTaskId()));
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        stop(event.getPlugin());
    }
}
