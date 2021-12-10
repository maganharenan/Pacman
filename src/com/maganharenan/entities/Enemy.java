package com.maganharenan.entities;

import com.maganharenan.main.Game;
import com.maganharenan.world.AStar;
import com.maganharenan.world.Camera;
import com.maganharenan.world.Vector2i;
import com.maganharenan.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity {

    private double speed = 1.2;
    public int right_direction = 0, left_direction = 1, up_direction = 2, down_direction = 3;
    public int direction = down_direction;

    private int frames = 0, maxFrames = 10, animationIndex = 0, maxAnimationIndex = 1;
    private BufferedImage[] movingLeft;
    private BufferedImage[] movingRight;
    private BufferedImage[] movingUp;
    private BufferedImage[] movingDown;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite, double life, double maxLife) {
        super(x, y, width, height, null, life, maxLife, 1.6);

        loadSprites();
    }

    private void loadSprites() {
        movingRight = this.getSpritesArray(2, 64, 0);
        movingLeft = this.getSpritesArray(2, 96, 0);
        movingUp = this.getSpritesArray(2, 128, 0);
        movingDown = this.getSpritesArray(2, 160, 0);
    }

    public void tick() {
        this.setDepth(0);

        if (path == null || path.size() == 0 || new Random().nextInt(100) < 30) {
            Vector2i start = new Vector2i((int)(this.x/ World.TILE_SIZE), (int)(this.y / World.TILE_SIZE));
            Vector2i end = new Vector2i((int)(Game.player.x / 16), (int)(Game.player.y / World.TILE_SIZE));

            path = AStar.findPath(Game.world, start, end);
        }

        if (new Random().nextInt(100) < 95) {
            followPath(path);
        }

        if (this.getLife() <= 0) {
            Game.enemies.remove(this);
            Game.entities.remove(this);
            return;
        }

        frames++;
        if (frames == maxFrames) {
            frames = 0;
            animationIndex++;
            if (animationIndex > maxAnimationIndex) {
                animationIndex = 0;
            }
        }
    }

    public void render(Graphics graphics) {
        switch (direction) {
            case 0: // right direction
                graphics.drawImage(movingRight[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case 1: // left direction
                graphics.drawImage(movingLeft[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case 2: // up direction
                graphics.drawImage(movingUp[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case 3: // down direction
                graphics.drawImage(movingDown[animationIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

}
