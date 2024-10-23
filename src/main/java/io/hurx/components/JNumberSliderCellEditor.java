package io.hurx.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;

public class JNumberSliderCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JNumberSlider slider;

    public JNumberSliderCellEditor() {
        slider = new JNumberSlider();
    }

    @Override
    public Object getCellEditorValue() {
        return slider.getCurrentValue(); // Return the current value of the slider
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// Set the slider value based on the table cell value
        if (value instanceof Integer) {
            slider.setCurrentValue((Integer) value);
        }
        return slider;
	}
}