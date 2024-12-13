package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.controllers.GameController;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Vector;
public class MinorInvader extends Invader {
    Random randDir = new Random();
    private int directionX;
    private int directionY;
    double mvtDifficulty = 4;
    double mvtSpeed = 0.05;

    public MinorInvader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void movementPattern() {


             this.directionX = randDir.nextInt(0, GameController.levelParameters[4])+1;
             this.directionY = randDir.nextInt(0, GameController.levelParameters[4])+1;
        if(randDir.nextBoolean()) directionX = -1 * directionX;
        if(randDir.nextBoolean()) directionY = -1 * directionY;
        if(getTranslateY()<=0 || getTranslateY() >= 800){
            setTranslateY(0);

        }
        if(getTranslateX()<=0 || getTranslateX() >= 800){
            setTranslateX(5);
            directionX = -1 *directionX;
        }


        setTranslateX(getTranslateX() + Math.sin(getTranslateX() * directionX/2) * 0.2*directionX);
            setTranslateY(getTranslateY() + directionY/3);



        }





}
