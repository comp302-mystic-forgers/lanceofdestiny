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

        setupControlPanel();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 200));
        add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("Assets/Images/Player.png");
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
    private void setupControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Obstacles"));
        controlPanel.setOpaque(false);
        Dimension preferredSize = new Dimension(350, controlPanel.getHeight());
        controlPanel.setPreferredSize(preferredSize);
        controlPanel.setLayout(new GridLayout(4, 4, 10, 10));

        // Add each barrier's control to the control panel
        controlPanel.add(createBarrierControl("Simple Barriers", simpleBarriersCount, 1, "Assets/Images/200Bluegem.png"));
        controlPanel.add(createBarrierControl("Firm Barriers", firmBarriersCount, 2, "Assets/Images/200Firm.png"));
        controlPanel.add(createBarrierControl("Explosive Barriers", explosiveBarriersCount, 3, "Assets/Images/200Redgem.png"));
        controlPanel.add(createBarrierControl("Gift Barriers", giftBarriersCount, 4, "Assets/Images/200Greengem.png"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton saveButton = new JButton("Load");
        JButton playButton = new JButton("Play");
        buttonPanel.add(saveButton);
        buttonPanel.add(playButton);

        controlPanel.add(buttonPanel);

        saveButton.addActionListener(e -> {
            // Placeholder for save functionality
            System.out.println("Load functionality to be implemented");
        });

        playButton.addActionListener(e -> {
            // Placeholder for play functionality
            System.out.println("Play functionality to be implemented");
        });
        // Add control panel to the main frame
        add(controlPanel, BorderLayout.EAST);
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        for (Component comp : controlPanel.getComponents()) {
            ((JComponent) comp).setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
            comp.setMaximumSize(new Dimension(350, comp.getMaximumSize().height)); // Limit width of components
        }
        controlPanel.revalidate();
        controlPanel.repaint();

    }

    private JPanel createBarrierControl(String barrierName, int initialValue, int barrierType, String path) {
        JPanel barrierPanel = new JPanel(new FlowLayout());
        barrierPanel.setOpaque(false);
        GridBagConstraints gbc;

        JLabel barrierImage = new JLabel(new ImageIcon(path));
        barrierPanel.add(barrierImage);

        JLabel barrierLabel = new JLabel(barrierName);
        barrierLabel.setForeground(Color.WHITE);
        barrierPanel.add(barrierLabel);

        JButton plusButton = new JButton("+");
        plusButton.setOpaque(false);
        plusButton.setContentAreaFilled(false);
        barrierPanel.add(plusButton);

        JLabel numberOfBarriers = new JLabel(String.valueOf(initialValue));
        numberOfBarriers.setOpaque(false);
        numberOfBarriers.setForeground(Color.WHITE);
        barrierPanel.add(numberOfBarriers);

        JButton minusButton = new JButton("-");
        minusButton.setOpaque(false);
        minusButton.setContentAreaFilled(false);
        barrierPanel.add(minusButton);

        Dimension buttonSize = new Dimension(40, 20);
        plusButton.setPreferredSize(buttonSize);
        minusButton.setPreferredSize(buttonSize);

        return barrierPanel;
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
