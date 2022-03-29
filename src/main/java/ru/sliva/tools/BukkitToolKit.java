package ru.sliva.tools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sliva.tools.command.Command;
import ru.sliva.tools.command.CommandManager;
import ru.sliva.tools.scheduler.Scheduler;
import ru.sliva.tools.scheduler.Task;

import java.util.List;

public final class BukkitToolKit extends JavaPlugin{

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CommandManager(), this);
        Bukkit.getPluginManager().registerEvents(new Scheduler(), this);

        Command testCommand = Command.builder()
                .plugin(this)
                .name("Test")
                .usageMessage("/test <args>")
                .description("Description")
                .permission("toolkit.test")
                .aliases("tset", "t")
                .executor((commandSender, strings) -> {
                    commandSender.sendMessage("Hello, world!");
                    return true;
                })
                .tabCompleter((commandSender, strings) -> List.of("tset", "test"))
                .build();

        CommandManager.register(testCommand);

        Task testTask = Task.builder()
                .plugin(this)
                .runnable(() -> Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("It works")))
                .async(true)
                .period(10)
                .repeating(true)
                .delay(20)
                .build();

        Scheduler.runTask(testTask);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
