import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class MenuUI2 extends JFrame {

    private JComboBox<Integer> numPlayersCombo;
    private JTextField[] playerNameFields = new JTextField[4];
    private JCheckBox[] ruleCheckboxes = new JCheckBox[6];
    private String[][] keybinds = {{"1", "2"}, {"Z", "X"}, {"9", "0"}, {"N", "M"}};
    private String[] rules = {"Doubles", "Sandwiches", "Marriages", "Divorces", "Gay Marriages", "Sequences"};

    public MenuUI2() {
        setTitle("Egyptian Hare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        //title panel
        JLabel title = new JLabel("Egyptian Hare", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        add(title, BorderLayout.PAGE_START);

        // Holds the two panels in the center of the menu
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        
        //Left Rule-set Panel
        JPanel ruleSetPanel = new JPanel();
        ruleSetPanel.setLayout(new BoxLayout(ruleSetPanel, BoxLayout.Y_AXIS));
        ruleSetPanel.setLayout(new BoxLayout(ruleSetPanel, BoxLayout.Y_AXIS));
        ruleSetPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Rule Set",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16)
        ));
        
        for(int i = 0; i < rules.length; i++)
        {
        	ruleCheckboxes[i] = new JCheckBox(rules[i], true);
        	ruleCheckboxes[i].setFont(new Font("Serif", Font.PLAIN, 14));
        	ruleSetPanel.add(ruleCheckboxes[i]);
        }
        
        //Right Player Username Input Panel
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Player Setup",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16)
        ));

        //small panel with the box selecting the number of players
        JPanel playerCountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerCountPanel.add(new JLabel("Number of Players:"));
        numPlayersCombo = new JComboBox<>(new Integer[]{2, 3, 4});
        numPlayersCombo.setSelectedIndex(0); //default selection
        playerCountPanel.add(numPlayersCombo);
        playerPanel.add(playerCountPanel);
        
        for (int i = 0; i < 4; i++) {
            JPanel playerInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel("Player " + (i + 1) + " Name:");
            JTextField nameField = new JTextField("Name" + (i + 1), 10);
            playerNameFields[i] = nameField;
            playerInput.add(label);
            playerInput.add(nameField);
            playerPanel.add(playerInput);
        }
        
        //add right and left to centerPanel
        centerPanel.add(ruleSetPanel);
        centerPanel.add(playerPanel);
        
        //add centerPanel to UI
        add(centerPanel, BorderLayout.CENTER);
        
        // Play Button
        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        playButton.addActionListener(e -> startGame());
        
        //button panel
        JPanel playButtonPanel = new JPanel();
        playButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        playButtonPanel.add(playButton);
        add(playButtonPanel, BorderLayout.SOUTH);
     
        


        setVisible(true);
    }

    // Sample function that can access class-level components
    private void startGame() {
        int playerCount = (int)(numPlayersCombo.getSelectedItem());
       
        System.out.println("Starting game with " + playerCount + " players");
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Player " + (i + 1) + ": " + playerNameFields[i].getText());
        }

        for (int i = 0; i < ruleCheckboxes.length; i++) {
            System.out.println(rules[i] + ": " + ruleCheckboxes[i].isSelected());
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
        	GameUI i = new GameUI(g, (int)(numPlayersCombo.getSelectedItem()));
        }
    }



    public void display() {
        SwingUtilities.invokeLater(MenuUI2::new);
    }
}