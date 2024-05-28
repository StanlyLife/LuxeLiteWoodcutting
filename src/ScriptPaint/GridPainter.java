package ScriptPaint;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GridPainter {
    public static final Color GRAY = new Color(70, 61, 50, 156);
    public static final Color RED = new Color(255, 61, 50, 156);
    public static final Color GREEN = new Color(70, 255, 50, 156);
    public static final Color WHITE = new Color(255, 255, 255, 156);
    public static final Font font = new Font("Arial", Font.PLAIN, 14);
    private boolean show;
    private double x;
    private double y;
    private double cellWidth;
    private double cellHeight;
    private Color cellBorderColor;
    private List<LinkedList<Cell>> grid;

    public GridPainter(boolean show, double x, double y, double cellWidth, double cellHeight, Color cellBorderColor) {
        this.show = show;
        this.x = x;
        this.y = y;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.cellBorderColor = cellBorderColor;
        this.grid = new LinkedList<>();
    }

    public void draw(Graphics2D g) {
        if (show) {
            double x = this.x;
            double y = this.y;
            for (LinkedList<Cell> row : grid) {
                for (Cell cell : row) {
                    cell.draw(g, x, y, cellWidth, cellHeight, cellBorderColor);
                    x += cellWidth;
                }
                x = this.x;
                y += cellHeight;
            }
        }
    }

    public void addCell(Cell cell) {
        int targetRowIndex = cell.getRowNum() - 1;
        // Ensure the grid has enough rows
        while (grid.size() <= targetRowIndex) {
            grid.add(new LinkedList<>());
        }
        // Add the cell to the appropriate row
        grid.get(targetRowIndex).add(cell);
    }

    public void updateCell(String name, String data) {
        Cell cell = findCell(name);
        if (cell != null) {
            if (data != null) {
                cell.setData(data);
                cell.setPrefixWithName(true);
                cell.setBgColor(new Color(70, 61, 50, 156));
                cell.setTextColor(new Color(255, 0, 0, 255));
            }
        }
    }
    public void updateCell(String name, String data, int progress) {
        Cell cell = findCell(name);
        if (cell != null) {
            if (data != null) {
                cell.setData(data);
                cell.setPrefixWithName(true);
                cell.setBgColor(new Color(70, 61, 50, 156));
                cell.setTextColor(new Color(255, 0, 0, 255));
                cell.setProgress(progress);
            }
        }
    }

    public void removeCell(String name) {
        for (LinkedList<Cell> row : grid) {
            row.remove(findCell(name));
        }
    }

    private Cell findCell(String name) {
        for (LinkedList<Cell> row : grid) {
            for (Cell cell : row) {
                if (cell.getName().equals(name)) {
                    return cell;
                }
            }
        }
        return null;
    }
}