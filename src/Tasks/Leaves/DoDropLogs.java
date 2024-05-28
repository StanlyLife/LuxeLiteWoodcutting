package Tasks.Leaves;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static Settings.ScriptSettings.getSettings;

public class DoDropLogs extends Leaf {
    @Override
    public boolean isValid() {
        return getSettings().shouldDrop && Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Inventory.dropAll("Logs");
        return 0;
    }
}
