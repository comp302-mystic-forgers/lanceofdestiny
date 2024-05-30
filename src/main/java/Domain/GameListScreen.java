package Domain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import static Domain.BuildingModePage.FINISH;

public class GameListScreen extends JFrame {

    private GameInfoDAO gameInfoDAO;
    private JTable gameTable;
    private JTextArea gameListArea;
    private BuildingModeController buildingModeController;
    private  PlayerAccount player;
    private GameInfo selectedGame;
    private List<GameInfo> games;

    public GameListScreen(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        this.player = UserSession.getInstance().getCurrentPlayer();
        Database connection = new Database(); // Initialize your Database connection
        this.gameInfoDAO = new GameInfoDAO(connection);
        setTitle("Game List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        gameTable = new JTable(new DefaultTableModel(new Object[]{"Game No", "Score", "Lives", "State", "Last Saved", "Spells", "Barriers", "Play"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only the "Play" button column is editable
            }
        });
        TableColumnModel columnModel = gameTable.getColumnModel();
        columnModel.getColumn(4).setPreferredWidth(150); // Set preferred width for "Last Saved"

        gameTable.getColumn("Play").setCellRenderer(new ButtonRenderer());
        gameTable.getColumn("Play").setCellEditor(new ButtonEditor(new JCheckBox(), buildingModeController, this));

        JScrollPane scrollPane = new JScrollPane(gameTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Now load the game info after the UI components are initialized
        loadGameInfo();
    }

    private void loadGameInfo() {
        DefaultTableModel model = (DefaultTableModel) gameTable.getModel();
        model.setRowCount(0); // Clear the table
        try {
            if (player == null) {
                throw new IllegalStateException("No player is currently logged in.");
            }

            String playerId = player.getPlayerId();
            games = gameInfoDAO.loadGameInfo(playerId);

            if (games.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No games found for this player.");
            } else {
                int count = 1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                for (GameInfo game : games) {
                    String formattedDate = dateFormat.format(game.getLastSaved());
                    model.addRow(new Object[]{
                            count,
                            game.getScore(),
                            game.getLives(),
                            game.getGameState(),
                            formattedDate,
                            game.getSpellsAcquired().size(),
                            game.getSimpleBarrierList().size() + game.getReinforcedBarrierList().size() + game.getRewardingBarrierList().size() + game.getExplosiveBarrierList().size(),
                            "Play"
                    });
                    count++;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading games: " + ex.getMessage());
        }
    }
    public GameInfo getSelectedGame(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < games.size()) {
            return games.get(rowIndex);
        }
        return null;
    }
    public GameInfo getSelectedGame() {
        return this.selectedGame;
    }

    public void setSelectedGame(GameInfo selectedGame) {
        this.selectedGame = selectedGame;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // ButtonEditor class
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private BuildingModeController buildingModeController;
        private GameListScreen gameListScreen;

        public ButtonEditor(JCheckBox checkBox, BuildingModeController buildingModeController, GameListScreen gameListScreen) {
            super(checkBox);
            this.buildingModeController = buildingModeController;
            this.gameListScreen = gameListScreen;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int selectedRow = gameTable.getSelectedRow();
                GameInfo selectedGame = gameListScreen.getSelectedGame(selectedRow);
                if (selectedGame != null) {
                    // Here you can add the code to handle the button click
                    System.out.println("Play button clicked on row " + selectedRow);
                    buildingModeController.setGameInfo(selectedGame);
                    gameListScreen.setVisible(false);
                    buildingModeController.setCurrentMode(FINISH);
                    buildingModeController.switchScreens();
                }
            }
            isPushed = false;
            return label;
        }
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    public static void main(String[] args) {
        Database connection = new Database(); // Initialize your Database connection
        GameInfoDAO gameInfoDAO = new GameInfoDAO(connection);
        SwingUtilities.invokeLater(() -> {
            //GameListScreen screen = new GameListScreen(new BuildingModeController());
            //screen.setVisible(true);
        });
    }
}
