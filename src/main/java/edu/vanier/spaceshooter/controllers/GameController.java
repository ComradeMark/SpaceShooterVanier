package edu.vanier.spaceshooter.controllers;

/**
Level difficulty manager

 */
public class GameController {
    public boolean allowMultipleFireModes = false;
    Integer numberOfInvaders;
    Integer allowedSpeed;

    //Entry 1: 1/0 for allowing fire modes, entry 2: number of minor invaders, entry 3: number of medium invaders, entry 4: boss invaders, entry 5: allowed speed
    public static int[] levelParameters = new int[6];

    public static int[] level1 = {0,10,0,0,3,1};
    public static int[] level2 = {0, 6, 6, 0, 3, 2};
    public static int[] level3 = {1, 10, 7, 1, 5,3};
    public static int[] level4 = {1,15, 10, 4, 7,4};


    public static void increaseLevel(){
        if(levelParameters == level1){
            levelParameters = level2;
        }
        else if(levelParameters == level2){
            levelParameters = level3;
        }
        else if(levelParameters == level3){
            levelParameters = level4;
        }
    }







}
