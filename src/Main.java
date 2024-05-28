import ScriptPaint.GridPainter;
import Tasks.Branches.*;
import Tasks.Leaves.*;
import org.dreambot.api.Client;
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
import org.dreambot.api.wrappers.widgets.WidgetChild;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static ScriptPaint.PaintUtils.PaintArea;
import static Settings.ScriptSettings.getSettings;
import static Utils.Formatters.*;
import static Utils.Formatters.roundToK;
import static Utils.Initializers.gridPainterInitializer;

@ScriptManifest(name = "Luxe Lite Woodcutter", description = "Chop wood, firemake, drop or fletch", author = "LUXE",
        version = 1.0, category = Category.WOODCUTTING, image = "")
public class Main extends TreeScript implements ExperienceListener {
    private Timer timer;
    private GridPainter gridPainter;
    private Frame gui;


    @Override
    public int onLoop() {
        if(timer.isPaused()) timer.resume();

        if(getSettings().shouldLoop) {
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
    private BufferedImage getImage(String relativePath) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(relativePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onStart() {

        if(getSettings().shouldUseStartTile) {
            getSettings().startArea = new Area(
                    new Tile(Players.getLocal().getX() + getSettings().radius, Players.getLocal().getY() + getSettings().radius),
                    new Tile(Players.getLocal().getX() - getSettings().radius, Players.getLocal().getY() -getSettings().radius)
            );
        }

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


    }


    @Override
    public void onPaint(Graphics2D graphics) {


        Widget dialogueBox = Widgets.getWidget(162);
        if(dialogueBox == null) return;
        WidgetChild chatBox = dialogueBox.getChild(558);
        if(chatBox == null) return;
        Logger.log("height: " + chatBox.getHeight() + " - width: " + chatBox.getWidth());


        if(getSettings().shouldUseStartTile) {
            PaintArea(graphics, getSettings().startArea, new Color(0,255,0,10), new Color(0,255,0,25));
        }

        if(gridPainter == null) return;
        gridPainter.updateCell("Time", formatElapsedTime(timer.elapsed()));
        gridPainter.updateCell("Woodcutting EXP", roundToK(SkillTracker.getGainedExperience(Skill.WOODCUTTING)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.WOODCUTTING) + ")" + " (" + formatTime(timeToLevel(SkillTracker.getGainedExperiencePerHour(Skill.WOODCUTTING))) + ")", getSkillProgress(Skill.WOODCUTTING));
        if(getSettings().shouldFletch) gridPainter.updateCell("Fletching EXP", roundToK(SkillTracker.getGainedExperience(Skill.FLETCHING)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.FLETCHING) + ")", getSkillProgress(Skill.FLETCHING));
        if(getSettings().shouldFiremake) gridPainter.updateCell("Firemaking EXP", roundToK(SkillTracker.getGainedExperience(Skill.FIREMAKING)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.FIREMAKING) + ")", getSkillProgress(Skill.FIREMAKING));
        gridPainter.updateCell("State", this.getCurrentBranchName() + " - " + this.getCurrentLeafName());
        gridPainter.draw(graphics);
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
