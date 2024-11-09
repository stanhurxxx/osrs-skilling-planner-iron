package io.hurx.components.comboBox;

import io.hurx.components.EditableComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ComboBox<T> extends JComboBox<T> implements EditableComponent {
    public ComboBox() {
        super();
    }

    @Override
    public ComboBox<T> onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
    }
}
