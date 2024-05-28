package Settings;


import org.dreambot.api.methods.map.Area;

public class ScriptSettings {

    public static final ScriptSettings scriptSettings = new ScriptSettings();
    public static ScriptSettings getSettings() {
        return scriptSettings;
    }

    public int radius = 5;

    public boolean shouldLoop = true;
    public boolean shouldFletch = true;
    public boolean shouldDrop = false;
    public boolean shouldFiremake = false;
    public boolean shouldUseStartTile = true;
    public boolean isDeveloperMode = false;

    public Area startArea = new Area();

}
