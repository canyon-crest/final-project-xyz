import javax.swing.*;
import java.awt.*;

public class WinnerUI extends JFrame{
	public WinnerUI(String name)
	{
		setTitle(name + "Wins!");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1000, 600);
	    setLocationRelativeTo(null);
	    setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    JPanel centerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints cpc = new GridBagConstraints();
        cpc.insets = new Insets(5, 5, 5, 5);
	    JLabel titleLabel = new JLabel(name + " Wins!");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        cpc.gridx = 0;
        cpc.gridy = 0;
        cpc.gridwidth = 2;
        centerPanel.add(titleLabel, cpc);
        gbc.gridx = 1;
        add(centerPanel, gbc);
	    
	    setVisible(true);
	}

	 
}
