import java.util.*;
public class Player {
	private Collection<Card> hand;
	private int score;
	private Card cantDiscard;
	public static Pool pool;
	Scanner scan = new Scanner(System.in); //scanner to get input from the player
	
	public void draw()
	{
		int cards;
		Stack source;
		giveCard(source, cards);
		
	}
	
	public void play()
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		boolean worked = false;
		int value;
		while(!worked)
		{
			value = playCards(cards);
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
		discard(card);
	}
	
}
