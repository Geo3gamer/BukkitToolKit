package ru.sliva.tools.scheduler;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return async == task.async && period == task.period && repeating == task.repeating && delay == task.delay && Objects.equals(plugin, task.plugin) && Objects.equals(runnable, task.runnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, runnable, async, period, repeating, delay);
    }

    @Override
    public String toString() {
        return "Task{" +
                "plugin=" + plugin +
                ", runnable=" + runnable +
                ", async=" + async +
                ", period=" + period +
                ", repeating=" + repeating +
                ", delay=" + delay +
                '}';
    }

    /**
     * Returns the plugin that owns this task
     *
     * @return The plugin.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Returns the runnable that is used to execute the task
     *
     * @return The runnable.
     */
    public Runnable getRunnable() {
        return runnable;
    }

    /**
     * Returns true if the task is asynchronous
     *
     * @return Should the task be asynchronous.
     */
    public boolean isAsync() {
        return async;
    }

    /**
     * Returns the period before task execution
     *
     * @return The period of the orbit.
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Returns true if the current event is repeating, false otherwise
     *
     * @return Should the task be repeating.
     */
    public boolean isRepeating() {
        return repeating;
    }

    /**
     * Returns the delay between runs
     *
     * @return The delay.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Returns a builder for creating a new instance of task
     *
     * @return The Builder.
     */
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

        /**
         * Build the Task
         *
         * @return A Task.
         */
        @Contract(" -> new")
        public @NotNull Task build() {
            Validate.notNull(plugin, "Plugin must be NotNull");
            Validate.notNull(runnable, "Runnable must be NotNull");
            return new Task(plugin, runnable, async, period, repeating, delay);
        }

        /**
         * Sets the owner of the task
         *
         * @param plugin The plugin that is owner of the task
         * @return Current builder;
         */
        public Builder plugin(@NotNull Plugin plugin) {
            Validate.notNull(plugin, "Plugin must be NotNull");
            this.plugin = plugin;
            return this;
        }

        /**
         * Sets the runnable that is a function that will be executed when the task runs
         *
         * @param runnable The Runnable that will be executed when the timer fires.
         * @return Current builder.
         */
        public Builder runnable(@NotNull Runnable runnable) {
            Validate.notNull(runnable, "Runnable must be NotNull");
            this.runnable = runnable;
            return this;
        }

        /**
         * Sets the async flag of the task.
         *
         * @param async If true, the task will be performed asynchronously.
         * @return Current builder.
         */
        public Builder async(boolean async) {
            this.async = async;
            return this;
        }

        /**
         * Sets the period before run.
         *
         * @param period The period before run, in ticks.
         * @return Current builder.
         */
        public Builder period(int period) {
            this.period = period;
            return this;
        }

        /**
         * Sets the repeating flag to the task
         *
         * @param repeating Whether the task is repeating.
         * @return Current builder.
         */
        public Builder repeating(boolean repeating) {
            this.repeating = repeating;
            return this;
        }

        /**
         * Sets the delay value.
         *
         * @param delay The delay in ticks between runs.
         * @return Current builder.
         */
        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }
    }
}
