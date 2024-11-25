package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Invader extends Sprite{
    public Invader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, imagePath);
        Image spriteImg = new Image(imagePath);


    }



}
