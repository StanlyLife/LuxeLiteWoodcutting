package Tasks.Leaves;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static Settings.ScriptSettings.getSettings;
import static org.dreambot.api.script.ScriptManager.getScriptManager;

public class DoFiremaking extends Leaf {


    @Override
    public boolean isValid() {
        return Inventory.contains(i -> i.getName().toLowerCase().contains("logs")) || getSettings().shouldPersistFiremaking;
    }

    @Override
    public int onLoop() {
        boolean inventoryHasLogs = Inventory.contains(i -> i.getName().toLowerCase().contains("logs"));
        Logger.log("Inventory has logs: " + inventoryHasLogs);
        if(inventoryHasLogs) {
            getSettings().shouldPersistFiremaking = true;
        }else {
            Logger.log("shouldpersist is over");
            getSettings().shouldPersistFiremaking = false;
            return 0;
        }

        if(!Inventory.contains("Tinderbox")) {
            Logger.log("Player does not have tinderbox in inventory");
            getScriptManager().stop();
        }

        boolean isCurrentTileFree = Arrays.stream(GameObjects.getObjectsOnTile(Players.getLocal().getTile())).filter(x -> x.getID() != 0 && x.getName() != null && !Objects.equals(x.getName(), "null")).count() == 0;
        if(isCurrentTileFree) {
            if(Inventory.count(i -> i.getName().toLowerCase().contains("logs")) <= 1) getSettings().shouldPersistFiremaking = false;
            Logger.log("Tile is free!");
            if(Inventory.isItemSelected()) Inventory.deselect();
            Inventory.interact("Tinderbox");
            Sleep.sleepUntil(() -> Inventory.isItemSelected(), 2500);

            int inventoryLogs = Inventory.count(i -> i.getName().toLowerCase().contains("logs"));
            Inventory.interact(i -> i.getName().toLowerCase().contains("logs"));
            Sleep.sleepUntil(() -> Inventory.count(i -> i.getName().toLowerCase().contains("logs")) == inventoryLogs-1 && !Players.getLocal().isMoving() && !Players.getLocal().isAnimating(), 5000);


            return 1000;
        }else {
            Logger.log("Tile is not free");
            GameObject[] g = GameObjects.getObjectsOnTile(Players.getLocal().getTile());
            logGameObjectNames(g);

            if(
                    Walking.canWalk(new Tile(Players.getLocal().getX(), Players.getLocal().getY()-1)) &&
                    Arrays.stream(GameObjects.getObjectsOnTile(new Tile(Players.getLocal().getX(), Players.getLocal().getY()-1))).filter(x -> x.getID() != 0 && x.getName() != null && !Objects.equals(x.getName(), "null")).count() == 0
            ) {
                Logger.log("Walking south");
                Walking.walk(new Tile(Players.getLocal().getX(), Players.getLocal().getY()-1));
            }else if(
                    Walking.canWalk(new Tile(Players.getLocal().getX(), Players.getLocal().getY()+1)) &&
                    Arrays.stream(GameObjects.getObjectsOnTile(new Tile(Players.getLocal().getX(), Players.getLocal().getY()+1))).filter(x -> x.getID() != 0 && x.getName() != null && !Objects.equals(x.getName(), "null")).count() == 0
            ) {
                Logger.log("Walking north");
                Walking.walk(new Tile(Players.getLocal().getX(), Players.getLocal().getY()+1));
            }else {
                Logger.log("Walking random");
                Walking.walk(getSettings().startArea.getRandomTile());
            }
            Sleep.sleep(250);
            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);

        }

        return 1000;
    }
    public static void logGameObjectNames( GameObject[] gameObjects) {
        Logger.log("logging gameobjects");
        if (gameObjects != null && gameObjects.length > 0) {
            for (GameObject gameObject : gameObjects) {
                Logger.log("GameObject Name: " + gameObject.getName() + " - " + gameObject.getID());
            }
        } else {
            Logger.log("No game objects found on the current tile.");
        }
    }




}
