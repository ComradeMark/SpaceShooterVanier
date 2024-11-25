package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Missile extends Sprite{

    public boolean canFire;
    public String missileType;
    public String missilesToFire;

    public Missile(int x, int y, int width, int height, String type, Color color, String imagePath) {
        super(x, y, width, height, type, color, imagePath);
    }



}
