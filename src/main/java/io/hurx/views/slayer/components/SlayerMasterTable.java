package io.hurx.views.slayer.components;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.models.slayer.masters.SlayerMasters;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class SlayerMasterTable extends JTable {
    public static int ICON_SIZE = 64;

    /**
     * Get the panel
     * @return
     */
    public SkillingPlannerPanel getPanel() {
        return panel;
    }

    /**
     * The ref to the main panel
     */
    private SkillingPlannerPanel panel;

    /**
     * Get the hovered column index
     * @return
     */
    public int getHoveredColumnIndex() {
        return hoveredColumnIndex;
    }

    /**
     * Hovered column index
     */
    private int hoveredColumnIndex = -1;

    public SlayerMasterTable(SkillingPlannerPanel panel) {
        super(
            new Object[][] {
                {
                    Resources.loadImageIcon("icons/npcs/duradel.png", ICON_SIZE, ICON_SIZE),
                    Resources.loadImageIcon("icons/npcs/nieve.png", ICON_SIZE, ICON_SIZE),
                },
                {
                    new JLabel("Duradel"),
                    new JLabel("Nieve")
                },
            }, 
            new String[] {
                "", ""
            }
        );
        this.panel = panel;
        SlayerMasterTable table = this;
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        super.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer());
        super.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
        super.setRowHeight(Theme.TABLE_V_PADDING * 2 + 16);
        super.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseMoved(MouseEvent e)
			{
				Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                if (col != hoveredColumnIndex) {
                    hoveredColumnIndex = col;
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                hoveredColumnIndex = -1;
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof ImageIcon) {
                label.setIcon((ImageIcon) value); // Set the icon on the JLabel
                label.setText(""); // Clear the text
                label.setFont(new Font("Arial", Font.PLAIN, 16));
                table.setRowHeight(row, ICON_SIZE + Theme.TABLE_V_PADDING * 2);
                label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, 0, Theme.TABLE_H_PADDING));
            }
            else if (value instanceof JLabel) {
                label = (JLabel) value; // Return the JLabel directly
                label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING));
            }
            label.setOpaque(true);
            if (((SlayerMasterTable)table).getPanel().getCache().getData().slayerMaster == SlayerMasters.Duradel) {
                if (column == 0) {
                    label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                }
                else {
                    if (((SlayerMasterTable)table).getHoveredColumnIndex() == 1) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    }
                    else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
            }
            else {
                if (column == 1) {
                    label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                }
                else {
                    if (((SlayerMasterTable)table).getHoveredColumnIndex() == 0) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    }
                    else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
            }
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label; // Return the label with the icon set
        }
    }
}