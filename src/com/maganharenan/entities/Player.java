package com.maganharenan.entities;

import com.maganharenan.main.Game;
import com.maganharenan.world.Camera;
import com.maganharenan.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private boolean moved;
    public boolean right, left, up, down;
    public int right_direction = 0, left_direction = 1, up_direction = 2, down_direction = 3;
    public int direction = right_direction;

    private int frames = 0, maxFrames = 5, animationIndex = 0, maxAnimationIndex = 3;
    private BufferedImage idleRight;
    private BufferedImage idleLeft;
    private BufferedImage[] movingLeft;
    private BufferedImage[] movingRight;

    public Player(int x, int y, int width, int height, BufferedImage sprite, double life, double maxLife) {
        super(x, y, width, height, sprite, life, maxLife, 2);

        loadSprites();
    }

    private void loadSprites() {
        idleRight = Game.spritesheet.getSprite(0,0,getWidth(),getHeight());
        idleLeft = Game.spritesheet.getSprite(0, 16, getWidth(), getHeight());
        movingRight = this.getSpritesArray(4, 0, 0);
        movingLeft = this.getSpritesArray(4, 0, 16);
    }

    public void tick() {
        moved = false;
        if (right && World.pathIsFree((int)(this.getX() + this.getSpeed()), this.getY())) {
            moved = true;
            setX(this.getX() + this.getSpeed());
            direction = right_direction;
        }
        else if (left && World.pathIsFree((int)(this.getX() - this.getSpeed()), this.getY())) {
            moved = true;
            setX(this.getX() - this.getSpeed());
            direction = left_direction;
        }

        if (up && World.pathIsFree(this.getX(), (int)(this.getY() - this.getSpeed()))) {
            moved = true;
            setY(this.getY() - this.getSpeed());
            direction = up_direction;
        }
        else if (down && World.pathIsFree(this.getX(), (int)(this.getY() + this.getSpeed()))) {
            moved = true;
            setY(this.getY() + this.getSpeed());
            direction = down_direction;
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                animationIndex++;
                if (animationIndex > maxAnimationIndex) {
                    animationIndex = 0;
                }
            }
        }
    }

    public void render(Graphics graphics) {
        if (moved) {
            if (right) {
                graphics.drawImage(movingRight[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
            } else if (left) {
                graphics.drawImage(movingLeft[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
            }
        } else {
            switch (direction) {
                case 0: // right direction
                    graphics.drawImage(idleRight, this.getX() - Camera.x, this.getY() - Camera.y, null);
                    break;
                case 1: // left direction
                    graphics.drawImage(idleLeft, this.getX() - Camera.x, this.getY() - Camera.y, null);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
            
        }
    }
}
