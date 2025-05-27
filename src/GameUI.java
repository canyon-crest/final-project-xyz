import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.net.URL;
import java.util.concurrent.*;

public class GameUI extends JFrame {

    private JPanel[] playerPanels = new JPanel[4];
    private JLabel[] cardCountLabels = new JLabel[4];
    private JLabel[] keybindLabels = new JLabel[4];
    private JLabel[] cardBackImages = new JLabel[4];
    private JLabel[] playerIcons = new JLabel[4];
    private JLabel centerPile;
    private Game g;

    // For example, 2 human players and 2 bots.
    private int activePlayers;

    public GameUI(Game g, int activePlayers) {
    	this.g = g;
    	this.activePlayers = activePlayers;
        setTitle("Egyptian Hare - Game Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Center pile display (placeholder)
        centerPile = new JLabel("CENTER PILE");
        centerPile.setHorizontalAlignment(SwingConstants.CENTER);
        centerPile.setFont(new Font("SansSerif", Font.BOLD, 20));
        centerPile.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(centerPile, BorderLayout.CENTER);

        // Bottom panel for players
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < this.activePlayers; i++) {
            playerPanels[i] = createPlayerSlot(i);
            bottomPanel.add(playerPanels[i]);
        }

        add(bottomPanel, BorderLayout.SOUTH);
        updateActivePlayers();
        updatePlayerCardCounts();

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
    	
        for (int i = 0; i < activePlayers; i++) {
            playerPanels[i].setVisible(true);
        }

        // Example keybinds
        String[][] keybinds = {
            {"1", "2"},
            {"Z", "X"},
            {"9", "0"},
            {"N", "M"}
        };

        for (int i = 0; i < activePlayers; i++) {
            keybindLabels[i].setText("Deal: " + keybinds[i][0] + "  Slap: " + keybinds[i][1]);
        }
        
        // Make keybinds functional
        ArrayList<Player> playerList = g.getPlayerList();
        for (int i = 0; i<activePlayers; i++)
        {
        	playerPanels[0].addKeyListener(new SlapOrPlace(playerPanels[0], playerList.get(i), i, this));
        }
        SlapOrPlace.setActivePlayers(activePlayers);
    }
    
    
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(GameUI::new);
    }
    
    public void changeCenterCardImg(Card c)
    {
    	String cardImageFileName = c.getCardFileName();
		URL imageURL = getClass().getResource(cardImageFileName);
    	ImageIcon icon = new ImageIcon(imageURL);
		centerPile.setIcon(icon);
		centerPile.setText("");
		centerPile.setVisible(true);
    }
    
    public void clearCenterCardImg()
    {
    	centerPile.setVisible(false);
    }
    
    public void updatePlayerCardCounts()
    {
    	for(int i = 0; i < activePlayers; i++)
    	{
    		cardCountLabels[i].setText("" + g.getPlayerList().get(i).getPile().getSize());
    	}
    }
}

class SlapOrPlace extends JFrame implements KeyListener
{
	private Player myPlayer;
	private int playerIdx;
	private static int currentPlayerIdx = 0;
	private static int activePlayers;
	private static ArrayList<Integer> activePlayersIdx = new ArrayList<Integer>();
	private static int currentPlayerPlay = 1;
	private GameUI myGameUI;
	
	public SlapOrPlace(JPanel myPanel, Player myPlayer, int playerIdx, GameUI myGameUI)
	{
		this.myPlayer = myPlayer;
		myPanel.setFocusable(true);
		this.playerIdx = playerIdx;
		this.myGameUI = myGameUI;
		
		activePlayersIdx.add(playerIdx);
	}
	
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == myPlayer.getSKey().charAt(0)) {
        	//Slapping a card
            boolean slapped = myPlayer.slap();
            if(slapped)
            	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - slapped");
            else
            	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - burned");
            
            if(slapped)
            {
            	currentPlayerPlay = 1;
            	currentPlayerIdx = playerIdx;
            	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        		scheduler.schedule(() -> {
        			myGameUI.clearCenterCardImg();
        		}, 1, TimeUnit.SECONDS);
            }

        }
        else if (e.getKeyCode() == myPlayer.getPKey().charAt(0) && currentPlayerIdx == playerIdx) {
          //Placing a card   
        	Card c = myPlayer.placeCard(myPlayer.getGame().getCenterPile());
        	if(c == null)
    		{
        		System.out.println("GAME OVER");
        		return;
    		}
        	myGameUI.changeCenterCardImg(c);
        	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - placed a " + c.getCardFileName());
        	currentPlayerPlay--;
        	
        	//check if its over
        	if(myPlayer.getGame().isRoundOver())
        	{
        		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        		scheduler.schedule(() -> {
        			previousPlayer();
            		myPlayer.getGame().endRound(myPlayer);
               		currentPlayerPlay = 1;
        			myGameUI.clearCenterCardImg();
        		}, 1, TimeUnit.SECONDS);
        	}
        	else if(currentPlayerPlay == 0 || c.isFaceCard())
        	{
	        	nextPlayer();
	        	currentPlayerPlay = c.mandatoryPlace();
        	}
        	
        }
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.schedule(() -> {
	        myGameUI.updatePlayerCardCounts();

			if(myPlayer.getPile().getSize() == 0)
			{
				activePlayersIdx.remove(playerIdx);
			}
			if(activePlayersIdx.size() == 0)
			{
				System.out.println("--------------GAME OVER------------------");
			}
		}, 1, TimeUnit.SECONDS);
        
        //after every click, update player card counts no matter what
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    public static void setActivePlayers(int activePlayersInput)
    {
    	activePlayers = activePlayersInput;
    }
    
    private static void previousPlayer()
    {
    	int i = activePlayersIdx.indexOf(currentPlayerIdx);
    	i = (i - 1 + activePlayersIdx.size()) % activePlayersIdx.size();
    	currentPlayerIdx = activePlayersIdx.get(i);
    }
    
    private static void nextPlayer()
    {
    	int i = activePlayersIdx.indexOf(currentPlayerIdx);
    	i = (i + 1) % activePlayersIdx.size();
    	currentPlayerIdx = activePlayersIdx.get(i);
    }
    
}