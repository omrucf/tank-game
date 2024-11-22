package src.project;

import javax.swing.*;

public class MainGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tank Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            JPanel startPanel = new JPanel();
            startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

            JLabel label1 = new JLabel("Number of mines:");
            JSlider slider1 = new JSlider(1, 10, 1);
            slider1.setMajorTickSpacing(1);
            slider1.setPaintTicks(true);
            slider1.setPaintLabels(true);

            JLabel label2 = new JLabel("Aggressiveness of cannon missiles:");
            JSlider slider2 = new JSlider(1, 10, 1);
            slider2.setMajorTickSpacing(1);
            slider2.setPaintTicks(true);
            slider2.setPaintLabels(true);
            JButton startButton = new JButton("Start Game");

            startButton.addActionListener(e -> {
                frame.remove(startPanel);
                frame.revalidate();
                frame.repaint();
                GamePanel gamePanel = new GamePanel(slider2.getValue(), slider1.getValue());
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
            });

            startPanel.add(label1);
            startPanel.add(slider1);
            startPanel.add(label2);
            startPanel.add(slider2);
            startPanel.add(startButton);



            frame.add(startPanel);

            // GamePanel gamePanel = new GamePanel();
            // frame.add(gamePanel);

            frame.setVisible(true);
        });
    }
}
