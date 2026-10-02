/*

    Jackson Varzali
    GameFrame class creates a GamePanel
    object. The game is started in the
    GamePanel constructor.

 */

// imports
import javax.swing.*;

// GameFrame creates a GamePanel object
public class GameFrame extends JFrame {
    GameFrame() {
        // creates GamePanel object
        GamePanel panel = new GamePanel();
        // sets GamePanel properties
        this.add(panel);
        this.setTitle("Asteroids");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
