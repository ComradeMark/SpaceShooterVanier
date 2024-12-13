package edu.vanier.spaceshooter.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handles all noise operations
 * <p>
    Sound effects courtesy of Pixabay:
 https://pixabay.com/sound-effects/search/laser/

 * </p>
 */
public class soundController {

    private MediaPlayer playerShot;
    private MediaPlayer enemyShot;

    public soundController(){
        Media playerShotSound = new Media(getClass().getResource("/fxml/shooting.mp3").toString());
        playerShot = new MediaPlayer(playerShotSound);

        Media enemyShotSound = new Media(getClass().getResource("/fxml/enemyShooting.mp3").toString());
        enemyShot = new MediaPlayer(enemyShotSound);
    }

    public void playPlayerSound(){
        playerShot.stop();
        playerShot.play();
    }
    public void playEnemySound(){
        enemyShot.stop();
        enemyShot.play();
    }
}
