package ru.sliva.tools.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public final class Command {

    private final Plugin plugin;

    private final String name;
    private final String description;
    private final String usageMessage;
    private final String permission;
    private final List<String> aliases;

    private final BiFunction<CommandSender, String[], Boolean> executor;
    private final BiFunction<CommandSender, String[], List<String>> tabCompleter;

    private Command(@NotNull Plugin plugin, @NotNull BiFunction<CommandSender, String[], Boolean> executor, @NotNull BiFunction<CommandSender, String[], List<String>> tabCompleter, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @Nullable String permission, @NotNull List<String> aliases) {
        this.plugin = plugin;

        this.name = name;
        this.description = description;
        this.usageMessage = usageMessage;
        this.permission = permission;
        this.aliases = aliases;

        this.executor = executor;
        this.tabCompleter = tabCompleter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(plugin, command.plugin) && Objects.equals(name, command.name) && Objects.equals(description, command.description) && Objects.equals(usageMessage, command.usageMessage) && Objects.equals(permission, command.permission) && Objects.equals(aliases, command.aliases) && Objects.equals(executor, command.executor) && Objects.equals(tabCompleter, command.tabCompleter);
    }

    @Override
    public String toString() {
        return "Command{" +
                "plugin=" + plugin +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", usageMessage='" + usageMessage + '\'' +
                ", permission='" + permission + '\'' +
                ", aliases=" + aliases +
                ", executor=" + executor +
                ", tabCompleter=" + tabCompleter +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, name, description, usageMessage, permission, aliases, executor, tabCompleter);
    }

    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull String getUsageMessage() {
        return usageMessage;
    }

    public @Nullable String getPermission() {
        return permission;
    }

    public @NotNull List<String> getAliases() {
        return aliases;
    }

    public @NotNull BiFunction<CommandSender, String[], Boolean> getExecutor() {
        return executor;
    }

    public @NotNull BiFunction<CommandSender, String[], List<String>> getTabCompleter() {
        return tabCompleter;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Plugin plugin;

        private String name;
        private String description = "";
        private String usageMessage;
        private String permission;
        private List<String> aliases = new ArrayList<>();

        private BiFunction<CommandSender, String[], Boolean> executor;
        private BiFunction<CommandSender, String[], List<String>> tabCompleter;

        @Contract(" -> new")
        public @NotNull Command build() {
            Validate.notNull(plugin, "Plugin must be NotNull");
            Validate.notNull(name, "Name must be NotNull");
            Validate.notNull(executor, "Executor must be NotNull");
            Validate.notNull(tabCompleter, "TabCompleter must be NotNull");
            return new Command(plugin, executor, tabCompleter, name, description, usageMessage, permission, aliases);
        }

        public Builder plugin(@NotNull Plugin plugin) {
            Validate.notNull(plugin, "Plugin must be NotNull");
            this.plugin = plugin;
            return this;
        }

        public Builder name(@NotNull String name) {
            Validate.notNull(name, "Name must be NotNull");
            this.name = name;
            if(usageMessage == null) usageMessage = "/" + name;
            return this;
        }

        public Builder description(@NotNull String description) {
            Validate.notNull(description, "Description must be NotNull");
            this.description = description;
            return this;
        }

        public Builder usageMessage(@NotNull String usageMessage) {
            Validate.notNull(usageMessage, "Usage Message must be NotNull");
            this.usageMessage = usageMessage;
            return this;
        }

        public Builder permission(@Nullable String permission) {
            this.permission = permission;
            return this;
        }

        public Builder aliases(@NotNull List<String> aliases) {
            Validate.notNull(aliases, "Aliases must be NotNull");
            this.aliases = aliases;
            return this;
        }

        public Builder aliases(@NotNull String... aliases) {
            return aliases(Arrays.asList(aliases));
        }

        public Builder executor(@NotNull BiFunction<CommandSender, String[], Boolean> executor) {
            Validate.notNull(executor, "Executor must be NotNull");
            this.executor = executor;
            return this;
        }

        public Builder tabCompleter(@NotNull BiFunction<CommandSender, String[], List<String>> tabCompleter) {
            Validate.notNull(tabCompleter, "TabCompleter must be NotNull");
            this.tabCompleter = tabCompleter;
            return this;
        }
    }


}