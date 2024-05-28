package Tasks.Leaves;

import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.event.impl.keyboard.awt.Key;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;

public class DoFletching extends Leaf {
    boolean shouldPersist = false;
    @Override
    public boolean isValid() {
        return (Inventory.contains("Knife") && Inventory.contains(item -> item.getName().toLowerCase().contains("logs"))) || shouldPersist;
    }

    @Override
    public int onLoop() {

        shouldPersist = true;
        if(!Inventory.contains(item -> item.getName().toLowerCase().contains("logs"))) {
            shouldPersist = false;
            return 0;
        }
        Inventory.deselect();
        Inventory.interact("knife");
        Sleep.sleepUntil(() -> Inventory.isItemSelected(), 2500);
        Inventory.interact(item -> item.getName().toLowerCase().contains("logs"));
        Sleep.sleep(350);
        Keyboard.typeKey(Key.SPACE);
        Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() , () -> Players.getLocal().isAnimating() , 15000, 25, 50);
        return 0;
    }
}
