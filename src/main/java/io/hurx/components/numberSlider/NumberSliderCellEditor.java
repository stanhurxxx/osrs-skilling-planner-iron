package io.hurx.components.numberSlider;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellEditor;

import java.util.EventObject;
import java.util.List;
import java.util.ArrayList;

public class NumberSliderCellEditor extends AbstractCellEditor implements TableCellEditor {
    private NumberSlider slider;

    private int index;

    private List<NumberSlider> mouseListeners = new ArrayList<>();

    public NumberSliderCellEditor() {
        // Default constructor, slider will be set for each row
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public NumberSlider getSlider() {
        return this.slider;
    }

    public void setSlider(NumberSlider slider) {
        this.slider = slider;
        if (!mouseListeners.contains(slider)) {
            NumberSliderCellEditor editor = this;
            slider.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    editor.stopCellEditing();
                }
            });
            mouseListeners.add(slider);
        }
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
        // Set the slider value based on the table cell value if it's an Integer
        if (value instanceof Integer) {
            slider.setCurrentValue((Integer) value);
        }
        return slider;
    }
}