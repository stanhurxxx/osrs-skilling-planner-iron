package io.hurx.components.table.defaultTable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import io.hurx.utils.Theme;

public class DefaultTable extends JTable {
    protected DefaultTable.Options options;

    public DefaultTable(DefaultTable.Options options) {
        super(new DefaultTableModel());
        this.options = options;
        DefaultTable table = this;
        setDoubleBuffered(true);
        setVisible(true);
        setFillsViewportHeight(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setCellSelectionEnabled(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setDefaultEditor(Object.class, null);
        setOpaque(true);
        setBackground(Theme.BG_COLOR);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                if (e.getClickCount() > 1) {
                    e.consume();
                }
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e)
            {
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
    }

    /**
     * Fills the table with data
     */
    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }
        if (model.getColumnCount() != options.columnCount) {
            for (int i = 0; i < options.columnCount; i ++) {
                model.addColumn("");
            }
        }
    }

    public static class Options {
        public void setColumnCount(int columnCount) {
            this.columnCount = columnCount;
        }
        public int getColumnCount() {
            return columnCount;
        }
        private int columnCount = 2;

        public int getVPadding() {
            return vPadding;
        }
        private int vPadding = 10;

        public int getHPadding() {
            return hPadding;
        }
        private int hPadding = 10;

        public int getRowHeight() {
            return rowHeight;
        }
        private int rowHeight = 20;

        private Options() {}
        
        public static class Builder {
            private Options options;
    
            private Builder(Options options) {
                this.options = options;
            }
    
            public static Builder construct() {
                return new Builder(new Options());
            }
    
            public Builder columnCount(int columnCount) {
                options.columnCount = columnCount;
                return this;
            }
    
            public Builder padding(int vPadding, int hPadding) {
                options.vPadding = vPadding;
                options.hPadding = hPadding;
                return this;
            }
    
            public Builder vPadding(int vPadding) {
                options.vPadding = vPadding;
                return this;
            }
    
            public Builder hPadding(int hPadding) {
                options.hPadding = hPadding;
                return this;
            }
    
            public Builder rowHeight(int rowHeight) {
                options.rowHeight = rowHeight;
                return this;
            }

            public Options build() {
                return this.options;
            }
        }
    }
}
