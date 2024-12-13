package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.controllers.GameController;
import javafx.scene.paint.Color;

import java.util.Random;
import edu.vanier.spaceshooter.controllers.GameController;

public class MediumInvader extends Invader{

    private double amplitude;
    private double frequency;
    private double speed;
    private long startTime;
    boolean down;
    Random random = new Random();

    public MediumInvader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);
        this.amplitude =random.nextInt(100) + 50;
        this.frequency = random.nextDouble() * 0.5 + 0.2;
        this.speed = random.nextDouble() * GameController.levelParameters[4]/2;
        this.startTime = System.currentTimeMillis();
        down = true;
    }

    @Override
    public void takeDamage(int damage) {
        this.health = health - damage;

    }

    @Override
    public void movementPattern() {
        double elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0;
        setTranslateX((int) (500 + amplitude * Math.cos(3 * Math.PI * frequency * elapsedTime)));
        setTranslateY(getTranslateY() +0.2);

        if(down){
            setTranslateY(getTranslateY() + speed);
            if( getTranslateY() >600){
                down = false;
            }
        }
        else {
            setTranslateY(getTranslateY() - speed/2);
            if (getTranslateY() < 0) {
                down = true;
            }
        }


    }
    }




