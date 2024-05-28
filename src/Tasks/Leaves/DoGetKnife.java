package Tasks.Leaves;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;

import static org.dreambot.api.script.ScriptManager.getScriptManager;

public class DoGetKnife extends Leaf {
    Area getKnifeArea = new Area(3204, 3211, 3208, 3214);


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {

        if(getKnifeArea.contains(Players.getLocal())) {
            GroundItem knife = GroundItems.closest("Knife");
            if(knife.exists()) {
                knife.interact();
                Sleep.sleepUntil(() -> Inventory.contains("Knife"), 5000);
            }else {
                Logger.log("No knife found - stopping script");
                getScriptManager().stop();
            }
            return 1000;
        }else {
            if(Walking.shouldWalk()) {
                Walking.walk(getKnifeArea.getRandomTile());
            }
            return 50;
        }
    }
}
