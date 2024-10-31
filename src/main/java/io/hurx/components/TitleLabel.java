package io.hurx.components;

import io.hurx.components.comboBox.JComboBoxCellEditor;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.menuButton.MenuButtonCellEditor;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.components.textField.TextFieldCellEditor;
import io.hurx.utils.Theme;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class TitleLabel extends DefaultTable {
    /**
     * Get the JLabel
     * @return
     */
    public JLabel getLabel() {
        return label;
    }

    private JLabel label;

    private JTextField textField;

    public boolean isTopPaddingEnabled() {
        return topPadding;
    }
    public TitleLabel enableTopPadding() {
        this.topPadding = true;
        return this;
    }
    public TitleLabel disableTopPadding() {
        this.topPadding = false;
        return this;
    }
    private boolean topPadding = false;

    public boolean isBottomPaddingEnabled() {
        return this.bottomPadding;
    }
    public TitleLabel enableBottomPadding() {
        this.bottomPadding = true;
        return this;
    }
    public TitleLabel disableBottomPadding() {
        this.bottomPadding = false;
        return this;
    }
    private boolean bottomPadding = true;

    /**
     * The other columns
     */
    public void setOtherColumns(Component[] otherColumns) {
        this.otherColumns = otherColumns;
        options.setColumnCount(otherColumns.length + 1);
        fillTableModel();
        initialize();
    }
    private Component[] otherColumns;

    public TitleLabel(String text) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.label = new JLabel(text);
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }
    
    public TitleLabel(String text, boolean noBottomPadding) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.label = new JLabel(text);
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    public TitleLabel(String text, Component[] otherColumns) {
        super(DefaultTable.Options.Builder.construct().columnCount(1 + otherColumns.length).build());
        this.label = new JLabel(text);
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    public TitleLabel(String text, Component[] otherColumns, boolean noBottomPadding) {
        super(DefaultTable.Options.Builder.construct().columnCount(1 + otherColumns.length).build());
        this.label = new JLabel(text);
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    public TitleLabel(JTextField text) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.textField = text;
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    public TitleLabel(JTextField text, boolean noBottomPadding) {
        super(DefaultTable.Options.Builder.construct().columnCount(1).build());
        this.textField = text;
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    public TitleLabel(JTextField text, Component[] otherColumns) {
        super(DefaultTable.Options.Builder.construct().columnCount(1 + otherColumns.length).build());
        this.textField = text;
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    public TitleLabel(JTextField text, Component[] otherColumns, boolean noBottomPadding) {
        super(DefaultTable.Options.Builder.construct().columnCount(1 + otherColumns.length).build());
        this.textField = text;
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    public void initialize() {
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TITLE_V_PADDING, Theme.TITLE_H_PADDING, Theme.TITLE_V_PADDING, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
        if (this.label != null) {
            this.label.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
            this.label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        }
        else if (this.textField != null) {
            this.textField.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
            this.textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        }
        if (topPadding) {
            setRowHeight(0, Theme.TITLE_V_PADDING - 3);
        }
        setRowHeight(1, Theme.TITLE_SIZE + 6);
        if (bottomPadding) {
            setRowHeight(2, Theme.TITLE_V_PADDING - 3);
        }
        for (int i = 0; i < getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setWidth(0);
            getColumnModel().getColumn(i).setMinWidth(0);
            getColumnModel().getColumn(i).setMaxWidth(0);
            getColumnModel().getColumn(i).setPreferredWidth(0);
        }
        if (otherColumns.length == 1 && otherColumns[0] instanceof JComboBox) {
            for (int i = 0; i < 2; i ++) {
                getColumnModel().getColumn(i).setWidth((int)Math.round(223 / 2));
                getColumnModel().getColumn(i).setMinWidth((int)Math.round(223 / 2));
                getColumnModel().getColumn(i).setMaxWidth((int)Math.round(223 / 2));
                getColumnModel().getColumn(i).setPreferredWidth((int)Math.round(223 / 2));
            }
            getColumnModel().getColumn(1).setCellEditor(new JComboBoxCellEditor() {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<?> box = (JComboBox<?>)otherColumns[0];
                    if (box == null) {
                        setIndex(-1);
                        setBox(null);
                        return null;
                    };
                    super.setBox(box);
                    super.setIndex(column);
                    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
                }

                @Override
                public boolean stopCellEditing() {
                    int index = getIndex();
                    if (index == -1) return false;
                    return super.stopCellEditing();
                }
            });
        }
        else {
            int hPadding = 10;
            getColumnModel().getColumn(0).setWidth((int)Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setMinWidth((int)Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setMaxWidth((int)Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setPreferredWidth((int)Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            for (int i = 0; i < otherColumns.length; i ++) {
                getColumnModel().getColumn(i + 1).setWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setMinWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setMaxWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setPreferredWidth(Theme.TITLE_SIZE + hPadding);
                if (otherColumns[i] instanceof MenuButton) {
                    ((MenuButton)otherColumns[i]).setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    ((MenuButton)otherColumns[i]).setVPadding(4);
                    ((MenuButton)otherColumns[i]).setHPadding((int)Math.round(hPadding / 2));
                    MenuButtonCellEditor cellEditor = new MenuButtonCellEditor();
                    cellEditor.setButton((MenuButton)otherColumns[i]);
                    getColumnModel().getColumn(i + 1).setCellEditor(cellEditor);
                }
            }
        }
        if (textField != null) {
            TextFieldCellEditor editor = new TextFieldCellEditor(textField) {
                @Override
                public boolean isCellEditable(EventObject e) {
                    if (e instanceof MouseEvent) {
                        MouseEvent mouseEvent = (MouseEvent) e;
                        JTable table = (JTable) mouseEvent.getSource();
                        int row = table.rowAtPoint(mouseEvent.getPoint());
                        return row == 1;  // Only allow editing on row 1
                    }
                    return false;
                }

                @Override
                public boolean stopCellEditing() {
                    if (textField.getText().trim().equals("")) {
                        textField.setText("Untitled");
                    }
                    else {
                        textField.setText(textField.getText().trim());
                    }
                    return super.stopCellEditing();
                }
            };
            getColumnModel().getColumn(0).setCellEditor(editor);
        }
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel) getModel();
        for (int i = 0; i < options.getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        List<Object> cells = new ArrayList<>();
        if (label != null) {
            cells.add(label);
        }
        else {
            cells.add(textField);
        }
        for (int i = 0; i < otherColumns.length; i ++) {
            cells.add(otherColumns[i]);
        }
        if (topPadding) {
            model.addRow(new Object[] {});
        }
        model.addRow(cells.toArray());
        if (bottomPadding) {
            model.addRow(new Object[] {});
        }
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            TitleLabel title = (TitleLabel) table;
            if (value instanceof MenuButton) {
                MenuButton button = (MenuButton) value;
                button.setOpaque(true);
                return button;
            }
            else if (value instanceof JComboBox) {
                JComboBox<?> box = (JComboBox<?>) value;
                box.setBorder(BorderFactory.createEmptyBorder());
                return box;
            }
            else if (column == 1 && row == 1 && title.otherColumns.length == 1 && title.otherColumns[0] instanceof JComboBox) {
                JComboBox<?> box = (JComboBox<?>)title.otherColumns[0];
                if (box == null) return null;
                if (value == null) return box;
                box.setBorder(BorderFactory.createEmptyBorder());
                box.setSelectedItem(value);
                return box;
            }
            else if (value instanceof JTextField) {
                JTextField text = (JTextField) value;
                return text;
            }
            else if (value instanceof Component) {
                return (Component)value;
            }
            else if (value instanceof String && title.textField != null) {
                title.textField.setText((String)value);
                return title.textField;
            }
            return null;
        }
    }
}
