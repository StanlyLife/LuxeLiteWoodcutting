package ScriptPaint;

import org.dreambot.api.wrappers.widgets.WidgetChild;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static Utils.Initializers.getChatboxWidget;
public class BackgroundPainter {
    private static BufferedImage image;
    public static void PaintBackground(Graphics2D g) {
                image = getImage("/bg.png");
                WidgetChild chatBox = getChatboxWidget();

                if (image != null) {
                    g.drawImage(image, chatBox.getX(), chatBox.getY(), chatBox.getWidth(), chatBox.getHeight(), null);
                }
    }

    private static BufferedImage getImage(String relativePath) {
        try {
            return ImageIO.read(BackgroundPainter.class.getResourceAsStream(relativePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
