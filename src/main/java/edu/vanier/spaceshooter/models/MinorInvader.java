package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Vector;

public class MinorInvader extends Invader {
    double mvtDifficulty = 4;
    double mvtSpeed = 0.05;

    public MinorInvader(int x, int y, int width, int height, String type, Color color, int health, String imagePath) {
        super(x, y, width, height, type, color, health, imagePath);

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void movementPattern() {
        if(getTranslateY() <= 650){

            setTranslateX(getTranslateX() + Math.sin(getTranslateY() * mvtSpeed) * mvtDifficulty);
            setTranslateY(getTranslateY() + 2);

        }


        }



}
