package io.hurx.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EditableComponent {
    Map<EditableComponent, List<Runnable>> onStopCellEditingRunnables = new HashMap<>();
    /**
     * Fires a runnable when the cell editing is completed
     * @param runnable the runnable
     */
    public EditableComponent onChange(Runnable runnable);
}
