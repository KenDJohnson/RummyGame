import java.util.*;
public class Player {
	private ArrayList<Card> hand;
	private int score;
	private Card cantDiscard;
	public static Pool pool;
	Scanner scan = new Scanner(System.in); //scanner to get input from the player
	
	public void draw()
	{
		int cards;
		Stack source;
		Controller.giveCard(source);
		
	}
	
	public void play()
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		boolean worked = false;
		int value =0;
		while(!worked)
		{
			value = Controller.playCards(cards);
			if(value>0)
			{
				worked = true;
			}
		}
		score = score+value;
	}
	
	public void discardFromHand()
	{
		Card discard;
		Controller.discard(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Card getCantDiscard() {
		return cantDiscard;
	}

	public void setCantDiscard(Card cantDiscard) {
		this.cantDiscard = cantDiscard;
	}

	public static Pool getPool() {
		return pool;
	}

	public static void setPool(Pool pool) {
		Player.pool = pool;
	}

	public Scanner getScan() {
		return scan;
	}

	public void setScan(Scanner scan) {
		this.scan = scan;
	}
	
	
}
