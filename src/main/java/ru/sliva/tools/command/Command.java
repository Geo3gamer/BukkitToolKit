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

    /**
     * Returns the plugin that owns this command
     *
     * @return The plugin.
     */
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    /**
     * Returns the name of the command
     *
     * @return The name of the command.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * It returns the description of the command.
     *
     * @return The description of the command.
     */
    public @NotNull String getDescription() {
        return description;
    }

    /**
     * Returns the usage message for the command
     *
     * @return The usage message.
     */
    public @NotNull String getUsageMessage() {
        return usageMessage;
    }

    /**
     * Returns the permission needed to have access to the command.
     *
     * @return The permission that the user has to access the command.
     */
    public @Nullable String getPermission() {
        return permission;
    }

    /**
     * Returns a list of aliases for this command
     *
     * @return A list of strings.
     */
    public @NotNull List<String> getAliases() {
        return aliases;
    }

    /**
     * Returns the command executor
     *
     * @return A function that takes a CommandSender and a String array and returns a boolean.
     */
    public @NotNull BiFunction<CommandSender, String[], Boolean> getExecutor() {
        return executor;
    }

    /**
     * Returns the tab completer for this command
     *
     * @return A function that takes a CommandSender and a String array and returns a List of Strings.
     */
    public @NotNull BiFunction<CommandSender, String[], List<String>> getTabCompleter() {
        return tabCompleter;
    }

    /**
     * Returns a builder for creating a new instance of this class
     *
     * @return The Builder class.
     */
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

        /**
         * Builds a Command
         *
         * @return The Command.
         */
        @Contract(" -> new")
        public @NotNull Command build() {
            Validate.notNull(plugin, "Plugin must be NotNull");
            Validate.notNull(name, "Name must be NotNull");
            Validate.notNull(executor, "Executor must be NotNull");
            Validate.notNull(tabCompleter, "TabCompleter must be NotNull");
            return new Command(plugin, executor, tabCompleter, name, description, usageMessage, permission, aliases);
        }

        /**
         * Sets the owner of the command
         *
         * @param plugin The plugin that is owner of the command
         * @return Current builder.
         */
        public Builder plugin(@NotNull Plugin plugin) {
            Validate.notNull(plugin, "Plugin must be NotNull");
            this.plugin = plugin;
            return this;
        }

        /**
         * Sets the name of the command.
         *
         * @param name The name of the command.
         * @return Current builder.
         */
        public Builder name(@NotNull String name) {
            Validate.notNull(name, "Name must be NotNull");
            this.name = name;
            if(usageMessage == null) usageMessage = "/" + name;
            return this;
        }

        /**
         * Sets the description of the command.
         *
         * @param description The description of the command.
         * @return Current builder.
         */
        public Builder description(@NotNull String description) {
            Validate.notNull(description, "Description must be NotNull");
            this.description = description;
            return this;
        }

        /**
         * Sets the usage message for the command.
         *
         * @param usageMessage The message to display when the user uses the command incorrectly.
         * @return Current builder.
         */
        public Builder usageMessage(@NotNull String usageMessage) {
            Validate.notNull(usageMessage, "Usage Message must be NotNull");
            this.usageMessage = usageMessage;
            return this;
        }

        /**
         * Sets the permission for the command.
         *
         * @param permission The permission to be checked in command
         * @return Current builder.
         */
        public Builder permission(@Nullable String permission) {
            this.permission = permission;
            return this;
        }

        /**
         * Sets the aliases for the command.
         *
         * @param aliases The list of aliases for the command.
         * @return Current builder.
         */
        public Builder aliases(@NotNull List<String> aliases) {
            Validate.notNull(aliases, "Aliases must be NotNull");
            this.aliases = aliases;
            return this;
        }

        /**
         * Sets the aliases for the command.
         *
         * @param aliases The list of aliases for the command.
         * @return Current builder.
         */
        public Builder aliases(@NotNull String... aliases) {
            Validate.notNull(aliases, "Aliases must be NotNull");
            return aliases(Arrays.asList(aliases));
        }

        /**
         * Sets the executor for command
         *
         * @param executor The executor that is a function that will be called when the command is executed.
         * @return Current builder.
         */
        public Builder executor(@NotNull BiFunction<CommandSender, String[], Boolean> executor) {
            Validate.notNull(executor, "Executor must be NotNull");
            this.executor = executor;
            return this;
        }

        /**
         * Sets the tabCompleter function that is used to provide a list of possible completions for the command
         *
         * @param tabCompleter The tabCompleter for the command
         * @return Current builder.
         */
        public Builder tabCompleter(@NotNull BiFunction<CommandSender, String[], List<String>> tabCompleter) {
            Validate.notNull(tabCompleter, "TabCompleter must be NotNull");
            this.tabCompleter = tabCompleter;
            return this;
        }
    }


}