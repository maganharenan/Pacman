package com.maganharenan.entities;

import com.maganharenan.main.Game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    private double speed = 0.6;
    private int frames = 0, maxFrames = 6, animationIndex = 0, maxAnimationIndex = 4;
    public int right_direction = 0, left_direction = 1, up_direction = 2, down_direction = 3;
    public int xDirection = left_direction;
    public int yDirection = down_direction;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite, double life, double maxLife) {
        super(x, y, width, height, null, life, maxLife, 1.6);
    }

}
