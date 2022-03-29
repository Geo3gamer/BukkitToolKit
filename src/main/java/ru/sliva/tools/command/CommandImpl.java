package ru.sliva.tools.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CommandImpl extends org.bukkit.command.Command {

    private final Command command;

    public CommandImpl(@NotNull Command command) {
        super(command.getName(), command.getDescription(), command.getUsageMessage(), command.getAliases());
        setPermission(command.getPermission());
        this.command = command;
    }

    public @NotNull Command getCommand() {
        return command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return command.getExecutor().apply(sender, args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return command.getTabCompleter().apply(sender, args);
    }
}
