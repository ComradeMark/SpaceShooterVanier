package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class of the MainApp UI.
 */
public class MainAppFXMLController {

    private final static Logger logger = LoggerFactory.getLogger(MainAppFXMLController.class);
    @FXML
    private Pane animationPanel;
    private double elapsedTime = 0;
    private Sprite spaceShip;
    private Scene mainScene;
    AnimationTimer gameLoop;
    ArrayList<String> input = new ArrayList<>();
    private long lastShot = 0; //Tracks time of last shot fired
    private static final int COOLDOWN = 150; //Adjustable cooldown to prevent spam

    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        spaceShip = new Spaceship(300, 750, 40, 40, "player", Color.BLUE, 20, "/icons/PNG/Sprites/Ships/spaceShips_001.png");
        animationPanel.setPrefSize(600, 800);
        animationPanel.getChildren().add(spaceShip);
    }

    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
        generateInvaders();
    }

    private void initGameLoop() {
        // Create the game loop.
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    /**
     * Sets up the key press event handler for the main scene.
     * <p>
     * This handler listens for specific key presses and executes corresponding
     * actions:
     * <ul>
     * <li>Pressing 'A' moves the spaceship to the left.</li>
     * <li>Pressing 'D' moves the spaceship to the right.</li>
     * <li>Pressing the SPACE key triggers the spaceship to shoot.</li>
     * </ul>
     * </p>
     */
    private void setupKeyPressHandlers() {
        // e the key event containing information about the key pressed.
        mainScene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        mainScene.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
            update();
        });



    }

    private void generateInvaders() {
        for (int i = 0; i < 5; i++) {
            MinorInvader invader = new MinorInvader(
                    90 + i * 100,
                    150, 30, 30, "enemy",
                    Color.RED, 20,"/icons/PNG/Sprites/Ships/spaceShips_009.png");
            animationPanel.getChildren().add(invader);
        }
    }

    /**
     * Retrieves a list of all sprites currently in the animation panel.
     * <p>
     * This method iterates through the children of the animation panel and
     * collects those that are instances of {@link Sprite} into a list.
     * </p>
     *
     * @return A list of {@link Sprite} objects found in the animation panel.
     */
    private List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Sprite sprite) {
                // We should add to the list any node that is a Sprite object.
                spriteList.add(sprite);
            }
        }
        return spriteList;
    }

    /**
     * Updates the game state for each frame.
     * <p>
     * This method increments the elapsed time and processes each sprite based
     * on its type. It handles the movement and collision detection for enemy
     * bullets and player bullets, as well as the shooting behavior for enemies.
     * Dead sprites are removed from the animation panel, and the elapsed time
     * is reset after a certain threshold.
     * </p>
     */
    private void update() {
        elapsedTime += 0.016;
        // Actions to be performed during each frame of the animation.
        getSprites().forEach(this::processSprite);
        removeDeadSprites();

        // game logic
        if (input.contains("LEFT") || input.contains("A")) {
            spaceShip.moveLeft();
        }
        if (input.contains("RIGHT") || input.contains("D")) {
            spaceShip.moveRight();
                }
        if (input.contains("UP") || input.contains("W")) {
            spaceShip.moveUp();
        }
        if (input.contains("DOWN") || input.contains("S")) {
            spaceShip.moveDown();
        }
        if(input.contains("SPACE")){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShot >= COOLDOWN) {
                if(!spaceShip.isDead()){
                    shoot(spaceShip);
                }
                lastShot = currentTime; // Update the last shoot time
            }
        }



        // Reset the elapsed time.
        if (elapsedTime > 2) {
            elapsedTime = 0;
        }

    }

    private void processSprite(Sprite sprite) {
        switch (sprite.getType()) {
            case "enemybullet" ->
                handleEnemyBullet(sprite);
            case "playerbullet" ->
                handlePlayerBullet(sprite);
            case "enemy" ->
                handleEnemyFiring(sprite);
        }
    }

    private void handleEnemyBullet(Sprite sprite) {
        sprite.moveDown();
        // Check for collision with the spaceship
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            spaceShip.setDead(true);
            sprite.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.moveUp();
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                // Check for collision with an enemy
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.setDead(true);
                    sprite.setDead(true);

                }
            }
        }
    }



    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            if (Math.random() < 0.3) {
                shoot(sprite);
            }
        }
    }

    /**
     * Removes all dead sprites from the animation panel.
     * <p>
     * This method iterates through the children of the animation panel and
     * removes any sprite that is marked as dead. It utilizes a lambda
     * expression to filter out the dead sprites efficiently.
     * </p>
     */
    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            Sprite sprite = (Sprite) n;
            return sprite.isDead();
        });
    }


    /**
     * Creates and adds a bullet sprite fired by the specified entity.
     * <p>
     * The firing entity can be either an enemy or the spaceship. A bullet is
     * created at the position of the firing entity with a slight offset to the
     * right. The bullet's dimensions are set, and it is given a type based on
     * the firing entity's type.
     * </p>
     *
     * @param firingEntity The entity that is firing the bullet, which can be
     * either an enemy or the spaceship.
     */
    private void shoot(Sprite firingEntity) {
        Timer coolDown = new Timer();
        // The firing entity can be either an enemy or the spaceship.
        Missile missile = new Missile(
                (int) (firingEntity.getTranslateX() + 45),
                (int) (firingEntity.getTranslateY() - 10),
                5, 20,
                firingEntity.getType() + "bullet", Color.BLACK, "/icons/PNG/Sprites/Missiles/spaceMissiles_004.png");
        animationPanel.getChildren().add(missile);

    }

    public void setScene(Scene scene) {
        mainScene = scene;
    }

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
