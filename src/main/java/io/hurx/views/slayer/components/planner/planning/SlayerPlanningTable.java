package io.hurx.views.slayer.components.planner.planning;

import io.hurx.Theme;
import io.hurx.cache.data.SlayerListData;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextField;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextFieldCellEditor;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.components.textField.TextFieldCellEditor;
import io.hurx.views.slayer.SlayerView;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import java.util.EventObject;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.awt.event.KeyEvent;

public class SlayerPlanningTable extends DefaultTable {
    public final static int ICON_SIZE = 24;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 5;
    public final static int ROW_HEIGHT = 30;

    private AbbreviatedNumberFormattedTextField textField;

    public SlayerPlanningData getPlanning() {
        for(SlayerPlanningData planning : view.getPanel().getCache().getData().getSlayer().getPlannings()) {
            if (planning.getUuid().equals(uuid)) {
                return planning;
            }
        }
        return null;
    }

    private SlayerView view;
    private String uuid;

    public SlayerPlanningTable(SlayerView view, String uuid) {
        super(DefaultTable.Options.Builder.construct().columnCount(2).build());
        this.view = view;
        this.uuid = uuid;
        this.textField = new AbbreviatedNumberFormattedTextField();

        SlayerPlanningData planning = view.getPanel().getCache().getData().getSlayer().findPlanningByUuid(uuid);
        if (planning == null) return;
        textField.setValue(planning.getStartXP());
        textField.setBackground(Theme.TABLE_BG_COLOR);
        textField.setBorder(BorderFactory.createEmptyBorder());
        fillTableModel();
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        getColumnModel().getColumn(0).setPreferredWidth(75);
        getColumnModel().getColumn(0).setMaxWidth(75);
        getColumnModel().getColumn(0).setMinWidth(75);
        getColumnModel().getColumn(0).setWidth(75);
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
                    if (number > 200_000_000) {
                        textField.setValue(200_000_000);
                        number = 200_000_000;
                    }
                    view.editPlanning(getPlanning(), number);
                }
                return super.stopCellEditing();
            }
        };
        getColumnModel().getColumn(1).setCellEditor(editor);

        DefaultTableModel model = (DefaultTableModel)getModel();
        SlayerListData list = view.getPanel().getCache().getData().getSlayer().findListByUuid(getPlanning().getListUuid());
        if (list == null) return;
        
        model.addRow(new Object[] {
            new JLabel("Start XP"),
            textField
        });
        // TODO: dummy
        model.addRow(new Object[] {
            new JLabel("Hours left"),
            new JLabel("1.000")
        });
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            SlayerPlanningTable planningTable = (SlayerPlanningTable)table;
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
                planningTable.textField.setValue(value);
                return planningTable.textField;
            }
        }
    }
}
