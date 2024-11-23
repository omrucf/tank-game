package src.project;

import javax.swing.*;
import java.awt.*;

public class MainGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tank Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            // Main Start Panel
            JPanel startPanel = new JPanel();
            startPanel.setLayout(new GridBagLayout());
            startPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            // Title Label
            JLabel titleLabel = new JLabel("Welcome to Tank Game!");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridwidth = 2;
            gbc.gridy = 0;
            startPanel.add(titleLabel, gbc);

            // Number of mines
            JLabel label1 = new JLabel("Number of Mines:");
            label1.setFont(new Font("Arial", Font.PLAIN, 16));
            gbc.gridwidth = 1;
            gbc.gridy = 1;
            startPanel.add(label1, gbc);

            JSlider slider1 = new JSlider(1, 10, 5);
            slider1.setMajorTickSpacing(1);
            slider1.setPaintTicks(true);
            slider1.setPaintLabels(true);
            gbc.gridx = 1;
            startPanel.add(slider1, gbc);

            // Aggressiveness of cannon missiles
            JLabel label2 = new JLabel("Aggressiveness of Cannon Missiles:");
            label2.setFont(new Font("Arial", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 2;
            startPanel.add(label2, gbc);

            JSlider slider2 = new JSlider(1, 10, 5);
            slider2.setMajorTickSpacing(1);
            slider2.setPaintTicks(true);
            slider2.setPaintLabels(true);
            gbc.gridx = 1;
            startPanel.add(slider2, gbc);

            // Start Button
            JButton startButton = new JButton("Start Game");
            startButton.setFont(new Font("Arial", Font.BOLD, 18));
            startButton.setBackground(Color.GREEN);
            startButton.setForeground(Color.BLACK);
            startButton.setFocusPainted(false);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            startPanel.add(startButton, gbc);

            // Button Action
            startButton.addActionListener(e -> {
                frame.remove(startPanel);
                frame.revalidate();
                frame.repaint();
                GamePanel gamePanel = new GamePanel(slider2.getValue(), slider1.getValue());
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
            });

            // Add Start Panel to Frame
            frame.add(startPanel);
            frame.setVisible(true);
        });
    }
}