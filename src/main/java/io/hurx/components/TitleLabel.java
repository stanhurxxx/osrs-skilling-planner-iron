package io.hurx.components;

import io.hurx.components.comboBox.JComboBoxCellEditor;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.menuButton.MenuButtonCellEditor;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.components.textField.TextFieldCellEditor;
import io.hurx.utils.Theme;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.awt.event.MouseEvent;

/**
 * The TitleLabel class is a custom table component that displays a title and
 * allows for the inclusion of additional components (like text fields and 
 * combo boxes) alongside the title. It extends the DefaultTable class, 
 * enabling the integration of various GUI elements in a structured format.
 */
public class TitleLabel extends DefaultTable {
    /**
     * The JLabel displaying the title text.
     */
    private JLabel label;

    /**
     * The JTextField that can be displayed in place of the title label.
     */
    private JTextField textField;

    /**
     * Indicates whether the top padding is enabled.
     */
    private boolean topPadding = false;

    /**
     * Indicates whether the bottom padding is enabled.
     */
    private boolean bottomPadding = true;

    /**
     * The other components that can be included in the title label.
     */
    private Component[] otherColumns;

    /**
     * Constructs a TitleLabel with the specified title text.
     *
     * @param text The text to display as the title.
     */
    public TitleLabel(String text) {
        super(DefaultTable.Options.builder().columnCount(1).build());
        this.label = new JLabel(text);
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified title text 
     * and an option to disable bottom padding.
     *
     * @param text The text to display as the title.
     * @param noBottomPadding If true, disables bottom padding.
     */
    public TitleLabel(String text, boolean noBottomPadding) {
        super(DefaultTable.Options.builder().columnCount(1).build());
        this.label = new JLabel(text);
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified title text 
     * and additional components.
     *
     * @param text The text to display as the title.
     * @param otherColumns An array of components to be displayed alongside the title.
     */
    public TitleLabel(String text, Component[] otherColumns) {
        super(DefaultTable.Options.builder().columnCount(1 + otherColumns.length).build());
        this.label = new JLabel(text);
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified title text, 
     * additional components, and an option to disable bottom padding.
     *
     * @param text The text to display as the title.
     * @param otherColumns An array of components to be displayed alongside the title.
     * @param noBottomPadding If true, disables bottom padding.
     */
    public TitleLabel(String text, Component[] otherColumns, boolean noBottomPadding) {
        super(DefaultTable.Options.builder().columnCount(1 + otherColumns.length).build());
        this.label = new JLabel(text);
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified JTextField.
     *
     * @param text The JTextField to display.
     */
    public TitleLabel(JTextField text) {
        super(DefaultTable.Options.builder().columnCount(1).build());
        this.textField = text;
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified JTextField 
     * and an option to disable bottom padding.
     *
     * @param text The JTextField to display.
     * @param noBottomPadding If true, disables bottom padding.
     */
    public TitleLabel(JTextField text, boolean noBottomPadding) {
        super(DefaultTable.Options.builder().columnCount(1).build());
        this.textField = text;
        this.otherColumns = new Component[] {};
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified JTextField 
     * and additional components.
     *
     * @param text The JTextField to display.
     * @param otherColumns An array of components to be displayed alongside the text field.
     */
    public TitleLabel(JTextField text, Component[] otherColumns) {
        super(DefaultTable.Options.builder().columnCount(1 + otherColumns.length).build());
        this.textField = text;
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    /**
     * Constructs a TitleLabel with the specified JTextField, 
     * additional components, and an option to disable bottom padding.
     *
     * @param text The JTextField to display.
     * @param otherColumns An array of components to be displayed alongside the text field.
     * @param noBottomPadding If true, disables bottom padding.
     */
    public TitleLabel(JTextField text, Component[] otherColumns, boolean noBottomPadding) {
        super(DefaultTable.Options.builder().columnCount(1 + otherColumns.length).build());
        this.textField = text;
        this.otherColumns = otherColumns;
        fillTableModel();
        initialize();
    }

    /**
     * Initializes the TitleLabel, setting borders, font styles, and row heights
     * for the components within the table.
     */
    public void initialize() {
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TITLE_V_PADDING, Theme.TITLE_H_PADDING, Theme.TITLE_V_PADDING, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
        
        if (this.label != null) {
            this.label.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
            this.label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        } else if (this.textField != null) {
            this.textField.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
            this.textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        }
        
        // Set row heights based on padding options
        if (topPadding) {
            setRowHeight(0, Theme.TITLE_V_PADDING - 3);
        }
        setRowHeight(1, Theme.TITLE_SIZE + 6);
        if (bottomPadding) {
            setRowHeight(2, Theme.TITLE_V_PADDING - 3);
        }

        // Configure column widths
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setWidth(0);
            getColumnModel().getColumn(i).setMinWidth(0);
            getColumnModel().getColumn(i).setMaxWidth(0);
            getColumnModel().getColumn(i).setPreferredWidth(0);
        }

        // Additional configurations based on the type of other components
        if (otherColumns.length == 1 && otherColumns[0] instanceof JComboBox) {
            for (int i = 0; i < 2; i++) {
                getColumnModel().getColumn(i).setWidth((int) Math.round(223 / 2));
                getColumnModel().getColumn(i).setMinWidth((int) Math.round(223 / 2));
                getColumnModel().getColumn(i).setMaxWidth((int) Math.round(223 / 2));
                getColumnModel().getColumn(i).setPreferredWidth((int) Math.round(223 / 2));
            }
            getColumnModel().getColumn(1).setCellEditor(new JComboBoxCellEditor() {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<?> box = (JComboBox<?>) otherColumns[0];
                    if (box == null) {
                        setIndex(-1);
                        setBox(null);
                        return null;
                    }
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
        } else {
            int hPadding = 10;
            getColumnModel().getColumn(0).setWidth((int) Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setMinWidth((int) Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setMaxWidth((int) Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            getColumnModel().getColumn(0).setPreferredWidth((int) Math.round(223 - (otherColumns.length * (Theme.TITLE_SIZE))));
            
            for (int i = 0; i < otherColumns.length; i++) {
                getColumnModel().getColumn(i + 1).setWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setMinWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setMaxWidth(Theme.TITLE_SIZE + hPadding);
                getColumnModel().getColumn(i + 1).setPreferredWidth(Theme.TITLE_SIZE + hPadding);
                
                if (otherColumns[i] instanceof MenuButton) {
                    ((MenuButton) otherColumns[i]).setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    ((MenuButton) otherColumns[i]).setVPadding(4);
                    ((MenuButton) otherColumns[i]).setHPadding((int) Math.round(hPadding / 2));
                    MenuButtonCellEditor cellEditor = new MenuButtonCellEditor();
                    cellEditor.setButton((MenuButton) otherColumns[i]);
                    getColumnModel().getColumn(i + 1).setCellEditor(cellEditor);
                }
            }
        }

        // Configure the text field editor
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
                    // Ensure the text field has a valid title
                    if (textField.getText().trim().equals("")) {
                        textField.setText("Untitled");
                    } else {
                        textField.setText(textField.getText().trim());
                    }
                    return super.stopCellEditing();
                }
            };
            getColumnModel().getColumn(0).setCellEditor(editor);
        }
    }

    /**
     * Fills the table model with the components defined in this TitleLabel.
     */
    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel) getModel();
        
        // Set cell renderer for each column
        for (int i = 0; i < options.getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        
        List<Object> cells = new ArrayList<>();
        if (label != null) {
            cells.add(label);
        } else {
            cells.add(textField);
        }
        
        for (int i = 0; i < otherColumns.length; i++) {
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

    /**
     * A custom cell renderer for the TitleLabel that handles 
     * the rendering of various component types in the table cells.
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            TitleLabel title = (TitleLabel) table;
            if (value instanceof MenuButton) {
                MenuButton button = (MenuButton) value;
                button.setOpaque(true);
                return button;
            } else if (value instanceof JComboBox) {
                JComboBox<?> box = (JComboBox<?>) value;
                box.setBorder(BorderFactory.createEmptyBorder());
                return box;
            } else if (column == 1 && row == 1 && title.otherColumns.length == 1 && title.otherColumns[0] instanceof JComboBox) {
                JComboBox<?> box = (JComboBox<?>) title.otherColumns[0];
                if (box == null) return null;
                if (value == null) return box;
                box.setBorder(BorderFactory.createEmptyBorder());
                box.setSelectedItem(value);
                return box;
            } else if (value instanceof JTextField) {
                JTextField text = (JTextField) value;
                return text;
            } else if (value instanceof Component) {
                return (Component) value;
            } else if (value instanceof String && title.textField != null) {
                title.textField.setText((String) value);
                return title.textField;
            }
            return null;
        }
    }

    /**
     * Gets the JLabel used for the title.
     *
     * @return The JLabel displaying the title.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Sets other columns (components) that can be displayed alongside the title.
     *
     * @param otherColumns An array of components to be displayed in the TitleLabel.
     */
    public void setOtherColumns(Component[] otherColumns) {
        this.otherColumns = otherColumns;
        options.setColumnCount(otherColumns.length + 1);
        fillTableModel();
        initialize();
    }

    /**
     * Checks if top padding is enabled.
     *
     * @return True if top padding is enabled, false otherwise.
     */
    public boolean isTopPaddingEnabled() {
        return topPadding;
    }

    /**
     * Enables top padding for the TitleLabel.
     *
     * @return The current TitleLabel instance for method chaining.
     */
    public TitleLabel enableTopPadding() {
        this.topPadding = true;
        return this;
    }

    /**
     * Disables top padding for the TitleLabel.
     *
     * @return The current TitleLabel instance for method chaining.
     */
    public TitleLabel disableTopPadding() {
        this.topPadding = false;
        return this;
    }

    /**
     * Checks if bottom padding is enabled.
     *
     * @return True if bottom padding is enabled, false otherwise.
     */
    public boolean isBottomPaddingEnabled() {
        return this.bottomPadding;
    }

    /**
     * Enables bottom padding for the TitleLabel.
     *
     * @return The current TitleLabel instance for method chaining.
     */
    public TitleLabel enableBottomPadding() {
        this.bottomPadding = true;
        return this;
    }

    /**
     * Disables bottom padding for the TitleLabel.
     *
     * @return The current TitleLabel instance for method chaining.
     */
    public TitleLabel disableBottomPadding() {
        this.bottomPadding = false;
        return this;
    }
}
