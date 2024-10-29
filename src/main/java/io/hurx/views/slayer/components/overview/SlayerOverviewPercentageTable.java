package io.hurx.views.slayer.components.overview;

import io.hurx.Theme;
import io.hurx.components.ProgressBar;
import io.hurx.components.table.defaultTable.DefaultTable;
import java.awt.Component;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

public class SlayerOverviewPercentageTable extends DefaultTable {
    private float percentage;
    
    public SlayerOverviewPercentageTable(float percentage) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.percentage = percentage;
        fillTableModel();
    }
    
    @Override
    public void fillTableModel() {
        super.fillTableModel();
        setRowHeight(25);
        getColumnModel().getColumn(0).setCellRenderer(new CellRenderer());
        DefaultTableModel model = (DefaultTableModel)getModel();
        ProgressBar bar = new ProgressBar(percentage);
        model.addRow(new Object[] { bar });
    }

    class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ProgressBar) {
                ProgressBar bar = (ProgressBar) value;
                bar.setOpaque(true);
                bar.setBackground(Theme.TABLE_BG_COLOR);
                return bar;
            }
            return null;
        }
    }
}
