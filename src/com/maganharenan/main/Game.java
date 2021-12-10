package com.maganharenan.main;

import com.maganharenan.entities.Enemy;
import com.maganharenan.entities.Entity;
import com.maganharenan.entities.Player;
import com.maganharenan.graphics.Spritesheet;
import com.maganharenan.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {
    private static JFrame frame;
    private Thread thread;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    protected static final int SCALE = 3;
    private boolean isRunningApplication = true;
    private BufferedImage image;

    public static Spritesheet spritesheet;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static World world;
    public static Player player;


    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.initFrame();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("/spritesheet.png");
        initEntities();
    }

    public static void initEntities() {
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        player = new Player(0,0,16,16, spritesheet.getSprite(0,72,16,16), 100, 100);
        entities.add(player);
        world = new World("/level1.png");
    }

    private void initFrame() {
        frame = new JFrame("Pac-Man");
        frame.add(this);
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunningApplication = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunningApplication = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void tick() {
        for (int index = 0; index < entities.size(); index++) {
            Entity entity = entities.get(index);
            entity.tick();
        }
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = image.getGraphics();
        graphics.setColor(new Color(0,0,0));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(graphics);

        for (int index = 0; index < entities.size(); index++) {
            Entity entity = entities.get(index);
            entity.render(graphics);
        }

        graphics.dispose();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image,0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bufferStrategy.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunningApplication) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            player.left = true;
        }
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            player.right = true;
        }
        else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            player.up = true;
        }
        else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            player.left = false;
        }
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            player.right = false;
        }
        else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            player.up = false;
        }
        else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            player.down = false;
        }
    }
}
