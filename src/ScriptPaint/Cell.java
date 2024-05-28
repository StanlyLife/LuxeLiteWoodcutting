package ScriptPaint;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Cell {
    public static final Font font = new Font("Arial", Font.PLAIN, 14);
    private String name;
    private int rowNum;
    private String data;
    private int progress = 0;
    private int span;
    private boolean prefixWithName;
    private Color bgColor;
    private Color textColor;

    public Cell(String name, int rowNum, String data, int span, boolean prefixWithName, Color bgColor, Color textColor) {
        this.name = name;
        this.rowNum = rowNum;
        this.data = data;
        this.span = span;
        this.prefixWithName = prefixWithName;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }

    public void draw(Graphics2D g, double x, double y, double cellWidth, double cellHeight, Color cellBorderColor) {
        double w = cellWidth * span;
        String text = prefixWithName ? name + ": " + data : data;
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, cellHeight);
        g.setColor(new Color(0, 0, 0, 100));
        g.fill(rect);
        g.setColor(cellBorderColor);
        g.draw(rect);

        if(progress != 0) {
        Rectangle2D.Double progressRect = new Rectangle2D.Double(x, y+cellHeight-5, Math.round((progress / 100.0) * w), 5);
        g.setColor(new Color(0, 255, 0, 100));
        g.fill(progressRect);
        g.setColor(new Color(0, 255, 0, 255));
        g.draw(progressRect);
        g.setColor(new Color(0, 0, 0, 100));
//        text = progress + " - width: " + w + " ---- " + Math.round((progress / 100.0) * w);
        }

        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        float textX = (float) (x + ((w - fm.stringWidth(text)) / 2));
        float textY = (float) (y + (((cellHeight - fm.getHeight()) / 2) + fm.getAscent()));
        g.setColor(Color.WHITE); //text color
        g.drawString(text, textX, textY);
    }

    public String getName() {
        return name;
    }

    public int getRowNum() {
        return rowNum;
    }


    public void setData(String data) {
        this.data = data;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public void setPrefixWithName(boolean prefixWithName) {
        this.prefixWithName = prefixWithName;
    }


    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }


    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }


}