package io.hurx.views.slayer.components.tasks.title;

import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.TitleLabel;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TasksTitleTable extends JTable {
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
     * The combo box
     */
    private TasksTitleComboBox comboBox;

    public TasksTitleTable(SlayerView view) {
        super(new DefaultTableModel());
        this.view = view;
        this.panel = view.getPanel();
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < 2; i ++) {
            model.addColumn("");
        }
        SlayerAssignment[] assignments = view.getSlayerAssignments();
        TasksTitleTable table = this;
        table.setDoubleBuffered(true);
        comboBox = new TasksTitleComboBox(view);
        comboBox.addItem(new TasksTitleComboBox.Model(null, "All tasks"));
        comboBox.setSelectedIndex(0);
        for (int i = 0; i < assignments.length; i ++) {
            comboBox.addItem(new TasksTitleComboBox.Model(assignments[i].getMonster(), assignments[i].getMonster().toString()));
            if (panel.getCache().getData().slayerSelectedMonster == assignments[i].getMonster()) {
                comboBox.setSelectedIndex(i + 1);
            }
        }
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksTitleComboBox.Model selectedOption = (TasksTitleComboBox.Model)comboBox.getSelectedItem();
                
                if (selectedOption != null && panel.getCache().getData().slayerSelectedMonster != selectedOption.getMonster()) {
                    view.setSelectedMonster(selectedOption.getMonster());
    
                    // Force the table to repaint the selected cell
                    int row = getEditingRow();
                    int col = getEditingColumn();
                    if (row >= 0 && col >= 0) {
                        repaint(getCellRect(row, col, true));
                    }
                }
            }
        });
        model.addRow(new Object[] {});
        model.addRow(new Object[] {
            new TitleLabel("Tasks"),
            comboBox
        });
        model.addRow(new Object[] {});
        super.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));        
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setIgnoreRepaint(true);
        super.setVisible(true);
        for (int i = 0; i < 2; i ++) {
            super.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        super.setRowHeight(Theme.TITLE_V_PADDING);
        super.setRowHeight(1, 22);
        super.setOpaque(true);
        super.setBackground(new Color(0, 0, 0, 0));
        setCellSelectionEnabled(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
        super.setBackground(Theme.BG_COLOR);
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return false; // Prevent cell selection
    }

     /**
     * When the selected monster has changed
     */
    public void onSetSelectedMonster() {
        SlayerMonsters monster = panel.getCache().getData().slayerSelectedMonster;
        System.out.println(monster);
        TasksTitleComboBox.Model model = null;
        boolean set = false;
        for (int i = 0; i < this.comboBox.getItemCount(); i ++) {
            model = this.comboBox.getItemAt(i);
            if (model.getMonster() == monster) {
                this.comboBox.setSelectedIndex(i);
                set = true;
                break;
            }
        }
        if (!set) {
            this.comboBox.setSelectedIndex(0);
        }
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == 1) {
                if (column == 0) {
                    if (value instanceof JLabel) {
                        JLabel label = (JLabel) value;
                        label.setVerticalAlignment(SwingConstants.CENTER);
                        label.setHorizontalAlignment(SwingConstants.LEFT);
                        label.setOpaque(true);
                        table.getColumnModel().getColumn(0).setPreferredWidth(57 + Theme.TABLE_H_PADDING * 2);
                        table.getColumnModel().getColumn(0).setMaxWidth(57 + Theme.TABLE_H_PADDING * 2);
                        return label; // Return JLabel directly
                    }
                }
                else {
                    if (value instanceof TasksTitleComboBox) {
                        TasksTitleComboBox comboBox = (TasksTitleComboBox) value;
                        comboBox.setOpaque(true);
                        comboBox.setBackground(Theme.TABLE_BG_COLOR);
                        for (int i = 0; i < comboBox.getItemCount(); i ++) {
                            TasksTitleComboBox.Model item = comboBox.getItemAt(i);
                            if (item.getMonster() == ((TasksTitleTable)table).panel.getCache().getData().slayerSelectedMonster) {
                                if (comboBox.getSelectedIndex() != i) {
                                    comboBox.setSelectedIndex(i);
                                }
                            }
                        }
                        return comboBox;
                    }
                    else if (value instanceof TasksTitleComboBox.Model) {
                        TasksTitleComboBox comboBox = ((TasksTitleTable) table).comboBox;
                        comboBox.setOpaque(true);
                        comboBox.setBackground(Theme.TABLE_BG_COLOR);
                        for (int i = 0; i < comboBox.getItemCount(); i ++) {
                            TasksTitleComboBox.Model item = comboBox.getItemAt(i);
                            if (item.getMonster() == ((TasksTitleTable)table).panel.getCache().getData().slayerSelectedMonster) {
                                if (comboBox.getSelectedIndex() != i) {
                                    comboBox.setSelectedIndex(i);
                                }
                            }
                        }
                        return comboBox; // Return JLabel instead of JComboBox
                    }
                }
            }
            else {
                JLabel label = new JLabel("");
                label.setBackground(Theme.BG_COLOR);
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
