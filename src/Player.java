public class Player{
	private String username;
	private Pile myPile;
	private Game myGame;
	private String sKey;
	private String pKey;
	
	public Player(String username, Pile myPile, Game myGame, String sKey, String pKey)
	{
		this.username = username;
		this.myPile = myPile;
		this.myGame = myGame;
		this.sKey = sKey;
		this.pKey = pKey;
	}
	
	public void placeCard(Pile otherPile) {
		otherPile.addCard(myPile.drawCard());
	}
	
	public String getSKey()
	{
		return sKey;
	}
	
	public String getPKey()
	{
		return pKey;
	}
	
	public void slap() {
		if (myGame.isASlap())
		{
			myPile.addPile(myGame.getBurnPile());
			myPile.addPile(myGame.getCenterPile());
		}
		else
		{
			burn();
		}
	}
	
	
	public void burn() {
		myGame.getBurnPile().addCard(myPile.drawCard());
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
	
}