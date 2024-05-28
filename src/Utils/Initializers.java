package Utils;

import ScriptPaint.Cell;
import ScriptPaint.GridPainter;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.awt.*;

import static Settings.ScriptSettings.getSettings;

public class Initializers {

    private final static int cellHeight = 35;

    public static WidgetChild getChatboxWidget() {

        Widget dialogueBox = Widgets.getWidget(162);
        if(dialogueBox == null) return null;
        WidgetChild chatBox = dialogueBox.getChild(558);
        if(chatBox == null) return null;


        return chatBox;
    }
    public static GridPainter gridPainterInitializer() {
        WidgetChild chatBox = getChatboxWidget();
        GridPainter gp  = new GridPainter(true, chatBox.getX() , chatBox.getY() - (cellHeight * 6), 100, cellHeight, new Color(255, 255, 255, 225));
        gp = addCells(gp);
        return gp;
    }

    private static GridPainter addCells(GridPainter gp) {
        gp.addCell(
                new Cell(
                        "Time",
                        1,
                        "52",
                        1,
                        false,
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                )
        );
        gp.addCell(
                new Cell(
                        "State",
                        2,
                        "52",
                        3,
                        false,
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                )
        );
        gp.addCell(
                new Cell(
                        "Woodcutting EXP",
                        3,
                        "0",
                        3,
                        false,
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                )
        );
        if(getSettings().shouldFiremake) gp.addCell(
                new Cell(
                        "Firemaking EXP",
                        4,
                        "0",
                        3,
                        false,
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                )
        );
        if(getSettings().shouldFletch)gp.addCell(
                new Cell(
                        "Fletching EXP",
                        4,
                        "0",
                        3,
                        false,
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                )
        );

        return gp;
    }

    public static Timer initializeTimer() {
        Timer timer = new Timer();
        timer.start();
        return timer;
    }
}
