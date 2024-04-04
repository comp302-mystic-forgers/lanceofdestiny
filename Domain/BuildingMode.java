package Domain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
public class BuildingMode extends JFrame {
    // Initialize counts with the minimum required values
    private int simpleBarriersCount = 75;
    private int firmBarriersCount = 10;
    private int explosiveBarriersCount = 5;
    private int giftBarriersCount = 10;
    public BuildingMode() {
        setTitle("Lance of Destiny - Building Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        GameLayoutPanel layoutPanel = new GameLayoutPanel();
        layoutPanel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Editing Area for the Player");
        layoutPanel.setBorder(titledBorder);
        titledBorder.setTitleColor(Color.white);
        layoutPanel.setPreferredSize(new Dimension(650, 400));
        backgroundPanel.add(layoutPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 200));
        add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("Images/Player.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        bottomPanel.add(imageLabel);
        bottomPanel.add(imageLabel, BorderLayout.SOUTH);


        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            BuildingMode frame = new BuildingMode();
            frame.pack(); // Adjusts the frame to fit its components
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }

}
