package io.hurx.views.slayer.components.manage.xpRates;

import io.hurx.Theme;
import io.hurx.cache.data.SlayerListData;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextField;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextFieldCellEditor;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.models.View;
import io.hurx.views.slayer.SlayerView;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.util.EventObject;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class TextFieldContainer extends DefaultTable {
    public final static int ROW_HEIGHT = 30;

    private int index = -1;

    private AbbreviatedNumberFormattedTextField textField;

    private SlayerView view;
    
    public TextFieldContainer(int index, SlayerView view, AbbreviatedNumberFormattedTextField textField) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.index = index;
        this.view = view;
        this.textField = textField;
        fillTableModel();
    }

    public void fillTableModel() {
        super.fillTableModel();
        setRowHeight(ROW_HEIGHT);
        for (int i = 0; i < options.getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        AbbreviatedNumberFormattedTextFieldCellEditor editor = new AbbreviatedNumberFormattedTextFieldCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                if (textField == null) {
                    setIndex(-1);
                    setTextField(null);
                    return null;
                };
                super.setTextField(textField); // Set it in the editor
                super.setIndex(row);
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }

            @Override
            public boolean isCellEditable(EventObject e) {
                if (e instanceof MouseEvent) {
                    MouseEvent mouseEvent = (MouseEvent) e;
                    JTable table = (JTable) mouseEvent.getSource();
                    int row = table.rowAtPoint(mouseEvent.getPoint());
                    return row == 0;  // Only allow editing on row 0
                }
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                Object raw = getCellEditorValue();
                if (raw instanceof Number) {
                    int number = ((BigDecimal) raw).intValue();
                    view.editXPRate(index, number);
                }
                return super.stopCellEditing();
            }
        };
        getColumnModel().getColumn(0).setCellEditor(editor);
        DefaultTableModel model = (DefaultTableModel)getModel();
        model.addRow(new Object[] {
            textField
        });
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            TextFieldContainer container = (TextFieldContainer)table;
            if (value instanceof JLabel) {
                JLabel label = (JLabel)value;
                label.setOpaque(true);
                if (column == 0) {
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                }
                else {
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                }
                label.setVerticalAlignment(SwingConstants.CENTER);
                if (row % 2 == 0) {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                }
                else {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                }
                return label;
            }
            else if (value instanceof AbbreviatedNumberFormattedTextField) {
                AbbreviatedNumberFormattedTextField text = (AbbreviatedNumberFormattedTextField) value;
                return text;
            }
            else {
                container.textField.setValue(value);
                return container.textField;
            }
        }
    }
}
