package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Invader extends Sprite {
    protected int xPos;
    protected int yPos;

    public int health;

    public Invader(int xPos, int yPos, int width, int height, String type, Color color, int health, String imagePath) {
        super(xPos, yPos, width, height, type, color, imagePath);
        this.health = health;
        this.xPos = xPos;
        this.yPos = yPos;



    }


    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public abstract void takeDamage(int damage);
    public abstract void movementPattern();
}




