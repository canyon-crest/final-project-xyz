public class Player{
	private String username;
	private Pile myPile;
	private Game myGame;
	private String sKey;
	private String pKey;
	
	public Player(String username, Pile myPile, Game myGame, String pKey, String sKey)
	{
		this.username = username;
		this.myPile = myPile;
		this.myGame = myGame;
		this.pKey = pKey;
		this.sKey = sKey;

	}
	
	
	//Place the top card from the current pile onto other pile
	//@param the other pile to be placed on
	//@return the card that was placed onto the other pile
	public Card placeCard(Pile otherPile) {
		if(myPile.getSize() == 0) 
			return null;
		Card c = myPile.drawCard();
		otherPile.addCard(c);
		return c;
	}
	
	
	
	//Simulates the slapping of a player. If it is a slap on Game, it gives the player the cards
	//Otherwise, the player burns a card. A player cannot slap if they have no cards
	//@return true if the player was able to slap and false if the player has no cards or burned. 
	public boolean slap() {
		if(myPile.getSize() == 0) 
			return false;
		
		if (myGame.isASlap())
		{
			takeCenterPile();
			return true;
		}
		else
		{
			burn();
			return false;
		}
	}
	
	
	//Adds a pile to the player's pile
	//@param pile to be added
	public void addPile(Pile other)
	{
		myPile.addPile(other);
	}
	
	//removes the top card from player's pile and places it a burn pile
	public void burn() {
		myGame.getBurnPile().addCard(myPile.drawCard());
	}
	
	//Gives Player all the cards in the center. 
	public void takeCenterPile()
	{
		myPile.addPile(myGame.getBurnPile());
		myPile.addPile(myGame.getCenterPile());
	}
	
	//GETTERS ARE BELOW
	
	//@return player username
	public String getUsername()
	{
		return username;
	}
	
	//@return player's pile
	public Pile getPile()
	{
		return myPile;
	}
	
	//@return player's game
	public Game getGame()
	{
		return myGame;
	}
	
	//@return player's slap keybind
	public String getSKey()
	{
		return sKey;
	}
	
	//@return player's place card keybind
	public String getPKey()
	{
		return pKey;
	}
}