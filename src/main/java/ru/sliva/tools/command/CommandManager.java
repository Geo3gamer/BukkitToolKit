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


    /**
     * Registers a command
     *
     * @param command The command that you want to register.
     */
    public static void register(@NotNull Command command) {
        commandMap.register(command.getPlugin().getName(), new CommandImpl(command));
    }

    /**
     * Removes the command
     *
     * @param command The command to unregister.
     */
    public static void unregister(@NotNull Command command) {
        CommandImpl bukkitCommand = getBukkitCommand(command);
        knownCommands.remove(bukkitCommand.getName(), bukkitCommand);
        bukkitCommand.getAliases().forEach(s -> knownCommands.remove(s, bukkitCommand));
    }

    /**
     * Unregisters plugin commands
     *
     * @param plugin The plugin that registered the commands.
     */
    private static void unregister(@NotNull Plugin plugin) {
        List<Command> commands = getCommands(plugin);
        commands.forEach(CommandManager::unregister);
    }

    /**
     * It returns a list of commands that are registered to the specified plugin
     *
     * @param plugin The plugin that the commands are being registered for.
     * @return A list of commands.
     */
    private static List<Command> getCommands(@NotNull Plugin plugin) {
        return commandMap.getCommands().stream().filter(cmd -> cmd instanceof CommandImpl).map(cmd -> ((CommandImpl) cmd).getCommand()).filter(cmd -> Objects.equals(cmd.getPlugin(), plugin)).collect(Collectors.toList());
    }

    /**
     * It returns the registered Bukkit implementation of the given command
     *
     * @param command The command
     * @return The command implementation that matches.
     */
    private static CommandImpl getBukkitCommand(@NotNull Command command) {
        return commandMap.getCommands().stream().filter(cmd -> cmd instanceof CommandImpl).map(cmd -> (CommandImpl) cmd).filter(cmd -> Objects.equals(cmd.getCommand(), command)).findFirst().orElseThrow();
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        unregister(event.getPlugin());
    }
}
