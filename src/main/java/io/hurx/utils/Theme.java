package io.hurx.utils;

import java.awt.Color;

/**
 * A utility class that defines the color themes and UI constants for the application.
 * This class holds color definitions for backgrounds, foregrounds, and other UI elements,
 * as well as padding and size constants for layout management.
 */
public class Theme {
    
    /** Background color for the main application interface. */
    public static Color BG_COLOR = new Color(40, 40, 40);
    
    /** Background color for table elements in the application. */
    public static Color TABLE_BG_COLOR = new Color(30, 30, 30, 255);
    
    /** Alternative background color for alternating rows in tables. */
    public static Color TABLE_BG_COLOR_ALT = new Color(35, 35, 35, 255);
    
    /** Background color for hovered table rows. */
    public static Color TABLE_BG_COLOR_HOVER = new Color(60, 60, 60, 255);
    
    /** Background color for selected table rows. */
    public static Color TABLE_BG_COLOR_SELECTED = new Color(10, 57, 145, 255);
    
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
    
    /** Horizontal padding for table elements. */
    public final static int TABLE_H_PADDING = 5;
    
    /** Vertical padding for table elements. */
    public final static int TABLE_V_PADDING = 10;
    
    /** Horizontal padding for title elements. */
    public final static int TITLE_H_PADDING = 5;
    
    /** Vertical padding for title elements. */
    public final static int TITLE_V_PADDING = 13;
    
    /** Font size for title elements. */
    public final static int TITLE_SIZE = 18;
    
    /** Font size for label elements. */
    public final static int LABEL_SIZE = 15;
    
    /** Size for icons used in the application. */
    public final static int ICON_SIZE = 22;
}
