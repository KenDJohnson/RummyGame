
public class Card {
	private String num;
	private char suit;
	
	public Card(String num, char suit)
	{
		this.num = num;
		this.suit = suit; 
	}
	
	public char suitSym()
	{
		char result = 0;
		if (this.suit =='H')
		{
			result = '\u2665';
		}
		else if (this.suit == 'D')
		{
			result = '\u2666';
		}
		else if (this.suit == 'S')
		{
			result = '\u2660';
		}
		else if (this.suit == 'C')
		{
			result = '\u2663';
		}
		return result;
	}

	public String getNum() {
		return num;
	}
	
	public int getValue()
	{
		return Integer.parseInt(getNum());
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
		return "[" + num + suitSym() + "]";
	}
	
	
}
