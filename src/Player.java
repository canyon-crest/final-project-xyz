public class Player{
	private String username;
	private Pile myPile;
	private Game myGame;
	
	public Player(String username, Pile myPile, Game myGame)
	{
		this.username = username;
		this.myPile = myPile;
		this.myGame = myGame;
	}
	
	public void placeCard(Pile otherPile) {
		otherPile.addCard(myPile.drawCard());
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
//		if (myPile.getCardList().size() == 0)
//		{
//			myGame.endGame();
//		}
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public Pile getPile()
	{
		return myPile;
	}
	
}