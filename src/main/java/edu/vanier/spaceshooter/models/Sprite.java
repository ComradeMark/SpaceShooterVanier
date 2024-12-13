package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {

    private boolean dead = false;
    private final String type;
    private int health;
    public int centreX;
    public int centreY;
    private double width;
    private double height;

    public Sprite(int x, int y, int width, int height, String type, Color color, String imagePath) {
        super();
        setFitWidth(width);
        setFitHeight(height);
        Image spriteImg = new Image(imagePath);
        this.health = health;
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        imageProperty().setValue(new Image(imagePath));
        centreX = x / 2;
        centreY = y / 2;

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
