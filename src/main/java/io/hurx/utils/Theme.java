package io.hurx.utils;

import net.runelite.client.ui.FontManager;

import java.awt.*;

/**
 * A utility class that defines the color themes and UI constants for the application.
 * This class holds color definitions for backgrounds, foregrounds, and other UI elements,
 * as well as padding and size constants for layout management.
 */
public class Theme {
    /** The available width */
    public static int WIDTH = 229;

    /** The default combo box icon size */
    public static int COMBO_BOX_ICON_SIZE = 18;

    /** Background color for the main application interface. */
    public static Color BG_COLOR = new Color(40, 40, 40);
    
    public static Color TABLE_BG_COLOR_DARK = new Color(15, 15, 15, 255);

    /** Background color for table elements in the application. */
    public static Color TABLE_BG_COLOR = new Color(30, 30, 30, 255);

    /** Background color in between table bg and hover bg */
    public static Color TABLE_BG_COLOR_IN_BETWEEN_HOVER = new Color(50, 50, 50, 255);

    /** Alternative background color for alternating rows in tables. */
    public static Color TABLE_BG_COLOR_ALT = new Color(35, 35, 35, 255);
    
    /** Background color for hovered table rows. */
    public static Color TABLE_BG_COLOR_HOVER = new Color(60, 60, 60, 255);

    public static Color TABLE_FG_COLOR_HOVER = Color.WHITE;

    /** Background color for selected table rows. */
    public static Color TABLE_BG_COLOR_SELECTED = new Color(10, 57, 145, 255);

    /** Background color for hovering buttons */
    public static Color TABLE_BG_COLOR_SELECTED_HOVER = new Color(13, 66, 166, 255);

    /** Background color indicating a successful action in the table. */
    public static Color TABLE_BG_COLOR_SUCCESS = new Color(12, 94, 36, 255);
    
    /** Foreground color indicating success messages or indicators in the table. */
    public static Color TABLE_FG_COLOR_SUCCESS = new Color(33, 209, 85, 255);
    
    /** Background color indicating a warning state in the table. */
    public static Color TABLE_BG_COLOR_WARN = new Color(145, 93, 10, 255);
    
    /** Foreground color indicating warning messages or indicators in the table. */
    public static Color TABLE_FG_COLOR_WARN = new Color(232, 218, 60, 255);
    
    /** Background color indicating a danger state in the table. */
    public static Color TABLE_BG_COLOR_DANGER = new Color(145, 10, 10, 255);

    /** Default row height for tables */
    public final static int TABLE_ROW_HEIGHT = 30;

    /** Horizontal padding for table elements. */
    public final static int TABLE_H_PADDING = 5;
    
    /** Vertical padding for table elements. */
    public final static int TABLE_V_PADDING = 10;
    
    /** Horizontal padding for title elements. */
    public final static int TITLE_H_PADDING = 5;
    
    /** Vertical padding for title elements. */
    public final static int TITLE_V_PADDING = 8;
    
    /** Font size for title elements. */
    public final static int TITLE_SIZE = 18;

    /** The title font */
    public final static Font TITLE_FONT = FontManager.getRunescapeBoldFont().deriveFont(Font.BOLD, TITLE_SIZE);

    /** Font size for label elements. */
    public final static int LABEL_SIZE = 15;

    public final static Font LABEL_FONT = FontManager.getRunescapeFont().deriveFont(Font.PLAIN, LABEL_SIZE);
    
    /** Size for icons used in the application. */
    public final static int ICON_SIZE = 22;

    /** Horizontal padding for controls */
    public final static int CONTROLS_H_PADDING = 5;
}
