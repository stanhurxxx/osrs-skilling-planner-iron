package io.hurx.components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

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
import java.util.ArrayList;
import java.util.List;

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

    // List of custom cell renderers for the table
    private final List<CellRenderer> cellRenderers = new ArrayList<>();

    // List of custom cell editors for the table
    private final List<CellEditor> cellEditors = new ArrayList<>();

    /** Whether the component is in prerender mode */
    private boolean prerendering = false;

    /** The hovered row index */
    private int hoveredRowIndex = -1;

    /** The hovered col index */
    private int hoveredColumnIndex = -1;

    /** All the registered mouse adapters */
    private List<MouseListener> mouseListeners = new ArrayList<>();

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

    public Table mouseListener(MouseListener mouseListener) {
        this.mouseListeners.add(mouseListener);
        addMouseListener(mouseListener);
        return this;
    }

    /**
     * Adds a cell renderer to the table with the specified start and end column indices.
     *
     * @param cellRenderer the cell renderer to be added
     * @return this table instance, for chaining
     */
    public final Table cellRenderer(CellRenderer cellRenderer) {
        cellRenderers.add(cellRenderer);
        return this;
    }

    /**
     * Adds a cell editor to the table with the specified start and end column indices.
     *
     * @param cellEditor the cell editor to be added
     * @return this table instance, for chaining
     */
    public final Table cellEditor(CellEditor cellEditor) {
        cellEditors.add(cellEditor);
        return this;
    }

    /**
     * Sets the height for each row in the table.
     *
     * @param rowHeight the height in pixels for each row
     * @return this table instance, for chaining
     */
    public Table height(int rowHeight) {
        setRowHeight(rowHeight);
        this.height = rowHeight;
        return this;
    }

    /** Gets the row height */
    public int height() {
        return height;
    }

    /** The height per row */
    private int height = Theme.TABLE_ROW_HEIGHT;

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
        for (Object[] row : rows.values()) {
            model.addRow(row);
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
                    Label.Plain data = (Label.Plain) cell;
                    data.hovered(i == hoveredRowIndex && j == hoveredColumnIndex);
                    data.revalidate();
                    data.repaint();
                    cellRenderer(new CellRenderer(new Label.Plain.CellRenderer(data), i, j));
                    cellEditor(new CellEditor(new Label.Plain.CellEditor(data), i, j));
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

    /**
     * Represents a custom cell renderer with specified column range.
     */
    public static class CellRenderer {
        private final DefaultTableCellRenderer cellRenderer;
        private final int row;
        private final int col;

        private CellRenderer(DefaultTableCellRenderer cellRenderer, int row, int col) {
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JComponent) {
                return (JComponent) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
