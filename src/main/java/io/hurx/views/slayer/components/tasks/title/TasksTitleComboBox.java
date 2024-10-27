package io.hurx.views.slayer.components.tasks.title;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.awt.*;  // Import for Component class

import io.hurx.Theme;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;

public class TasksTitleComboBox extends JComboBox<TasksTitleComboBox.Model> {
    private SlayerView view;
    
    private boolean isMenuOpen = false;

    public TasksTitleComboBox(SlayerView view) {
        super();  // Call to superclass constructor
        this.view = view;
        TasksTitleComboBox comboBox = this;
        super.setOpaque(true);
        super.setBackground(Theme.TABLE_BG_COLOR);
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!isMenuOpen) {
                    comboBox.setSelectedIndex(comboBox.getSelectedIndex());
                    comboBox.revalidate();
                    comboBox.repaint();
                }
            }
        });
        super.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                isMenuOpen = true; // Set flag to true when menu opens
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                isMenuOpen = false; // Set flag to false when menu closes
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                isMenuOpen = false; // Set flag to false if menu is canceled
            }
        });

        // Set a custom renderer for the JComboBox
        super.setRenderer(new Renderer());
    }

    public static class Renderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Call the default renderer's method
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Check if the value is an instance of ComboBoxModel
            if (value instanceof Model) {
                label.setText(((Model) value).getLabel());
            } else {
                label.setText(value != null ? value.toString() : ""); // Handle null values gracefully
            }

            // Align text to the center or change as needed
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }

    public static class Model {
        private SlayerMonsters monster;
        private String label;

        public Model(SlayerMonsters monster, String label) {
            this.monster = monster;
            this.label = label;
        }

        public SlayerMonsters getMonster() {
            return monster;
        }

        public String getLabel() {
            return label;
        }
    }
}