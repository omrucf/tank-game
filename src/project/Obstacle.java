package src.project;
import java.awt.*;

public class Obstacle {
    private int x, y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image mountainImage = toolkit.getImage("imgs/mountain.png");
        g.drawImage(mountainImage, x, y, 200, 120, null);
    }
}