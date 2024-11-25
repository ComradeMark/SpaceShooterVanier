package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class MediumInvader extends Invader{
    public MediumInvader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);
    }

    @Override
    public void takeDamage(int damage) {
        this.health = health - damage;

    }

    @Override
    public void movementPattern() {

    }
}
