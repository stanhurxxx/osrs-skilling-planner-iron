package io.hurx.old.components.overview;

import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.old.SlayerView;
import io.hurx.utils.Theme;

import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.text.NumberFormat;
import java.util.Locale;

public class SlayerOverviewTable extends DefaultTable {
    public final static int ICON_SIZE = 24;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 5;
    public final static int ROW_HEIGHT = 30;

    private SlayerView view;

    public SlayerOverviewTable(SlayerView view) {
        super(DefaultTable.Options.Builder.construct().build());
        this.view = view;
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
        DefaultTableModel model = (DefaultTableModel)getModel();

        // TODO: dummy
        model.addRow(new Object[] {
            new JLabel("Current XP"),
            new JLabel(NumberFormat.getInstance(Locale.US).format(13_034_431))
        });
        model.addRow(new Object[] {
            new JLabel("Hours done"),
            new JLabel(NumberFormat.getInstance(Locale.US).format(350))
        });
        model.addRow(new Object[] {
            new JLabel("Hours left"),
            new JLabel(NumberFormat.getInstance(Locale.US).format(3650))
        });
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
            return null;
        }
    }
}
