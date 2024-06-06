package Tasks.Leaves;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static Settings.ScriptSettings.getSettings;
import static org.dreambot.api.script.ScriptManager.getScriptManager;

public class DoWoodcutting extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {


        Camera.mouseRotateToYaw(Calculations.random(0,183));
        Camera.setZoom(Calculations.random(181,193));
        Camera.rotateToPitch(Calculations.random(370,383));


        if(getSettings().shouldUseStartTile) {

            if(!getSettings().startArea.contains(Players.getLocal())) {
                if(Walking.shouldWalk(Calculations.random(1,9))) {
                    Walking.walk(getSettings().startArea.getNearestTile(Players.getLocal()));
                }
                return Calculations.random(50,1250);
            }


            List<GameObject> trees = GameObjects.all(t -> (t.getName().equalsIgnoreCase(getSettings().treeType.getName().toLowerCase())  && getSettings().startArea.contains(t.getTile())));
            if(trees.isEmpty() && GameObjects.all(t -> t.getName().equalsIgnoreCase("tree stump") && getSettings().startArea.contains(t.getTile())).isEmpty()) {
                Logger.log("No trees in area - stopping script");
                getScriptManager().stop();
            }

            GameObject closestTree = getClosestTreeInStartArea();
            if(closestTree == null) return Calculations.random(100,5000);
            closestTree.interact("Chop down");
            Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 15000, 25, 50);
            return 0;
        }

        Logger.log("End of function");

        return 1000;

    }


    public static GameObject getClosestTreeInStartArea() {
        Player player = Players.getLocal();
        if (player == null) {
            return null; // Player not loaded
        }

        List<GameObject> trees = GameObjects.all(t -> t.getName().equalsIgnoreCase(getSettings().treeType.getName().toLowerCase()) && getSettings().startArea.contains(t.getTile()));
        if (trees.isEmpty()) {
            return null; // No trees found
        }

        Optional<GameObject> closestTree = trees.stream()
                .min(Comparator.comparingDouble(tree -> tree.distance(player)));

        return closestTree.orElse(null);
    }

}
