import java.util.*;
public interface Player {
	
	public void draw();
	
	public void play();
	
	public void discardFromHand();

	public ArrayList<Card> getHand();

	public void setHand(ArrayList<Card> hand);

	public int getScore();

	public void setScore(int score);

	public Card getCantDiscard();

	public void setCantDiscard(Card cantDiscard);

	public Scanner getScan();

	public void setScan(Scanner scan);
	
	
}
