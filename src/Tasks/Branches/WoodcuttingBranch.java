package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.ScriptSettings.getSettings;

public class WoodcuttingBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Inventory.isFull() && !getSettings().shouldPersistFiremaking;
    }
}
