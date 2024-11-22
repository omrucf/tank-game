package src.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class HealthBar extends JPanel {
    private int health;
    private int maxHealth;

    public HealthBar(int initialHealth) {
        this(initialHealth, 3); // Default max health is 3
    }

    public HealthBar(int initialHealth, int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth); // Ensure maxHealth is at least 1
        this.health = Math.max(0, Math.min(initialHealth, maxHealth)); // Clamp health to valid range
        setPreferredSize(new Dimension(200, 30));
        setBackground(Color.BLACK);
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth)); // Clamp health to valid range
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate bar width based on health percentage
        int barWidth = (int) ((double) health / maxHealth * getWidth());

        // Determine health color based on current health
        Color healthColor;
        if (health == 3) {
            healthColor = Color.GREEN;
        } else if (health == 2) {
            healthColor = Color.YELLOW;
        } else if (health == 1) {
            healthColor = Color.ORANGE;
        } else {
            healthColor = Color.RED;
        }

        // Draw health bar
        g.setColor(healthColor);
        g.fillRect(0, 0, barWidth, getHeight());

        // Draw outline
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
