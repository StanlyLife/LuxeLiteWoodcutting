package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.ScriptSettings.getSettings;

public class HandleLogsBranch extends Branch {
    @Override
    public boolean isValid() {
        return !getSettings().shouldFletch && !getSettings().shouldFiremake && Inventory.isFull();
    }
}
