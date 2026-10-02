// imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;

// mainMenu creates a basic nagivatable main menu
// with instructions and setting options that affects
// the game
public class MainMenu extends JPanel implements ActionListener {

    // variables
    JButton title = new JButton();
    JButton start = new JButton();
    JButton settings = new JButton();
    JButton instructions = new JButton();
    JButton modernVisual = new JButton();
    JButton classicVisual = new JButton();
    JButton effectsVisual = new JButton();
    JButton originalGame = new JButton();
    JButton flashGame = new JButton();
    JButton upgradeGame = new JButton();
    JButton home = new JButton();
    JButton buffer1 = new JButton();
    JButton buffer2 = new JButton();
    JTextArea instructionText = new JTextArea();
    GamePanel game;
    Graphics2D g2d;
    String instructionstxt;

    // MainMenu constructor initializes buttons
    MainMenu(int width, int height, GamePanel g) {


        // set GamePanel to that of AsteroidsGame and set Layout
        game = g;
        g.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 60));

        // initialize instructions txt
        instructionstxt = "Use left and right arrows to rotate the ship and forward arrow to accelerate. Spacebar shoots lasers, and 's' teleports the ship to safety, however at the   risk of a 1/4 chance of the ship exploding. Shoot asteroids, and survive!     10,000 points adds a life. For Flashlight gamemode, asteroids can only be      located with flashlights, which are controllable with 'z', 'x', and 'c'. For     Upgrade gamemode every 5,000 points allows you to choose an upgrade to             the ship, however your ship starts off significantly weaker.";

        // initialize title button
        title.setText("Asteroids");
        title.setFocusable(false);
        title.setHorizontalTextPosition(JButton.CENTER);
        title.setVerticalTextPosition(JButton.BOTTOM);
        title.setFont(new Font("Monaco", Font.BOLD, 170));
        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);
        title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        title.setVisible(true);
        title.setEnabled(true);

        // intialize buffer1 button
        buffer1.setText("         ");
        buffer1.setFocusable(false);
        buffer1.setHorizontalTextPosition(JButton.CENTER);
        buffer1.setVerticalTextPosition(JButton.BOTTOM);
        buffer1.setFont(new Font("Monaco", Font.BOLD, 80));
        buffer1.setForeground(Color.WHITE);
        buffer1.setBackground(Color.BLACK);
        buffer1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buffer1.setVisible(false);
        buffer1.setEnabled(false);

        // intialize buffer2 button
        buffer2.setText("         ");
        buffer2.setFocusable(false);
        buffer2.setHorizontalTextPosition(JButton.CENTER);
        buffer2.setVerticalTextPosition(JButton.BOTTOM);
        buffer2.setFont(new Font("Monaco", Font.BOLD, 80));
        buffer2.setForeground(Color.WHITE);
        buffer2.setBackground(Color.BLACK);
        buffer2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buffer2.setVisible(false);
        buffer2.setEnabled(false);

        // initialize start button
        start.addActionListener(this);
        start.setText(" Start ");
        start.setFocusable(false);
        start.setHorizontalTextPosition(JButton.CENTER);
        start.setVerticalTextPosition(JButton.BOTTOM);
        start.setFont(new Font("Monaco", Font.BOLD, 70));
        start.setForeground(Color.WHITE);
        start.setBackground(Color.BLACK);
        start.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        start.setVisible(true);
        start.setEnabled(true);

        // initialize settings button
        settings.setLayout(null);
        settings.setBounds(100, 300, 200, 100);
        settings.addActionListener(this);
        settings.setText("Settings");
        settings.setFocusable(false);
        settings.setHorizontalTextPosition(JButton.CENTER);
        settings.setVerticalTextPosition(JButton.BOTTOM);
        settings.setFont(new Font("Monaco", Font.BOLD, 40));
        settings.setForeground(Color.WHITE);
        settings.setBackground(Color.BLACK);
        settings.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        settings.setVisible(true);
        settings.setEnabled(true);
        settings.setMargin(null);

        // initialize instructions button
        instructions.setLayout(null);
        instructions.addActionListener(this);
        instructions.setText("Instructions");
        instructions.setFocusable(false);
        instructions.setHorizontalTextPosition(JButton.CENTER);
        instructions.setVerticalTextPosition(JButton.BOTTOM);
        instructions.setFont(new Font("Monaco", Font.BOLD, 40));
        instructions.setForeground(Color.WHITE);
        instructions.setBackground(Color.BLACK);
        instructions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        instructions.setVisible(true);
        instructions.setEnabled(true);
        instructions.setMargin(null);

        // initialize modernVisual button
        modernVisual.addActionListener(this);
        modernVisual.setText("Modern");
        modernVisual.setFocusable(false);
        modernVisual.setHorizontalTextPosition(JButton.CENTER);
        modernVisual.setVerticalTextPosition(JButton.BOTTOM);
        modernVisual.setFont(new Font("Monaco", Font.BOLD, 50));
        modernVisual.setForeground(Color.WHITE);
        modernVisual.setBackground(Color.BLACK);
        modernVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        modernVisual.setVisible(false);
        modernVisual.setEnabled(false);

        // initialize effectsVisual button
        effectsVisual.addActionListener(this);
        effectsVisual.setText("Effects");
        effectsVisual.setFocusable(false);
        effectsVisual.setHorizontalTextPosition(JButton.CENTER);
        effectsVisual.setVerticalTextPosition(JButton.BOTTOM);
        effectsVisual.setFont(new Font("Monaco", Font.BOLD, 60));
        effectsVisual.setForeground(Color.WHITE);
        effectsVisual.setBackground(Color.BLACK);
        effectsVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        effectsVisual.setVisible(false);
        effectsVisual.setEnabled(false);

        // initialize classicVisual button
        classicVisual.addActionListener(this);
        classicVisual.setText("Classic");
        classicVisual.setFocusable(false);
        classicVisual.setHorizontalTextPosition(JButton.CENTER);
        classicVisual.setVerticalTextPosition(JButton.BOTTOM);
        classicVisual.setFont(new Font("Monaco", Font.BOLD, 60));
        classicVisual.setForeground(Color.WHITE);
        classicVisual.setBackground(Color.BLACK);
        classicVisual.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        classicVisual.setVisible(false);
        classicVisual.setEnabled(false);

        // initialize home button
        home.addActionListener(this);
        home.setText("Home");
        home.setFocusable(false);
        home.setHorizontalTextPosition(JButton.CENTER);
        home.setVerticalTextPosition(JButton.BOTTOM);
        home.setFont(new Font("Monaco", Font.BOLD, 60));
        home.setForeground(Color.WHITE);
        home.setBackground(Color.BLACK);
        home.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        home.setVisible(false);
        home.setEnabled(false);

        // initialize originalGame button
        originalGame.addActionListener(this);
        originalGame.setText("Original");
        originalGame.setFocusable(false);
        originalGame.setHorizontalTextPosition(JButton.CENTER);
        originalGame.setVerticalTextPosition(JButton.BOTTOM);
        originalGame.setFont(new Font("Monaco", Font.BOLD, 60));
        originalGame.setForeground(Color.WHITE);
        originalGame.setBackground(Color.BLACK);
        originalGame.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        originalGame.setVisible(false);
        originalGame.setEnabled(false);

        // initialize flashGame button
        flashGame.addActionListener(this);
        flashGame.setText("Flashlight");
        flashGame.setFocusable(false);
        flashGame.setHorizontalTextPosition(JButton.CENTER);
        flashGame.setVerticalTextPosition(JButton.BOTTOM);
        flashGame.setFont(new Font("Monaco", Font.BOLD, 60));
        flashGame.setForeground(Color.WHITE);
        flashGame.setBackground(Color.BLACK);
        flashGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flashGame.setVisible(false);
        flashGame.setEnabled(false);

        // initialize upgradeGame button
        upgradeGame.addActionListener(this);
        upgradeGame.setText("Upgrade");
        upgradeGame.setFocusable(false);
        upgradeGame.setHorizontalTextPosition(JButton.CENTER);
        upgradeGame.setVerticalTextPosition(JButton.BOTTOM);
        upgradeGame.setFont(new Font("Monaco", Font.BOLD, 60));
        upgradeGame.setForeground(Color.WHITE);
        upgradeGame.setBackground(Color.BLACK);
        upgradeGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upgradeGame.setVisible(false);
        upgradeGame.setEnabled(false);

        // initialize instructionText button
        instructionText.setText(instructionstxt);
        instructionText.setFont(new Font("Monaco", Font.BOLD, 25));
        instructionText.setLineWrap(true);
        instructionText.setSize(900, 300);
        instructionText.setForeground(Color.WHITE);
        instructionText.setBackground(Color.BLACK);
        instructionText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        instructionText.setVisible(false);
        instructionText.setEnabled(false);

        // add buttons
        g.add(buffer1);
        g.add(title);
        g.add(buffer2);
        g.add(settings);
        g.add(start);
        g.add(instructions);
        g.add(classicVisual);
        g.add(modernVisual);
        g.add(effectsVisual);
        g.add(originalGame);
        g.add(flashGame);
        g.add(upgradeGame);
        g.add(instructionText);
        g.add(home);
    }

    // actionPerformed detects button clicks that cause actions
    @Override
    public void actionPerformed(ActionEvent e) {
        // startGame and hide buttons with start button is clicked
        if (e.getSource() == start) {
            game.startGame();
            start.setEnabled(false);
            start.setVisible(false);
            settings.setEnabled(false);
            settings.setVisible(false);
            instructions.setVisible(false);
            instructions.setEnabled(false);
            title.setVisible(false);
            title.setEnabled(false);
        }
        // hide all buttons except show settings buttons
        if (e.getSource() == settings) {
            game.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 20));
            title.setText("Settings");
            title.setFont(new Font("Monaco", Font.BOLD, 80));
            start.setEnabled(false);
            start.setVisible(false);
            settings.setEnabled(false);
            settings.setVisible(false);
            classicVisual.setEnabled(true);
            classicVisual.setVisible(true);
            effectsVisual.setEnabled(true);
            effectsVisual.setVisible(true);
            modernVisual.setVisible(true);
            modernVisual.setEnabled(true);
            originalGame.setVisible(true);
            originalGame.setEnabled(true);
            flashGame.setVisible(true);
            flashGame.setEnabled(true);
            upgradeGame.setVisible(true);
            upgradeGame.setEnabled(true);
            home.setVisible(true);
            home.setEnabled(true);
            buffer1.setVisible(true);
            buffer1.setEnabled(true);
            buffer2.setVisible(true);
            buffer2.setEnabled(true);
            instructions.setVisible(false);
        }

        // hide all buttons except show instructions buttons
        if (e.getSource() == instructions) {
            game.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
            title.setText("Instructions");
            title.setFont(new Font("Monaco", Font.BOLD, 80));
            start.setEnabled(false);
            start.setVisible(false);
            settings.setEnabled(false);
            settings.setVisible(false);
            home.setVisible(true);
            home.setEnabled(true);
            buffer1.setVisible(true);
            buffer1.setEnabled(true);
            buffer2.setVisible(true);
            buffer2.setEnabled(true);
            instructions.setVisible(false);
            instructions.setEnabled(false);
            instructionText.setVisible(true);
            instructionText.setEnabled(true);
        }

        // when classic/effects/modern visual button is clicked, change the artstyle accordingly
        if (e.getSource() == classicVisual) {
            classicVisual.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            effectsVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            modernVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            game.artStyle = 'c';
        }
        if (e.getSource() == effectsVisual) {
            classicVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            effectsVisual.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            modernVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            game.artStyle = 'e';
        }
        if (e.getSource() == modernVisual) {
            classicVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            effectsVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            modernVisual.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            game.artStyle = 'm';
        }

        // when original/flashlight/upgrade gamemode button is clicked, change the gamemode accordingly
        if (e.getSource() == originalGame) {
            originalGame.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            flashGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            upgradeGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            game.gamemode = 'o';
        }
        if (e.getSource() == flashGame) {
            originalGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            flashGame.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            upgradeGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            game.gamemode = 'f';
        }
        if (e.getSource() == upgradeGame) {
            originalGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            flashGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            upgradeGame.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            game.gamemode = 'u';
        }

        // reset buttons to buttons displayed on home
        if (e.getSource() == home) {
            game.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 60));
            title.setText("Asteroids");
            title.setFont(new Font("Monaco", Font.BOLD, 170));
            start.setEnabled(true);
            start.setVisible(true);
            settings.setEnabled(true);
            settings.setVisible(true);
            modernVisual.setVisible(false);
            modernVisual.setEnabled(false);
            classicVisual.setEnabled(false);
            classicVisual.setVisible(false);
            effectsVisual.setEnabled(false);
            effectsVisual.setVisible(false);
            originalGame.setVisible(false);
            flashGame.setVisible(false);
            upgradeGame.setVisible(false);
            originalGame.setEnabled(false);
            flashGame.setEnabled(false);
            upgradeGame.setEnabled(false);
            home.setVisible(false);
            home.setEnabled(false);
            instructions.setVisible(true);
            buffer1.setVisible(false);
            buffer1.setEnabled(false);
            buffer2.setVisible(false);
            buffer2.setEnabled(false);
            instructionText.setVisible(false);
            instructionText.setEnabled(false);
        }
    }
}
