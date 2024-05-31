package Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MultiJoinScreen extends JFrame {

    private JTextField ipInput;
    private JTextField portInput;
    private JLabel connectionStatusLabel;
    private TCPClient tcpClient = new TCPClient();
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";
    private Timer countdownTimer;
    private int countdown = 3;
    private JLabel countdownLabel = new JLabel("Starting in: " + countdown);

    public MultiJoinScreen(BuildingModeController buildingModeController) {
        setTitle("Network Info Join");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        setContentPane(new BackgroundPanel(backgroundImagePath));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Constraints for centering the components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ipInput = new JTextField(15);
        portInput = new JTextField(15);
        connectionStatusLabel = new JLabel("Connection status: Not connected");
        connectionStatusLabel.setForeground(Color.WHITE); // Change connection status text color
        JButton connectButton = new JButton("Connect");

        JLabel ipLabel = new JLabel("IP Address:");
        ipLabel.setForeground(Color.WHITE); // Change IP Address label color

        JLabel portLabel = new JLabel("Port:");
        portLabel.setForeground(Color.WHITE); // Change Port label color

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(ipLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(ipInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(portLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(portInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(connectButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(connectionStatusLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(countdownLabel, gbc);
        countdownLabel.setVisible(false);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipInput.getText();
                int port;
                try {
                    port = Integer.parseInt(portInput.getText());
                } catch (NumberFormatException ex) {
                    connectionStatusLabel.setText("Invalid port number");
                    return;
                }
                tcpClient.connectToServer(ip, port);
                if (tcpClient.getIsConnected()) {
                    connectionStatusLabel.setText("Connected: waiting for host to start the game");
                    connectButton.setEnabled(false);
                } else {
                    connectionStatusLabel.setText("Connection failed");
                }
            }
        });

        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100)); // Set padding: top, left, bottom, right

        paddingPanel.add(mainPanel, BorderLayout.CENTER);

        add(paddingPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public void displayCountdown() {
        startCountdown();
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

}
