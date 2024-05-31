package Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiHostScreen extends JFrame {
    private JLabel ipLabel;
    private JLabel ipValueLabel;
    private JLabel portLabel;
    private JLabel portValueLabel;
    private JLabel statusLabel;
    private JLabel statusValueLabel;
    private TCPServer tcpServer;
    private Timer countdownTimer;
    private int countdown = 3;
    private JLabel countdownLabel;
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";
    private BuildingModeController buildingModeController;

    public MultiHostScreen(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        setTitle("Host Game");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        setContentPane(new BackgroundPanel(backgroundImagePath));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            ipValueLabel = new JLabel(ipAddress);
            ipValueLabel.setForeground(Color.WHITE);
        } catch (UnknownHostException e) {
            ipLabel = new JLabel("IP address: Not available");
            e.printStackTrace();
        }

        tcpServer = new TCPServer(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ipLabel = new JLabel("IP address: ");
        ipLabel.setForeground(Color.WHITE);
        portLabel = new JLabel("Port: ");
        portLabel.setForeground(Color.WHITE);
        portValueLabel = new JLabel(String.valueOf(tcpServer.getPort()));
        portValueLabel.setForeground(Color.WHITE);
        statusLabel = new JLabel("Status: ");
        statusLabel.setForeground(Color.WHITE);
        statusValueLabel = new JLabel("Waiting for connection...");
        statusValueLabel.setForeground(Color.WHITE);
        countdownLabel = new JLabel();
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setVisible(false);
        JButton startButton = new JButton("Start");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(ipLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(ipValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(portLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(portValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(statusValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(countdownLabel, gbc);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcpServer.getIsConnected()) {
                    countdownLabel.setVisible(true);
                    startCountdown();
                }
            }
        });

        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100)); // Set padding: top, left, bottom, right

        paddingPanel.add(mainPanel, BorderLayout.CENTER);

        add(paddingPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        new Thread(() -> tcpServer.startServer()).start();
    }

    private void startCountdown() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdown > 0) {
                    countdownLabel.setText("Starting in: " + countdown);
                    tcpServer.sendMessage("Countdown: " + countdown);
                    countdown--;
                } else {
                    countdownTimer.stop();
                    countdownLabel.setText("Starting now!");
                    tcpServer.sendMessage("Game Started!");
                    // Here you can trigger the start of the game
                }
            }
        });
        countdownTimer.start();
    }

    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> statusValueLabel.setText(status));
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}