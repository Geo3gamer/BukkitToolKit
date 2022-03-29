package ru.sliva.tools.scoreboard;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Sidebar {

    private final Component title;
    private final List<Component> lines;

    private Sidebar(Component title, List<Component> lines) {
        this.title = title;
        this.lines = lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sidebar sidebar = (Sidebar) o;
        return Objects.equals(title, sidebar.title) && Objects.equals(lines, sidebar.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, lines);
    }

    @Override
    public String toString() {
        return "Sidebar{" +
                "title=" + title +
                ", lines=" + lines +
                '}';
    }

    /**
     * Returns the title of the sidebar
     *
     * @return The title component.
     */
    public Component getTitle() {
        return title;
    }

    /**
     * Returns a list of all the lines in the sidebar
     *
     * @return A list of components.
     */
    public List<Component> getLines() {
        return lines;
    }

    /**
     * Returns a builder for creating a new instance of the sidebar
     *
     * @return The Builder.
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Component title;
        private List<Component> lines;

        /**
         * Build the Sidebar
         *
         * @return A new Sidebar.
         */
        public Sidebar build() {
            Validate.notNull(title, "Title cannot be null");
            Validate.notNull(lines, "Lines cannot be null");
            return new Sidebar(title, lines);
        }

        /**
         * The title of the sidebar
         *
         * @param title The title of the sidebar.
         * @return Current builder.
         */
        public Builder title(@NotNull Component title) {
            Validate.notNull(title, "Title cannot be null");
            this.title = title;
            return this;
        }

        /**
         * Sets the lines of the sidebar.
         *
         * @param lines The lines of the sidebar.
         * @return Current builder.
         */
        public Builder lines(@NotNull List<Component> lines) {
            Validate.notNull(lines, "Lines cannot be null");
            this.lines = lines;
            return this;
        }

        /**
         * Sets the lines of sidebar
         *
         * @param lines The lines of the sidebar.
         * @return Current builder
         */
        public Builder lines(@NotNull Component... lines) {
            Validate.notNull(lines, "Lines cannot be null");
            return lines(Arrays.asList(lines));
        }
    }
}
