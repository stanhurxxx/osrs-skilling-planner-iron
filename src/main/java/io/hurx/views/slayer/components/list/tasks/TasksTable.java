package io.hurx.views.slayer.components.list.tasks;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.data.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import io.hurx.components.table.defaultTable.DefaultTable;

public class TasksTable extends DefaultTable {
    public final static int H_PADDING = 10;

    public final static int V_PADDING = 10;

    public final static int ICON_SIZE = 25;

    public final static int ICONS_PER_ROW = 5;

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
     * The view
     */
    private SlayerView view;

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

    public TasksTable(SlayerView view) {
        super(DefaultTable.Options.Builder.construct().columnCount(ICONS_PER_ROW).build());
        this.panel = view.getPanel();
        this.view = view;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            model.addColumn("");
        }
        fillTableModel();
        TasksTable table = this;
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            super.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        setRowHeight(Theme.TABLE_V_PADDING * 2 + ICON_SIZE);
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseMoved(MouseEvent e)
			{
				Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                int row = table.rowAtPoint(point);
                if (col != hoveredColumnIndex || row != hoveredRowIndex) {
                    hoveredColumnIndex = col;
                    hoveredRowIndex = row;
                    table.revalidate();
                    table.repaint();
                }
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                hoveredColumnIndex = -1;
                hoveredRowIndex = -1;
                table.revalidate();
                table.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    SlayerAssignment monster = view.getSlayerAssignments()[row * ICONS_PER_ROW + column];
                    view.setSelectedMonster(monster.getMonster());
                }
                else if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    SlayerAssignment monster = view.getSlayerAssignments()[row * ICONS_PER_ROW + column];
                    java.util.List<SlayerMonsters> skipped = new ArrayList<SlayerMonsters>();
                    CacheData data = panel.getCache().getData();
                    SlayerListData list = data.getSlayer().getList();
                    if (list == null) return;

                    for (int i = 0; i < list.getSkipped().length; i ++) {
                        skipped.add(list.getSkipped()[i]);
                    }
                    java.util.List<SlayerMonsters> blocked = new ArrayList<SlayerMonsters>();
                    for (int i = 0; i < list.getBlocked().length; i ++) {
                        blocked.add(list.getBlocked()[i]);
                    }
                    // Create a popup menu
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem doItem = new JMenuItem("Do");
                    doItem.addActionListener(ae -> {
                        if (skipped.contains(monster.getMonster())) {
                            skipped.remove(monster.getMonster());
                        }
                        if (blocked.contains(monster.getMonster())) {
                            blocked.remove(monster.getMonster());
                        }
                        list.setSkipped(skipped.toArray(new SlayerMonsters[skipped.size()]));
                        list.setBlocked(blocked.toArray(new SlayerMonsters[blocked.size()]));
                        try {
                            panel.getCache().save();
                            table.revalidate();
                            table.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    JMenuItem blockItem = new JMenuItem("Block");
                    blockItem.addActionListener(ae -> {
                        if (skipped.contains(monster.getMonster())) {
                            skipped.remove(monster.getMonster());
                        }
                        if (!blocked.contains(monster.getMonster())) {
                            blocked.add(monster.getMonster());
                        }
                        list.setSkipped(skipped.toArray(new SlayerMonsters[skipped.size()]));
                        list.setBlocked(blocked.toArray(new SlayerMonsters[blocked.size()]));
                        try {
                            panel.getCache().save();
                            table.revalidate();
                            table.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    JMenuItem skipItem = new JMenuItem("Skip");
                    skipItem.addActionListener(ae -> {
                        if (blocked.contains(monster.getMonster())) {
                            blocked.remove(monster.getMonster());
                        }
                        if (!skipped.contains(monster.getMonster())) {
                            skipped.add(monster.getMonster());
                        }
                        list.setSkipped(skipped.toArray(new SlayerMonsters[skipped.size()]));
                        list.setBlocked(blocked.toArray(new SlayerMonsters[blocked.size()]));
                        try {
                            panel.getCache().save();
                            table.revalidate();
                            table.repaint();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    if (skipped.contains(monster.getMonster())) {
                        popupMenu.add(doItem);
                        popupMenu.add(blockItem);
                    }
                    else if (blocked.contains(monster.getMonster())) {
                        popupMenu.add(doItem);
                        popupMenu.add(skipItem);
                    }
                    else {
                        popupMenu.add(skipItem);
                        popupMenu.add(blockItem);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                table.revalidate();
                table.repaint();
            }
        });
        fillTableModel();
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        // Get the location of the mouse event
        Point p = e.getPoint();
        // Find out which row and column the mouse is over
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        SlayerAssignment[] assignments = view.getSlayerAssignments();
        int index = rowIndex * ICONS_PER_ROW + colIndex;
        if (index >= assignments.length) {
            return null;
        }
        else {
            return assignments[index].getMonster().toString() + " (weight: " + assignments[index].getWeight() + ")";
        }
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }
        SlayerAssignment[] assignments = view.getSlayerAssignments();
        for (int i = 0; i <= Math.ceil((assignments.length - 1) / ICONS_PER_ROW); i ++) {
            java.util.List<Object> list = new ArrayList<>();
            loop: for (int j = 0; j < ICONS_PER_ROW; j ++) {
                int index = i * ICONS_PER_ROW + j;
                if (index >= assignments.length) {
                    list.add(new JLabel(""));
                    continue loop;
                }
                list.add(
                    new Icon(
                        Resources.loadImageIcon(assignments[index].getMonster().getIconPath().getPath(), ICON_SIZE, ICON_SIZE),
                        (int)Math.round(Math.random() * 500000 + (Math.random() > 0.5 ? 10000000 : 0))
                    )
                );
            }
            model.addRow(list.toArray());
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
            TasksTable slayerTasksTable = (TasksTable)table;
            if (value instanceof JComponent) {
                if (value instanceof Icon) {
                    Icon icon = (Icon) value;
                    CacheData data = slayerTasksTable.panel.getCache().getData();
                    SlayerListData list = data.getSlayer().getList();
                    if (list == null) return null;

                    SlayerAssignment assignment = slayerTasksTable.view.getSlayerAssignments()[row * ICONS_PER_ROW + column];
                    boolean isBlocked = false;
                    boolean isSkipped = false;
                    for (int i = 0; i < list.getBlocked().length; i ++) {
                        if (list.getBlocked()[i] == assignment.getMonster()) {
                            isBlocked = true;
                            break;
                        }
                    }
                    if (!isBlocked) {
                        for (int i = 0; i < list.getSkipped().length; i ++) {
                            if (list.getSkipped()[i] == assignment.getMonster()) {
                                isSkipped = true;
                                break;
                            }
                        }
                    }
                    if (isBlocked) {
                        icon.setBackground(Theme.TABLE_BG_COLOR_DANGER);
                    }
                    else if (isSkipped) {
                        icon.setBackground(Theme.TABLE_BG_COLOR_WARN);
                    }
                    else if (row == slayerTasksTable.hoveredRowIndex && column == slayerTasksTable.hoveredColumnIndex) {
                        icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    }
                    else {
                        icon.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
                return (JComponent)value;
            }
            return label;
        }
    }    
}
