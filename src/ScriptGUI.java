import ScriptEnums.WoodcuttingTrees;
import org.dreambot.api.utilities.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import static Settings.ScriptSettings.getSettings;
import static org.dreambot.api.utilities.Images.loadImage;

public class ScriptGUI extends JFrame {
    private JLabel headerLogo;
    private JComboBox actionCombobox;
    private JComboBox locationCombobox;
    private JButton button1;
    private JPanel MotherPanel;
    public JPanel headerPanel;
    public JPanel radiusPanel;
    public JSlider slider1;
    public JLabel titleLabel;
    public JLabel radiusLabel;
    public JComboBox treeTypeComboBox;
    public JPanel borderLayoutPanelForIcon;

    public ScriptGUI() {

        try {


            for (String opt : WoodcuttingTrees.options) {
                treeTypeComboBox.addItem(opt);
            }

            actionCombobox.addItem("Drop");
            actionCombobox.addItem("Bank");
            actionCombobox.addItem("Firemake");
            actionCombobox.addItem("Fletch");

            locationCombobox.addItem("Current position");
            headerLogo.setIcon(new ImageIcon(loadImage("https://i.ibb.co/NZZtR7r/Finishing-Logo-LUXE.png")));
            ScriptGUI getThis = this;
            slider1.setMinimum(1);
            slider1.setMaximum(15);
            slider1.setMinorTickSpacing(1);
            slider1.setPaintTicks(true);
            slider1.setPaintLabels(true);
            button1.setPreferredSize(new Dimension(button1.getWidth(), 50));
            slider1.setValue(getSettings().radius);
            radiusLabel.setText("Radius: " + getSettings().radius);
            slider1.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int value = slider1.getValue();
                    radiusLabel.setText("Radius: " + value);
                    getSettings().radius = value;
                    Logger.log("Slider value: " + value);
                }
            });

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Logger.log("Clicked start");
                    getSettings().shouldLoop = true;

                    getSettings().shouldDrop = actionCombobox.getSelectedItem() == "Drop";
                    getSettings().shouldFletch = actionCombobox.getSelectedItem() == "Fletch";
                    getSettings().shouldFiremake = actionCombobox.getSelectedItem() == "Firemake";

                    Logger.log("setting tree type to: " + treeTypeComboBox.getSelectedItem().toString());
                    getSettings().setWoodcuttingTrees(treeTypeComboBox.getSelectedItem().toString());
                    Logger.log("set tree type to: " + getSettings().treeType.getName() + " with logs: " + getSettings().treeType.getLogs());
                    getSettings().shouldUseStartTile = locationCombobox.getSelectedItem() == "Current position";

                    getThis.dispose();
                }
            });

            setContentPane(MotherPanel);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setVisible(true);
        } catch (NullPointerException e) {
            Logger.log("Error in setting up GUI");
            e.printStackTrace();
            Logger.log(e.getStackTrace());
            Logger.log(e.getMessage());
        }

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MotherPanel = new JPanel();
        MotherPanel.setLayout(new GridBagLayout());
        MotherPanel.setBackground(new Color(-15983320));
        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.setBackground(new Color(-15983320));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(headerPanel, gbc);
        titleLabel = new JLabel();
        titleLabel.setBackground(new Color(-2508288));
        Font titleLabelFont = this.$$$getFont$$$("Exo ExtraBold", -1, 28, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setForeground(new Color(-2508288));
        titleLabel.setText("LITE WOODCUTTING");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        headerPanel.add(titleLabel, gbc);
        locationCombobox = new JComboBox();
        locationCombobox.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MotherPanel.add(locationCombobox, gbc);
        actionCombobox = new JComboBox();
        actionCombobox.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MotherPanel.add(actionCombobox, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Candara", Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Action");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        MotherPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Candara", Font.BOLD, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Location");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        MotherPanel.add(label2, gbc);
        button1 = new JButton();
        button1.setBackground(new Color(-2508288));
        Font button1Font = this.$$$getFont$$$("Exo Medium", Font.BOLD, 18, button1.getFont());
        if (button1Font != null) button1.setFont(button1Font);
        button1.setForeground(new Color(-65546));
        button1.setText("Start");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MotherPanel.add(button1, gbc);
        radiusPanel = new JPanel();
        radiusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        radiusPanel.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(radiusPanel, gbc);
        slider1 = new JSlider();
        slider1.setBackground(new Color(-15983320));
        slider1.setMaximum(15);
        radiusPanel.add(slider1);
        radiusLabel = new JLabel();
        Font radiusLabelFont = this.$$$getFont$$$("Candara", Font.BOLD, 18, radiusLabel.getFont());
        if (radiusLabelFont != null) radiusLabel.setFont(radiusLabelFont);
        radiusLabel.setText("Radius: 0");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        MotherPanel.add(radiusLabel, gbc);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Candara", Font.BOLD, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Tree type");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        MotherPanel.add(label3, gbc);
        treeTypeComboBox = new JComboBox();
        treeTypeComboBox.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MotherPanel.add(treeTypeComboBox, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(panel2, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(panel3, gbc);
        borderLayoutPanelForIcon = new JPanel();
        borderLayoutPanelForIcon.setLayout(new BorderLayout(0, 0));
        borderLayoutPanelForIcon.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        MotherPanel.add(borderLayoutPanelForIcon, gbc);
        headerLogo = new JLabel();
        headerLogo.setText("");
        borderLayoutPanelForIcon.add(headerLogo, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(panel4, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel5.setBackground(new Color(-15983320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        MotherPanel.add(panel5, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MotherPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}



