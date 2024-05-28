package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.ScriptSettings.getSettings;

public class GetKnifeBranch extends Branch {

    @Override
    public boolean isValid() {
        return getSettings().shouldFletch && !Inventory.contains("Knife");
    }
}
