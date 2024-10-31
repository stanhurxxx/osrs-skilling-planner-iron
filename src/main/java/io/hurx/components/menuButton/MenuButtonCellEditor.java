package io.hurx.components.menuButton;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;

import java.util.EventObject;

public class MenuButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
    private MenuButton button;

    private int index;

    public MenuButtonCellEditor() {
        // Default constructor, slider will be set for each row
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public MenuButton getButton() {
        return this.button;
    }

    public void setButton(MenuButton button) {
        this.button = button;
    }

    @Override
    public Object getCellEditorValue() {
        return button; // Return the current value of the slider
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the slider value based on the table cell value if it's an Integer
        return button;
    }
}