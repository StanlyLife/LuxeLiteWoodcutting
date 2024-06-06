package ScriptEnums;

import org.dreambot.api.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.dreambot.api.methods.ignore.IgnoredProvider.add;

public enum WoodcuttingTrees {
    TREE("Tree","logs"),
    OAK_TREE("Oak tree","Oak logs"),
    WILLOW_TREE("Willow tree","Willow logs"),
    MAPLE_TREE("Maple tree","Willow logs"),
    YEW_TREE("Yew tree","Yew logs"),
    MAGIC_TREE("Magic tree","Magic logs");

    private String treeName;
    private String logsName;

    WoodcuttingTrees(String treeName, String logsName) {
        this.treeName = treeName;
        this.logsName = logsName;
    }

    public String getName() {
        return this.treeName;
    }
    public String getLogs() {
        return this.logsName;
    }

    public static List<String> options = new ArrayList<String>() {{
        add("Tree");
        add("Oak tree");
        add("Willow tree");
        add("Maple tree");
        add("Yew tree");
        add("Magic tree");
    }};

    public static WoodcuttingTrees getTreeEnum(String name) {
        for (WoodcuttingTrees pickaxe : WoodcuttingTrees.values()) {
            if (pickaxe.getName().equalsIgnoreCase(name)) {
                return pickaxe;
            }
        }
        Logger.log("Invalid fishingtype: " + name);
        throw new IllegalArgumentException("Invalid fishingtype name: " + name);
    }
}
