import ScriptPaint.GridPainter;
import Tasks.Branches.*;
import Tasks.Leaves.*;
import org.dreambot.api.Client;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.listener.ExperienceListener;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static ScriptPaint.PaintUtils.PaintArea;
import static Settings.ScriptSettings.getSettings;
import static Utils.Formatters.*;
import static Utils.Formatters.roundToK;
import static Utils.Initializers.gridPainterInitializer;
import static org.dreambot.api.utilities.Images.loadImage;

@ScriptManifest(name = "Luxe Lite Woodcutter", description = "Chop wood, firemake, fletch, bank or drop the logs you collect", author = "LUXE",
        version = 1.0, category = Category.WOODCUTTING, image = "https://i.ibb.co/NZZtR7r/Finishing-Logo-LUXE.png")
public class Main extends TreeScript implements ExperienceListener {
    private Timer timer = new Timer();
    private GridPainter gridPainter;
    private Frame gui;


    @Override
    public int onLoop() {
        if(timer.isPaused()) timer.resume();
        if(getSettings().shouldUseStartTile) {
            getSettings().startArea = new Area(
                    new Tile(getSettings().startTile.getX() + getSettings().radius, getSettings().startTile.getY() + getSettings().radius),
                    new Tile(getSettings().startTile.getX() - getSettings().radius, getSettings().startTile.getY() -getSettings().radius)
            );
        }
        if (Client.isLoggedIn() && (!SkillTracker.hasStarted(Skill.WOODCUTTING) || !SkillTracker.hasStarted(Skill.FLETCHING) || !SkillTracker.hasStarted(Skill.FIREMAKING)) ) {
            SkillTracker.start(Skill.WOODCUTTING, Skill.FLETCHING, Skill.FIREMAKING);
        }
        if(getSettings().shouldLoop) {
            Logger.log(this.getRoot().getTree().getCurrentBranchName() + " - " + this.getRoot().getTree().getCurrentLeafName() + " - Should persist firemaking? " + getSettings().shouldPersistFiremaking);
            return this.getRoot().onLoop();
        }
        return 1000;
    }
    @Override
    public void onPause() {
        if (timer != null) {
            timer.pause();
        }
    }

    @Override
    public void onExit() {
        gui.dispose();
    }


    @Override
    public void onStart() {
        image = loadImage("https://i.ibb.co/MhKGfmG/paint.jpg");
        getSettings().startTile = Players.getLocal().getTile();

        if (Client.isLoggedIn()) {
            SkillTracker.start(Skill.WOODCUTTING, Skill.FLETCHING, Skill.FIREMAKING);
        }
        timer = initializeTimer();
        gridPainter = gridPainterInitializer();

        this.addBranches(
                new GetAxeBranch(),
                new GetKnifeBranch().addLeaves(new DoGetKnife()),
                new HandleLogsBranch().addLeaves(new DoDropLogs(), new DoBankLogs()),
                new WoodcuttingBranch().addLeaves(new DoWoodcutting()),
                new FiremakingBranch().addLeaves(new DoFiremaking()),
                new FletchingBranch().addLeaves(new DoFletching())
        );

        SwingUtilities.invokeLater(() -> {
            gui = new ScriptGUI();
            gui.setVisible(true);
        });

    }

    private BufferedImage image;
    private boolean showImage = true;
    @Override
    public void onPaint(Graphics2D graphics) {


        Widget dialogueBox = Widgets.getWidget(162);
        if(dialogueBox == null) return;
        WidgetChild chatBox = dialogueBox.getChild(558);
        if(chatBox == null) return;

        graphics.drawImage(image, chatBox.getX(), chatBox.getY() , chatBox.getWidth(), chatBox.getHeight() , null);
        graphics.setFont(new Font("Arial", Font.BOLD, 12));

        //TIMER
        graphics.drawString(showImage ? formatElapsedTime(timer.elapsed()) : "", 23, chatBox.getY() + 42 );
        //EXP

        //FLETCHING
        graphics.drawString(showImage ? formatTime(timeToLevel(SkillTracker.getGainedExperiencePerHour(Skill.FLETCHING))) : "", 430, 120 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperiencePerHour(Skill.FLETCHING)) + "" : "", 348, 120 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperience(Skill.FLETCHING) ) + "" : "", 250, 120 + chatBox.getY());
        graphics.drawString(showImage ? Skill.FLETCHING.getLevel() + "" : "", 170, 120 + chatBox.getY());
        //FIREMAKING
        graphics.drawString(showImage ? formatTime(timeToLevel(SkillTracker.getGainedExperiencePerHour(Skill.FIREMAKING))) : "", 430, 84 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperiencePerHour(Skill.FIREMAKING)) + "" : "", 348, 84 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperience(Skill.FIREMAKING) ) + "" : "", 250, 84 + chatBox.getY());
        graphics.drawString(showImage ? Skill.FIREMAKING.getLevel() + "" : "", 170, 84 + chatBox.getY());
        //WOODCUTTING
        graphics.drawString(showImage ? formatTime(timeToLevel(SkillTracker.getGainedExperiencePerHour(Skill.WOODCUTTING))) : "", 430, 48 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperiencePerHour(Skill.WOODCUTTING)) + "" : "", 348, 48 + chatBox.getY());
        graphics.drawString(showImage ? roundToK(SkillTracker.getGainedExperience(Skill.WOODCUTTING) ) + "" : "", 250, 48 + chatBox.getY());
        graphics.drawString(showImage ? Skill.WOODCUTTING.getLevel() + "" : "", 170, 48 + chatBox.getY());

        if(getSettings().shouldUseStartTile) {
            PaintArea(graphics, getSettings().startArea, new Color(0,255,0,10), new Color(0,255,0,25));
        }


        List<GameObject> trees = GameObjects.all(i -> i != null && i.getName().toLowerCase().contains(getSettings().shouldLoop ? getSettings().treeType.getName().toLowerCase() : "tree") && getSettings().startArea.contains(i.getTile()) && i.isOnScreen());
        for(GameObject tree : trees) {

        Shape shape = tree.getModel().getHullBounds(1.f);
        // Enable anti-aliasing for smoother lines
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set the color to Cyan
            graphics.setColor(Color.CYAN);
        // Set the thickness of the boarder
            graphics.setStroke(new BasicStroke(2.f));
        //Draw the shape
            graphics.draw(shape);
        }



    }

    public static int timeToLevel(int experiencePerHour) {
        if (experiencePerHour == 0) return 0;
        if(Skills.getRealLevel(Skill.WOODCUTTING ) == 99) return 0;
        long experienceNeeded = Skills.getExperienceForLevel(Skills.getRealLevel(Skill.WOODCUTTING ) + 1) - Skills.getExperience(Skill.WOODCUTTING);
        double timeInSeconds = ((double) experienceNeeded / experiencePerHour) * 3600;
        return (int) timeInSeconds; // Convert to seconds
    }

    private int getSkillProgress(Skill skill) {
        int currentExp = Skills.getExperience(skill);
        int currentLevelExp = Skills.getExperienceForLevel(skill.getLevel());
        int nextLevelExp = Skills.getExperienceForLevel(skill.getLevel() + 1) - currentLevelExp;
        int percentage = (int) Math.round(((double) currentExp - currentLevelExp) / nextLevelExp * 100);
        return percentage;
    }




    public static Timer initializeTimer() {
        Timer timer = new Timer();
        timer.start();
        return timer;
    }
    }
