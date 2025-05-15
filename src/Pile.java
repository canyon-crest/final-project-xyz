import java.util.ArrayList;

public class Pile
{
	//top card represented by index 0;
	private ArrayList<Card> cardList;
	
	public Pile() 
	{
		resetPile();
	}
	
	
	public void resetPile() 
	{
		cardList = new ArrayList<Card>();
	}
	
	public void addPile(Pile other) 
	{
		ArrayList<Card> otherCardList = other.getCardList();
		for(Card c : otherCardList)
			cardList.add(c);
		other.resetPile();
	}
	
	public Card drawCard()
	{
		return cardList.remove(0);
	}
	
	public int getSize()
	{
		return cardList.size();
	}
	
	public ArrayList<Card> getCardList()
	{
		return cardList;
	}
	
	
	
}