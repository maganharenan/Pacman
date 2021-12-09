package com.maganharenan.entities;

import com.maganharenan.main.Game;
import com.maganharenan.world.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity {
    public double x, y;
    private double speed;
    private int width, height;
    private int depth;
    private BufferedImage sprite;
    private int maskX, maskY, maskWidth, maskHeight;
    protected List<Node> path;
    private double life;
    private double maxLife;

    public Entity(int x, int y, int width, int height, BufferedImage sprite, double life, double maxLife, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.maskX = 0;
        this.maskY = 0;
        this.maskWidth = width;
        this.maskHeight = height;

        this.life = life;
        this.maxLife = maxLife;

        this.speed = speed;
    }

    public BufferedImage[] getSpritesArray(int amountOfSprites, int x, int y) {
        BufferedImage[] spritesArray = new BufferedImage[amountOfSprites];

        for (int index = 0; index < amountOfSprites; index++) {
            spritesArray[index] = Game.spritesheet.getSprite(x + (index * getWidth()), y, getWidth(), getHeight());
        }

        return spritesArray;
    }

    public int getX() {
        return (int)this.x;
    }

    public int getY() {
        return (int)this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLife(double life) { this.life = life; }

    public double getMaxLife() { return this.maxLife; }

    public double getSpeed() { return this.speed; }

    public void tick() {

    }

    public void render(Graphics graphics) {

    }
}
