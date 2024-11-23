package src.project;

import java.awt.*;
import java.util.ArrayList;

public class Tank {
    private int x, y;
    private int direction;
    private int health;
    private final int speed = 1;
    private ArrayList<Missile> missiles;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Image tankImage = toolkit.getImage("imgs/def_tank.png");
    private House h;
    private Obstacle o;
    private HealthBar hb;
    


    // Rotation cooldown in milliseconds
    private final long rotationCooldown = 150;
    private long lastRotationTime = 0;

    // Missle Cool Down
    private final long missileCooldown = 500;
    private long lastMissileTime = 0;

    public Tank(int x, int y, House h, Obstacle o, HealthBar hb) {
        this.x = x;
        this.y = y;
        this.direction = 6;
        this.health = 3;
        this.missiles = new ArrayList<>();
        this.h = h;
        this.o = o;
        this.hb = hb;

    }

    public void sethealthbar(HealthBar hb)
    {
        this.hb = hb;
    }

    public void moveForward(int maxWidth, int maxHeight) {

        int oldX = x;
        int oldY = y;
        switch (direction) {
            case 0 -> y = Math.max(0, y - speed);
            case 1 -> {
                x = Math.min(maxWidth, x + speed);
                y = Math.max(0, y - speed);
            }
            case 2 -> x = Math.min(maxWidth, x + speed);
            case 3 -> {
                x = Math.min(maxWidth, x + speed);
                y = Math.min(maxHeight, y + speed);
            }
            case 4 -> y = Math.min(maxHeight, y + speed);
            case 5 -> {
                x = Math.max(0, x - speed);
                y = Math.min(maxHeight, y + speed);
            }
            case 6 -> x = Math.max(0, x - speed);
            case 7 -> {
                x = Math.max(0, x - speed);
                y = Math.max(0, y - speed);
            }
        }

        if (h.getX() < x + 50 && h.getX() + 100 > x && h.getY() < y + 50 && h.getY() + 100 > y) {
            x = oldX;
            y = oldY;
        }
        if (o.getX() < x + 50 && o.getX() + 200 > x && o.getY() < y + 50 && o.getY() + 120 > y) {
            x = oldX;
            y = oldY;
        }
    }

    public void moveBackward(int maxWidth, int maxHeight) {
        int oldX = x;
        int oldY = y;

        switch (direction) {
            case 0 -> y = Math.min(maxHeight, y + speed);
            case 1 -> {
                x = Math.max(0, x - speed);
                y = Math.min(maxHeight, y + speed);
            }
            case 2 -> x = Math.max(0, x - speed);
            case 3 -> {
                x = Math.max(0, x - speed);
                y = Math.max(0, y - speed);
            }
            case 4 -> y = Math.max(0, y - speed);
            case 5 -> {
                x = Math.min(maxWidth, x + speed);
                y = Math.max(0, y - speed);
            }
            case 6 -> x = Math.min(maxWidth, x + speed);
            case 7 -> {
                x = Math.min(maxWidth, x + speed);
                y = Math.min(maxHeight, y + speed);
            }
        }
        if (h.getX() < x + 50 && h.getX() + 100 > x && h.getY() < y + 50 && h.getY() + 100 > y) {
            x = oldX;
            y = oldY;
        }
        if (o.getX() < x + 50 && o.getX() + 200 > x && o.getY() < y + 50 && o.getY() + 120 > y) {
            x = oldX;
            y = oldY;
        }
    }

    public void rotateLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRotationTime >= rotationCooldown) {
            direction = (direction + 7) % 8;
            lastRotationTime = currentTime;

            switch (direction) {
                case 0 -> tankImage = toolkit.getImage("imgs/up_tank.png");
                case 1 -> tankImage = toolkit.getImage("imgs/up_rt_tank.png");
                case 2 -> tankImage = toolkit.getImage("imgs/rt_tank.png");
                case 3 -> tankImage = toolkit.getImage("imgs/dn_rt_tank.png");
                case 4 -> tankImage = toolkit.getImage("imgs/down_tank.png");
                case 5 -> tankImage = toolkit.getImage("imgs/dn_lt_tank.png");
                case 6 -> tankImage = toolkit.getImage("imgs/def_tank.png");
                case 7 -> tankImage = toolkit.getImage("imgs/up_lt_tank.png");
            }
        }
    }

    public void rotateRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRotationTime >= rotationCooldown) {
            direction = (direction + 1) % 8;
            lastRotationTime = currentTime;

            switch (direction) {
                case 0 -> tankImage = toolkit.getImage("imgs/up_tank.png");
                case 1 -> tankImage = toolkit.getImage("imgs/up_rt_tank.png");
                case 2 -> tankImage = toolkit.getImage("imgs/rt_tank.png");
                case 3 -> tankImage = toolkit.getImage("imgs/dn_rt_tank.png");
                case 4 -> tankImage = toolkit.getImage("imgs/down_tank.png");
                case 5 -> tankImage = toolkit.getImage("imgs/dn_lt_tank.png");
                case 6 -> tankImage = toolkit.getImage("imgs/def_tank.png");
                case 7 -> tankImage = toolkit.getImage("imgs/up_lt_tank.png");
            }
        }
    }

    public void fireMissile() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMissileTime >= missileCooldown) {
            lastMissileTime = currentTime;

            Missile missile = new Missile(x + 20, y + 20, direction);
            missiles.add(missile);

            new Thread(() -> {
                while (missile.isActive()) {
                    missile.move();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void moveMissiles() {
        for (Missile missile : missiles) {
            if (missile.isActive()) {
                missile.move();
            }
        }
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void takeDamage() {
        health--;
        hb.setHealth(health);
        System.out.println("Tank hit! Remaining health: " + health);
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void draw(Graphics g) {
        g.drawImage(tankImage, x, y, 150, 150, null);
        for (Missile missile : missiles) {
            missile.draw(g);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }
}
