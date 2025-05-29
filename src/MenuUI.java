import javax.swing.*;
import java.awt.*;

public class MenuUI extends JFrame {

    private JComboBox<String> numPlayersCombo;
    private JTextField[] playerNameFields = new JTextField[4];
    private JComboBox<String> numBotsCombo;
    private JComboBox<String>[] botDifficultyCombos = new JComboBox[3];
    private JCheckBox[] ruleCheckboxes = new JCheckBox[6];
    private String[][] keybinds = {{"1", "2"}, {"Z", "X"}, {"9", "0"}, {"N", "M"}};

    public MenuUI() {
        setTitle("Egyptian Hare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // RULE SET PANEL
        JPanel ruleSetPanel = new JPanel(new GridLayout(6, 1));
        ruleSetPanel.setBorder(BorderFactory.createTitledBorder("RULE SET"));
        String[] rules = {"Doubles", "Sandwiches", "Marriages", "Divorces", "Gay Marriages", "Sequences"};
        for (int i = 0; i < rules.length; i++) {
            ruleCheckboxes[i] = new JCheckBox(rules[i], true);
            ruleSetPanel.add(ruleCheckboxes[i]);
        }

        // HOW TO PLAY PANEL
        JPanel howToPlayPanel = new JPanel(new GridLayout(13, 1));
        howToPlayPanel.setBorder(BorderFactory.createTitledBorder("HOW TO PLAY"));
        howToPlayPanel.add(new JLabel("Insert Rules Here:"));
        howToPlayPanel.add(new JLabel("• Select game-rules for slaps on the left"));
        howToPlayPanel.add(new JLabel("• When the game starts, each player places"));
        howToPlayPanel.add(new JLabel("  a card in clockwise order."));
        howToPlayPanel.add(new JLabel("• Slapping the pile when one of the patterns"));
        howToPlayPanel.add(new JLabel("  appears will win you the deck"));
        howToPlayPanel.add(new JLabel("• Slapping the pile when there is no pattern "));
        howToPlayPanel.add(new JLabel("  will cause you to lose a card to the pile"));
        howToPlayPanel.add(new JLabel("• The game ends when the users click the "));
        howToPlayPanel.add(new JLabel("  \"End Game\" button, or if one player has"));
        howToPlayPanel.add(new JLabel("  all the cards and no other players have an"));
        howToPlayPanel.add(new JLabel("  opportunity to win"));
        // CENTER PANEL (Players, Keybinds, etc.)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cpc = new GridBagConstraints();
        cpc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Egyptian Hare");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        cpc.gridx = 0;
        cpc.gridy = 0;
        cpc.gridwidth = 2;
        centerPanel.add(titleLabel, cpc);

        cpc.gridy++;
        cpc.gridwidth = 1;
        centerPanel.add(new JLabel("Number of Players:"), cpc);

        cpc.gridx = 1;
        numPlayersCombo = new JComboBox<>(new String[]{"2", "3", "4"});
        centerPanel.add(numPlayersCombo, cpc);

        // Keybind + Player Fields
        for (int i = 0; i < 4; i++) {
            cpc.gridx = 0;
            cpc.gridy++;
            centerPanel.add(new JLabel("Place: " + keybinds[i][0] + "  Slap: " + keybinds[i][1]), cpc);

            cpc.gridx = 1;
            playerNameFields[i] = new JTextField("Player" + (i + 1), 10);
            centerPanel.add(playerNameFields[i], cpc);
        }

        // BOT SETTINGS
        JPanel botPanel = new JPanel(new GridLayout(4, 2));
        botPanel.setBorder(BorderFactory.createTitledBorder("BOT SETTINGS"));
        botPanel.add(new JLabel("Number of Bots:"));
        numBotsCombo = new JComboBox<>(new String[]{"0", "1", "2", "3"});
        botPanel.add(numBotsCombo);

        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < 3; i++) {
            botPanel.add(new JLabel("BOT " + (i + 1) + ":"));
            botDifficultyCombos[i] = new JComboBox<>(difficulties);
            botPanel.add(botDifficultyCombos[i]);
        }

        // Play Button
        JButton playButton = new JButton("PLAY");
        playButton.addActionListener(e -> startGame());

        // Add Panels to Frame
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(ruleSetPanel, gbc);

        gbc.gridx = 1;
        add(centerPanel, gbc);

        gbc.gridx = 2;
        add(howToPlayPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(botPanel, gbc);

        gbc.gridy = 2;
        add(playButton, gbc);

        setVisible(true);
    }

    // Sample function that can access class-level components
    private void startGame() {
        int playerCount = Integer.parseInt((String) numPlayersCombo.getSelectedItem());
//        int botCount = Integer.parseInt((String) numBotsCombo.getSelectedItem());
       

        System.out.println("Starting game with " + playerCount + " players");
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Player " + (i + 1) + ": " + playerNameFields[i].getText());
        }

//        for (int i = 0; i < botCount; i++) {
//            System.out.println("Bot " + (i + 1) + " Difficulty: " + botDifficultyCombos[i].getSelectedItem());
//        }

        for (int i = 0; i < ruleCheckboxes.length; i++) {
            System.out.println(rules()[i] + ": " + ruleCheckboxes[i].isSelected());
        }
        
        if(playerCount >= 2 && playerCount <= 4)
        {
        	Game g = new Game();
        	
        	//add players given menu selections
        	for(int i = 0; i < playerCount; i++)
        	{
        		g.addPlayer(new Player(playerNameFields[i].getText(), new Pile(), g, keybinds[i][0], keybinds[i][1]));
        	}
        	
        	//deal cards out
        	g.dealCards();
        	
        	//array will hold the selected ruleset
        	boolean[] a = new boolean[6];
        	for(int i = 0; i < 6; i++)
        		a[i] = ruleCheckboxes[i].isSelected();
        	
        	//update rules in Game class
        	g.initializeRules(a);
        	
        	//transition the display to the game screen
        	GameUI i = new GameUI(g, Integer.parseInt((String)numPlayersCombo.getSelectedItem()));
        }
    }

    // Helper method to get rule names again
    private String[] rules() {
        return new String[]{"Doubles", "Sandwiches", "Marriages", "Divorces", "Gay Marriages", "Sequences"};
    }

    public void display() {
        SwingUtilities.invokeLater(MenuUI::new);
    }
}