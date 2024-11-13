package io.hurx.components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import io.hurx.components.Icon;
import io.hurx.components.Label;
import io.hurx.components.button.Button;
import io.hurx.components.button.ButtonCellEditor;
import io.hurx.components.button.ButtonCellRenderer;
import io.hurx.components.comboBox.ComboBox;
import io.hurx.components.comboBox.ComboBoxCellEditor;
import io.hurx.components.comboBox.ComboBoxCellRenderer;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.menuButton.MenuButtonCellEditor;
import io.hurx.components.menuButton.MenuButtonCellRenderer;
import io.hurx.components.numberSlider.NumberSlider;
import io.hurx.components.numberSlider.NumberSliderCellEditor;
import io.hurx.components.numberSlider.NumberSliderCellRenderer;
import io.hurx.components.textField.TextField;
import io.hurx.components.textField.TextFieldCellEditor;
import io.hurx.components.textField.TextFieldCellRenderer;
import io.hurx.components.textField.formattedNumber.FormattedNumberTextField;
import io.hurx.components.textField.formattedNumber.FormattedNumberTextFieldCellEditor;
import io.hurx.components.textField.formattedNumber.FormattedNumberTextFieldCellRenderer;
import io.hurx.models.repository.Repository;
import io.hurx.utils.Theme;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom JTable implementation that provides enhanced customization options.
 * Allows for specific cell renderers and editors to be assigned to specified
 * ranges of columns, with an appearance matching the provided Theme.
 */
public class Table extends JTable {
    /** Adds a row to the table */
    public final Table row(Object[] row) {
        this.rows.add(row);
        return this;
    }

    // Holds data rows for the table
    public final Repository.Property.List<Object[]> rows = new Repository.Property.List<>();

    /** Adds a cell renderer */
    public final Table cellRenderer(CellRenderer cellRenderer) {
        cellRenderers.add(cellRenderer);
        return this;
    }
    /** ist of custom cell renderers for the table */
    private final List<CellRenderer> cellRenderers = new ArrayList<>();

    /** Adds a cell editor */
    public final Table cellEditor(CellEditor cellEditor) {
        cellEditors.add(cellEditor);
        return this;
    }
    /** List of custom cell editors for the table */
    private final List<CellEditor> cellEditors = new ArrayList<>();

    /** Whether the component is in prerender mode */
    private boolean prerendering = false;

    /** The hovered row index */
    private int hoveredRowIndex = -1;

    /** The hovered col index */
    private int hoveredColumnIndex = -1;

    /** Add a mouse adapters */
    public Table mouseListener(MouseListener mouseListener) {
        this.mouseListeners.add(mouseListener);
        addMouseListener(mouseListener);
        return this;
    }
    /** All the registered mouse adapters */
    private List<MouseListener> mouseListeners = new ArrayList<>();

    /** SET Whether the table can be validated */
    public Table validatable(boolean validatable) {
        this.validatable = validatable;
        return this;
    }
    /** GET Whether the table can be validated */
    public boolean validatable() {
        return validatable;
    }
    /** Whether the table can be validated */
    private boolean validatable = false;

    /** SET the height per row */
    public Table height(int rowHeight) {
        setRowHeight(rowHeight);
        this.height = rowHeight;
        return this;
    }

    /** SET the height for a specific row*/
    public Table height(int index, int rowHeight) {
        setRowHeight(index, rowHeight);
        this.heights.put(index, rowHeight);
        return this;
    }

    /** Gets the row height */
    public int height() {
        return height;
    }

    /** The height per row */
    private int height = Theme.TABLE_ROW_HEIGHT;

    /** The map of custom heights per row */
    private Map<Integer, Integer> heights = new HashMap<>();

    /**
     * Calculates the maximum number of columns across all rows.
     *
     * @return the maximum number of columns
     */
    public int columnCount() {
        int maxColumnCount = 0;
        for (Object[] row : rows.values()) {
            maxColumnCount = Math.max(row.length, maxColumnCount);
        }
        return maxColumnCount;
    }

    /** ADD to the grouped cells */
    public Table group(Group group) {
        this.groups.add(group);
        return this;
    }
    /** Grouped cells */
    private List<Group> groups = new ArrayList<>();

    /** SET The default horizontal alignment (SwingConstants.LEFT/CENTER/ETC) */
    public Table horizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }
    /** The default horizontal alignment (SwingConstants.LEFT/CENTER/ETC) */
    private int horizontalAlignment = SwingConstants.LEFT;

    /** SET The default vertical alignment (SwingConstants.LEFT/CENTER/ETC) */
    public Table verticalAlignment(int verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }
    /** The default vertical alignment (SwingConstants.LEFT/CENTER/ETC) */
    private int verticalAlignment = SwingConstants.CENTER;

    /**
     * Constructs a new table instance with a default table model and configures
     * properties like buffering, visibility, and background colors.
     */
    public Table() {
        super(new DefaultTableModel());
        Table table = this;
        setDoubleBuffered(true);
        setVisible(true);
        setFillsViewportHeight(true);
        setCellSelectionEnabled(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setDefaultEditor(Object.class, null);  // Disable editing by default
        setOpaque(true);
        setBackground(Theme.BG_COLOR);
        setRowHeight(Theme.TABLE_ROW_HEIGHT);

        // Add mouse listeners to handle click and movement events
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() > 1) {
                    e.consume(); // Prevent double-click action
                }
                clearSelection(); // Clear selection on click
                revalidate();
                repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                Group group = null;
                for (Group g : groups) {
                    for (Point coordinate : g.coordinates) {
                        if (coordinate.x == hoveredColumnIndex && coordinate.y == hoveredRowIndex) {
                            group = g;
                            break;
                        }
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (group != null && group.onClickRunnable != null) {
                        group.onClickRunnable.run();
                    }
                }
                else {
                    if (group != null) {
                        group.onRightClick(e);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                int col = columnAtPoint(point);
                if (row != hoveredRowIndex || col != hoveredColumnIndex) {
                    hoveredRowIndex = row;
                    hoveredColumnIndex = col;
                    updateRender();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRowIndex = -1;
                hoveredColumnIndex = -1;
                updateRender();
            }
        });

        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                // Calculate the hovered row/col
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                int col = columnAtPoint(point);
                if (row != hoveredRowIndex || col != hoveredColumnIndex) {
                    hoveredRowIndex = row;
                    hoveredColumnIndex = col;
                    updateRender();
                }
            }
        });

        // Listen for changes to the rows property and trigger re-rendering
        rows.listen(new Repository.Property.List.Listener<Object[]>() {
            @Override
            public void onChange(Repository.Property<Object[]> property, Integer oldKey, Integer newKey, Object[] oldValue, Object[] newValue) {
                if (prerendering) return;
                render();
            }

            @Override
            public void onAdd(Repository.Property<Object[]> property, int key, Object[] newValue) {
                if (prerendering) return;
                render();
            }

            @Override
            public void onRemove(Repository.Property<Object[]> property, int key, Object[] oldValue) {
                if (prerendering) return;
                render();
            }

            @Override
            public void onClear() {
                if (prerendering) return;
                render();
            }

            @Override
            public void onDelete() {
                if (prerendering) return;
                render();
            }
        });
    }

    /**
     * Prepares the table for rendering.
     */
    protected void prerender() throws Exception {
        // Variables
        DefaultTableModel model = (DefaultTableModel) this.getModel();

        // Clear existing rows
        model.setRowCount(0);
        model.setColumnCount(0);

        // Clear cell editors/renderers
        cellEditors.clear();
        cellRenderers.clear();

        // Add new columns
        for (int i = 0; i < columnCount(); i ++) {
            model.addColumn("");
        }
    }

    /**
     * Updates the table's model to match the data in rows and applies
     * any specified cell renderers and editors to the appropriate columns.
     *
     * @return this table instance, for chaining
     */
    public Table render() {
        // Pre-rendering
        prerendering = true;
        try {
            this.prerender();
        }
        catch (Exception ignored) {}
        prerendering = false;

        // Variables
        DefaultTableModel model = (DefaultTableModel) this.getModel();

        // Populate rows from the repository property list
        for (int i = 0; i < rows.size(); i ++) {
            Object[] row = rows.get(i);
            if (row == null) continue;
            // Add the row
            model.addRow(row);
            // Set the row height for each row
            setRowHeight(i, heights.getOrDefault(i, height));
        }

        for (int i = 0; i < model.getRowCount(); i ++) {
            Object[] row = rows.get(i);
            if (row == null) continue;
            for (int j = 0; j < row.length; j ++) {
                Object cell = row[j];
                if (cell == null) continue;
                if (cell instanceof JComponent) {
                    JComponent k = (JComponent) cell;
                    k.setBorder(BorderFactory.createEmptyBorder());
                }
                if (cell instanceof MenuButton) {
                    MenuButton data = (MenuButton) cell;
                    data.isHovered(hoveredRowIndex == i && hoveredColumnIndex == j);
                    cellRenderer(new CellRenderer(new MenuButtonCellRenderer((MenuButton) cell), i, j));
                    cellEditor(new CellEditor(new MenuButtonCellEditor((MenuButton) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            for (Runnable runnable : MenuButton.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof io.hurx.components.button.Button) {
                    io.hurx.components.button.Button data = (io.hurx.components.button.Button) cell;
                    data.isHovered(hoveredRowIndex == i && hoveredColumnIndex == j);
                    cellRenderer(new CellRenderer(new ButtonCellRenderer((io.hurx.components.button.Button) cell), i, j));
                    cellEditor(new CellEditor(new ButtonCellEditor((io.hurx.components.button.Button) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            for (Runnable runnable : io.hurx.components.button.Button.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof ComboBox) {
                    cellRenderer(new CellRenderer(new ComboBoxCellRenderer((ComboBox<?>) cell), i, j));
                    cellEditor(new CellEditor(new ComboBoxCellEditor((ComboBox<?>) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            ComboBox<?> data = (ComboBox<?>) cell;
                            for (Runnable runnable : ComboBox.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof NumberSlider) {
                    cellRenderer(new CellRenderer(new NumberSliderCellRenderer((NumberSlider) cell), i, j));
                    cellEditor(new CellEditor(new NumberSliderCellEditor((NumberSlider) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            NumberSlider data = (NumberSlider) cell;
                            for (Runnable runnable : NumberSlider.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof FormattedNumberTextField) {
                    cellRenderer(new CellRenderer(new FormattedNumberTextFieldCellRenderer((FormattedNumberTextField) cell), i, j));
                    cellEditor(new CellEditor(new FormattedNumberTextFieldCellEditor((FormattedNumberTextField) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            FormattedNumberTextField data = (FormattedNumberTextField) cell;
                            for (Runnable runnable : FormattedNumberTextField.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof TextField) {
                    TextField data = (TextField) cell;
                    data.isHovered(i == hoveredRowIndex && j == hoveredColumnIndex);
                    cellRenderer(new CellRenderer(new TextFieldCellRenderer((TextField) cell), i, j));
                    cellEditor(new CellEditor(new TextFieldCellEditor((TextField) cell) {
                        @Override
                        public boolean stopCellEditing() {
                            for (Runnable runnable : TextField.onStopCellEditingRunnables.getOrDefault(data, new ArrayList<>())) {
                                runnable.run();
                            }
                            return super.stopCellEditing();
                        }
                    }, i, j));
                }
                else if (cell instanceof Label.Plain) {
                    if (validatable) {
                        cellRenderer(new CellRenderer(new DefaultCellRenderer(), i, j));
                    }
                    else {
                        Label.Plain data = (Label.Plain) cell;
                        data.hovered(i == hoveredRowIndex && j == hoveredColumnIndex);
                        data.revalidate();
                        data.repaint();
                        cellRenderer(new CellRenderer(new Label.Plain.CellRenderer(data), i, j));
                        cellEditor(new CellEditor(new Label.Plain.CellEditor(data), i, j));
                    }
                }
                else {
                    cellRenderer(new CellRenderer(new DefaultCellRenderer(), i, j));
                }
            }
        }

        revalidate();
        repaint();

        return this;
    }

    /**
     * Updates the rendered components and sets the hover state
     */
    private void updateRender() {
        for (CellRenderer cellRenderer : cellRenderers) {
            if (cellRenderer.cellRenderer instanceof MenuButtonCellRenderer) {
                MenuButtonCellRenderer renderer = (MenuButtonCellRenderer) cellRenderer.cellRenderer;
                MenuButton menuButton = renderer.menuButton();
                menuButton.isHovered(hoveredRowIndex == cellRenderer.row && hoveredColumnIndex == cellRenderer.col);
                menuButton.revalidate();
                menuButton.repaint();
            }
            else if (cellRenderer.cellRenderer instanceof ButtonCellRenderer) {
                ButtonCellRenderer renderer = (ButtonCellRenderer) cellRenderer.cellRenderer;
                Button button = renderer.button();
                button.isHovered(hoveredRowIndex == cellRenderer.row && hoveredColumnIndex == cellRenderer.col);
                button.revalidate();
                button.repaint();
            }
            else if (cellRenderer.cellRenderer instanceof TextFieldCellRenderer) {
                TextFieldCellRenderer renderer = (TextFieldCellRenderer) cellRenderer.cellRenderer;
                TextField textField = renderer.textField();
                textField.isHovered(hoveredRowIndex == cellRenderer.row && hoveredColumnIndex == cellRenderer.col);
                textField.revalidate();
                textField.repaint();
            }
            else if (cellRenderer.cellRenderer instanceof FormattedNumberTextFieldCellRenderer) {
                FormattedNumberTextFieldCellRenderer renderer = (FormattedNumberTextFieldCellRenderer) cellRenderer.cellRenderer;
                FormattedNumberTextField textField = renderer.textField();
                textField.isHovered(hoveredRowIndex == cellRenderer.row && hoveredColumnIndex == cellRenderer.col);
                textField.revalidate();
                textField.repaint();
            }
            else if (cellRenderer.cellRenderer instanceof Label.Plain.CellRenderer) {
                Label.Plain.CellRenderer renderer = (Label.Plain.CellRenderer) cellRenderer.cellRenderer;
                Label.Plain label = renderer.plain();
                label.hovered(hoveredRowIndex == cellRenderer.row && hoveredColumnIndex == cellRenderer.col);
                label.revalidate();
                label.repaint();
            }
        }
        revalidate();
        repaint();
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        for (CellEditor cellEditor : cellEditors) {
            if (cellEditor.row == row && cellEditor.col == column) {
                return cellEditor.cellEditor;
            }
        }
        return super.getCellEditor(row, column);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        for (CellRenderer cellRenderer : cellRenderers) {
            if (cellRenderer.row == row && cellRenderer.col == column) {
                return cellRenderer.cellRenderer;
            }
        }
        return super.getCellRenderer(row, column);
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return false;
    }

    /** A group of cells */
    public static class Group {
        /** Checks whether the group is validated */
        public boolean isValidated() {
            return true;
        }

        /** Checks whether a group is validatable */
        public boolean isValidatable() {
            return this.colorValidated != null && this.colorInvalidated != null;
        }

        /** On right click handler */
        public void onRightClick(MouseEvent e) {
            return;
        }

        /** Whether or not the group is active */
        public boolean isActive() {
            return false;
        }

        /** Whether or not the group is hoverable */
        public boolean isHoverable() {
            return false;
        }

        /** ADD to the list of coordinates of this group */
        public Group coordinate(Point point) {
            coordinates.add(point);
            return this;
        }
        /** The list of coordinates of this group */
        public List<Point> coordinates = new ArrayList<>();

        /** SET The color when validated */
        public Group colorValidated(Color color) {
            colorValidated = color;
            return this;
        }
        /** The color when validated */
        private Color colorValidated;

        /** SET The color when invalidated */
        public Group colorInvalidated(Color color) {
            colorInvalidated = color;
            return this;
        }
        /** The color when invalidated */
        private Color colorInvalidated;

        public Group colorInactive(Color color) {
            colorInactive = color;
            return this;
        }
        /** The color when not hovering and inactive */
        private Color colorInactive;

        /** SET The color on hover */
        public Group colorHover(Color color) {
            colorHover = color;
            return this;
        }
        /** The color on hover */
        private Color colorHover;

        /** SET The onclick runnable */
        public Group onClick(Runnable onClickRunnable) {
            this.onClickRunnable = onClickRunnable;
            return this;
        }
        /** The onclick runnable */
        private Runnable onClickRunnable;

        public Group() {}
    }

    /**
     * Represents a custom cell renderer with specified column range.
     */
    public static class CellRenderer {
        private final DefaultTableCellRenderer cellRenderer;
        private final int row;
        private final int col;

        public CellRenderer(DefaultTableCellRenderer cellRenderer, int row, int col) {
            this.cellRenderer = cellRenderer;
            this.row = row;
            this.col = col;
        }
    }

    /**
     * Represents a custom cell editor with specified column range.
     */
    public static class CellEditor {
        private final TableCellEditor cellEditor;
        private final int row;
        private final int col;

        private CellEditor(TableCellEditor cellEditor, int row, int col) {
            this.cellEditor = cellEditor;
            this.row = row;
            this.col = col;
        }
    }

    /**
     * Default cell renderer for the table that allows for JComponent rendering.
     */
    public static class DefaultCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Table table = (Table) jTable;
            Group group = null;
            boolean isHovered = false;
            for (Group g : table.groups) {
                for (Point coordinate : g.coordinates) {
                    if (coordinate.x == column && coordinate.y == row) {
                        group = g;
                    }
                }
            }
            if (group != null) {
                for (Point coordinate : group.coordinates) {
                    if (coordinate.x == table.hoveredColumnIndex && coordinate.y == table.hoveredRowIndex) {
                        isHovered = true;
                    }
                }
            }
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel();
                processLabel(label, group, table, isHovered);
                label.setIcon((ImageIcon)value);
                return label;
            }
            else if (value instanceof JLabel) {
                JLabel label = (JLabel) value;
                processLabel(label, group, table, isHovered);
                return label;
            }
            else if (value instanceof Icon) {
                Icon icon = (Icon) value;
                icon.setOpaque(true);
                if (group != null) {
                    if (group.isHoverable() && isHovered && group.colorHover != null) {
                        icon.setBackground(group.colorHover);
                    }
                    else if (table.validatable && group.isValidated() && group.isValidatable() && group.colorValidated != null) {
                        icon.setBackground(group.colorValidated);
                    }
                    else if (table.validatable && !group.isValidated() && group.isValidatable() && group.colorInvalidated != null) {
                        icon.setBackground(group.colorInvalidated);
                    }
                    else if (group.colorInactive != null) {
                        icon.setBackground(group.colorInactive);
                    }
                    else {
                        icon.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
                else {
                    icon.setBackground(Theme.BG_COLOR);
                }
                return icon;
            }
            else if (value instanceof Component) {
                return (Component) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        /** Processes the background color and other properties for a label */
        private void processLabel(JLabel label, Group group, Table table, boolean isHovered) {
            label.setOpaque(true);
            label.setHorizontalAlignment(table.horizontalAlignment);
            label.setVerticalAlignment(table.verticalAlignment);
            if (group != null) {
                if (group.isHoverable() && isHovered && group.colorHover != null) {
                    label.setBackground(group.colorHover);
                }
                else if (table.validatable && group.isValidated() && group.isValidatable() && group.colorValidated != null) {
                    label.setBackground(group.colorValidated);
                }
                else if (table.validatable && !group.isValidated() && group.isValidatable() && group.colorInvalidated != null) {
                    label.setBackground(group.colorInvalidated);
                }
                else if (group.colorInactive != null) {
                    label.setBackground(group.colorInactive);
                }
                else {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                }
            }
            else {
                label.setBackground(Theme.BG_COLOR);
            }
            if (label instanceof Label.Plain) {
                Label.Plain plain = (Label.Plain) label;
                if (plain.border() != null) {
                    plain.setBorder(plain.border());
                }
                if (plain.font() != null) {
                    plain.setFont(plain.font());
                }
            }
        }
    }
}
