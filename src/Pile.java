import java.util.ArrayList;

public class Pile
{
	//top card represented by index 0;
	private ArrayList<Card> cardList;
	
	public Pile() 
	{
		resetPile();
	}
	
	//Clears the arrayList 
	public void resetPile() 
	{
		cardList = new ArrayList<Card>();
	}
	
	//Adds the contents from the other pile to this pile
	//@param another Pile
	public void addPile(Pile other) 
	{
		ArrayList<Card> otherCardList = other.getCardList();
		for(Card c : otherCardList)
			cardList.add(c);
		other.resetPile();
	}
	
	//Add a card to current pile
	//@param Card c to be added
	public void addCard(Card c)
	{
		cardList.add(c);
	}
	
	//Draw the top card from the pile
	//@return Card at index zero of array
	public Card drawCard()
	{
		return cardList.remove(0);
	}
	
	//Get the size of the pile
	//@return the size of the arrayList
	public int getSize()
	{
		return cardList.size();
	}
	
	//Get the arrayList
	//@return the arrayList holding all the a cards
	public ArrayList<Card> getCardList()
	{
		return cardList;
	}
	
	//Get the card at index i
	//@param integer index i
	//@return the card at the position index i
	public Card get(int i)
	{
		if(i >= 0 && i < getSize())
			return cardList.get(i);
		return null;
	}
	
	
	
}