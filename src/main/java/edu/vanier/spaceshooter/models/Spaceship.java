package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

public class Spaceship extends Sprite{

    public Spaceship(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, imagePath);
    setFitWidth(65);
    setFitHeight(65);
    }
    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
        if (getTranslateX() <= 0){
            setTranslateX(0+1);
        }
        else if(getTranslateX() >= 999){
            setTranslateX(1000-1);
        }

    }

    public void moveRight() {
        if (getTranslateX() <= 0){
            setTranslateX(0+1);
        }
        else if (getTranslateX() >= 1000-getFitWidth()){
            setTranslateX(1000-getFitWidth());
        }
        else {
            setTranslateX(getTranslateX() + 5);
        }
    }


    public void moveUp() {

        if (getTranslateY() <= 0){
            setTranslateY(0+1);
        }

        else {
            setTranslateY(getTranslateY() - 5);
        }
    }

    public void moveDown() {
        if (getTranslateY() <= 0){
            setTranslateY(0+1);
        }
        else if (getTranslateY() >= 1000-getFitHeight()){
            setTranslateY(1000-getFitHeight());
        }
        else {
            setTranslateY(getTranslateY() + 5);
        }
    }


}
