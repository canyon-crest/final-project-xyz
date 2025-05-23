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
	
	public Card placeCard(Pile otherPile) {
		if(myPile.getSize() == 0) 
			return null;
		Card c = myPile.drawCard();
		otherPile.addCard(c);
		return c;
	}
	
	
	
	
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
	
	public void addPile(Pile other)
	{
		myPile.addPile(other);
	}
	
	public void burn() {
		myGame.getBurnPile().addCard(myPile.drawCard());
	}
	
	
	public void takeCenterPile()
	{
		myPile.addPile(myGame.getBurnPile());
		myPile.addPile(myGame.getCenterPile());
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public Pile getPile()
	{
		return myPile;
	}
	
	public Game getGame()
	{
		return myGame;
	}
	
	public String getSKey()
	{
		return sKey;
	}
	
	public String getPKey()
	{
		return pKey;
	}
}