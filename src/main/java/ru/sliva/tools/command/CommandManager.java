package ru.sliva.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CommandManager implements Listener {

    private static final SimpleCommandMap commandMap = (SimpleCommandMap) Bukkit.getCommandMap();
    private static final Map<String, org.bukkit.command.Command> knownCommands = commandMap.getKnownCommands();

    public static void register(@NotNull Command command) {
        commandMap.register(command.getPlugin().getName(), new CommandImpl(command));
    }

    public static void unregister(@NotNull Command command) {
        CommandImpl bukkitCommand = getBukkitCommand(command);
        knownCommands.remove(bukkitCommand.getName(), bukkitCommand);
        bukkitCommand.getAliases().forEach(s -> knownCommands.remove(s, bukkitCommand));
    }

    public static void unregister(@NotNull Plugin plugin) {
        List<Command> commands = getCommands(plugin);
        commands.forEach(CommandManager::unregister);
    }

    public static List<Command> getCommands(@NotNull Plugin plugin) {
        return commandMap.getCommands().stream().filter(cmd -> cmd instanceof CommandImpl).map(cmd -> ((CommandImpl) cmd).getCommand()).filter(cmd -> Objects.equals(cmd.getPlugin(), plugin)).collect(Collectors.toList());
    }

    private static CommandImpl getBukkitCommand(@NotNull Command command) {
        return commandMap.getCommands().stream().filter(cmd -> cmd instanceof CommandImpl).map(cmd -> (CommandImpl) cmd).filter(cmd -> Objects.equals(cmd.getCommand(), command)).findFirst().orElseThrow();
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        unregister(event.getPlugin());
    }
}
