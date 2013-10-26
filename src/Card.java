
public class Card {
	private String num;
	private char suit;
	
	public Card (String num, char suit)
	{
		this.num = num;
		this.suit = suit;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public char getSuit() {
		return suit;
	}

	public void setSuit(char suit) {
		this.suit = suit;
	}

	@Override
	public String toString() {
		return "[" + num + suit + "]";
	}
	
	
}
