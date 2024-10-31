package io.hurx.components;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

import io.hurx.components.comboBox.JComboBoxCellEditor;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.old.SlayerView.ComboBoxModel;

/**
 * The MultiComboBox class represents a custom table component that allows 
 * the use of multiple JComboBox controls within a JTable.
 * Each column in the table corresponds to a JComboBox, enabling users to 
 * select options from drop-down menus in a tabular format.
 */
public class MultiComboBox extends DefaultTable {
    private JComboBox<?>[] controls; // Array of JComboBox controls for the table

    /**
     * Gets the JComboBox controls used in the table.
     *
     * @return An array of JComboBox controls.
     */
    public JComboBox<?>[] getControls() {
        return this.controls;
    }

    /**
     * Constructs a MultiComboBox with the specified JComboBox controls.
     *
     * @param controls An array of JComboBox controls to be displayed in the table.
     */
    public MultiComboBox(JComboBox<?>[] controls) {
        super(DefaultTable.Options.builder().build());
        this.controls = controls;
        this.fillTableModel(); // Populate the table model with the JComboBox controls
    }

    /**
     * Constructs a MultiComboBox with the specified JComboBox controls and options.
     *
     * @param controls An array of JComboBox controls to be displayed in the table.
     * @param options  The options for customizing the table's appearance and behavior.
     */
    public MultiComboBox(JComboBox<?>[] controls, DefaultTable.Options options) {
        super(options);
        this.controls = controls;
    }

    /**
     * Fills the table model with the JComboBox controls.
     * Sets up the cell renderer and editor for each column, enabling 
     * the selection of items from the JComboBoxes.
     */
    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        
        // Set cell renderer and editor for each column
        for (int i = 0; i < options.getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
            getColumnModel().getColumn(i).setCellEditor(new JComboBoxCellEditor() {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<?> box = controls[column];
                    if (box == null) {
                        setIndex(-1);
                        setBox(null);
                        return null;
                    }
                    super.setBox(box);
                    super.setIndex(column);
                    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
                }

                @Override
                public boolean stopCellEditing() {
                    int index = getIndex();
                    if (index == -1) return false; // Cannot stop editing if index is -1
                    return super.stopCellEditing();
                }
            });
        }

        // Add the JComboBox controls as a new row in the model
        model.addRow(this.controls);
    }

    /**
     * Custom cell renderer for the JComboBox columns in the MultiComboBox.
     * This renderer ensures that the JComboBox is displayed correctly in each cell.
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        /**
         * Returns the component used for rendering the cell at the specified position.
         *
         * @param table     The JTable that is asking the renderer to draw.
         * @param value     The value to be displayed in the cell.
         * @param isSelected Whether or not the cell is selected.
         * @param hasFocus  Whether or not the cell has focus.
         * @param row       The row of the cell to be rendered.
         * @param column    The column of the cell to be rendered.
         * @return The component used for rendering the cell, which could be a JComboBox.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            MultiComboBox thisTable = (MultiComboBox) table;

            // Return JComboBox if the value is an instance of JComboBox
            if (value instanceof JComboBox) {
                return (JComboBox<?>) value;
            } else if (value instanceof ComboBoxModel) {
                JComboBox<?> box = thisTable.controls[column];
                if (box == null) {
                    return null;
                }
                box.setSelectedItem(value); // Set the selected item in the JComboBox
                return box;
            }
            return null; // Return null if value does not match expected types
        }
    }
}
