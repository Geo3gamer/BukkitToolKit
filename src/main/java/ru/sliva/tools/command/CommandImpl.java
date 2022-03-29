package ru.sliva.tools.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CommandImpl extends org.bukkit.command.Command implements PluginIdentifiableCommand {

    private final Command command;

    public CommandImpl(@NotNull Command command) {
        super(command.getName(), command.getDescription(), command.getUsageMessage(), command.getAliases());
        setPermission(command.getPermission());
        this.command = command;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return command.getPlugin();
    }

    public @NotNull Command getCommand() {
        return command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        boolean success;

        if (!getPlugin().isEnabled()) {
            throw new CommandException("Cannot execute command '" + commandLabel + "' in plugin " + getPlugin().getDescription().getFullName() + " - plugin is disabled.");
        }

        if (!testPermission(sender)) {
            return true;
        }

        try {
            success = command.getExecutor().apply(sender, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + getPlugin().getDescription().getFullName(), ex);
        }

        if (!success && usageMessage.length() > 0) {
            for (String line : usageMessage.replace("<command>", commandLabel).split("\n")) {
                sender.sendMessage(line);
            }
        }

        return success;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        List<String> completions;
        try {
            completions = command.getTabCompleter().apply(sender, args);
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args) {
                message.append(arg).append(' ');
            }
            message.deleteCharAt(message.length() - 1).append("' in plugin ").append(getPlugin().getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }

        if (completions == null) {
            return super.tabComplete(sender, alias, args);
        }
        return completions;
    }
}
