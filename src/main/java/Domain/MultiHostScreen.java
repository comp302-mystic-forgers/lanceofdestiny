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
    JLabel ipValueLabel;
    private JLabel portLabel;
    private JLabel portValueLabel;
    private JLabel statusLabel;
    private JLabel statusValueLabel;
    private TCPServer tcpServer;
    private Timer countdownTimer;
    private int countdown = 3;
    private JLabel countdownLabel;
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";
    private MultiJoinScreen multiJoinScreen;

    public MultiHostScreen(BuildingModeController buildingModeController, MultiJoinScreen multiJoinScreen) {
        this.multiJoinScreen = multiJoinScreen;
        setTitle("Network Info Join");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        setContentPane(new MultiJoinScreen.BackgroundPanel(backgroundImagePath));

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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcpServer.getIsConnected()) {

                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.anchor = GridBagConstraints.CENTER;
                    mainPanel.add(countdownLabel, gbc);

                    multiJoinScreen.displayCountdown();

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
                    countdown--;
                } else {
                    countdownTimer.stop();
                    countdownLabel.setText("Starting now!");
                }
            }
        });
        countdownTimer.start();
    }

    public void updateStatus(String status) {
        statusValueLabel.setText(status);
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
    }

}