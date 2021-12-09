package com.maganharenan.world;

import com.maganharenan.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public static BufferedImage tile_floor = Game.spritesheet.getSprite(0,176, 24,24);
    public static BufferedImage tile_wall = Game.spritesheet.getSprite(16,176,24,24);

    private BufferedImage sprite;
    private int x, y;

    public Tile (int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}
