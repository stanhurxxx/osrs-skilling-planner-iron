package io.hurx.views.slayer.components;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.models.slayer.masters.Duradel;
import io.hurx.models.slayer.masters.Nieve;
import io.hurx.models.slayer.masters.SlayerMaster;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.slayer.monsters.SlayerMonsters;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SlayerTasksTable extends JTable {
    public static int ICON_SIZE = 16;

    public static int ICONS_PER_ROW = 5;

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
     * Get the hovered column index
     * @return
     */
    public int getHoveredRowIndex() {
        return hoveredRowIndex;
    }

    /**
     * Hovered row index
     */
    private int hoveredRowIndex = -1;

    /**
     * Get the slayer monsters
     * @return
     */
    public SlayerMonster[] getSlayerMonsters() {
        SlayerMasters master = panel.getCache().getData().slayerMaster;
        return master == SlayerMasters.Duradel ? new Duradel().getAssignments() : new Nieve().getAssignments();
    }

    public SlayerTasksTable(SkillingPlannerPanel panel) {
        super(new DefaultTableModel());
        this.panel = panel;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            model.addColumn("");
        }
        SlayerMonster[] assignments = getSlayerMonsters();
        for (int i = 0; i <= Math.ceil(assignments.length / ICONS_PER_ROW); i ++) {
            java.util.List<Object> list = new ArrayList<>();
            loop: for (int j = 0; j < ICONS_PER_ROW; j ++) {
                int index = i * ICONS_PER_ROW + j;
                if (index >= assignments.length) {
                    list.add(new JLabel(""));
                    continue loop;
                }
                list.add(
                    Resources.loadImageIcon(assignments[index].iconSrc, ICON_SIZE, ICON_SIZE)
                );
            }
            model.addRow(list.toArray());
            System.out.println("Row added: " + i);
        }
        SlayerTasksTable table = this;
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            super.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        super.setRowHeight(Theme.TABLE_V_PADDING * 2 + ICON_SIZE);
        super.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseMoved(MouseEvent e)
			{
				Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                int row = table.rowAtPoint(point);
                if (col != hoveredColumnIndex || row != hoveredRowIndex) {
                    hoveredColumnIndex = col;
                    hoveredRowIndex = row;
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
                hoveredRowIndex = -1;
                panel.revalidate();
                panel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    SlayerMonster monster = getSlayerMonsters()[row * ICONS_PER_ROW + column];
                    System.out.println(monster.name);
                    java.util.List<SlayerMonsters> skipped = new ArrayList<SlayerMonsters>();
                    CacheData data = panel.getCache().getData();
                    for (int i = 0; i < data.slayerSkipped.length; i ++) {
                        skipped.add(data.slayerSkipped[i]);
                    }
                    java.util.List<SlayerMonsters> blocked = new ArrayList<SlayerMonsters>();
                    for (int i = 0; i < data.slayerBlocked.length; i ++) {
                        blocked.add(data.slayerBlocked[i]);
                    }
                    // Create a popup menu
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem doItem = new JMenuItem("Do");
                    doItem.addActionListener(ae -> {
                        if (skipped.contains(monster.name)) {
                            skipped.remove(monster.name);
                        }
                        if (blocked.contains(monster.name)) {
                            blocked.remove(monster.name);
                        }
                        panel.getCache().getData().slayerSkipped = skipped.toArray(new SlayerMonsters[skipped.size()]);
                        panel.getCache().getData().slayerBlocked = blocked.toArray(new SlayerMonsters[blocked.size()]);
                        try {
                            panel.getCache().save();
                            panel.revalidate();
                            panel.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    JMenuItem blockItem = new JMenuItem("Block");
                    blockItem.addActionListener(ae -> {
                        if (skipped.contains(monster.name)) {
                            skipped.remove(monster.name);
                        }
                        if (!blocked.contains(monster.name)) {
                            blocked.add(monster.name);
                        }
                        panel.getCache().getData().slayerSkipped = skipped.toArray(new SlayerMonsters[skipped.size()]);
                        panel.getCache().getData().slayerBlocked = blocked.toArray(new SlayerMonsters[blocked.size()]);
                        try {
                            panel.getCache().save();
                            panel.revalidate();
                            panel.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    JMenuItem skipItem = new JMenuItem("Skip");
                    skipItem.addActionListener(ae -> {
                        if (blocked.contains(monster.name)) {
                            blocked.remove(monster.name);
                        }
                        if (!skipped.contains(monster.name)) {
                            skipped.add(monster.name);
                        }
                        panel.getCache().getData().slayerSkipped = skipped.toArray(new SlayerMonsters[skipped.size()]);
                        panel.getCache().getData().slayerBlocked = blocked.toArray(new SlayerMonsters[blocked.size()]);
                        try {
                            panel.getCache().save();
                            panel.revalidate();
                            panel.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    if (skipped.contains(monster.name)) {
                        popupMenu.add(doItem);
                        popupMenu.add(blockItem);
                    }
                    else if (blocked.contains(monster.name)) {
                        popupMenu.add(doItem);
                        popupMenu.add(skipItem);
                    }
                    else {
                        popupMenu.add(skipItem);
                        popupMenu.add(blockItem);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        // Get the location of the mouse event
        Point p = e.getPoint();
        // Find out which row and column the mouse is over
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        SlayerMonster[] monsters = getSlayerMonsters();
        int index = rowIndex * ICONS_PER_ROW + colIndex;
        if (index >= monsters.length) {
            return null;
        }
        else {
            return monsters[index].name.toString();
        }
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
                label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING));
            }
            else if (value instanceof JLabel) {
                label = (JLabel) value; // Return the JLabel directly
                label.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING));
            }
            label.setOpaque(true);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            SlayerMonster[] monsters = ((SlayerTasksTable) table).getSlayerMonsters();
            int index = row * ICONS_PER_ROW + column;
            if (index >= monsters.length) {
                label.setBackground(Theme.TABLE_BG_COLOR);
            }
            else {
                SlayerMonster monster = monsters[row * 5 + column];
                java.util.List<SlayerMonsters> skipped = new ArrayList<SlayerMonsters>();
                CacheData data = ((SlayerTasksTable) table).panel.getCache().getData();
                for (int i = 0; i < data.slayerSkipped.length; i ++) {
                    skipped.add(data.slayerSkipped[i]);
                }
                java.util.List<SlayerMonsters> blocked = new ArrayList<SlayerMonsters>();
                for (int i = 0; i < data.slayerBlocked.length; i ++) {
                    blocked.add(data.slayerBlocked[i]);
                }
                if (skipped.contains(monster.name)) {
                    label.setBackground(Theme.TABLE_BG_COLOR_WARN);
                }
                else if (blocked.contains(monster.name)) {
                    label.setBackground(Theme.TABLE_BG_COLOR_DANGER);
                }
                else {
                    if (((SlayerTasksTable) table).getHoveredColumnIndex() == column && ((SlayerTasksTable) table).getHoveredRowIndex() == row) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    }
                    else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
            }
            return label; // Return the label with the icon set
        }
    }
}
