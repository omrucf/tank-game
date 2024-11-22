package src.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private Tank tank;
    private House house;
    private HealthBar hb;
    private ArrayList<Mine> mines;
    private boolean gameRunning = true;
    private String gameOverMessage = "";
    private Obstacle mountain;
    private int aggrs, mines_c;

    private final Set<Integer> activeKeys = new HashSet<>();

    public GamePanel(int aggrs, int mines_c) {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.aggrs = aggrs;
        this.mines_c = mines_c;

        initializeGame();

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        new Thread(this::runGameLoop).start();
    }

    private void initializeGame() {

        house = new House(100, 300, null, aggrs);
        mountain = new Obstacle(600, 100);

        tank = new Tank(600, 400, house, mountain, null);

        hb = new HealthBar(tank.getHealth());

        tank.sethealthbar(hb);

        house.setTank(tank);

        mines = new ArrayList<>();
        // mines.add(new Mine(300, 200));
        // mines.add(new Mine(400, 150));
        // mines.add(new Mine(150, 100));

        for (int i = 0; i < mines_c; i++) {
            if (Math.random() > 0.5) {
                mines.add(new Mine((int) (Math.random() * 400), (int) (Math.random() * 250)));
            } else {
                mines.add(new Mine((int) (Math.random() * 400 + 300), (int) (Math.random() * 250 + 100)));
            }

        }
    }

    private void runGameLoop() {
        while (gameRunning) {
            processActiveKeys(); // Continuously process active keys for movement/rotation
            tank.moveMissiles();
            house.moveMissiles();
            handleCollisions();

            repaint();

            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processActiveKeys() {
        for (int keyCode : activeKeys) {
            switch (keyCode) {
                case KeyEvent.VK_UP -> tank.moveForward(700, 500);
                case KeyEvent.VK_DOWN -> tank.moveBackward(700, 500);
                case KeyEvent.VK_LEFT -> tank.rotateLeft();
                case KeyEvent.VK_RIGHT -> tank.rotateRight();
            }
        }
    }

    private void handleCollisions() {
        for (Missile missile : tank.getMissiles()) {
            if (missile.isActive()
                    && checkCollision(missile.getX(), missile.getY(), house.getX(), house.getY() + 70, 10, 50)) {
                missile.deactivate();
                house.takeDamage();
                if (house.getHealth() <= 0) {
                    endGame("You Win!");
                }
            }
        }

        for (Missile missile : house.getMissiles()) {
            if (missile.isActive()
                    && checkCollision(missile.getX(), missile.getY(), tank.getX(), tank.getY() + 40, 10, 30)) {
                missile.deactivate();
                tank.takeDamage();
                if (tank.getHealth() <= 0) {
                    endGame("Game Over!");
                }
            }
        }

        for (Mine mine : mines) {
            if (checkCollision(tank.getX(), tank.getY(), mine.getX(), mine.getY(), 20, 15)) {
                endGame("Game Over! You hit a mine.");
            }
        }

        // if (checkCollision(tank.getX(), tank.getY(), mountain.getX(),
        // mountain.getY(), 20, 50)) {
        // endGame("Game Over! You hit an obstacle.");
        // }
    }

    private boolean checkCollision(int x1, int y1, int x2, int y2, int radius1, int radius2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy) < (radius1 + radius2);
    }

    private void endGame(String message) {
        gameRunning = false;
        gameOverMessage = message;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameRunning) {
            setBackground(Color.WHITE);
            tank.draw(g);
            house.draw(g);
            for (Mine mine : mines) {
                mine.draw(g);
            }
            mountain.draw(g);
            hb.setBounds(10, 10, 200, 30); // Position the health bar
            hb.paint(g); // Render the health bar
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.BLACK);
            g.drawString(gameOverMessage, getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameRunning) {
            int keyCode = e.getKeyCode();
            if (!activeKeys.contains(keyCode)) {
                activeKeys.add(keyCode);
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                new Thread(tank::fireMissile).start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        activeKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for key typed
    }

}
