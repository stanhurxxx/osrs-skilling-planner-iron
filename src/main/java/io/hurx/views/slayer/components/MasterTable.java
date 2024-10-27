package io.hurx.views.slayer.components;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.views.slayer.SlayerView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class MasterTable extends JTable {
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

    /**
     * The slayer view
     */
    private SlayerView view;

    public MasterTable(SlayerView view) {
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
                {
                    new JLabel("Points per task"),
                    new JLabel("1")
                },
                {
                    new JLabel("Skip percentage"),
                    new JLabel("65% / 75%")
                },
                {
                    new JLabel("XP rate"),
                    new JLabel("44.382 / h")
                },
                {
                    new JLabel("Hours left"),
                    new JLabel("5.000")
                }
            }, 
            new String[] {
                "", ""
            }
        );
        this.panel = view.getPanel();
        this.view = view;
        MasterTable table = this;
        table.setDoubleBuffered(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        super.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer());
        super.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
        super.setRowHeight((int) Math.round(Theme.TABLE_V_PADDING * 1.5 + 16));
        super.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseMoved(MouseEvent e)
			{
				Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                int row = table.rowAtPoint(point);
                if (row > 1) {
                    if (hoveredColumnIndex != -1) {
                        hoveredColumnIndex = -1;
                        table.revalidate();
                        table.repaint();
                    }
                }
                else {
                    if (col != hoveredColumnIndex) {
                        hoveredColumnIndex = col;
                        table.revalidate();
                        table.repaint();
                    }
                }
            }
        });
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                hoveredColumnIndex = -1;
                table.revalidate();
                table.repaint();
            }
            @Override
            public void mousePressed(MouseEvent e)
            {
                table.revalidate();
                table.repaint();
            }
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                int row = table.rowAtPoint(point);
                SlayerMasters master = panel.getCache().getData().slayerMaster;
                if (row == 0) {
                    if (col == 0) {
                        if (master != SlayerMasters.Duradel) {
                            view.setSlayerMaster(SlayerMasters.Duradel);
                            table.revalidate();
                            table.repaint();
                        }
                    }
                    else {
                        if (master != SlayerMasters.Nieve) {
                            view.setSlayerMaster(SlayerMasters.Nieve);
                            table.revalidate();
                            table.repaint();
                        }
                    }
                }
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
            label.setOpaque(true);
            if (row < 2) {
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value); // Set the icon on the JLabel
                    label.setText(""); // Clear the text
                    label.setFont(new Font("Arial", Font.PLAIN, 16));
                    table.setRowHeight(row, ICON_SIZE + Theme.TABLE_V_PADDING * 2);
                    label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, 0, Theme.TABLE_H_PADDING));
                }
                else if (value instanceof JLabel) {
                    label = (JLabel) value; // Return the JLabel directly
                    label.setOpaque(true);
                    label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING / 2, Theme.TABLE_H_PADDING));
                }
                if (((MasterTable)table).getPanel().getCache().getData().slayerMaster == SlayerMasters.Duradel) {
                    if (column == 0) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SUCCESS);
                    }
                    else {
                        if (((MasterTable)table).getHoveredColumnIndex() == 1) {
                            label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        }
                        else {
                            label.setBackground(Theme.TABLE_BG_COLOR);
                        }
                    }
                }
                else {
                    if (column == 1) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SUCCESS);
                    }
                    else {
                        if (((MasterTable)table).getHoveredColumnIndex() == 0) {
                            label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        }
                        else {
                            label.setBackground(Theme.TABLE_BG_COLOR);
                        }
                    }
                }
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
            else {
                if (value instanceof JLabel) {
                    label = (JLabel) value; // Return the JLabel directly
                    label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING / 2, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING / 2, Theme.TABLE_H_PADDING));
                    table.setRowHeight(row, Theme.TABLE_V_PADDING + 16);
                }
                label.setOpaque(true);
                label.setBackground(Theme.TABLE_BG_COLOR_SUCCESS);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
            return label; // Return the label with the icon set
        }
    }
}
