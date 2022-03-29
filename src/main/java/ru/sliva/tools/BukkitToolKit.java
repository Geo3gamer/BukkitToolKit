package ru.sliva.tools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sliva.tools.command.Command;
import ru.sliva.tools.command.CommandManager;

import java.util.List;

public final class BukkitToolKit extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CommandManager(), this);

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
