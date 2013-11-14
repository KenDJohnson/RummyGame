import java.util.*;
public class Human extends Player{
	private Collection<Card> hand;
	private int score;
	private Card cantDiscard;
	public static Pool pool;
	Scanner scan = new Scanner(System.in); //scanner to get input from the player
	
	public void draw()
	{
		int cards;
		String source;
		System.out.println("Where would you like to draw from?(discard or deck)");
		source = scan.next();
		if(source.equals("discard"))
		{
			System.out.println("How many cards would you like to draw?");
			cards = scan.nextInt();
		}
		else
		{
			cards = 1;
		}
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
