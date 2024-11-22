package src.project;
import java.awt.*;

public class Missile {
    private int x, y;
    private int direction;
    private int speed = 10;
    private boolean active = true;

    public Missile(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case 0 -> y -= speed;
            case 1 -> { x += speed; y -= speed; }
            case 2 -> x += speed;
            case 3 -> { x += speed; y += speed; }
            case 4 -> y += speed;
            case 5 -> { x -= speed; y += speed; }
            case 6 -> x -= speed;
            case 7 -> { x -= speed; y -= speed; }
        }

        if (x < 0 || y < 0 || x > 800 || y > 600) {
            active = false;
        }
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.RED);
            g.fillOval(x, y, 10, 10);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
