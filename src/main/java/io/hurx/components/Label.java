package io.hurx.components;

import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.table.Table;
import io.hurx.utils.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * The Label class is a custom table component that displays a title and
 * allows for the inclusion of additional components (like text fields and 
 * combo boxes) alongside the title. It extends the DefaultTable class, 
 * enabling the integration of various GUI elements in a structured format.
 */
public class Label extends Table {
    /**
     * Enables top padding for the Label.
     *
     * @return The current Label instance for method chaining.
     */
    public Label enableTopPadding() {
        this.topPadding = true;
        return this;
    }

    /**
     * Disables top padding for the Label.
     *
     * @return The current Label instance for method chaining.
     */
    public Label disableTopPadding() {
        this.topPadding = false;
        return this;
    }

    /**
     * Indicates whether the top padding is enabled.
     */
    private boolean topPadding = false;

    /**
     * Enables bottom padding for the Label.
     *
     * @return The current Label instance for method chaining.
     */
    public Label enableBottomPadding() {
        this.bottomPadding = true;
        return this;
    }

    /**
     * Disables bottom padding for the Label.
     *
     * @return The current Label instance for method chaining.
     */
    public Label disableBottomPadding() {
        this.bottomPadding = false;
        return this;
    }

    /**
     * Indicates whether the bottom padding is enabled.
     */
    private boolean bottomPadding = false;

    public Label font(Font font) {
        this.font = font;
        return this;
    }

    /**
     * The font
     */
    private Font font = Theme.LABEL_FONT;

    public Label height(int rowHeight) {
        return (Label)super.height(rowHeight);
    }

    public Label() {
        super();
        this.setRowHeight(Theme.TABLE_ROW_HEIGHT);
    }

    public Label(String text) {
        this();
        row(new Object[] {
            new JLabel(text)
        });
    }

    public Label(Object[] row) {
        this();
        row(row);
    }

    /**
     * Title label
     */
    public static class Title extends Label {
        public Title() {
            super();
        }

        public Title(String text) {
            super(text);
        }

        public Title(Object[] row) {
            super(row);
        }

        @Override
        public void prerender() throws Exception {
            font(Theme.TITLE_FONT);
            super.prerender();
        }
    }

    public static class Plain extends JLabel {
        public boolean hoverable() {
            return hoverable;
        }
        public Plain hoverable(boolean hoverable) {
            this.hoverable = hoverable;
            return this;
        }
        private boolean hoverable = false;

        public Plain isSelected(boolean isSelected) {
            this.isSelected = isSelected;
            return this;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public boolean isSelected = false;

        public boolean hovered() {
            return hovered;
        }
        public Plain hovered(boolean hovered) {
            this.hovered = hovered;
            return this;
        }
        private boolean hovered = false;

        public Plain mouseListener(MouseAdapter adapter) {
            addMouseListener(adapter);
            return this;
        }

        public Plain(String text) {
            super(text);
        }

        public Plain() {
            this("");
        }

        public static class CellRenderer extends DefaultTableCellRenderer {
            public Plain plain() {
                return plain;
            }
            private Plain plain;

            public CellRenderer(Plain plain) {
                this.plain = plain;
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Plain plain = value instanceof Plain ? (Plain) value : this.plain;
                plain.setOpaque(true);
                plain.setBorder(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING));
                plain.setForeground(Color.WHITE);
                plain.setFont(Theme.LABEL_FONT);
                if (plain.hoverable) {
                    if (plain.hovered || plain.isSelected()) {
                        plain.setBackground(Theme.TABLE_BG_COLOR_SELECTED_HOVER);
                    }
                    else {
                        plain.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
                else {
                    plain.setBackground(Theme.TABLE_BG_COLOR);
                }
                return plain;
            }
        }

        public static class CellEditor extends AbstractCellEditor implements TableCellEditor {
            private Plain plain;

            public CellEditor(Plain plain) {
                this.plain = plain;
            }

            @Override
            public Object getCellEditorValue() {
                return plain; // Return the current value of the button
            }

            @Override
            public boolean isCellEditable(EventObject e) {
                return true; // Allow editing for all events
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                // Set the button state based on the table cell value if needed
                return plain;
            }
        }
    }

    @Override
    public void prerender() throws Exception {
        super.prerender();

        // Variables
        int columnCount = columnCount();

        // Create an empty row list based on column count
        List<Object> emptyRow = new ArrayList<>();
        for (int i = 0; i < columnCount; i ++) {
            emptyRow.add(new EmptyComponent());
        }

        // Find the row property that is not an empty row
        Object[] row = rows.get(0);
        loop: for (int i = 0; i < rows.size(); i ++) {
            row = rows.get(i);
            if (row == null) continue;
            for (int j = 0; j < row.length; j ++) {
                if (!(row[j] instanceof EmptyComponent)) {
                    break loop;
                }
            }
        }

        // Reset the rows (can be done in prerender only)
        rows.clear();
        if (topPadding) rows.add(emptyRow.toArray());
        rows.add(row);
        if (bottomPadding) rows.add(emptyRow.toArray());

        // Calculate cell widths
        int totalWidth = 234;
        int elements = 0;
        int menuButtons = 0;
        for (int i = 0; i < row.length; i ++) {
            Object cell = row[i];
            if (cell instanceof MenuButton) {
                menuButtons ++;
                totalWidth -= height();
            }
            else {
                elements ++;
            }
        }

        // Process each cell and modify the table based on types.
        for (int i = 0; i < row.length; i ++) {
            Object cell = row[i];
            // Add font
            if (cell instanceof JComponent) {
                JComponent jComponent = (JComponent) cell;
                jComponent.setFont(font);
                setForeground(Color.WHITE);
            }
            // Set divided width
            if (elements > 0 && !(cell instanceof MenuButton)) {
                getColumnModel().getColumn(i).setPreferredWidth(Math.round((float)totalWidth / elements));
                getColumnModel().getColumn(i).setMinWidth(Math.round((float)totalWidth / elements));
                getColumnModel().getColumn(i).setMaxWidth(Math.round((float)totalWidth / elements));
                getColumnModel().getColumn(i).setWidth(Math.round((float)totalWidth / elements));
            }
            // Except for menu buttons
            if (cell instanceof MenuButton) {
                MenuButton button = (MenuButton) cell;
                button.setBorder(BorderFactory.createEmptyBorder());
                getColumnModel().getColumn(i).setPreferredWidth(getRowHeight(topPadding ? 1 : 0));
                getColumnModel().getColumn(i).setMaxWidth(getRowHeight(topPadding ? 1 : 0));
                getColumnModel().getColumn(i).setMinWidth(getRowHeight(topPadding ? 1 : 0));
                getColumnModel().getColumn(i).setWidth(getRowHeight(topPadding ? 1 : 0));
            }
        }
    }

    /**
     * An empty component in a row
     */
    public static class EmptyComponent {}
}