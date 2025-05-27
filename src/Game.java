import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game{
	private boolean doubles;
	private boolean sandwiches;
	private boolean marriages;
	private boolean divorces;
	private boolean gayMarriages;
	private boolean sequences;
	
	private int numPlayers;
	private int numBots;
	
	private Pile centerPile;
	private Pile burnPile;
	
	private ArrayList<Player> playerList;
	
	//Constructor
	public Game() {
		centerPile = new Pile();
		burnPile = new Pile();
		playerList = new ArrayList<Player>();
	}
	
	//Returns whether a slap is valid or not
	public boolean isASlap() {
		ArrayList<Card> cardList = centerPile.getCardList();
		
		if(cardList.size() < 2) 
			return false;
		
		int c1 = cardList.get(cardList.size() - 1).getPointValue();
		int c2 = cardList.get(cardList.size() - 2).getPointValue();

		if(cardList.size() >= 2)
		{
			if(doubles && c1 == c2)
				return true;
			if(marriages && Math.max(c1, c2) == 13 && Math.min(c1, c2) == 12)
				return true;
			if(gayMarriages && Math.max(c1, c2) == 13 && Math.min(c1, c2) == 11)
				return true;
		}
			
		if(cardList.size() >= 3)
		{
			int c3 = cardList.get(cardList.size() - 3).getPointValue();
			
			if(sandwiches && c1 == c3) 
				return true;
			if(divorces && Math.max(c1, c3) == 13 && Math.min(c1, c3) == 12)
				return true;
			if(sequences && c1 == c2 + 1 && c2 == c3 + 1)
				return true;
			if(sequences && c3 == c2 + 1 && c2 == c1 + 1)
				return true;	
		}
		return false;
	}
	
	public boolean isRoundOver()
	{
		int cardsAfterFace = 0;
		int cardsNeededAfterFace = 0;
		for(int i = centerPile.getSize() - 1; i >= 0; i--)
		{
			if(!centerPile.get(i).isFaceCard())
				cardsAfterFace++;
			else 
			{
				cardsNeededAfterFace = centerPile.get(i).mandatoryPlace();
				break;
			}
		}
		return cardsAfterFace == cardsNeededAfterFace;
	}
	
	public void dealCards()
	{
		String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
		String[] SUITS ={"spades", "hearts", "diamonds", "clubs"};
		int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		
		Deck d = new Deck(RANKS, SUITS, POINT_VALUES);
		
		//Deals 52 cards to players. 
		for(int i = 0; i < 52; i++)
		{
			playerList.get(i % playerList.size()).getPile().addCard(d.deal());
		}
			
	}
	
	public void initializeRules(boolean[] rules)
	{
		doubles = rules[0];
		sandwiches = rules[1];
		marriages = rules[2];
		divorces = rules[3];
		gayMarriages = rules[4];
		sequences = rules[5];
	}
	//Starts the game
	public void startGame() {
		
	}
	
	public void endRound(Player playerAfterWinner)
	{
		Player winner = playerList.get((playerList.indexOf(playerAfterWinner) - 1 + playerList.size()) % playerList.size());
		winner.addPile(centerPile);
		winner.addPile(burnPile);
	}
	
	
	
	
	public void addPlayer(Player p)
	{
		playerList.add(p);
	}
	
	public Pile getCenterPile()
	{
		return centerPile;
	}
	
	public Pile getBurnPile()
	{
		return burnPile;
	}
	public ArrayList<Player> getPlayerList()
	{
		return playerList;
	}
}