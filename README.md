# Asteroids

A retro-style remake of the arcade classic, written in Java with Swing. The ship flies on a small hand-written physics model, asteroids break apart when shot, and three game modes and three art styles change how each run plays and looks.

## Features

**Physics**
- Thrust builds acceleration the longer it's held, and that thrust is split into x/y velocity with trigonometry so the ship always accelerates in the direction it's facing.
- Momentum carries the ship after thrust stops, with light drag slowing it over time and a cap on top speed.
- Turning is slower while thrusting.
- The ship, lasers and asteroids wrap around the screen edges.

**Combat**
- Bounding-box collision detection between lasers, the ship and asteroids.
- Large asteroids split into two medium ones, and medium into two small, with each split moving faster and veering off at a new angle.
- An alien ship crosses the screen and fires at the player's current position.
- Teleport (`S`) jumps the ship to a spot clear of asteroids, with a 1 in 4 chance of exploding.
- An extra life every 10,000 points.

**Game modes**
- **Original**: classic Asteroids.
- **Flashlight**: the field is dark, and asteroids can only be seen inside flashlight beams you switch between with `Z`, `X` and `C`.
- **Upgrade**: the ship starts weaker, and every 5,000 points you choose from randomized upgrades (laser count, speed, range or piercing; ship speed, turn rate, score multiplier, safer teleport; or an extra life).

**Art styles**: Classic, Modern and Effects sprite sets, chosen from the Settings menu.

## Controls

| Key | Action |
| --- | --- |
| ← / → | Rotate |
| ↑ | Thrust |
| Space | Shoot |
| S | Teleport |
| Z / X / C | Switch flashlight (Flashlight mode) |

## Running it

Requires Java 14 or newer (the code uses switch expressions).

```bash
cd Asteroids
javac -d out Asteroids/src/*.java
java -cp out AsteroidsGame
```

Run from the outer `Asteroids/` folder, since images load from `./Asteroids/img/`. You can also open the project in IntelliJ IDEA and run `AsteroidsGame`.

## Project structure

```
Asteroids/Asteroids/
├── src/
│   ├── AsteroidsGame.java   # entry point
│   ├── GameFrame.java       # window setup
│   ├── GamePanel.java       # game loop, physics, collisions, rendering, input
│   ├── MainMenu.java        # start, settings and instructions menus
│   └── UpgradeMenu.java     # upgrade choices for Upgrade mode
└── img/                     # sprites for each art style
```

## Author

Jackson Varzali, May 2025
