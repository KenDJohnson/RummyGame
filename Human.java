import java.util.*;
public class Human implements Player{
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
		Controller.giveCard(source, cards);
		
	}
	
	public void play()
	{
		
		Card[] cards = new Card[12];
		boolean worked = false;
		int value = 0;
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
	//	discard(card);
	}

	@Override
	public ArrayList<Card> getHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setScore(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card getCantDiscard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCantDiscard(Card cantDiscard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Scanner getScan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScan(Scanner scan) {
		// TODO Auto-generated method stub
		
	}
}
