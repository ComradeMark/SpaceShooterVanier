package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Missile extends Sprite{
    public Missile(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);
    }
}
