package ru.sliva.tools.scheduler;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Task {

    private final Plugin plugin;

    private final Runnable runnable;
    private final boolean async;
    private final int period;
    private final boolean repeating;
    private final int delay;

    private Task(@NotNull Plugin plugin, @NotNull Runnable runnable, boolean async, int period, boolean repeating, int delay) {
        this.plugin = plugin;

        this.runnable = runnable;
        this.async = async;
        this.period = period;
        this.repeating = repeating;
        this.delay = delay;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public boolean isAsync() {
        return async;
    }

    public int getPeriod() {
        return period;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public int getDelay() {
        return delay;
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Plugin plugin;

        private Runnable runnable;
        private boolean async = false;
        private int period = 0;
        private boolean repeating = false;
        private int delay = 0;

        @Contract(" -> new")
        public @NotNull Task build() {
            Validate.notNull(plugin, "Plugin must be NotNull");
            Validate.notNull(runnable, "Runnable must be NotNull");
            return new Task(plugin, runnable, async, period, repeating, delay);
        }

        public Builder plugin(@NotNull Plugin plugin) {
            Validate.notNull(plugin, "Plugin must be NotNull");
            this.plugin = plugin;
            return this;
        }

        public Builder runnable(@NotNull Runnable runnable) {
            Validate.notNull(runnable, "Runnable must be NotNull");
            this.runnable = runnable;
            return this;
        }

        public Builder async(boolean async) {
            this.async = async;
            return this;
        }

        public Builder period(int period) {
            this.period = period;
            return this;
        }

        public Builder repeating(boolean repeating) {
            this.repeating = repeating;
            return this;
        }

        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }
    }
}
