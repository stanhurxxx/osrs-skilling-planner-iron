package io.hurx.components.table.defaultTable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import io.hurx.utils.Theme;

/**
 * A custom JTable implementation that extends the default functionality 
 * of JTable to include specific options and a customized appearance.
 */
public class DefaultTable extends JTable {
    protected DefaultTable.Options options;

    /**
     * Constructs a DefaultTable with the specified options.
     *
     * @param options The options that configure the table's appearance and behavior.
     */
    public DefaultTable(DefaultTable.Options options) {
        super(new DefaultTableModel());
        this.options = options;
        DefaultTable table = this;
        setDoubleBuffered(true);
        setVisible(true);
        setFillsViewportHeight(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setCellSelectionEnabled(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setDefaultEditor(Object.class, null);
        setOpaque(true);
        setBackground(Theme.BG_COLOR);
        
        // Add mouse listeners for click and movement events
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() > 1) {
                    e.consume(); // Prevent double-click action
                }
                table.clearSelection(); // Clear selection on click
                table.revalidate();
                table.repaint();
            }
        });
        
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                table.clearSelection(); // Clear selection on mouse move
                table.revalidate();
                table.repaint();
            }
        });
    }

    /**
     * Fills the table model with the appropriate number of columns and clears existing rows.
     */
    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        
        // Clear existing rows
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        // Ensure the model has the correct number of columns
        if (model.getColumnCount() != options.columnCount) {
            for (int i = 0; i < options.columnCount; i++) {
                model.addColumn(""); // Add empty column headers
            }
        }
    }

    /**
     * A static inner class for configuring options related to the DefaultTable.
     */
    public static class Options {
        private int columnCount = 2; // Default number of columns
        private int vPadding = 10; // Vertical padding
        private int hPadding = 10; // Horizontal padding
        private int rowHeight = 20; // Default row height

        private Options() {}

        /**
         * Starts the building process for configuring Options.
         *
         * @return A new instance of the Builder class, which allows for
         *         step-by-step configuration of the Options instance.
         */
        public static Builder builder() {
            return new Builder(new Options());
        }

        /**
         * Sets the number of columns for the table.
         *
         * @param columnCount The number of columns.
         */
        public void setColumnCount(int columnCount) {
            this.columnCount = columnCount;
        }

        /**
         * Gets the number of columns for the table.
         *
         * @return The number of columns.
         */
        public int getColumnCount() {
            return columnCount;
        }

        /**
         * Gets the vertical padding for the table.
         *
         * @return The vertical padding.
         */
        public int getVPadding() {
            return vPadding;
        }

        /**
         * Gets the horizontal padding for the table.
         *
         * @return The horizontal padding.
         */
        public int getHPadding() {
            return hPadding;
        }

        /**
         * Gets the row height for the table.
         *
         * @return The row height.
         */
        public int getRowHeight() {
            return rowHeight;
        }

        /**
         * A builder class for constructing Options instances.
         */
        public static class Builder {
            private Options options;

            private Builder(Options options) {
                this.options = options;
            }

            /**
             * Sets the number of columns in the Options.
             *
             * @param columnCount The number of columns.
             * @return The Builder instance for chaining.
             */
            public Builder columnCount(int columnCount) {
                options.columnCount = columnCount;
                return this;
            }

            /**
             * Sets both vertical and horizontal padding in the Options.
             *
             * @param vPadding The vertical padding.
             * @param hPadding The horizontal padding.
             * @return The Builder instance for chaining.
             */
            public Builder padding(int vPadding, int hPadding) {
                options.vPadding = vPadding;
                options.hPadding = hPadding;
                return this;
            }

            /**
             * Sets the vertical padding in the Options.
             *
             * @param vPadding The vertical padding.
             * @return The Builder instance for chaining.
             */
            public Builder vPadding(int vPadding) {
                options.vPadding = vPadding;
                return this;
            }

            /**
             * Sets the horizontal padding in the Options.
             *
             * @param hPadding The horizontal padding.
             * @return The Builder instance for chaining.
             */
            public Builder hPadding(int hPadding) {
                options.hPadding = hPadding;
                return this;
            }

            /**
             * Sets the row height in the Options.
             *
             * @param rowHeight The row height.
             * @return The Builder instance for chaining.
             */
            public Builder rowHeight(int rowHeight) {
                options.rowHeight = rowHeight;
                return this;
            }

            /**
             * Builds and returns the configured Options instance.
             *
             * @return The configured Options instance.
             */
            public Options build() {
                return this.options;
            }
        }
    }
}