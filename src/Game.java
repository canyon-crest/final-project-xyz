import java.util.ArrayList;

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
	
	//Constructor
	public Game() {
		centerPile = new Pile();
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
	
	//Makes the game end
	public void endGame() {
		
	}
	
	//Starts the game
	public void startGame() {
	
		
	}
	
	
	public Pile getCenterPile()
	{
		return centerPile;
	}
	
	public Pile getBurnPile()
	{
		return burnPile;
	}
}