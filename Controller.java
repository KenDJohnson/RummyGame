import java.util.*;

public class Controller{

	protected Player[] players; 

	protected static Pool pool; 

	protected static Deck deck;

	protected static LinkedList<Card> discardPile;

	public Controller(int numOfHumans, int numOfBots){

		players = new Player[1 + numOfBots];

		players[0] = new Human();

		for(int i = 1; i < players.length; i++){
	//		players[i] = new Bot();
		}


		deck = new Deck(false);

		discardPile = new LinkedList<Card>();

		pool = new Pool();

	}

	public void display(){

		System.out.println("Player " + players);

		System.out.println("Discard Pile " + discardPile);

		//System.out.println("Player's hand " + HumanPlayer.getHand());

	}

	public static int playCards(Card[] cards){
		return pool.score(cards);
	}

	public static void discard(Card card){

		discardPile.push(card); //adds discard to the top of the pile. 

	}

	public static Card[] giveCard(String source, int numberOfCards){ 

		Card[] n = new Card[numberOfCards]; 

		if(source == "discard"){

			for(int i = 0; i < n.length; i++){
				n[i] = discardPile.pop();
			}

		}
		else if(source == "deck"){

			n[0] = deck.pop();

		}
		return n;
	}

	public static void main(String[] args){

		Controller play = new Controller(1,1);
		play.display();


	}
}