package io.hurx.components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;

/**
 * A table in the skilling planner panel.
 */
public class Table extends JTable {
    /**
     * Table data
     */
    private Object[][] data;
    
    /**
     * Ref to main panel
     */
    private SkillingPlannerPanel panel;

    /**
     * The hovered row
     */
    private int hoveredRow = -1;

    public Table(SkillingPlannerPanel panel, Object[][] data) {
        super(data, new String[] { "", "", "" });
        Table table = this;
        this.panel = panel;
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        super.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());
        super.getColumnModel().getColumn(1).setCellRenderer(new JLabelCellRenderer());
        super.getColumnModel().getColumn(2).setCellRenderer(new JLabelCellRenderer());
        super.getColumnModel().getColumn(0).setPreferredWidth(Theme.ICON_SIZE + Theme.TABLE_H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMaxWidth(Theme.ICON_SIZE + Theme.TABLE_H_PADDING * 2);
        super.addMouseMotionListener(new MouseAdapter() {
            @Override
			public void mouseMoved(MouseEvent e)
			{
				Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (row != hoveredRow) {
                    hoveredRow = row;
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                hoveredRow = -1;
                panel.revalidate();
                panel.repaint();
            }
        });
        super.setRowHeight(Theme.TABLE_V_PADDING * 2 + 16);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);

        // Check if the row is hovered, apply hover color, else default color
        if (row == hoveredRow) {
            component.setBackground(Theme.TABLE_BG_COLOR_HOVER); // Hover background color
        } else {
            component.setBackground(Theme.TABLE_BG_COLOR); // Normal background color
        }

        // Set the opaque property to ensure the background is rendered
        if (component instanceof JComponent) {
            ((JComponent) component).setOpaque(true);
        }

        return component;
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class IconCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setOpaque(true);
            if (value instanceof ImageIcon) {
                label.setIcon((ImageIcon) value); // Set the icon on the JLabel
                label.setText(""); // Clear the text
                label.setFont(new Font("Arial", Font.PLAIN, 16));
            }
            label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING));
            label.setVerticalAlignment(SwingConstants.CENTER);
            return label; // Return the label with the icon set
        }
    }

    /**
     * Custom cell renderer for JLabel
     */
    public static class JLabelCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            if (value instanceof JLabel) {
                label = (JLabel) value; // Return the JLabel directly
            }
            label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING));
            label.setVerticalAlignment(SwingConstants.CENTER);
            if (column == table.getColumnCount() - 1) {
                label.setHorizontalAlignment(SwingConstants.RIGHT);
            }
            return label;
        }
    }
}
