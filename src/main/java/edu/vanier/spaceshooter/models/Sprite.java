package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

    private boolean dead = false;
    private final String type;
    private int health;


    public Sprite(String imagePath, int x, int y, int width, int height, String type, Color color, int health) {
        super(width, height, color);
        Image spriteImg = new Image(imagePath);
        this.health = health;
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }

    public boolean isDead() {
        return dead;
    }

    public String getType() {
        return type;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
