package src.project;

import java.awt.*;

public class Mine {
    private int x, y;

    public Mine(int x, int y) {
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
        Image mineImage = toolkit.getImage("imgs/bomb.png"); // Path to mine image
        g.drawImage(mineImage, x, y, 70, 70, null); // Adjust size and position
    }
}
