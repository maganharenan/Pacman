package com.maganharenan.entities;

import com.maganharenan.main.Game;
import com.maganharenan.world.Node;
import com.maganharenan.world.Vector2i;
import com.maganharenan.world.World;

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

    public static BufferedImage idleGhost = Game.spritesheet.getSprite(96, 0, 16, 16);

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

    public void setDepth(int depth) { this.depth = depth; }

    public double getLife() { return this.life; }

    public double getMaxLife() { return this.maxLife; }

    public double getSpeed() { return this.speed; }

    public void tick() {

    }

    public void render(Graphics graphics) {

    }

    public void  followPath(List<Node> path) {
        if (path != null) {
            if (path.size() > 0) {
                Vector2i target = path.get(path.size() - 1).tile;

                if (x < target.x * World.TILE_SIZE && World.pathIsFree((int) (x + speed), this.getY()) && !isColliding((int) (x + 1), this.getY())) {
                    x++;
                }
                else if (x > target.x * World.TILE_SIZE && World.pathIsFree((int) (x - speed), this.getY()) && !isColliding((int) (x - 1), this.getY())) {
                    x--;
                }

                if (y < target.y * World.TILE_SIZE && World.pathIsFree(this.getX(), (int) (y + speed)) && !isColliding(this.getX(), (int) (y + 1))) {
                    y++;
                }
                else if (y > target.y * World.TILE_SIZE && World.pathIsFree(this.getX(), (int) (y - speed)) && !isColliding(this.getX(), (int) (y - 1))) {
                    y--;
                }

                if (x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE) {
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    public boolean isColliding(int nextX, int nextY) {
        Rectangle currentEnemy = new Rectangle(nextX, nextY, World.TILE_SIZE, World.TILE_SIZE);

        for (int index = 0; index < Game.enemies.size(); index++) {
            Enemy enemy = Game.enemies.get(index);
            if (enemy == this) {
                continue;
            } else {
                Rectangle targetEnemy = new Rectangle(enemy.getX(), enemy.getY(), World.TILE_SIZE, World.TILE_SIZE);
                if (currentEnemy.intersects(targetEnemy)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isColliding(Entity firstEntity, Entity secondEntity) {
        Rectangle firstEntityMask = new Rectangle(
                firstEntity.getX() + firstEntity.maskX,
                firstEntity.getY() + firstEntity.maskY,
                firstEntity.maskWidth,
                firstEntity.maskHeight);
        Rectangle secondEntityMask = new Rectangle(
                secondEntity.getX() + secondEntity.maskX,
                secondEntity.getY() + secondEntity.maskY,
                secondEntity.maskWidth,
                secondEntity.maskHeight);

        if (firstEntityMask.intersects(secondEntityMask)) {
            return true;
        }

        return false;
    }
}
