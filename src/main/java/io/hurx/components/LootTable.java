package io.hurx.components;

import io.hurx.models.items.Items;
import io.hurx.utils.Theme;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import io.hurx.components.table.defaultTable.DefaultTable;

/**
 * The LootTable class represents a custom table component for displaying 
 * icons and their corresponding item counts in a grid format.
 * The table is designed to show a collection of items with a user-friendly
 * interface, including tooltips that display additional item information.
 */
public class LootTable extends DefaultTable {
    public final static int ICONS_PER_ROW = 5; // Number of icons displayed per row
    public final static int H_PADDING = 10; // Horizontal padding for icons
    public final static int V_PADDING = 10; // Vertical padding for icons

    /**
     * The list of item names to be displayed in the table.
     */
    private List<String> itemNames;

    /**
     * A list of icons representing the items in the table.
     */
    private List<Icon> icons;

    /**
     * Constructs a LootTable with default settings.
     * Initializes the table with a specified number of columns and sets up
     * the cell renderer for displaying icons.
     */
    public LootTable() {
        super(DefaultTable.Options.builder().columnCount(ICONS_PER_ROW).build());
        LootTable table = this;
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        
        // Create columns for the table
        for (int i = 0; i < ICONS_PER_ROW; i++) {
            model.addColumn("");
        }
        
        // Set custom cell renderer for each column
        for (int i = 0; i < ICONS_PER_ROW; i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        
        table.setDoubleBuffered(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setRowHeight(230 / ICONS_PER_ROW);
        super.setVisible(true);
        fillTableModel(); // Populate the table model with item data
    }

    /**
     * Returns the tooltip text for the table cell at the specified mouse event point.
     *
     * @param e The MouseEvent that triggered the tooltip request.
     * @return The tooltip text or null if no tooltip should be displayed.
     */
    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        Point point = e.getPoint();
        int row = rowAtPoint(point);
        int col = columnAtPoint(point);
        int index = row * ICONS_PER_ROW + col;
        
        // Ensure the index is within bounds
        if (index >= icons.size() || index >= itemNames.size()) {
            return null;
        } else {
            Integer amount = icons.get(index).getAmount();
            if (amount == null) return null;
            
            // Format the tooltip text to include the amount and item name
            return NumberFormat.getInstance(Locale.US).format(amount) + " x " + itemNames.get(index);
        }
    }

    /**
     * Fills the table model with item icons and their corresponding names.
     * Clears the current rows and populates the model with new data.
     */
    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();

        // Clear existing rows in the model
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        itemNames = new ArrayList<>();
        icons = new ArrayList<>();

        // Populate the table with item icons and random amounts (dummy data)
        for (Items item : Items.values()) {
            Icon icon = new Icon(item.getItem().getIcon(),
                (int) Math.round(Math.random() * 100_000 + (Math.random() > 0.5 ? 100_000 : 0)
                + (Math.random() > 0.5 ? 10_000_000 : 0)));
            icon.setOpaque(true);
            icon.setHPadding(H_PADDING);
            icon.setVPadding(V_PADDING);
            icon.setBackground(Theme.TABLE_BG_COLOR);
            icon.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
            icons.add(icon);
            itemNames.add(item.getItem().getName());
        }

        // Add a special icon for coins
        Icon icon = new Icon(Items.Coins.getItem().getIcon(), 1_337_000_000);
        icon.setOpaque(true);
        icon.setHPadding(H_PADDING);
        icon.setVPadding(V_PADDING);
        icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
        icon.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
        icons.add(0, icon);
        itemNames.add(0, Items.Coins.getItem().getName());

        // Populate the model with rows of icons
        for (int i = 0; i < 3; i++) {
            List<Object> cells = new ArrayList<>();

            for (int j = 0; j < ICONS_PER_ROW; j++) {
                int k = i * ICONS_PER_ROW + j;
                if (k >= icons.size()) {
                    JLabel label = new JLabel("");
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    label.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
                    cells.add(label);
                } else {
                    cells.add(icons.get(k));
                }
            }

            model.addRow(cells.toArray()); // Add the constructed row to the model
        }
    }

    /**
     * Custom cell renderer for the icon columns in the LootTable.
     * This renderer ensures that icons are displayed correctly in each cell.
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        /**
         * Returns the component used for rendering the cell at the specified position.
         *
         * @param table     The JTable that is asking the renderer to draw.
         * @param value     The value to be displayed.
         * @param isSelected Whether or not the cell is selected.
         * @param hasFocus  Whether or not the cell has focus.
         * @param row       The row of the cell to be rendered.
         * @param column    The column of the cell to be rendered.
         * @return The component used for rendering the cell.
         */
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setOpaque(true);
            label.setBackground(Theme.TABLE_BG_COLOR);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            
            // Return the icon or JLabel for rendering
            if (value instanceof Icon) {
                return (Icon) value;
            } else if (value instanceof JLabel) {
                return (JLabel) value;
            }
            return label;
        }
    }
}
