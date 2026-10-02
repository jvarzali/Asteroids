/*

    Jackson Varzali
    GamePanel class uses the GameFrame
    created in the GameFrame class in order
    to run an asteroids game

 */

// imports
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// class GamePanel runs the game
public class GamePanel extends JPanel implements ActionListener {

    // variables and objects
    public char artStyle = 'c';
    public char gamemode = 'o';
    static final int screenWidth = 1000;
    static final int screenHeight = 500;
    static final int delay = 1;
    public double xPos;
    public double yPos;
    public double rotation;
    public double dx;
    public double dy;
    public Timer timer;
    public BufferedImage shipImg1;
    public BufferedImage shipImg2;
    public BufferedImage shipImg3;
    public BufferedImage shipImg4;
    public BufferedImage bAsteroidImg1;
    public BufferedImage bAsteroidImg2;
    public BufferedImage bAsteroidImg3;
    public BufferedImage mAsteroidImg1;
    public BufferedImage mAsteroidImg2;
    public BufferedImage mAsteroidImg3;
    public BufferedImage sAsteroidImg1;
    public BufferedImage sAsteroidImg2;
    public BufferedImage sAsteroidImg3;
    public BufferedImage flashlight1;
    public BufferedImage flashlight2;
    public BufferedImage flashlight3;
    public BufferedImage explosion1;
    public BufferedImage explosion2;
    public BufferedImage explosion3;
    public BufferedImage explosion4;
    public BufferedImage explosion5;
    public BufferedImage teleport;
    public int flashlightType;
    public double speedIncrease;
    public boolean running;
    public boolean gameStart = false;
    public int[] xStars;
    public int[] yStars;
    public Color[] colors;
    public int lives;
    public int trueScore;
    public int shownScore;
    public int lasers;
    public ArrayList<Double[]> laserInfo;
    public double acceleration;
    public int isTurning;
    public double numPress;
    public double rotSpeedMultiplier;
    public double scoreMultiplier;
    public int isThrusting;
    public double laserSpeed;
    public BufferedImage laserImg;
    public ArrayList<Double[]> asteroidInfo;
    public double asteroidSpeedMultiplier;
    public boolean pause;
    public ArrayList<int[]> explosionTick;
    public ArrayList<int[]> teleportTick;
    public double[] alien;
    public double[] alienLaser;
    public double deathRotation;
    public int laserPierce;
    public double laserDistance;
    public int teleportDeathChance;
    public Graphics g;

    // constructor creates a GamePanel object and calls startGame
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setVisible(true);
        startMenu();
    }

    // startMenu creates a menu giving users choices and an overview of the game
    public void startMenu() {
        MainMenu menu = new MainMenu(screenWidth, screenHeight, this);
    }

    // startGame initializes starting variables and starts timer
    public void startGame() {
        loadImages();
        xPos = 500;
        yPos = 250;
        rotation = 0;
        dx = 0;
        dy = 0;
        speedIncrease = 0.6;
        if (gamemode == 'u') {
            speedIncrease = 0.5;
        }
        lives = 3;
        trueScore = -3000;
        shownScore = -3000;
        lasers = 3;
        if (gamemode == 'u') {
            lasers = 1;
        }
        laserDistance = 1;
        acceleration = 0;
        isTurning = 0;
        numPress = 0;
        isThrusting = 0;
        laserSpeed = 0.002;
        if (gamemode == 'u') {
            laserSpeed = 0.0015;
        }
        laserInfo = new ArrayList<>();
        flashlightType = 1;
        xStars = starLocation(1000);
        yStars = starLocation(500);
        colors = starColors();
        asteroidInfo = new ArrayList<>();
        asteroidSpeedMultiplier = 0.6;
        laserPierce = 0;
        scoreMultiplier = 1;
        rotSpeedMultiplier = 1;
        if (gamemode == 'u') {
            rotSpeedMultiplier = 0.8;
        }
        teleportDeathChance = 4;
        explosionTick = new ArrayList<int[]>();
        teleportTick = new ArrayList<int[]>();
        alien = new double[4];
        alienLaser = new double[4];
        alien[0] = -10000;
        timer = new Timer(delay, this);
        running = true;
        pause = false;
        gameStart = true;
        timer.start();
    }

    // paintComponent calls draw method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // draw method draws the output of the game
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // draw gameOver screen if game is not running
        if (running) {
            // draw flashlight if gamemode is flashlight
            if (gamemode == 'f') {
                // based on flashlight type draw flashlight accordingly
                if (flashlightType == 1) {
                    g2d.drawImage(flashlight1, (int)(xPos + shipImg1.getWidth() / 2 - flashlight1.getWidth() / 2), (int)(yPos + shipImg1.getHeight() / 2 - flashlight1.getHeight() / 2), null);
                }
                else if (flashlightType == 2) {
                    AffineTransform backup = g2d.getTransform();
                    double rotRequired = Math.toRadians(rotation - 130);
                    AffineTransform a = AffineTransform.getRotateInstance(rotRequired, xPos + shipImg1.getWidth() / 2.0, yPos + shipImg1.getHeight() / 2.0);
                    g2d.setTransform(a);
                    g2d.drawImage(flashlight2, (int)(xPos + shipImg1.getWidth() - 3), (int)(yPos  + shipImg1.getHeight() / 2 + 5), null);
                    g2d.setTransform(backup);
                }
                else if (flashlightType == 3) {
                    AffineTransform backup = g2d.getTransform();
                    double rotRequired = Math.toRadians(rotation - 105);
                    AffineTransform a = AffineTransform.getRotateInstance(rotRequired, xPos + shipImg1.getWidth() / 2.0, yPos + shipImg1.getHeight() / 2.0);
                    g2d.setTransform(a);
                    g2d.drawImage(flashlight3, (int)(xPos + shipImg1.getWidth() / 2), (int)(yPos + shipImg1.getHeight() / 2 - 1), null);
                    g2d.setTransform(backup);
                }
            }

            // draw lasers
            if (lasers < 3) {
                for (Double[] laser : laserInfo) {
                    AffineTransform backupB = g2d.getTransform();
                    AffineTransform b = AffineTransform.getRotateInstance(Math.toRadians(laser[5]), laser[0] + (double) laserImg.getWidth() / 2, laser[1] + (double) laserImg.getHeight() / 2);
                    g2d.setTransform(b);
                    g2d.drawImage(laserImg, (int) (double) laser[0], (int) (double) laser[1], null);
//                    g.drawOval((int) (double) laser[0], (int) (double) laser[1], 10, 10);
                    g2d.setTransform(backupB);
                }
            }

            // draw aliens lasers and aliens
            g.setColor(Color.WHITE);
            g.fillRect((int)alien[0], (int)alien[1], 30, 30);
            g.fillOval((int)alienLaser[0], (int)alienLaser[1], 5, 5);

            // check if gamemode isn't flashlight to draw stars
            if (gamemode != 'f') {
                // draw stars with random x and y positions
                for (int i = 0; i < xStars.length; i++) {
                    g.setColor(colors[i]);
                    g.fillOval(xStars[i], yStars[i], 3, 3);
                }
            }

            AffineTransform backup = g2d.getTransform();
            // draw explosion aftereffects
            if (!explosionTick.isEmpty()) {
                // remove explosions whose timer is over
                explosionTick.removeIf(e -> e[0] < 0);
                // rotate image based on death rotation
                if (artStyle == 'c') {
                    AffineTransform a = AffineTransform.getRotateInstance(deathRotation, xPos + explosion1.getWidth() / 2.0, yPos + explosion1.getHeight() / 2.0);
                }
                for (int[] e : explosionTick) {
                    e[0]--;
                    System.out.println(e[1] + " " + e[2]);
                    // draw explosion stage based on time of explosion
                    if (e[0] > 85) {
                        g2d.drawImage(explosion1, e[1], e[2], null);
                    }
                    else if (e[0] > 70) {
                        g2d.drawImage(explosion2, e[1], e[2], null);
                    }
                    else if (e[0] > 55) {
                        g2d.drawImage(explosion3, e[1], e[2], null);
                    }
                    else if (e[0] > 40) {
                        g2d.drawImage(explosion4, e[1], e[2], null);
                    }
                    else {
                        g2d.drawImage(explosion5, e[1], e[2], null);
                    }
                }
                if (artStyle == 'c') {
                    g2d.setTransform(backup);
                }
            }

            // draw teleport aftereffects
            if (!teleportTick.isEmpty()) {
                teleportTick.removeIf(e -> e[0] < 0);
                for (int[] t : teleportTick) {
                    t[0]--;
                    g2d.drawImage(teleport, t[1], t[2], null);
                }
            }

            // draw spaceship with specified rotation and position
            double rotRequired = Math.toRadians(rotation);
            AffineTransform a = AffineTransform.getRotateInstance(rotRequired, xPos + (double) shipImg1.getWidth() / 2, yPos + (double) shipImg1.getHeight() / 2);
            g2d.setTransform(a);
            // if ship is long thrusting use img3, else if thrusting use img2, else use img1
            if (isThrusting == 0) {
                g2d.drawImage(shipImg1, (int) xPos, (int) yPos, null);
            }
            else if (isThrusting == 1) {
                g2d.drawImage(shipImg2, (int) xPos, (int) yPos, null);
            }
            else if (isThrusting == 2) {
                g2d.drawImage(shipImg3, (int) xPos, (int) yPos, null);
            }
            else {
                g2d.drawImage(shipImg4, (int) xPos, (int) yPos, null);
            }
            g2d.setTransform(backup);

            // draw asteroids with specified position and size
            for (Double[] d : asteroidInfo) {
                if (d[4] == 3) {
                    // draw random asteroid image based on asteroid info
                    if (d[5] == 0.0) {
                        g2d.drawImage(bAsteroidImg1, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else if (d[5] == 1.0) {
                        g2d.drawImage(bAsteroidImg2, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else {
                        g2d.drawImage(bAsteroidImg3, (int) (double) d[0], (int) (double) d[1], null);
                    }
                }
                else if (d[4] == 2) {
                    if (d[5] == 0) {
                        // draw random asteroid image based on asteroid info
                        g2d.drawImage(mAsteroidImg1, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else if (d[5] == 1) {
                        g2d.drawImage(mAsteroidImg2, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else {
                        g2d.drawImage(mAsteroidImg3, (int) (double) d[0], (int) (double) d[1], null);
                    }
                }
                else {
                    // draw random asteroid image based on asteroid info
                    if (d[5] == 0) {
                        g2d.drawImage(sAsteroidImg1, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else if (d[5] == 1) {
                        g2d.drawImage(sAsteroidImg2, (int) (double) d[0], (int) (double) d[1], null);
                    }
                    else {
                        g2d.drawImage(sAsteroidImg3, (int) (double) d[0], (int) (double) d[1], null);
                    }
                }
            }

            // draw score at top center of screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("" + shownScore, (screenWidth - metrics.stringWidth("" + shownScore))/2, 30);

            // draw lives at top left of screen
            for (int i = 0; i < lives; i++) {
                g.drawImage(shipImg1, 5 + 22 * (i), 5, null);
            }
        }
        else {
            // draw game over screen here
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth, screenHeight);
            if (gameStart) {
                g.setFont(new Font("Monospaced", Font.PLAIN, 100));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.setColor(Color.RED);
                g.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over")) / 2, screenHeight / 2);
            }
        }
    }

    public int clamp(int val, int max, int min) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }
    public Color darker(Color c, double FACTOR) {
        return new Color(clamp((int)(c.getRed()  * FACTOR), 255, 0),
                clamp((int)(c.getGreen()*FACTOR), 255, 0),
                clamp((int)(c.getBlue() *FACTOR), 255, 0),
                c.getAlpha());
    }

    // set colors of stars
    public Color[] starColors() {
        // create 30 star locations
        int stars = 0;
        Color[] colors = new Color[30];
        while (stars < 30) {
            // randomize star colors
            // if game is modern make stars red
            if (artStyle == 'm') {
                colors[stars] = darker(Color.RED, (int)(Math.random() * 10) + 0.5);
            }
            else  {
                colors[stars] = darker(Color.GRAY, (int)(Math.random() * 10) + 0.5);
            }
            stars++;
        }
        return colors;
    }

    // loadImage loads the img for the spaceship
    public void loadImages() {
        // catch IOExceptions if file doesn't exist
        try {
            if (artStyle == 'c') {
                shipImg1 = ImageIO.read(new File("./Asteroids/img/Classic Asteroid Ship.PNG"));
                shipImg2 = ImageIO.read(new File("./Asteroids/img/Classic Asteroid Ship Thrusting.PNG"));
                shipImg3 = ImageIO.read(new File("./Asteroids/img/Classic Asteroid Ship Medium Thrusting.PNG"));
                shipImg4 = ImageIO.read(new File("./Asteroids/img/Classic Asteroid Ship Long Thrusting.PNG"));
                laserImg = ImageIO.read(new File("./Asteroids/img/Classic Laser.PNG"));
                bAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 1.PNG"));
                bAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 2.PNG"));
                bAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 3.PNG"));
                mAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 1.PNG"));
                mAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 2.PNG"));
                mAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 3.PNG"));
                sAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 1.PNG"));
                sAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 2.PNG"));
                sAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 3.PNG"));
                teleport = ImageIO.read(new File("./Asteroids/img/Classic Teleport.PNG"));
                explosion1 = ImageIO.read(new File("./Asteroids/img/Classic Explosion 1.PNG"));
                explosion2 = ImageIO.read(new File("./Asteroids/img/Classic Explosion 2.PNG"));
                explosion3 = ImageIO.read(new File("./Asteroids/img/Classic Explosion 3.PNG"));
                explosion4 = ImageIO.read(new File("./Asteroids/img/Classic Explosion 4.PNG"));
                explosion5 = ImageIO.read(new File("./Asteroids/img/Classic Explosion 5.PNG"));

            }
            else if (artStyle == 'm') {
                shipImg1 = ImageIO.read(new File("./Asteroids/img/Modern Asteroid Ship.PNG"));
                shipImg2 = ImageIO.read(new File("./Asteroids/img/Modern Asteroid Ship Thrusting.PNG"));
                shipImg3 = ImageIO.read(new File("./Asteroids/img/Modern Asteroid Ship Medium Thrusting.PNG"));
                shipImg4 = ImageIO.read(new File("./Asteroids/img/Modern Asteroid Ship Long Thrusting.PNG"));
                laserImg = ImageIO.read(new File("./Asteroids/img/Modern Laser.PNG"));
                bAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Modern Big Asteroid 1.PNG"));
                bAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Modern Big Asteroid 2.PNG"));
                bAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Modern Big Asteroid 3.PNG"));
                mAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Modern Medium Asteroid 1.PNG"));
                mAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Modern Medium Asteroid 2.PNG"));
                mAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Modern Medium Asteroid 3.PNG"));
                sAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Modern Small Asteroid 1.PNG"));
                sAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Modern Small Asteroid 2.PNG"));
                sAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Modern Small Asteroid 3.PNG"));
                teleport = ImageIO.read(new File("./Asteroids/img/Modern Teleport.PNG"));
                explosion1 = ImageIO.read(new File("./Asteroids/img/Modern Explosion 1.PNG"));
                explosion2 = ImageIO.read(new File("./Asteroids/img/Modern Explosion 2.PNG"));
                explosion3 = ImageIO.read(new File("./Asteroids/img/Modern Explosion 3.PNG"));
                explosion4 = ImageIO.read(new File("./Asteroids/img/Modern Explosion 4.PNG"));
                explosion5 = ImageIO.read(new File("./Asteroids/img/Modern Explosion 5.PNG"));

            }
            else if (artStyle == 'e') {
                shipImg1 = ImageIO.read(new File("./Asteroids/img/Classic Asteroid Ship.PNG"));
                shipImg2 = ImageIO.read(new File("./Asteroids/img/Asteroid Ship Thrusting.PNG"));
                shipImg3 = ImageIO.read(new File("./Asteroids/img/Asteroid Ship Medium Thrusting.PNG"));
                shipImg4 = ImageIO.read(new File("./Asteroids/img/Asteroid Ship Long Thrusting.PNG"));
                laserImg = ImageIO.read(new File("./Asteroids/img/Modern Laser.PNG"));
                bAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 1.PNG"));
                bAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 2.PNG"));
                bAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Big Asteroid 3.PNG"));
                mAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 1.PNG"));
                mAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 2.PNG"));
                mAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Medium Asteroid 3.PNG"));
                sAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 1.PNG"));
                sAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 2.PNG"));
                sAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Classic Small Asteroid 3.PNG"));
                teleport = ImageIO.read(new File("./Asteroids/img/Effects Teleport.PNG"));
                explosion1 = ImageIO.read(new File("./Asteroids/img/Explosion 1.PNG"));
                explosion2 = ImageIO.read(new File("./Asteroids/img/Explosion 2.PNG"));
                explosion3 = ImageIO.read(new File("./Asteroids/img/Explosion 3.PNG"));
                explosion4 = ImageIO.read(new File("./Asteroids/img/Explosion 4.PNG"));
                explosion5 = ImageIO.read(new File("./Asteroids/img/Explosion 5.PNG"));
            }
            if (gamemode == 'f') {
                laserImg = ImageIO.read(new File("./Asteroids/img/Flashlight Laser.PNG"));
                bAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Flashlight Big Asteroid 1.PNG"));
                bAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Flashlight Big Asteroid 1.PNG"));
                bAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Flashlight Big Asteroid 1.PNG"));
                mAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Flashlight Medium Asteroid 1.PNG"));
                mAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Flashlight Medium Asteroid 1.PNG"));
                mAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Flashlight Medium Asteroid 1.PNG"));
                sAsteroidImg1 = ImageIO.read(new File("./Asteroids/img/Flashlight Small Asteroid 1.PNG"));
                sAsteroidImg2 = ImageIO.read(new File("./Asteroids/img/Flashlight Small Asteroid 1.PNG"));
                sAsteroidImg3 = ImageIO.read(new File("./Asteroids/img/Flashlight Small Asteroid 1.PNG"));
                flashlight1 = ImageIO.read(new File("./Asteroids/img/Flashlight 1.PNG"));
                flashlight2 = ImageIO.read(new File("./Asteroids/img/Flashlight 2.PNG"));
                flashlight3 = ImageIO.read(new File("./Asteroids/img/Flashlight 3.PNG"));
            }
        } catch (IOException e) {
            System.out.println("File not found" + e);
        }
    }

    // starLocation takes the bound of the x/y field and returns an
    // array of star positions
    public int[] starLocation(int bound) {
        // create 30 star locations
        int stars = 0;
        int[] locations = new int[30];
        while (stars < 30) {
            // randomize star locations and make sure they aren't within 50 of the border
            locations[stars] = (int)(Math.random() * (bound - 100)) + 50;
            stars++;
        }
        return locations;
    }

    // move method is called by action performed
    // move method updates xPos, yPos, dx, and dy as needed
    public void move() {
        // update xPos and yPos by velocity of x and y
        xPos += dx;
        yPos += dy;
        // if ship coords are offscreen make ship appear of other end of the screen
        if (xPos > screenWidth + 15) {
            xPos -= screenWidth + 30;
        }
        else if (xPos < -15) {
            xPos += screenWidth + 30;
        }
        if (yPos > screenHeight + 15) {
            yPos -= screenHeight + 30;
        }
        else if (yPos < -15) {
            yPos += screenHeight + 30;
        }
        // slow ship velocity over time
        dx *= .999;
        dy *= .999;
        // cap velocity at a certain speed
        if (dx > 1.5) {
            dx = 1.5;
        }
        else if (dx < -1.5) {
            dx = -1.5;
        }
        if (dy > 1.5) {
            dy = 1.5;
        }
        else if (dy < -1.5) {
            dy = -1.5;
        }
        // continue turning if ship is turning
        if (isThrusting == 0) {
            if (isTurning == 1) {
                rotation -= 3.3 * scoreMultiplier;
            } else if (isTurning == 2) {
                rotation += 3.3 * scoreMultiplier;
            }
        }
        else {
            if (isTurning == 1) {
                rotation -= 2.1 * scoreMultiplier;
            } else if (isTurning == 2) {
                rotation += 2.1 * scoreMultiplier;
            }
        }
        if (isThrusting != 0) {
            numPress++;
            // if longThrusting set isThrusting to 2 or 3
            if (numPress > 90) {
                isThrusting = 3;
            }
            else if (numPress > 60) {
                isThrusting = 2;
            }
            else {
                isThrusting = 1;
            }

            // acceleration's value is equivalent to the time thrust
            // has been held down for, yet can't be greater than 1
            acceleration = (numPress / 100);
            if (acceleration > 1) {
                acceleration = 1;
            }
            else if (acceleration < 0.1) {
                acceleration = 0.1;
            }
            // decrease speed if turning
            if (isTurning != 0) {
                speedIncrease = 0.3;
                // increase velocity using trig, speedIncrease, and acceleration
                // so velocity is added in the correct direction and at the correct magnitude
                dx += speedIncrease * Math.cos(Math.toRadians(rotation - 90)) * acceleration;
                dy += speedIncrease * Math.sin(Math.toRadians(rotation - 90)) * acceleration;
            }
            else {
                speedIncrease = 0.6;
                // increase velocity using trig, speedIncrease, and acceleration
                // so velocity is added in the correct direction and at the correct magnitude
                dx += speedIncrease * Math.cos(Math.toRadians(rotation - 90)) * acceleration;
                dy += speedIncrease * Math.sin(Math.toRadians(rotation - 90)) * acceleration;
            }
        }

        // check if ship is colliding with any asteroids
        for (Double[] asteroid: asteroidInfo) {
            // check if the ship is in the bounds of the asteroid, on x and y
            // the bounds of asteroid change based on asteroid size
            if (asteroid[4] == 3) {
                if ((asteroid[0] < xPos + shipImg1.getWidth() && asteroid[0] + 50 > xPos)) {
                    if ((asteroid[1] < yPos + shipImg1.getHeight() && asteroid[1] + 50 > yPos)) {
                        explosionTick.add(new int[] {100, (int) xPos, (int) yPos});
                        lives -= 1;
                        xPos = -10000;
                        yPos = -10000;
                        // save the rotation at death to draw the explosion
                        if (artStyle == 'c') {
                            deathRotation = rotation;
                        }
                        // if lifes = 0, end game, else find a new safe spawn for ship
                        if (lives <= 0) {
                            running = false;
                        }
                        else {
                            // spawn to a safe place
                            tpSafe();
                            dy = 0;
                            dx = 0;
                        }
                    }
                }
            }
            // different size asteroids have different sized hitboxes
            else if (asteroid[4] == 2) {
                if ((asteroid[0] < xPos + shipImg1.getWidth() && asteroid[0] + 30 > xPos)) {
                    if ((asteroid[1] < yPos + shipImg1.getHeight() && asteroid[1] + 30 > yPos)) {
                        explosionTick.add(new int[] {100, (int) xPos, (int) yPos});
                        lives -= 1;
                        xPos = -10000;
                        yPos = -10000;
                        // save the rotation at death to draw the explosion
                        if (artStyle == 'c') {
                            deathRotation = rotation;
                        }
                        // if lifes = 0, end game, else find a new safe spawn for ship
                        if (lives <= 0) {
                            running = false;
                        }
                        else {
                            // spawn to a safe place
                            tpSafe();
                            dy = 0;
                            dx = 0;
                        }
                    }
                }
            }
            // different size asteroids have different sized hitboxes
            else {
                if ((asteroid[0] < xPos + shipImg1.getWidth() && asteroid[0] + 15 > xPos)) {
                    if ((asteroid[1] < yPos + shipImg1.getHeight() && asteroid[1] + 15 > yPos)) {
                        explosionTick.add(new int[] {100, (int) xPos, (int) yPos});
                        lives -= 1;
                        xPos = -10000;
                        yPos = -10000;
                        // save the rotation at death to draw the explosion
                        if (artStyle == 'c') {
                            deathRotation = rotation;
                        }
                        // if lifes = 0, end game, else find a new safe spawn for ship
                        if (lives <= 0) {
                            running = false;
                        }
                        else {
                            // spawn to a safe place
                            tpSafe();
                            dy = 0;
                            dx = 0;
                        }
                    }
                }
            }
            // check if asteroids collide with alien ship
            if (asteroid[4] == 3) {
                if ((asteroid[0] < alien[0] + 30 && asteroid[0] + 50 > alien[0])) {
                    if ((asteroid[1] < alien[1] + 30 && asteroid[1] + 50 > alien[1])) {
                        alien[0] = -1000;
                        alien[2] = 0;
                    }
                }
            }
            else if (asteroid[4] == 2) {
                if ((asteroid[0] < alien[0] + 30 && asteroid[0] + 30 > alien[0])) {
                    if ((asteroid[1] < alien[1] + 30 && asteroid[1] + 30 > alien[1])) {
                        alien[0] = -1000;
                        alien[2] = 0;
                    }
                }
            }
            else {
                if ((asteroid[0] < alien[0] + 30 && asteroid[0] + 15 > alien[0])) {
                    if ((asteroid[1] < alien[1] + 30 && asteroid[1] + 15 > alien[1])) {
                        alien[0] = -1000;
                        alien[2] = 0;
                    }
                }
            }

            // check if alien ship or laser hits player
            if ((alien[0] < xPos + shipImg1.getWidth() && alien[0] + 30 > xPos)) {
                if ((alien[1] < yPos + shipImg1.getHeight() && alien[1] + 30 > yPos)) {
                    alien[0] = -1000;
                    alien[2] = 0;
                    // blow up player
                    explosionTick.add(new int[] {100, (int) xPos, (int) yPos});
                    lives -= 1;
                    xPos = -10000;
                    yPos = -10000;
                    // save the rotation at death to draw the explosion
                    if (artStyle == 'c') {
                        deathRotation = rotation;
                    }
                    // if lifes = 0, end game, else find a new safe spawn for ship
                    if (lives <= 0) {
                        running = false;
                    }
                    else {
                        // spawn to a safe place
                        tpSafe();
                        dy = 0;
                        dx = 0;
                    }
                }
            }

            // check if alien ship or laser hits player
            if ((alienLaser[0] < xPos + shipImg1.getWidth() && alienLaser[0] + 3 > xPos)) {
                if ((alienLaser[1] < yPos + shipImg1.getHeight() && alienLaser[1] + 3 > yPos)) {
                    alienLaser[0] = -1000;
                    alienLaser[2] = 0;
                    alienLaser[3] = 0;
                    // blow up player
                    explosionTick.add(new int[]{100, (int) xPos, (int) yPos});
                    lives -= 1;
                    xPos = -10000;
                    yPos = -10000;
                    // save the rotation at death to draw the explosion
                    if (artStyle == 'c') {
                        deathRotation = rotation;
                    }
                    // if lifes = 0, end game, else find a new safe spawn for ship
                    if (lives <= 0) {
                        running = false;
                    } else {
                        // spawn to a safe place
                        tpSafe();
                        dy = 0;
                        dx = 0;
                    }
                }
            }
        }
    }

    // checkVarious checks lives, scores, asteroids, and more
    // and updates game accordingly
    public void checkVarious() {
        if (lives <= 0) {
            running = false;
        }
        if (trueScore >= 5000 && gamemode == 'u') {
            xPos = - 10000;
            pause = true;
            upgrade();
        }
        else if (trueScore > 10000 && gamemode != 'u') {
            lives++;
            trueScore -= 10000;
        }
        if (asteroidInfo.isEmpty()) {
            shownScore += 3000;
            trueScore += 3000;
            newAsteroidWave();
        }
        if (alien[0] < - 900 && gamemode != 'f') {
            if ((int)(Math.random() * 1000) == 0) {
                spawnAlien();
                System.out.println("Spawned");
            }
        }
    }

    // upgrade method provides a choice of upgrades to player
    // when a certain number of points is reached
    public void upgrade() {
        pause = true;
        UpgradeMenu upgrade = new UpgradeMenu(this);
    }

    // shootLaser method launches a projectile from the ship in the direction
    // the ship is facing
    public void shootLaser() {
        // initialize laser variables and
        // check if there are lasers to shoot
        if (lasers > 0) {
            // find rotation needed to shift a point from top left of image to top center
            // assign variables based on ships current position and rotation
            double laserXPos = xPos + shipImg1.getWidth() / 2.0;
            double laserYPos = yPos;
            double lsrdx = 0;
            double lsrdy = 0;
            double laserRotation = rotation;

            // use trig in order to get the exact direction to add velocity
            lsrdx = Math.cos(Math.toRadians(rotation - 90)) * 7;
            lsrdy = Math.sin(Math.toRadians(rotation - 90)) * 7;


            // add Laser to arraylist of laser information
            Double[] thisLaserInfo = { laserXPos, laserYPos, lsrdx, lsrdy, 0.0, laserRotation };
            laserInfo.add(thisLaserInfo);
            lasers--;
        }
    }

    // laserUpdate updates the position and distance of all lasers and deletes lasers
    // that have traveled their total distance
    public void laserUpdate() {
        if (laserInfo.size() > 0) {
            // change every laser's positions
            for (int laserNum = 0; laserNum < laserInfo.size(); laserNum++) {
                // store one laser value of laserInfo in laser
                Double[] laser = laserInfo.get(laserNum);

                // check if laser hits alien ship
                if ((laser[0] < alien[0] + 30 && laser[0] + 3 > alien[0])) {
                    if ((laser[1] < alien[1] + 30 && laser[1] + 3 > alien[1])) {
                        alien[0] = -1000;
                        alien[2] = 0;
                        laser[4] = 20000.0;
                        trueScore += 1000;
                        shownScore += 1000;
                    }
                }
                // increment laser position and distance by velocity and distance variable
                laser[0] += laser[2];
                laser[1] += laser[3];
                laser[4] += ((Math.abs(laser[1]) + Math.abs(laser[2]))) / laserDistance;

                // delete asteroid if it has been moved far off screen
                if (laser[0] < -900) {
                    laserInfo.remove(laserNum);
                } else {
                    // set laser position to opposite side if cross outer border
                    if (laser[0] > 1000) {
                        laser[0] = 0.0;
                        laser[1] = 500.0 - laser[1];
                        laser[4] *= 2;
                        if (laser[4] > 16000) {
                            laser[4] = 16000.0;
                        }
                    } else if (laser[0] < 0) {
                        laser[0] = 1000.0;
                        laser[1] = 500.0 - laser[1];
                        laser[4] *= 2;
                        if (laser[4] > 16000) {
                            laser[4] = 16000.0;
                        }
                    }
                    if (laser[1] > 500) {
                        laser[1] = 0.0;
                        laser[0] = 1000.0 - laser[0];
                        laser[4] *= 2;
                        if (laser[4] > 16000) {
                            laser[4] = 16000.0;
                        }
                    } else if (laser[1] < 0) {
                        laser[1] = 500.0;
                        laser[0] = 1000.0 - laser[0];
                        laser[4] *= 2;
                        if (laser[4] > 16000) {
                            laser[4] = 16000.0;
                        }
                    }
                    // check if laser should be deleted
                    if (laser[4] > 20000) {
                        laserInfo.remove(laserNum);
                        laserNum--;
                        lasers++;
                    }
                }
            }
        }
    }

    //    // create new wave of asteroids with information
    public void newAsteroidWave() {
        int max = 5;
        if (gamemode == 'f') {
            max = 4;
            asteroidSpeedMultiplier -= 0.3;
        }
        for (int i = 0; i < max; i++) {
            boolean uniqueSpawn = false;
            Double[] asteroids = new Double[5];
            // repeat until asteroid is initialized at a unique spawnpoint from other asteroids
            while (!uniqueSpawn) {
                uniqueSpawn = true;
                // create a random rotation
                Double rot = Math.random() * 361;
                // asteroid is created at a random position with a random velocity
                // and is initialized as a "big" asteroid (asteroids[4] = 2)
                asteroids = new Double[]{(Math.random() * 900) + 50, (Math.random() * 400) + 50, asteroidSpeedMultiplier, rot, 3.0, (Double) (double)(int)(Math.random() * 3)};

                // check if asteroid position is colliding with other asteroids or player
                if (asteroidInfo.size() > 0) {
                    for (Double[] asteroid : asteroidInfo) {
                        if (asteroids[0] + 50 >= asteroid[0] && asteroids[0] <= asteroid[0]) {
                            uniqueSpawn = false;
                        } else if (asteroids[1] + 50 >= asteroid[1] && asteroids[1] <= asteroid[1]) {
                            uniqueSpawn = false;
                        }
                    }
                }
                if (asteroids[0] + 150 >= xPos && asteroids[0] - 100 <= xPos) {
                    uniqueSpawn = false;
                }
                else if (asteroids[1] + 150 >= yPos && asteroids[1] - 100 <= yPos) {
                    uniqueSpawn = false;
                }
            }
            // add Double[] to the arraylist
            asteroidInfo.add(asteroids);
            asteroidSpeedMultiplier += 0.1;
        }
    }

    // newAsteroid takes input to append a new asteroid
    public void newAsteroid (Double x, Double y, Double velocity, Double rotation, Double size, Double img) {
        Double[] asteroid = { x, y, velocity, this.rotation, size, img };
        asteroidInfo.add(asteroid);
    }

    // asteroidUpdate moves asteroids according to velocity and checks for lasers
    public void asteroidUpdate() {
        // update all asteroids
        for (int asteroidNum = 0; asteroidNum < asteroidInfo.size(); asteroidNum++) {
            Double[] asteroid = asteroidInfo.get(asteroidNum);
            // check all lasers to see if they have collided
            if (!laserInfo.isEmpty()) {
                for (Double[] laser : laserInfo) {
                    // check if the ship is in the bounds of the asteroid, on x and y
                    // bounds of asteroid changes depending on asteroid size
                    if (asteroid[4] == 3) {
                        if ((asteroid[0] < laser[0] + laserImg.getWidth()) && (asteroid[0] + 50 > laser[0])) {
                            if ((asteroid[1] < laser[1] + laserImg.getHeight()) && (asteroid[1] + 50 > laser[1])) {
                                // delete laser and split asteroid (split asteroid has increased speed)
                                shownScore += (int) (300 * scoreMultiplier);
                                trueScore += (int) (300 * scoreMultiplier);
                                asteroid[4] -= 1.0;
                                laser[4] = 100000.0;
                                asteroid[2] *= 1.5;
                                asteroid[3] += 30;
                                newAsteroid(asteroid[0], asteroid[1], asteroid[2], asteroid[3] - 60, asteroid[4], (Double)(double)(int)(Math.random() * 3));
                            }
                        }
                    }
                    if (asteroid[4] == 2) {
                        if ((asteroid[0] < laser[0] + laserImg.getWidth()) && (asteroid[0] + 30 > laser[0])) {
                            if ((asteroid[1] < laser[1] + laserImg.getHeight()) && (asteroid[1] + 30 > laser[1])) {
                                // delete laser and split asteroid (split asteroid has increased speed)
                                shownScore += (int) (150 * scoreMultiplier);
                                trueScore += (int) (150 * scoreMultiplier);
                                laser[4] = 100000.0;
                                asteroid[4] -= 1.0;
                                asteroid[2] *= 1.5;
                                // change rotation for split asteroids
                                asteroid[3] += 45;
                                Double newRot = asteroid[3] - 90;
                                if (asteroid[3] > 360) {
                                    asteroid[3] -= 360;
                                }
                                else if (asteroid[3] < 0) {
                                    asteroid[3] += 360;
                                }
                                if (newRot > 360) {
                                    newRot += 360;
                                }
                                else if (newRot < 0) {
                                    newRot += 360;
                                }
                                newAsteroid(asteroid[0], asteroid[1], asteroid[2], newRot, asteroid[4], (Double)(double)(int)(Math.random() * 3));
                            }
                        }
                    }
                    else {
                        if ((asteroid[0] < laser[0] + laserImg.getWidth()) && (asteroid[0] + 15 > laser[0])) {
                            if ((asteroid[1] < laser[1] + laserImg.getHeight()) && (asteroid[1] + 15 > laser[1])) {
                                // delete asteroid and laser
                                shownScore += (int) (50 * scoreMultiplier);
                                trueScore += (int) (50 * scoreMultiplier);
                                asteroid[4] -= 1.0;
                                if (laserPierce < 1) {
                                    laser[4] = 100000.0;
                                }
                            }
                        }
                    }
                }
            }

            // move asteroid according to velocity and rotation
            asteroid[0] += asteroid[2] * Math.cos(Math.toRadians(asteroid[3]));
            asteroid[1] += asteroid[2] * Math.sin(Math.toRadians(asteroid[3]));

            // delete asteroid if the asteroid type has been set to -1
            if (asteroid[4] == 0) {
                asteroidInfo.remove(asteroidNum);
                asteroidNum--;
            }
            // break the asteroid apart depending
            else {
                // if asteroid moves off screen move it to other end of screen
                if (asteroid[0] > 1030) {
                    asteroid[0] -= 1060;
                }
                else if (asteroid[0] < -30) {
                    asteroid[0] += 1060;
                }
                if (asteroid[1] > 530) {
                    asteroid[1] -= 560;
                } else if (asteroid[1] < -30) {
                    asteroid[1] += 560;
                }
            }
        }
    }

    // call move and repaint whenever an action is performed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!pause) {
            if (running) {
                move();
                checkVarious();
                laserUpdate();
                asteroidUpdate();
                updateAlien();
            }
            repaint();
        }
    }

    public void tpSafe() {
        double tempx = 0;
        double tempy = 0;
        boolean uniqueSpawn = true;
        // repeat randomized x an y coords until find a safe spot to tp
        while (uniqueSpawn) {
            uniqueSpawn = false;
            tempx = Math.random() * 800 + 100;
            tempy = Math.random() * 300 + 100;
            for (Double[] a : asteroidInfo) {
                if ((a[0] - 50 < tempx+ shipImg1.getWidth() && a[0] + 100 > tempx)) {
                    if ((a[1] - 50 < tempy + shipImg1.getHeight() && a[1] + 100 > tempy)) {
                        uniqueSpawn = true;
                    }
                }
            }
        }
        xPos = tempx;
        yPos = tempy;
    }

    // spawn alien spawns a new alien in at a random position
    public void spawnAlien() {
        double alienY = ((Math.random() * 450) + 25);
        int alienX;
        int alienDirection;
        // aliens always spawn at the edge of the screen
        if ((int)(Math.random() * 2) == 0) {
            alienX = 0;
            alienDirection = 1;
        }
        else {
            alienX = 1000;
            alienDirection = -1;
        }
        alien = new double[] {alienX, alienY, alienDirection, 1};
    }

    // updateAlien moves alien and shoots alien laser
    public void updateAlien() {
        alien[0] += 0.7 * alien[2];
        // reset alien when it flies offscreen
        if ((alien[0] < -20 && alien[0] > -800) || alien[0] > 1020) {
            alien[0] = - 1000;
            alien[2] = 0;
        }
        // create a new laser when old laser is gone
        if (alien[3] == 1) {
            alien[3] = 0;
            alienLaser[0] = alien[0];
            alienLaser[1] = alien[1];
            alienLaser[2] = (xPos - alienLaser[0]) / 200;
            alienLaser[3] = (yPos - alienLaser[1]) / 200;
        }
        // move alien laser
        alienLaser[0] += alienLaser[2];
        alienLaser[1] += alienLaser[3];
        // delete laser when it flies far offscreen
        if (alienLaser[0] < -100 || alienLaser[0] > 1100 || alienLaser[1] < - 100 || alienLaser[1] > 1100) {
            alienLaser[0] = -1000;
            alien[3] = 1;
        }
    }

    // change velocity and rotation based on player input
    public class MyKeyAdapter extends KeyAdapter {
        // keyReleased checks for when thruster is released to reset acceleration
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                numPress = 0;
                isThrusting = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isTurning = 0;
            }
        }

        // keyPressed gets key input to move the space ship
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                // increase rotation on left key
                case KeyEvent.VK_LEFT -> {
                    // change isThrusting and isTurning to change ship appearence and status
                    isTurning = 1;
                    if (isThrusting > 0) {
                        isThrusting = 1;
                    }
                    // if KeyUp is still pressed, add reduced velocity while turning
                    if (isThrusting != 0) {
                        // cap acceleration in a turn
                        if (acceleration > 0.4) {
                            acceleration = 0.4;
                        }
                        // increase velocity using trig, speedIncrease, and acceleration
                        // so velocity is added in the correct direction and at the correct magnitude
                        dx += speedIncrease * Math.cos(Math.toRadians(rotation - 90)) * acceleration;
                        dy += speedIncrease * Math.sin(Math.toRadians(rotation - 90)) * acceleration;
                    }
                }

                // decrease rotation on right key
                case KeyEvent.VK_RIGHT -> {
                    // change isThrusting and isTurning to change ship appearence and status
                    isTurning = 2;
                    if (isThrusting > 0) {
                        isThrusting = 1;
                    }
                    if (rotation > 360) {
                        rotation -= 360;
                    }
                    // if KeyUp is still pressed, add reduced velocity while turning
                    if (isThrusting != 0) {
                        // cap acceleration in a turn
                        if (acceleration > 0.4) {
                            acceleration = 0.4;
                        }
                        // increase velocity using trig, speedIncrease, and acceleration
                        // so velocity is added in the correct direction and at the correct magnitude
                        dx += speedIncrease * Math.cos(Math.toRadians(rotation - 90)) * acceleration;
                        dy += speedIncrease * Math.sin(Math.toRadians(rotation - 90)) * acceleration;
                    }
                }

                //  thrust moves the ship forward at an increasing rate
                case KeyEvent.VK_UP -> {
                    isThrusting = 1;
                }

                // on space clicked call shootLaser class
                case KeyEvent.VK_SPACE -> {
                    shootLaser();
                    if (isThrusting != 0) {
                        // increase velocity using trig, speedIncrease, and acceleration
                        // so velocity is added in the correct direction and at the correct magnitude
                        dx += speedIncrease * Math.cos(Math.toRadians(rotation - 90)) * acceleration;
                        dy += speedIncrease * Math.sin(Math.toRadians(rotation - 90)) * acceleration;
                    }
                }

                // on s clicked teleport ship, with a 25% chance of losing a life
                case  KeyEvent.VK_S -> {
                    // teleport to safe place
                    teleportTick.add(new int[] {100, (int) xPos, (int) yPos});
                    tpSafe();
                    // 1/4 chance of losing a life
                    if ((int)(Math.random() * teleportDeathChance) == 0) {
                        lives--;
                    }
                }

                // if gamemode is flashlight game, use z, x, and c keys to control flashlight type
                case KeyEvent.VK_Z -> {
                    if (gamemode == 'f') {
                        flashlightType = 1;
                        System.out.println(flashlightType);
                    }
                }
                case KeyEvent.VK_X -> {
                    if (gamemode == 'f') {
                        flashlightType = 2;
                        System.out.println(flashlightType);
                    }
                }
                case KeyEvent.VK_C -> {
                    if (gamemode == 'f') {
                        flashlightType = 3;
                        System.out.println(flashlightType);
                    }
                }
                case KeyEvent.VK_P -> {
                    shownScore += 5000;
                    trueScore += 5000;
                }
            }
        }
    }
}