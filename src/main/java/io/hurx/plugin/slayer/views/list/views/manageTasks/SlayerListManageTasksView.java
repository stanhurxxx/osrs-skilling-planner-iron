package io.hurx.plugin.slayer.views.list.views.manageTasks;

import io.hurx.components.Icon;
import io.hurx.components.Label;
import io.hurx.components.Padding;
import io.hurx.components.comboBox.ComboBox;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.table.Table;
import io.hurx.models.IconPaths;
import io.hurx.models.MenuButtons;
import io.hurx.models.Skills;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.SlayerBracelets;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.views.list.SlayerListMaster;
import io.hurx.plugin.slayer.views.list.SlayerListViews;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/** The manage task sub view in the slayer list view */
public class SlayerListManageTasksView extends ViewManagement.Entity.View<SlayerListMaster, SlayerListRepository, SlayerListViews> {
    public SlayerListManageTasksView(SlayerListMaster master) {
        super(master, SlayerListViews.ManageTasks);
        add(new TitleContainer(master));
        add(new TasksTableContainer(master));
    }

    /** The title with combo box */
    public static class TitleContainer extends ViewManagement.Entity.Container<SlayerListMaster, SlayerListRepository, SlayerListViews> {
        public TitleContainer(SlayerListMaster master) {
            super(master);
            update();
            onBeforeRender(() -> {
                if (getMaster().getRepository().wasDirty()) {
                    update();
                }
            });
        }

        /** Updates the container */
        private void update() {
            removeAll();
            add(new Padding(Theme.TITLE_V_PADDING));
            add(
                new Label()
                    .fixedWidth(new Label.FixedWidth(0, 55))
                    .row(new Object[] {
                        new Label.Plain("Tasks")
                            .border(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING))
                            .font(Theme.TITLE_FONT),
                        generateComboBox()
                    })
                    .height(1, Theme.TITLE_V_PADDING / 2)
                    .validatable(true)
                    .group(new Table.Group().coordinate(new Point(0, 0)).colorInactive(Theme.BG_COLOR))
                    .render()
            );
            if (getMaster().getRepository().selectedMonster.isNull()) {
                add(
                    new Label()
                    .fixedWidth(new Label.FixedWidth(0, 55))
                    .row(new Object[] {
                        new Label.Plain("Sort by")
                            .border(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING))
                            .font(Theme.LABEL_FONT),
                        generateTaskSortingComboBox()
                    })
                    .height(1, Theme.TITLE_V_PADDING / 2)
                    .validatable(true)
                    .group(new Table.Group().coordinate(new Point(0, 0)).colorInactive(Theme.BG_COLOR))
                    .render()
                );
            }
            add(new Padding(Theme.TITLE_V_PADDING));
        }

        /** Generates the monsters combo box */
        private ComboBox<ComboBoxModel> generateComboBox() {
            ComboBox<ComboBoxModel> cb = new ComboBox<>();
            ComboBoxModel all = new ComboBoxModel(null);
            ComboBoxModel selected = all;
            cb.addItem(all);
            for (SlayerAssignment assignment : getMaster().getRepository().master.get().getMaster().getAssignments()) {
                ComboBoxModel model = new ComboBoxModel(assignment.getMonster());
                cb.addItem(model);
                if (model.monster == getMaster().getRepository().selectedMonster.get()) {
                    selected = model;
                }
            }
            cb.setSelectedItem(selected);
            cb.setRenderer(new ListCellRenderer<ComboBoxModel>() {
                @Override
                public JComponent getListCellRendererComponent(
                        JList<? extends ComboBoxModel> list,
                        ComboBoxModel value,
                        int index,
                        boolean isSelected,
                        boolean cellHasFocus) {

                    if (value == null) return new JLabel("");

                    String name = value.name;
                    if (name == null) {
                        name = "";
                    }

                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setText(name);
                    if (value.imageIcon != null) {
                        label.setIcon(value.imageIcon);
                    }

                    // Set label color based on selection state
                    if (value.isSelected()) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                        label.setForeground(Color.white);
                    } else if (isSelected) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        label.setForeground(Color.white);
                    } else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                        label.setForeground(Color.white);
                    }

                    return label;
                }
            });
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ComboBoxModel selectedOption = (ComboBoxModel) cb.getSelectedItem();
                    assert selectedOption != null;
                    getMaster().getRepository().selectedMonster.replace(selectedOption.monster);
                    getRoot().render();
                }
            });
            return cb;
        }

        /** Generates a combo box for task sorting */
        private ComboBox<SlayerTaskSorting> generateTaskSortingComboBox() {
            ComboBox<SlayerTaskSorting> cb = new ComboBox<>();
            SlayerTaskSorting selected = getMaster().getRepository().taskSorting.get();
            for (SlayerTaskSorting taskSorting : SlayerTaskSorting.values()) {
                cb.addItem(taskSorting);
            }
            cb.setSelectedItem(selected);
            cb.setRenderer(new ListCellRenderer<SlayerTaskSorting>() {
                @Override
                public JComponent getListCellRendererComponent(
                        JList<? extends SlayerTaskSorting> list,
                        SlayerTaskSorting value,
                        int index,
                        boolean isSelected,
                        boolean cellHasFocus) {

                    if (value == null) return new JLabel("");

                    String name = value.description();
                    if (name == null) {
                        name = "";
                    }

                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setText(name);

                    // Set label color based on selection state
                    if (getMaster().getRepository().taskSorting.isEqualTo(value)) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                        label.setForeground(Color.white);
                    } else if (isSelected) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        label.setForeground(Color.white);
                    } else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                        label.setForeground(Color.white);
                    }

                    return label;
                }
            });
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SlayerTaskSorting selectedOption = (SlayerTaskSorting) cb.getSelectedItem();
                    assert selectedOption != null;
                    getMaster().getRepository().taskSorting.replace(selectedOption);
                    getRoot().render();
                }
            });
            return cb;
        }

        /** The combo box model */
        public class ComboBoxModel {
            private final SlayerMonsters monster;

            private final ImageIcon imageIcon;

            private final String name;

            /** Whether the menu item is selected */
            public boolean isSelected() {
                return monster == null
                    ? getMaster().getRepository().selectedMonster.isNull()
                    : getMaster().getRepository().selectedMonster.isEqualTo(monster);
            }

            public ComboBoxModel(SlayerMonsters monster) {
                this.monster = monster;
                this.imageIcon = monster == null
                    ? Resources.loadImageIcon(IconPaths.SkillSlayer.getPath(), Theme.COMBO_BOX_ICON_SIZE, Theme.COMBO_BOX_ICON_SIZE)
                    : Resources.loadImageIcon(monster.getIconPath().getPath(), Theme.COMBO_BOX_ICON_SIZE, Theme.COMBO_BOX_ICON_SIZE);
                this.name = monster == null
                    ? "All tasks"
                    : monster.getName();
            }
        }
    }

    /** The tasks table */
    public static class TasksTableContainer extends ViewManagement.Entity.Container<SlayerListMaster, SlayerListRepository, SlayerListViews> {
        @Override
        public boolean isVisible() {
            return getMaster().getRepository().selectedMonster.isNull();
        }

        public TasksTableContainer(SlayerListMaster master) {
            super(master);
            update();
            onBeforeRender(() -> {
                if (master.getRepository().wasDirty()) {
                    update();
                }
            });
        }

        /** Updates the task table */
        private void update() {
            removeAll();
            int columnsPerRow = 5;
            List<List<Object>> rows = new ArrayList<>();
            List<Object[]> rowsArrays = new ArrayList<>();
            List<Object> row = new ArrayList<>();
            rows.add(row);
            SlayerAssignment[] assignments = getMaster().getRepository().master.get().getMaster().getAssignments();
            Table table = new Table().height(Theme.WIDTH / columnsPerRow)
                .validatable(true);
            class TableCell {
                public Icon getIcon() {
                    return icon;
                }
                private Icon icon;
                private SlayerAssignment assignment;
                public TableCell(Icon icon, SlayerAssignment assignment) {
                    this.icon = icon;
                    this.assignment = assignment;
                }
            }
            List<TableCell> tableCells = new ArrayList<>();
            for (int i = 0; i < assignments.length; i ++) {
                SlayerAssignment assignment = assignments[i];
                Icon icon = new Icon(Resources.loadImageIcon(assignment.getMonster().getIconPath().getPath(), 128, 128));
                SlayerBracelets bracelet = getMaster().getRepository().bracelets.get(assignment.getMonster());
                if (bracelet != null && bracelet != SlayerBracelets.None && bracelet.getItem() != null && bracelet.getItem().getItem() != null && bracelet.getItem().getItem().getIcon() != null) {
                    icon.setSubIcon(bracelet.getItem().getItem().getIcon());
                }
                if (getMaster().getRepository().taskSorting.isEqualTo(SlayerTaskSorting.Weight)) {
                    icon.setAmount(assignment.getWeight());
                }
                else if (getMaster().getRepository().taskSorting.isEqualTo(SlayerTaskSorting.XPPerHour)) {
                    icon.setAmount(Math.round(getMaster().calculator().getXpPerHour(assignment.getMonster())));
                }
                else if (getMaster().getRepository().taskSorting.isEqualTo(SlayerTaskSorting.KillCount)) {
                    icon.setAmount(Math.round(getMaster().calculator().getKillCount(assignment.getMonster())));
                }
                else {
                    icon.setAmount(0);
                }
                icon.setToolTipText(assignment.getMonster().getName());
                tableCells.add(new TableCell(icon, assignment));
            }
            tableCells.sort(Comparator.comparingInt((TableCell cell) -> cell.getIcon().getAmount()).reversed());
            for (int i = 0; i < tableCells.size(); i ++) {
                TableCell tableCell = tableCells.get(i);
                Icon icon = tableCell.icon;
                if (row.size() == columnsPerRow) {
                    rowsArrays.add(row.toArray());
                    row = new ArrayList<>();
                    rows.add(row);
                }
                row.add(icon);
                if (i == tableCells.size() - 1) {
                    for (int j = 0; j < columnsPerRow - row.size(); j ++) {
                        JLabel jLabel = new JLabel("");
                        jLabel.setOpaque(true);
                        jLabel.setBackground(Theme.TABLE_BG_COLOR_IN_BETWEEN_HOVER);
                        row.add(jLabel);
                    }
                    rowsArrays.add(row.toArray());
                }
                table.group(
                    new Table.Group()
                        {
                            @Override
                            public void onRightClick(MouseEvent e) {
                                JPopupMenu menu = new JPopupMenu();
                                boolean isBlocked = getMaster().getRepository().blocked.contains(tableCell.assignment.getMonster());
                                boolean isSkipped = getMaster().getRepository().skipped.contains(tableCell.assignment.getMonster());
                                SlayerBracelets bracelet = getMaster().getRepository().bracelets.get(tableCell.assignment.getMonster());
                                boolean isSlaughtered = bracelet == SlayerBracelets.Slaughter;
                                boolean isExpeditious = bracelet == SlayerBracelets.Expeditious;
                                boolean isExtendable = tableCell.assignment.getMinExtended() != null;
                                boolean isExtended = getMaster().getRepository().extendedMonsters.containsKey(tableCell.assignment.getMonster());
                                JMenuItem blockItem = new JMenuItem(isBlocked ? "Do" : "Block");
                                blockItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (isBlocked) {
                                            getMaster().getRepository().blocked.remove(tableCell.assignment.getMonster());
                                        }
                                        else {
                                            getMaster().getRepository().blocked.add(tableCell.assignment.getMonster());
                                            if (isSkipped) {
                                                getMaster().getRepository().skipped.remove(tableCell.assignment.getMonster());
                                            }
                                        }
                                        getRoot().render();
                                    }
                                });
                                JMenuItem skipItem = new JMenuItem(isSkipped ? "Do" : "Skip");
                                skipItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (isSkipped) {
                                            getMaster().getRepository().skipped.remove(tableCell.assignment.getMonster());
                                        }
                                        else {
                                            getMaster().getRepository().skipped.add(tableCell.assignment.getMonster());
                                            if (isBlocked) {
                                                getMaster().getRepository().blocked.remove(tableCell.assignment.getMonster());
                                            }
                                        }
                                        getRoot().render();
                                    }
                                });
                                if (isBlocked) {
                                    menu.add(blockItem);
                                    menu.add(skipItem);
                                }
                                else {
                                    menu.add(skipItem);
                                    menu.add(blockItem);
                                }
                                if (isExtendable) {
                                    JMenuItem extendItem = new JMenuItem(isExtended ? "Un-extend" : "Extend");
                                    extendItem.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (isExtended) {
                                                getMaster().getRepository().extendedMonsters.remove(tableCell.assignment.getMonster());
                                            }
                                            else {
                                                getMaster().getRepository().extendedMonsters.set(tableCell.assignment.getMonster(), true);
                                            }
                                            getRoot().render();
                                        }
                                    });
                                    menu.add(extendItem);
                                }
                                JMenuItem slaughterItem = new JMenuItem(isSlaughtered ? "Use expeditious bracelets" : "Use slaughter bracelets");
                                slaughterItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (isSlaughtered) {
                                            getMaster().getRepository().bracelets.set(tableCell.assignment.getMonster(), SlayerBracelets.Expeditious);
                                        }
                                        else {
                                            getMaster().getRepository().bracelets.set(tableCell.assignment.getMonster(), SlayerBracelets.Slaughter);
                                        }
                                        getRoot().render();
                                    }
                                });
                                JMenuItem noSlaughterItem = new JMenuItem("Use no bracelets");
                                noSlaughterItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        getMaster().getRepository().bracelets.set(tableCell.assignment.getMonster(), SlayerBracelets.None);
                                        getRoot().render();
                                    }
                                });
                                menu.add(slaughterItem);
                                if (isSlaughtered || isExpeditious) {
                                    menu.add(noSlaughterItem);
                                }
                                menu.show(e.getComponent(), e.getX(), e.getY());
                            }

                            @Override
                            public boolean isHoverable() {
                                return true;
                            }

                            @Override
                            public boolean isValidated() {
                                if (getMaster().getRepository().skipped.contains(tableCell.assignment.getMonster())) {
                                    return true;
                                }
                                if (getMaster().getRepository().blocked.contains(tableCell.assignment.getMonster())) {
                                    return false;
                                }
                                return true;
                            }

                            @Override
                            public boolean isValidatable() {
                                return getMaster().getRepository().blocked.contains(tableCell.assignment.getMonster())
                                        || getMaster().getRepository().skipped.contains(tableCell.assignment.getMonster());
                            }

                            @Override
                            public boolean isActive() {
                                if (getMaster().getRepository().blocked.contains(tableCell.assignment.getMonster())) {
                                    return false;
                                }
                                if (getMaster().getRepository().skipped.contains(tableCell.assignment.getMonster())) {
                                    return true;
                                }
                                return true;
                            }
                        }
                        .coordinate(new Point(i % columnsPerRow, (int)Math.floor(i / columnsPerRow)))
                        .colorInvalidated(Theme.TABLE_BG_COLOR_DANGER)
                        .colorValidated(Theme.TABLE_BG_COLOR_WARN)
                        .colorInactive(i % 2 == 0 ? Theme.TABLE_BG_COLOR : Theme.TABLE_BG_COLOR_ALT)
                        .colorHover(Theme.TABLE_BG_COLOR_HOVER)
                );
            }
            for (Object[] r : rowsArrays) {
                table.row(r);
            }
            table.render();
            add(table);
        }
    }
}
