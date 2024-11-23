package src.project;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class House {
    private int x, y, can_x, can_y;
    private Tank tank;
    private int health;
    private CopyOnWriteArrayList<Missile> missiles;
    private int aggrs;

    public House(int x, int y, Tank tank, int aggrs) {
        this.x = x;
        this.y = y;
        this.can_x = x + 60;
        this.can_y = y;
        this.tank = tank;
        this.health = 3;
        this.missiles = new CopyOnWriteArrayList<>();
        this.aggrs = aggrs;

        new Thread(() -> {
            while (health > 0) {
                try {
                    Thread.sleep(5000 - (this.aggrs * 350));
                    fireMissile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public void fireMissile() {
        int deltaX = tank.getX() - can_x;
        int deltaY = tank.getY() - can_y;
        double angle = Math.atan2(deltaY, deltaX);
        angle = angle * 180 / Math.PI;
        // System.out.println(angle);
        int direction;
        if (angle >= -22.5 && angle < 22.5) {
            direction = 2; // Right
        } else if (angle >= 22.5 && angle < 67.5) {
            direction = 3; // Down-Right
        } else if (angle >= 67.5 && angle < 112.5) {
            direction = 4; // Down
        } else if (angle >= 112.5 && angle < 157.5) {
            direction = 5; // Down-Left
        } else if (angle >= 157.5 || angle < -157.5) {
            direction = 6; // Left
        } else if (angle >= -157.5 && angle < -112.5) {
            direction = 7; // Up-Left
        } else if (angle >= -112.5 && angle < -67.5) {
            direction = 0; // Up
        } else {
            direction = 1; // Up-Right
        }
        missiles.add(new Missile(can_x + 60, can_y + 10, direction));
    }

    public void moveMissiles() {
        for (Missile missile : missiles) {
            if (missile.isActive()) {
                missile.move();
            } else {
                missiles.remove(missile);
            }
        }
    }

    public CopyOnWriteArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void takeDamage() {
        health--;
        System.out.println("House hit! Remaining health: " + health);
    }

    public int getHealth() {
        return health;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image houseImage = toolkit.getImage("imgs/house.png");
        Image cannonImage = toolkit.getImage("imgs/cannon.png");
        g.drawImage(houseImage, x, y, 150, 150, null);
        g.drawImage(cannonImage, can_x, can_y, 75, 60, null);

        for (Missile missile : missiles) {
            missile.draw(g);
        }
    }
}
