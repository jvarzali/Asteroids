/*

    Jackson Varzali
    UpgradeMenu creates an upgrade menu using information from
    gamePanel, and allows user to choose upgrades from buttons
    that impact the game

*/

// imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// UpgradeMenu gets user input for upgrades
public class UpgradeMenu extends JPanel implements ActionListener {

    // variables
    GamePanel game;
    JButton choice1 = new JButton();
    JButton choice2 = new JButton();
    JButton choice3 = new JButton();
    String opt1;
    String opt2;
    Boolean unique;

    //  Constructor gets information from GamePanel
    UpgradeMenu(GamePanel game) {
        this.game = game;

        // initialize choice1 button
        choice1.addActionListener(this);
        choice1.setText("Choice 1");
        choice1.setFocusable(false);
        choice1.setHorizontalTextPosition(JButton.CENTER);
        choice1.setVerticalTextPosition(JButton.BOTTOM);
        choice1.setFont(new Font("Monaco", Font.BOLD, 30));
        choice1.setBounds(100, 200, 200, 100);
        choice1.setForeground(Color.WHITE);
        choice1.setBackground(Color.BLACK);
        choice1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        choice1.setVisible(true);
        choice1.setEnabled(true);

        // initialize choice2 button
        choice2.addActionListener(this);
        choice2.setText("Choice 2");
        choice2.setFocusable(false);
        choice2.setHorizontalTextPosition(JButton.CENTER);
        choice2.setVerticalTextPosition(JButton.BOTTOM);
        choice2.setFont(new Font("Monaco", Font.BOLD, 30));
        choice2.setBounds(400, 200, 200, 100);
        choice2.setForeground(Color.WHITE);
        choice2.setBackground(Color.BLACK);
        choice2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        choice2.setVisible(true);
        choice2.setEnabled(true);

        // initialize choice3 button
        choice3.addActionListener(this);
        choice3.setText("Add Life");
        choice3.setFocusable(false);
        choice3.setHorizontalTextPosition(JButton.CENTER);
        choice3.setVerticalTextPosition(JButton.BOTTOM);
        choice3.setFont(new Font("Monaco", Font.BOLD, 30));
        choice3.setBounds(700, 200, 200, 100);
        choice3.setForeground(Color.WHITE);
        choice3.setBackground(Color.BLACK);
        choice3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        choice3.setVisible(true);
        choice3.setEnabled(true);

        // add buttons
        game.add(choice1);
        game.add(choice2);
        game.add(choice3);

        // call method to set upgrade options
        unique = true;
        upgradeChoices();
    }

    // upgradeChoices sets upgrade options
    public void upgradeChoices() {
        // randomize an upgrade choice for each button,
        // loop until upgrades are capped

        // option 1 is always an upgrade to the lasers
        while (unique) {
            unique = false;
            // check if upgrades are maxed
            if (game.lasers >= 5 && game.laserSpeed >= 0.003 && game.laserPierce >= 1 && game.laserDistance >= 1.5) {
                opt1 = "na";
            }
            else {
                int random = (int) (Math.random() * 8);
                if (random <= 2) {
                    opt1 = "lc";
                } else if (random <= 4) {                    opt1 = "ls";
                } else if (random <= 6) {
                    opt1 = "ld";
                } else {
                    opt1 = "lp";
                }
                if (opt1.equals("lc") && game.lasers >= 5) {
                    unique = true;
                }
                if (opt1.equals("ls") && game.laserSpeed >= 0.003) {
                    unique = true;
                }
                if (opt1.equals("lp") && game.laserPierce >= 1) {
                    unique = true;
                }
                if (opt1.equals("ld") && game.laserDistance >= 1.5) {
                    unique = true;
                }
            }
        }

        // reset unique
        unique = true;

        // option 2 is always an upgrade to the ship
        while (unique) {
            unique = false;
            // check if upgrades are maxed
            if (game.speedIncrease >= 0.8 && game.rotSpeedMultiplier >= 1.4 && game.scoreMultiplier >= 1.4 && game.teleportDeathChance == 8) {
                opt2 = "na";
            }
            else {
                int random = (int) (Math.random() * 8);
                if (random <= 2) {
                    opt2 = "si";
                }
                else if (random <= 4) {
                    opt2 = "rs";
                }
                else if (random <= 6) {
                    opt2 = "sm";
                }
                else {
                    opt2 = "ts";
                }
                if (opt2.equals("si") && game.speedIncrease >= 0.8) {
                    unique = true;
                }
                if (opt2.equals("rs") && game.rotSpeedMultiplier >= 1.4) {
                    unique = true;
                }
                if (opt2.equals("sm") && game.scoreMultiplier >= 1.4) {
                    unique = true;
                }
                if (opt2.equals("ts") && game.teleportDeathChance == 8) {
                    unique = true;
                }
            }
        }

            // set buttons text
            switch (opt1) {
                case "lc" -> choice1.setText("Laser Count");
                case "ls" -> choice1.setText("Laser Speed");
                case "lp" -> choice1.setText("Laser Pierce");
                case "ld" -> choice1.setText("Laser Distance");
                default -> choice1.setText("NA");
            }
            switch (opt2) {
                case "si" -> choice2.setText("Speed Increase");
                case "rs" -> choice2.setText("Rotation Speed");
                case "sm" -> choice2.setText("Score Multiplier");
                case "ts" -> choice2.setText("Safer Teleport");
                default -> choice2.setText("NA");
            }


        // choice3 only adds lives if lives is less than
        if (game.lives >= 5) {
            choice3.setText("NA");
        }
    }

    // restart game hides buttons and resets spaceship and game
    public void restartGame() {
        choice1.setVisible(false);
        choice1.setEnabled(false);
        choice2.setVisible(false);
        choice2.setEnabled(false);
        choice3.setVisible(false);
        choice3.setEnabled(false);
        game.tpSafe();
        game.pause = false;
    }

    // action performed detects user input on buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        // decrease true score as cost for upgrade
        game.trueScore -= 5000;
        // on choice 1 change laser attributes accordingly
        if (e.getSource() == choice1) {
            switch (opt1) {
                case "lc" -> game.lasers++;
                case "ls" -> game.laserSpeed += 0.0005;
                case "lp" -> game.laserPierce = 1;
                case "ld" -> game.laserDistance += 0.25;
                default -> {
                    game.shownScore += 1000;
                    game.trueScore += 1000;
                }
            }
            restartGame();
        }
        // on choice 2 change ship attributes accordingly
        if (e.getSource() == choice2) {
            switch (opt2) {
                case "si" -> game.speedIncrease += 0.1;
                case "rs" -> game.rotSpeedMultiplier += 0.2;
                case "sm" -> game.scoreMultiplier += 0.2;
                case "ts" -> game.teleportDeathChance = 8;
                default -> {
                    game.shownScore += 1000;
                    game.trueScore += 1000;
                }
            }
            restartGame();
        }
        if (e.getSource() == choice3) {
            if (game.lives < 5) {
                game.lives += 1;
            }
            else {
                game.trueScore += 1000;
                game.shownScore += 1000;
            }
            restartGame();
        }
    }
}
