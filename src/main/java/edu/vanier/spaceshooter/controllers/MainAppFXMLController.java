package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.models.*;

import java.awt.*;
import java.io.File;
import java.util.*;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * FXML Controller class of the MainApp UI.
 */
public class MainAppFXMLController {

    soundController sounds = new soundController();


    Random randomInt = new Random();
    Boolean acceptInput;

    private final static Logger logger = LoggerFactory.getLogger(MainAppFXMLController.class);
    @FXML
    private Pane animationPanel;
    private double elapsedTime = 0;
    private Sprite spaceShip;
    private Scene mainScene;
    Boolean burstAllowed = false;
    AnimationTimer gameLoop;
    int killedSprites;
    boolean isPaused;
ArrayList<String> input = new ArrayList<>();
    ArrayList<Invader> enemies = new ArrayList<>();
    GameController level = new GameController();
    Label levelMsg;
    Label killedEnemies;


    private long lastShot = 0; //Tracks time of last shot fired
    private static final int COOLDOWN = 150; //Adjustable cooldown to prevent spam

    @FXML
    public void initialize() {

        //Instantiates player's ship

        acceptInput = true;
        logger.info("Initializing MainAppController...");
        spaceShip = new Spaceship(300, 750, 40, 40, "player", Color.BLUE, 20, "/icons/PNG/Sprites/Ships/spaceShips_001.png");
        animationPanel.setPrefSize(600, 800);
        animationPanel.getChildren().add(spaceShip);
        setFirstLevelParameters();

        levelMsg = new Label();
        levelMsg.setText("Level " + GameController.levelParameters[5]);
        levelMsg.setStyle("-fx-text-fill: white; -fx-font-size: 25");
        levelMsg.setLayoutX(10);
        levelMsg.setLayoutY(15);
        killedEnemies = new Label();
        killedEnemies.setText("Enemies killed: " + killedSprites);
        killedEnemies.setStyle("-fx-text-fill: white; -fx-font-size: 25");
        killedEnemies.setLayoutX(10);
        killedEnemies.setLayoutY(35);

        animationPanel.getChildren().addAll(levelMsg, killedEnemies);

    }

    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
        generateInvaders();


    }
    //Initializes animation
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
//Sets first level parameters (difficulty)
    private void setFirstLevelParameters() {
        GameController.levelParameters = GameController.level1;
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
//  Initializes button handlers for gameplay
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

    /**
     * Generates invaders for the game by using LevelParameters from GameController class to set numbers, difficulty, etc
     */
    private void generateInvaders() {


        for (int i = 0; i < GameController.levelParameters[1]; i++) {
            MinorInvader invaderS = new MinorInvader(randomInt.nextInt(1000),
                    randomInt.nextInt(0, 150), 32, 32, "enemy",
                    Color.RED, 20,"/icons/PNG/Sprites/Ships/spaceShips_009.png");
            animationPanel.getChildren().add(invaderS);
            enemies.add(invaderS);
        }
        for (int i = 0; i < GameController.levelParameters[2]; i++) {
            MediumInvader invaderM = new MediumInvader(randomInt.nextInt(1000),
                    randomInt.nextInt(50, 200), 45, 45, "enemy",
                    Color.RED, 40,"/icons/PNG/Sprites/Ships/spaceShips_003.png");
            animationPanel.getChildren().add(invaderM);
            enemies.add(invaderM);
        }
        for (int i = 0; i < GameController.levelParameters[3]; i++) {
            BossInvader invaderL = new BossInvader(randomInt.nextInt(1000),
                    randomInt.nextInt(0, 150), 55, 55, "enemy",
                    Color.RED, 75,"/icons/PNG/Sprites/Ships/spaceShips_005.png");
            animationPanel.getChildren().add(invaderL);
            enemies.add(invaderL);
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
        for (Invader invader : enemies){

            invader.movementPattern();

        }

        // game logic
        if(acceptInput) {
            if ((input.contains("LEFT") || input.contains("A"))) {
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

            if (input.contains("SPACE")) {

                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShot >= COOLDOWN) {
                        if (!spaceShip.isDead()) {
                            shoot(spaceShip);
                            sounds.playPlayerSound();
                        }
                        lastShot = currentTime; // Update the last shoot time
                    }
                }

                }


            if (spaceShip.isDead()) {
                System.out.println("dead");
                announceGameOver();
            }
            //For debugging purposes
            if (input.contains("L")) {
                killedSprites = 0;

                GameController.increaseLevel();
                newLevel();
            }
            if (killedSprites == (GameController.levelParameters[1] + GameController.levelParameters[2] + GameController.levelParameters[3])) {
                System.out.println("INCREASING LEVEL");
                GameController.increaseLevel();
                killedSprites = 0;

                newLevel();
            }



        // Reset the elapsed time.
        if (elapsedTime > 2) {
            elapsedTime = 0;
        }
        killedEnemies.setText("Enemies Killed: " + killedSprites);

    }


    /**
     * Displays game over screen, offers ability to restart. Also deletes all objects from screen.
     */
    private void announceGameOver() {
        acceptInput = false;
        gameLoop.stop();

        Label gameOverText = new Label("Game Over");
         gameOverText.setLayoutX(400);
         gameOverText.setLayoutY(300);
         gameOverText.setStyle("--fx-font-size: 170px; -fx-text-fill: red");
         Button restartBtn = new javafx.scene.control.Button("Restart");
         restartBtn.setLayoutX(400);
         restartBtn.setLayoutY(300+30);
        restartBtn.setVisible(true);
        gameOverText.setVisible(true);
        killedSprites=0;
        killedEnemies.setText("Sprites killed: " + killedSprites);

        animationPanel.getChildren().remove(levelMsg);
       animationPanel.getChildren().addAll(restartBtn, gameOverText);
        restartBtn.setOnAction(event -> {
            restartBtn.setVisible(false);
            gameOverText.setVisible(false);
            animationPanel.getChildren().removeAll(restartBtn, gameOverText);

            restartGame();

        });
    }
    /**
     Reinitializes game through deletion of already existing sprites (incl. bullets)
     */
    private void restartGame() {
        animationPanel.getChildren().removeAll(enemies);
        acceptInput = true;
        //Remove existing bullets
        List<Missile> bullets = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Missile missile) {
                // We should add to the list any node that is a missile object.
                bullets.add(missile);
            }
        }
        animationPanel.getChildren().removeAll(bullets);
        setFirstLevelParameters();
        enemies.clear();
        killedSprites = 0;
        spaceShip = new Spaceship(300, 750, 40, 40, "player", Color.BLUE, 20, "/icons/PNG/Sprites/Ships/spaceShips_001.png");
        animationPanel.setPrefSize(600, 800);
        animationPanel.getChildren().add(spaceShip);

       setupGameWorld();

    }
    /**
     *Handles increasing level difficulty from GameController class. Displays current level.
     * </p>
     */
    private void newLevel() {

        gameLoop.stop();
        Label levelLabel = new Label("Level " + GameController.levelParameters[5]);
        levelLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-font-weight: bold;");
        levelLabel.setLayoutX(200);
        levelLabel.setLayoutY(200);

        animationPanel.getChildren().add(levelLabel);
        FadeTransition fade = new FadeTransition(Duration.seconds(2), levelLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> {
            animationPanel.getChildren().remove(levelLabel);
            setupGameWorld();

            gameLoop.start();

        });
        fade.play();

        levelMsg.setText("Level: " + GameController.levelParameters[5]);

        //Remove existing bullets
        List<Missile> bullets = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Missile missile) {
                // We should add to the list any node that is a missile object.
                bullets.add(missile);
            }
        }
        animationPanel.getChildren().removeAll(bullets);
        killedSprites = 0;

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
                    killedSprites += 1;
                    System.out.println(killedSprites);

                }
            }
        }
    }



    private void handleEnemyFiring(Sprite sprite) {
        if (elapsedTime > 2) {
            if (Math.random() < 0.3) {
                if(!sprite.isDead()){
                shoot(sprite);
                sounds.playEnemySound();}
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
        List<Sprite> spritesToRemove = getSprites();
        for(int i = 0; i<spritesToRemove.size(); i++){
            if(spritesToRemove.get(i).isDead()) {

                animationPanel.getChildren().remove(spritesToRemove.get(i));
            };


        }

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
        if(!firingEntity.isDead()) {
            Missile missile = new Missile(
                    (int) (firingEntity.getTranslateX() + firingEntity.getFitWidth() / 2),
                    (int) (firingEntity.getTranslateY() - 10),
                    5, 20,
                    firingEntity.getType() + "bullet", Color.BLACK, "/icons/PNG/Sprites/Missiles/spaceMissiles_004.png");


            animationPanel.getChildren().add(missile);

        }
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
/**
 * Background image courtesy of NASA
 */