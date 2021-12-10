package com.maganharenan.world;

import com.maganharenan.entities.*;
import com.maganharenan.graphics.Spritesheet;
import com.maganharenan.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 16;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            tiles = new Tile[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            map.getRGB(0,0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int axisX = 0; axisX < map.getWidth(); axisX++) {
                for (int axisY = 0; axisY < map.getHeight(); axisY++) {
                    int currentPixel = pixels[axisX + (axisY * WIDTH)];
                        tiles[axisX + (axisY * WIDTH)] = new FloorTile(axisX * TILE_SIZE, axisY * TILE_SIZE, Tile.tile_floor);
                    if (currentPixel == 0xFF5B5B5B) {
                        tiles[axisX + (axisY * WIDTH)] = new FloorTile(axisX * TILE_SIZE, axisY * TILE_SIZE, Tile.tile_floor);
                    }
                    else if (currentPixel == 0xFFFFFFFF) {
                        tiles[axisX + (axisY * WIDTH)] = new WallTile(axisX * TILE_SIZE, axisY * TILE_SIZE, Tile.tile_wall);
                    }
                    else if (currentPixel == 0xFFf59947) {
                        Game.player.setX(axisX * TILE_SIZE);
                        Game.player.setY(axisY * TILE_SIZE);
                    }
                    else if (currentPixel == 0xFF4846B6) {
                        System.out.println("aiiiiii papai");
                        Enemy enemy = new Enemy(axisX * 16, axisY * 16,16,16, Entity.idleGhost, 1, 1);
                        Game.entities.add(enemy);
                        Game.enemies.add(enemy);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restartGame(String level) {
        Game.spritesheet = new Spritesheet("/spritesheet.png");
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.initEntities();
        Game.world = new World("/" + level);
        Game.player.setLife(Game.player.getMaxLife());
        return;
    }

    public static boolean pathIsFree(int nextX, int nextY) {
        int firstX = nextX / TILE_SIZE;
        int firstY = nextY / TILE_SIZE;

        int secondX = (nextX + TILE_SIZE - 1) / TILE_SIZE;
        int secondY = nextY / TILE_SIZE;

        int thirdX = nextX / TILE_SIZE;
        int thirdY = (nextY + TILE_SIZE - 1) / TILE_SIZE;

        int fourthX = (nextX + TILE_SIZE - 1) / TILE_SIZE;
        int fourthY = (nextY + TILE_SIZE - 1) / TILE_SIZE;

        boolean firstConditional = tiles[firstX + (firstY * World.WIDTH)] instanceof WallTile;
        boolean secondConditional = tiles[secondX + (secondY * World.WIDTH)] instanceof WallTile;
        boolean thirdConditional = tiles[thirdX + (thirdY * World.WIDTH)] instanceof WallTile;
        boolean fourthConditional = tiles[fourthX + (fourthY * World.WIDTH)] instanceof WallTile;

        return !(firstConditional || secondConditional || thirdConditional || fourthConditional);
    }

    public void render(Graphics graphics) {
        int startX = Camera.x / 24;
        int startY = Camera.y / 24;
        int finalX = startX + (Game.WIDTH / TILE_SIZE) + TILE_SIZE;
        int finalY = startY + (Game.HEIGHT / TILE_SIZE) + TILE_SIZE;

        for (int axisX = startX; axisX <= finalX; axisX++) {
            for (int axisY = startY; axisY <= finalY; axisY++) {
                if (axisX < 0 || axisY < 0 || axisX >= WIDTH || axisY >= HEIGHT) {
                    continue;
                }
                Tile tile = tiles[axisX + (axisY * WIDTH)];
                tile.render(graphics);
            }
        }
    }
}