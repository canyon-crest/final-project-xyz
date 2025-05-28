import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.net.URL;
import java.util.concurrent.*;
import javax.swing.border.LineBorder;

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

    //Create a new player slot and panel
    //@param player Index
    //@return JPanel of the player
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
        String cardImageFileName = "cards/back1.GIF";
		URL imageURL = getClass().getResource(cardImageFileName);
    	ImageIcon icon = new ImageIcon(imageURL);
    	cardBackImages[index].setIcon(icon);
    	cardBackImages[index].setText("");
    	cardBackImages[index].setVisible(true);
		panel.add(cardBackImages[index]);

        // Placeholder for player icon (robot or person)
        playerIcons[index] = new JLabel("[Player Icon]");
        playerIcons[index].setAlignmentX(Component.CENTER_ALIGNMENT);
        playerIcons[index].setPreferredSize(new Dimension(80, 80));
        String playerIconFileName = "cards/playerIcon.png";
		URL iconURL = getClass().getResource(playerIconFileName);
    	ImageIcon playerIcon = new ImageIcon(iconURL);
    	playerIcons[index].setIcon(playerIcon);
    	playerIcons[index].setText("");
    	playerIcons[index].setVisible(true);
        panel.add(playerIcons[index]);

        return panel;
    }

    //update the information for each player slot
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
    }
    
    
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(GameUI::new);
    }
    
    //Change the card image to whatever is inputted
    //@param the card to change to
    public void changeCenterCardImg(Card c)
    {
    	String cardImageFileName = c.getCardFileName();
		URL imageURL = getClass().getResource(cardImageFileName);
    	ImageIcon icon = new ImageIcon(imageURL);
		centerPile.setIcon(icon);
		centerPile.setText("");
		centerPile.setVisible(true);
    }
    
    
    //Clears the center card image
    public void clearCenterCardImg()
    {
    	centerPile.setVisible(false);
    }
    
    //Updates the card counts of all players. 
    public void updatePlayerCardCounts()
    {
    	for(int i = 0; i < activePlayers; i++)
    	{
    		cardCountLabels[i].setText("" + g.getPlayerList().get(i).getPile().getSize());
    	}
    }
    
    public JPanel[] getPlayerPanels()
    {
    	return playerPanels;
    }
}

class SlapOrPlace extends JFrame implements KeyListener
{
	private Player myPlayer;
	private int playerIdx;
	private static int currentPlayerIdx = 0;
	private static ArrayList<Integer> activePlayersIdx = new ArrayList<Integer>();
	private static int currentPlayerPlay = 1;
	private GameUI myGameUI;
	private JPanel[] playerPanels;
	private static LineBorder ACTIVE_BORDER = (LineBorder) BorderFactory.createLineBorder(Color.RED, 2);
	private static LineBorder IDLE_BORDER = (LineBorder) BorderFactory.createLineBorder(Color.GRAY, 1);
	
	public SlapOrPlace(JPanel myPanel, Player myPlayer, int playerIdx, GameUI myGameUI)
	{
		this.myPlayer = myPlayer;
		myPanel.setFocusable(true);
		this.playerIdx = playerIdx;
		this.myGameUI = myGameUI;
		playerPanels = myGameUI.getPlayerPanels();
		playerPanels[0].setBorder(ACTIVE_BORDER);
		
		activePlayersIdx.add(playerIdx);
	}
	
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    //Process slaps and card placements based on key presses. 
    //@param key pressed by player
    //@return none
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == myPlayer.getSKey().charAt(0)) {
        	//Slapping a card
            boolean slapped = myPlayer.slap(); //returns true for successful slap and false for burn
            if(slapped)
            	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - slapped");
            else
            	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - burned");
            
            if(slapped) //if slap successful
            {
            	//player associated with the slap key must place one card
            	currentPlayerPlay = 1;
            	currentPlayerIdx = playerIdx;
            	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        		scheduler.schedule(() -> {
        			myGameUI.clearCenterCardImg();
        		}, 1, TimeUnit.SECONDS);
            }
            updateActivePlayers();
        }
        else if (e.getKeyCode() == myPlayer.getPKey().charAt(0) && currentPlayerIdx == playerIdx) {
          //Placing a card   
        	Card c = myPlayer.placeCard(myPlayer.getGame().getCenterPile());
        	if(c == null)
    		{
        		//next player must play the rest of the required cards for the eliminated player
        		nextPlayer();
        		//if a player is ever unable to place a card, they are out
        		updateActivePlayers();
        		return;
    		}
        	myGameUI.changeCenterCardImg(c);
        	System.out.println("(" + myPlayer.getPile().getSize() + ")" + myPlayer.getUsername() + " - placed a " + c.getCardFileName());
        	currentPlayerPlay--;
        	
        	//after one second to give people time to slap, check if the game is over. 
        	//NOTE: if someone does slap during the 1 second, the pile will be empty and the round will not be over
        		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        		scheduler.schedule(() -> {
        			if(myPlayer.getGame().isRoundOver())
                	{
	        			previousPlayer(); //go to previous player
	            		myPlayer.getGame().endRound(currentPlayerIdx); //winner was the previous active player who played face card
	               		currentPlayerPlay = 1; //after center is cleared, winner must play 1 card
	        			myGameUI.clearCenterCardImg(); //clear UI center
	        			updateActivePlayers();
                	}
                	else if(currentPlayerPlay == 0 || c.isFaceCard()) //if round isn't over and cur player fulfills obligation/places face card
                	{
        	        	nextPlayer(); //go to next player
        	        	currentPlayerPlay = c.mandatoryPlace(); //calculate the cards they need to place
                	}
        		}, 1, TimeUnit.SECONDS);
        	
        }
        
        //update card counts with a delay, giving time for slaps
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.schedule(() -> {
	        myGameUI.updatePlayerCardCounts();
	        //remove panel border color from all player panels
	        for (int i = 0; i< playerPanels.length; i++)
	        {
	        	if (i == currentPlayerIdx)
	        	{
	        		playerPanels[i].setBorder(ACTIVE_BORDER);
	        	}
	        	else
	        	{
	        		playerPanels[i].setBorder(IDLE_BORDER);
	        	}
	        }
	        //set the current player's border to red
		}, 1, TimeUnit.SECONDS);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    
    //move currentPlayerIdx to the previous active player
    private static void previousPlayer()
    {
    	int i = activePlayersIdx.indexOf(currentPlayerIdx);
    	i = (i - 1 + activePlayersIdx.size()) % activePlayersIdx.size();
    	currentPlayerIdx = activePlayersIdx.get(i);
    }
    
    //move currentPlayerIdx to the next active player
    private static void nextPlayer()
    {
    	int i = activePlayersIdx.indexOf(currentPlayerIdx);
    	i = (i + 1) % activePlayersIdx.size();
    	currentPlayerIdx = activePlayersIdx.get(i);
    }
    
    
    //update activePlayerIdx based on which players now have 0 cards
    private void updateActivePlayers()
    {
    	ArrayList<Player> playerList = myPlayer.getGame().getPlayerList();
    	for(int i = activePlayersIdx.size() - 1; i >= 0; i--)
    	{
    		if(playerList.get(activePlayersIdx.get(i)).getPile().getSize() == 0)//if player at active player idx position i has no cards
    		{
    			System.out.println(playerList.get(activePlayersIdx.get(i)).getUsername() + " has been eliminated.");
    			activePlayersIdx.remove(i); //removes position i
    			for(Integer j : activePlayersIdx) System.out.print(playerList.get(j).getUsername() + " ");
    			System.out.println("remain.");
    			playerPanels[i].setVisible(false);
    		}
    	}
    }
    
}