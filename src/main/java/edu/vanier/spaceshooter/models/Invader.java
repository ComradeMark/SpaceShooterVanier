package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Invader extends Sprite {
    String movementPattern;
    public int health;

    public Invader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, imagePath);
        this.health = health;


    }


    public abstract void takeDamage(int damage);
    public abstract void movementPattern();
}




