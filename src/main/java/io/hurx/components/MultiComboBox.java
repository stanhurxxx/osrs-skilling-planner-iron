package io.hurx.components;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.util.Arrays;

import io.hurx.components.comboBox.ComboBoxCellEditor;
import io.hurx.components.comboBox.ComboBoxCellRenderer;
import io.hurx.components.table.Table;

/**
 * The MultiComboBox class represents a custom table component that allows 
 * the use of multiple JComboBox controls within a JTable.
 * Each column in the table corresponds to a JComboBox, enabling users to 
 * select options from drop-down menus in a tabular format.
 */
public class MultiComboBox extends Table {
    private final JComboBox<?>[] controls; // Array of JComboBox controls for the table

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
        super();
        this.controls = controls;
    }

    @Override
    public void prerender() throws Exception {
        rows.clear();
        row(controls);
        super.prerender();
    }
}
