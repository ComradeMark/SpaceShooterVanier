package edu.vanier.spaceshooter.models;

import edu.vanier.spaceshooter.controllers.GameController;
import javafx.scene.paint.Color;

import java.util.Random;

public class BossInvader extends Invader{

    private double amplitude;
    private double frequency;
    private double speed;
    private long startTime;
    boolean down;
    Random random = new Random();
    public BossInvader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);

        this.amplitude =random.nextInt(150) + 100;
        this.frequency = random.nextDouble(0, GameController.levelParameters[4]) * 0.5 + 0.7;
        this.speed = random.nextDouble(0, GameController.levelParameters[4]/4);
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
        setTranslateX((int) (400 + amplitude * Math.sin(2 * Math.PI * frequency * elapsedTime)));
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

