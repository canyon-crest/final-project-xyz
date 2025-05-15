import javax.swing.*;
import java.awt.*;

public class HareUI extends JFrame {

	public HareUI() 
	{
		setTitle("Egyptian Hare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// RULE SET PANEL
		JPanel ruleSetPanel = new JPanel(new GridLayout(7, 1));
		ruleSetPanel.setBorder(BorderFactory.createTitledBorder("RULE SET"));
		String[] rules = {"Doubles", "Sandwiches", "Marriages", "Divorces", "Gay Marriages", "Tomfoolery", "Sequences"};
		for (String rule : rules) 
		{
			ruleSetPanel.add(new JCheckBox(rule, true));
		}

		// HOW TO PLAY PANEL
		JPanel howToPlayPanel = new JPanel(new GridLayout(6, 1));
		howToPlayPanel.setBorder(BorderFactory.createTitledBorder("HOW TO PLAY"));
		howToPlayPanel.add(new JLabel("Insert Rules Here:"));
		for (int i = 0; i < 5; i++) 
		{
			howToPlayPanel.add(new JLabel("• Rules"));
		}

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
		String[] numPlayersOptions = {"1", "2", "3", "4"};
		centerPanel.add(new JComboBox<>(numPlayersOptions), cpc);

		// Keybind + Player Fields
		String[][] keybinds = {{"1", "2"}, {"Z", "X"}, {"9", "0"}, {"N", "M"}};
		for (int i = 0; i < 4; i++) 
		{
			cpc.gridx = 0;
			cpc.gridy++;
			centerPanel.add(new JLabel("Place: " + keybinds[i][0] + " Slap: " + keybinds[i][1]), cpc);
			
			cpc.gridx = 1;
			centerPanel.add(new JTextField("Name" + (i + 1), 10), cpc);
		}
		
		// BOT SETTINGS
		JPanel botPanel = new JPanel(new GridLayout(4, 2));
		botPanel.setBorder(BorderFactory.createTitledBorder("BOT SETTINGS"));
		botPanel.add(new JLabel("Number of Bots:"));
		String[] botOptions = {"0", "1", "2", "3"};
		botPanel.add(new JComboBox<>(botOptions));
		
		String[] difficulties = {"Easy", "Medium", "Hard"};
		for (int i = 1; i <= 3; i++) 
		{
			botPanel.add(new JLabel("BOT " + i + ":"));
			botPanel.add(new JComboBox<>(difficulties));
		}
		
		// Play Button
		JButton playButton = new JButton("PLAY");
		
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

	public static void display() {
		SwingUtilities.invokeLater(HareUI::new);
	}
}