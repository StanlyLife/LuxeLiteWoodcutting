package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class GetAxeBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Inventory.contains(i -> i.getName().contains("axe"));
    }
}
