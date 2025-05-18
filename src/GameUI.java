import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameUI extends JFrame {

    private JPanel[] playerPanels = new JPanel[4];
    private JLabel[] cardCountLabels = new JLabel[4];
    private JLabel[] keybindLabels = new JLabel[4];
    private JLabel[] cardBackImages = new JLabel[4];
    private JLabel[] playerIcons = new JLabel[4];

    // For example, 2 human players and 2 bots.
    private int activePlayers = 4;

    public GameUI() {
        setTitle("Egyptian Hare - Game Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Center pile display (placeholder)
        JLabel centerPile = new JLabel("CENTER PILE");
        centerPile.setHorizontalAlignment(SwingConstants.CENTER);
        centerPile.setFont(new Font("SansSerif", Font.BOLD, 20));
        centerPile.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(centerPile, BorderLayout.CENTER);

        // Bottom panel for players
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 4; i++) {
            playerPanels[i] = createPlayerSlot(i);
            bottomPanel.add(playerPanels[i]);
        }

        add(bottomPanel, BorderLayout.SOUTH);
        updateActivePlayers();

        setVisible(true);
    }

    private JPanel createPlayerSlot(int index) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Card count label
        cardCountLabels[index] = new JLabel("0");
        cardCountLabels[index].setFont(new Font("SansSerif", Font.BOLD, 24));
        cardCountLabels[index].setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cardCountLabels[index]);

        // Keybind info
        keybindLabels[index] = new JLabel("Slap: ?  Deal: ?");
        keybindLabels[index].setFont(new Font("SansSerif", Font.PLAIN, 12));
        keybindLabels[index].setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(keybindLabels[index]);

        // Placeholder for card back image
        cardBackImages[index] = new JLabel("[Card Image]");
        cardBackImages[index].setPreferredSize(new Dimension(80, 120));
        cardBackImages[index].setHorizontalAlignment(SwingConstants.CENTER);
        cardBackImages[index].setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cardBackImages[index]);

        // Placeholder for player icon (robot or person)
        playerIcons[index] = new JLabel("[Player Icon]");
        playerIcons[index].setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playerIcons[index]);

        return panel;
    }

    private void updateActivePlayers() {
        // Show only active player slots, hide others
        for (int i = 0; i < 4; i++) {
            playerPanels[i].setVisible(i < activePlayers);
        }

        // Example keybinds
        String[][] keybinds = {
            {"1", "2"},
            {"Z", "X"},
            {"9", "0"},
            {"N", "M"}
        };

        for (int i = 0; i < activePlayers; i++) {
            keybindLabels[i].setText("Slap: " + keybinds[i][1] + "  Deal: " + keybinds[i][0]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameUI::new);
    }
}