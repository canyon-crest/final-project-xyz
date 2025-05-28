
/*** CODE FROME ELEVENS' LAB ***/
public class Card{

	private String suit;
	private String rank;
	private int pointValue;
	private String cardFileName;


   /**
	 * Creates a new <code>Card</code> instance.
	 *
	 * @param cardRank  a <code>String</code> value
	 *                  containing the rank of the card
	 * @param cardSuit  a <code>String</code> value
	 *                  containing the suit of the card
	 * @param cardPointValue an <code>int</code> value
	 *                  containing the point value of the card
	 */
	public Card(String cardRank, String cardSuit, int cardPointValue) {
		//initializes a new Card with the given rank, suit, and point value
		rank = cardRank;
		suit = cardSuit;
		pointValue = cardPointValue;
		
		String str = "cards/";
		str += cardRank + cardSuit;
		str += ".GIF";
		cardFileName = str;
	}

	public String getCardFileName() {
		return cardFileName;
	}

	/**
	 * Accesses this <code>Card's</code> suit.
	 * @return this <code>Card's</code> suit.
	 */
	public String getSuit() {
		return suit;
	}

	//@return this card's rank
	public String getRank() {
		return rank;
	}

	//@return this card's pointValue
	public int getPointValue() {
		return pointValue;
	}

	//@return true if the card is a face card and false otherwise
	public boolean isFaceCard() {
		return (pointValue > 10 || pointValue == 1);
	}
	
	//Give the number of cards the next player should place if the previous player plays this
	//@return int representing number of cards
	public int mandatoryPlace()
	{
		if(pointValue == 1) return 4;
		if(pointValue == 13) return 3;
		if(pointValue == 12) return 2;
		return 1;
	}
	
	/** Compare this card with the argument.
	 * @param otherCard the other card to compare to this
	 * @return true if the rank, suit, and point value of this card
	 *              are equal to those of the argument;
	 *         false otherwise.
	 */
	
	public boolean matches(Card otherCard) {
		return otherCard.getSuit().equals(this.getSuit())
			&& otherCard.getRank().equals(this.getRank())
			&& otherCard.getPointValue() == this.getPointValue();
	}

	/**
	 * Converts the rank, suit, and point value into a string in the format
	 *     "[Rank] of [Suit] (point value = [PointValue])".
	 * This provides a useful way of printing the contents
	 * of a <code>Deck</code> in an easily readable format or performing
	 * other similar functions.
	 *
	 * @return a <code>String</code> containing the rank, suit,
	 *         and point value of the card.
	 */
	@Override
	public String toString() {
		return rank + " of " + suit + " (point value = " + pointValue + ")";
	}
}