package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.ScriptSettings.getSettings;

public class FiremakingBranch extends Branch {
    @Override
    public boolean isValid() {
        return Inventory.contains(i -> i.getName().toLowerCase().contains("logs"))  && getSettings().shouldFiremake;
    }
}
