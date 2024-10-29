package io.hurx.views.slayer.components.planner.planning;

import io.hurx.Theme;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.components.ProgressBar;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.views.slayer.SlayerView;

import java.awt.Component;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

public class SlayerPlanningPercentageTable extends DefaultTable {
    public SlayerPlanningData getPlanning() {
        for(SlayerPlanningData planning : view.getPanel().getCache().getData().getSlayer().getPlannings()) {
            if (planning.getUuid().equals(uuid)) {
                return planning;
            }
        }
        return null;
    }
    
    private float percentage;
    private String uuid;
    private SlayerView view;
    
    public SlayerPlanningPercentageTable(SlayerView view, String uuid) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.percentage = (float)Math.random() * 100;
        this.uuid = uuid;
        this.view = view;
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
