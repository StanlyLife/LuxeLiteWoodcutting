package Settings;


import ScriptEnums.WoodcuttingTrees;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

import static ScriptEnums.WoodcuttingTrees.getTreeEnum;

public class ScriptSettings {

    public static final ScriptSettings scriptSettings = new ScriptSettings();
    public static ScriptSettings getSettings() {
        return scriptSettings;
    }

    public int radius = 5;

    public WoodcuttingTrees treeType = WoodcuttingTrees.TREE;

    public void setWoodcuttingTrees(String type) {
        this.treeType = getTreeEnum(type);
    }
    public boolean shouldLoop = false;
    public Tile startTile = new Tile(3189,3254);

    public boolean shouldFletch = true;
    public boolean shouldDrop = false;
    public boolean shouldFiremake = false;
    public boolean shouldUseStartTile = true;
    public boolean isDeveloperMode = true;
    public boolean shouldPersistFiremaking = false;

    public Area startArea = new Area();

}
