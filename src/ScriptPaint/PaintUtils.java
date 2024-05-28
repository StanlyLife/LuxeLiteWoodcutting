package ScriptPaint;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;

import java.awt.*;

public class PaintUtils {

    public static void PaintMultiArea(Graphics g , Area[] areas, Color borderColor, Color fillColor){
        for (Area area : areas) {
            PaintArea(g, area, borderColor, fillColor);
        }
    }
    public static void PaintArea(Graphics g ,Area area, Color borderColor, Color fillColor){
        Tile[] areaTiles = area.getTiles();
        if(areaTiles == null || areaTiles.length == 0) return;
        for(Tile t : areaTiles) {
            if(t != null && Map.isTileOnScreen(t))PaintTile(g, t, borderColor, fillColor);
        }
    }

    public static void PaintTile(Graphics g ,Tile tile, Color borderColor, Color fillColor){
        g.setColor(borderColor);
        g.drawPolygon(tile.getPolygon());
        g.setColor(fillColor);
        g.fillPolygon(tile.getPolygon());
    }




}
