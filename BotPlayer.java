import java.util.ArrayList;

public class BotPlayer implements Player {

	private ArrayList<Card> hand;//the bot's hand
	private int score;//the bot's score
	private ArrayList<ArrayList<ArrayList<Card>>> possible;//possible melds in the bot's hand
	private ArrayList<ArrayList<Card>> complete;//complete melds from the possible melds
	

	public BotPlayer() {
		score = 0;
		hand = Controller.giveCard("deck start", 7); //gets its 7 cards from
													//the deck

		hand = sort(hand); //sorts the hand

		possible = findMelds(hand); //finds possible melds in the hand
		
		complete = findComplete(possible); //finds the complete melds
	}

	@Override
	public void draw() {
		Card tempCard = Controller.discardPile.peek();	//temporary card to check if top card is useful
		ArrayList<Card> tempHand = new ArrayList<Card>();//temporary hand with tempCard to check if useful
		tempHand.addAll(hand); //duplicates the hand
		tempHand.add(tempCard);	//adds the possible card
		tempHand = sort(tempHand); //sorts the hand for testing purposes
		//calculates the possibilities of tempHand
		ArrayList<ArrayList<ArrayList<Card>>> tempPossible = findMelds(tempHand);
		//uses the possibilities to calculate the complete melds
		ArrayList<ArrayList<Card>> tempComplete = findComplete(tempPossible);
		
		//finds the size of the complete melds with the possible card
		int tempCompleteSize = 0;
		for(int i = 0;i<tempComplete.size();i++)
			tempCompleteSize += tempComplete.get(i).size();
		//finds the size of complete melds in hand
		int completeSize = 0;
		for(int j = 0;j<complete.size();j++)
			completeSize += complete.get(j).size();
		
		//if the complete melds with the tempCard is more than already in hand
		//draw the card from the discardPile
		if(tempCompleteSize > completeSize)
		{
			hand.add(Controller.discardPile.pop());
			possible = tempPossible;
			complete = tempComplete;
		}
		//otherwise draw the top card from the deck
		else
		{
			hand.add(Controller.deck.pop());
			hand = sort(hand);
			possible = findMelds(hand);
			complete = findComplete(possible);
		}
		
	}

	@Override
	public void play() {
		
		for(int w =0;w<complete.size();w++) //loop through complete to get list to play
		{ 
			ArrayList<Card> completeSet = new ArrayList<Card>();
			for(int j =0;j<complete.get(w).size() && (hand.size()-completeSet.size())>1;j++) // put cards from a complete set into an ArrayList<Card>, make sure not to play all of your cards (need 1 left to discard)
			{ 
				completeSet.add(complete.get(w).get(j));
			}
			
			Card[] completeSetArray = new Card[completeSet.size()];
			for(int j=0;j<completeSet.size();j++)
				completeSetArray[j] = completeSet.get(j);
						
			int value = Controller.playCards(completeSetArray); //find the value
				//of playing the cards 
			if(value > 0) //if they're accepted by pool
			{
				score += value;	//increment score
				hand.removeAll(complete.get(w)); //remove the cards from the hand
				System.out.println("\nPlayed: " + complete.get(w) + " for " + value + " points.");
				System.out.println("\nPool\n---------------------\n" + Controller.pool + "---------------------\n");
			}
		}
		
		for(int i = 0;i<hand.size() && hand.size()>1;i++) //loop through hand to play and cards that can be  			
		{	                                              //added to the pool (once again, leaving 1 to discard)
			Card[] card = new Card[1];
			card[0] = hand.get(i);
			int value = Controller.playCards(card);
			if(value>0)
			{
				score += value;
				hand.remove(card[0]);
				System.out.println("\nPlayed: " + card[0] + " for " + value + " points.");
				System.out.println("\nPool\n---------------------\n" + Controller.pool + "---------------------\n");
			}
		}

		hand = sort(hand);
		possible = findMelds(hand);
		
	}

	@Override
	public void DiscardFromHand() {
		Card discard = hand.get(0); // default to first card in hand
		for(int w = 0;w<possible.size();w++)
			for(int q = 0;q<possible.get(w).size();q++)
				for (int i = 0; i < hand.size() - 1; i++)
			// goes through hand
					if (!possible.get(w).get(q).contains(hand.get(i))) // finds first card that
						discard = hand.get(i); // isnt in possible and selects is

		Controller.discard(discard); // removes 

		hand.remove(discard);
		
		System.out.println("\nDiscarding: " + discard + "\n");
	}

	
	//getters and setters
	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;

	}

	@Override
	public Card getCantDiscard() {
		// does nothing but needs to be here to implement player
		return null;
	}

	@Override
	public void setCantDiscard(Card cantDiscard) {
		// does nothing but needs to be here to implement player

	}

	
	
	
	
	public ArrayList<ArrayList<ArrayList<Card>>> findMelds(ArrayList<Card> hand) {
		//List of list of list of cards. Deepest list contains the cards that are possible
		//the next deepest is a list to hold each individual set of possibilities
		//the next deepest (top list) holds the possibilities for each suit
		ArrayList<ArrayList<ArrayList<Card>>> melds = new ArrayList<ArrayList<ArrayList<Card>>>();
		ArrayList<ArrayList<Card>> sets = new ArrayList<ArrayList<Card>>();

		
		
		// split the hand into suits
		ArrayList<Card> hearts = new ArrayList<Card>();
		for (int i = 0; i <= hand.size() - 1; i++)
			if (hand.get(i).getSuit() == 'H')
				hearts.add(hand.get(i));
		ArrayList<Card> clubs = new ArrayList<Card>();
		for (int i = 0; i <= hand.size() - 1; i++)
			if (hand.get(i).getSuit() == 'C')
				clubs.add(hand.get(i));
		ArrayList<Card> diamonds = new ArrayList<Card>();
		for (int i = 0; i <= hand.size() - 1; i++)
			if (hand.get(i).getSuit() == 'D')
				diamonds.add(hand.get(i));
		ArrayList<Card> spades = new ArrayList<Card>();
		for (int i = 0; i <= hand.size() - 1; i++)
			if (hand.get(i).getSuit() == 'S')
				spades.add(hand.get(i));
		
		//holds the possible heart runs
		ArrayList<ArrayList<Card>> heartRuns = new ArrayList<ArrayList<Card>>(); 
		melds.add(heartRuns);//adds this to melds

		//iterate through the list of lists of possible hearts
		for (int j = 0; j < hearts.size() - 1; j++) {
			//iterate though the sublists in hearts
			for (int i = j; i < hearts.size() - 1; i++) {
				//if the difference in value of the two cards is less that 2
				//and this is the first time time, create a new list and add the first card
				//then add the second card(regardless if first time or not)
				if (hearts.get(i+1).getValue() - hearts.get(i).getValue() <= 2) {
					if(i==j)
					{
						melds.get(0).add(new ArrayList<Card>());
						melds.get(0).get(melds.get(0).size()-1).add(hearts.get(i));
					}
					melds.get(0).get(melds.get(0).size()-1).add(hearts.get(i+1));
				} 
				else{
					j = i;
					break;
				}
				if(i==hearts.size()-2)
					j=i;
			}
		}
		
		//holds the possible club runs
		ArrayList<ArrayList<Card>> clubRuns = new ArrayList<ArrayList<Card>>(); 
		melds.add(clubRuns);//adds this to melds												
		
		//iterate through the list of runs
		for (int j = 0; j < clubs.size() - 1; j++) {
			//iterate through the cards in the runs
			for (int i = j; i < clubs.size() - 1; i++) {
				//if the difference in value of the two cards is less that 2
				//and this is the first time time, create a new list and add the first card
				//then add the second card(regardless if first time or not)
				if (clubs.get(i+1).getValue() - clubs.get(i).getValue() <= 2) {
					if(i==j)
					{
						melds.get(1).add(new ArrayList<Card>());
						melds.get(1).get(melds.get(1).size()-1).add(clubs.get(i));
					}
					melds.get(1).get(melds.get(1).size()-1).add(clubs.get(i+1));
				}
				else
				{
					j = i;
					break;
				}
				if(i==clubs.size()-2)
					j=i;

			}
		}

		//holds the possible runs in of diamonds
		ArrayList<ArrayList<Card>> diamondRuns = new ArrayList<ArrayList<Card>>(); 
		melds.add(diamondRuns);	//add the list to melds
		
		//iterates through the list of possible runs
		for (int j = 0; j < diamonds.size() - 1; j++) {
			//iterates through the cards in the runs
			for (int i = j; i < diamonds.size() - 1; i++) {
				//if the difference in value of the two cards is less that 2
				//and this is the first time time, create a new list and add the first card
				//then add the second card(regardless if first time or not)
				if (diamonds.get(i+1).getValue() - diamonds.get(i).getValue() <= 2) {
					if(i==j)
					{
						melds.get(2).add(new ArrayList<Card>());
						melds.get(2).get(melds.get(2).size()-1).add(diamonds.get(i));
					}
					melds.get(2).get(melds.get(2).size()-1).add(diamonds.get(i+1));
				}
				else
				{
					j = i;
					break;
				}
				if(i==diamonds.size()-2)
					j=i;
			}
		}

		//holds the possible runs of spades
		ArrayList<ArrayList<Card>> spadeRuns = new ArrayList<ArrayList<Card>>(); 
		melds.add(spadeRuns);//adds the list to melds																				
		
		//iterate through the list of runs
		for (int j = 0; j < spades.size() - 1; j++) {
			//iterate through the cards in the runs
			for (int i = j; i < spades.size() - 1; i++) {
				//if the difference in value of the two cards is less that 2
				//and this is the first time time, create a new list and add the first card
				//then add the second card(regardless if first time or not)
				if (spades.get(i+1).getValue() - spades.get(i).getValue() <= 2) {
					if(i==j)
					{
						melds.get(3).add(new ArrayList<Card>());
						melds.get(3).get(melds.get(3).size()-1).add(spades.get(i));
					}
					
					melds.get(3).get(melds.get(3).size()-1).add(spades.get(i + 1));
				}
				else
				{
					j = i;
					break;
				}
				if(i==spades.size()-2)
					j=i;
			}
		}
		//temporary hand to hold which possibilities are removed from to keep from getting dups
		ArrayList<Card> tempHand = new ArrayList<Card>();
		//copies hand
		tempHand.addAll(hand);
		//adds list of sets to melds
		melds.add(sets);
		
		//iterates through the tempHand
		for(int j = 0; j < tempHand.size();j++)
		{
			//temp card to compare to other cards in hand
			Card temp = tempHand.get(j);
			//iterates through tempHand and next pos
			for(int i = j+1; i < tempHand.size();i++)
			{
				//if they are the same value and the first card being compared to (j) is not
				//on the list, create a new list and add the first card
				//add the second card
				if(temp.getValue()==tempHand.get(i).getValue())
				{
					if(melds.get(4).isEmpty() || melds.get(4).get(melds.get(4).size()-1).get(0).getValue() != temp.getValue())
					{
						melds.get(4).add(new ArrayList<Card>());
						melds.get(4).get(melds.get(4).size()-1).add(temp);
					}
					melds.get(4).get(melds.get(4).size()-1).add(tempHand.remove(i));
					i--;
				}
			}
		}
		
		return melds;
	}

	public ArrayList<Card> sort(ArrayList<Card> hand) // used to sort the hand
	// before
	{ // finding possible melds.. quite inefficiently
		ArrayList<Card> sorted = new ArrayList<Card>();

		// adding possible hearts
		if (hand.contains(new Card("A", 'H')))
			sorted.add(new Card("A", 'H'));
		if (hand.contains(new Card("1", 'H')))
			sorted.add(new Card("1", 'H'));
		if (hand.contains(new Card("2", 'H')))
			sorted.add(new Card("2", 'H'));
		if (hand.contains(new Card("3", 'H')))
			sorted.add(new Card("3", 'H'));
		if (hand.contains(new Card("4", 'H')))
			sorted.add(new Card("4", 'H'));
		if (hand.contains(new Card("5", 'H')))
			sorted.add(new Card("5", 'H'));
		if (hand.contains(new Card("6", 'H')))
			sorted.add(new Card("6", 'H'));
		if (hand.contains(new Card("7", 'H')))
			sorted.add(new Card("7", 'H'));
		if (hand.contains(new Card("8", 'H')))
			sorted.add(new Card("8", 'H'));
		if (hand.contains(new Card("9", 'H')))
			sorted.add(new Card("9", 'H'));
		if (hand.contains(new Card("10", 'H')))
			sorted.add(new Card("10", 'H'));
		if (hand.contains(new Card("J", 'H')))
			sorted.add(new Card("J", 'H'));
		if (hand.contains(new Card("Q", 'H')))
			sorted.add(new Card("Q", 'H'));
		if (hand.contains(new Card("K", 'H')))
			sorted.add(new Card("K", 'H'));

		// adding possible clubs
		if (hand.contains(new Card("A", 'C')))
			sorted.add(new Card("A", 'C'));
		if (hand.contains(new Card("1", 'C')))
			sorted.add(new Card("1", 'C'));
		if (hand.contains(new Card("2", 'C')))
			sorted.add(new Card("2", 'C'));
		if (hand.contains(new Card("3", 'C')))
			sorted.add(new Card("3", 'C'));
		if (hand.contains(new Card("4", 'C')))
			sorted.add(new Card("4", 'C'));
		if (hand.contains(new Card("5", 'C')))
			sorted.add(new Card("5", 'C'));
		if (hand.contains(new Card("6", 'C')))
			sorted.add(new Card("6", 'C'));
		if (hand.contains(new Card("7", 'C')))
			sorted.add(new Card("7", 'C'));
		if (hand.contains(new Card("8", 'C')))
			sorted.add(new Card("8", 'C'));
		if (hand.contains(new Card("9", 'C')))
			sorted.add(new Card("9", 'C'));
		if (hand.contains(new Card("10", 'C')))
			sorted.add(new Card("10", 'C'));
		if (hand.contains(new Card("J", 'C')))
			sorted.add(new Card("J", 'C'));
		if (hand.contains(new Card("Q", 'C')))
			sorted.add(new Card("Q", 'C'));
		if (hand.contains(new Card("K", 'C')))
			sorted.add(new Card("K", 'C'));

		// adding possible diamonds
		if (hand.contains(new Card("A", 'D')))
			sorted.add(new Card("A", 'D'));
		if (hand.contains(new Card("1", 'D')))
			sorted.add(new Card("1", 'D'));
		if (hand.contains(new Card("2", 'D')))
			sorted.add(new Card("2", 'D'));
		if (hand.contains(new Card("3", 'D')))
			sorted.add(new Card("3", 'D'));
		if (hand.contains(new Card("4", 'D')))
			sorted.add(new Card("4", 'D'));
		if (hand.contains(new Card("5", 'D')))
			sorted.add(new Card("5", 'D'));
		if (hand.contains(new Card("6", 'D')))
			sorted.add(new Card("6", 'D'));
		if (hand.contains(new Card("7", 'D')))
			sorted.add(new Card("7", 'D'));
		if (hand.contains(new Card("8", 'D')))
			sorted.add(new Card("8", 'D'));
		if (hand.contains(new Card("9", 'D')))
			sorted.add(new Card("9", 'D'));
		if (hand.contains(new Card("10", 'D')))
			sorted.add(new Card("10", 'D'));
		if (hand.contains(new Card("J", 'D')))
			sorted.add(new Card("J", 'D'));
		if (hand.contains(new Card("Q", 'D')))
			sorted.add(new Card("Q", 'D'));
		if (hand.contains(new Card("K", 'D')))
			sorted.add(new Card("K", 'D'));

		// adding possible spades
		if (hand.contains(new Card("A", 'S')))
			sorted.add(new Card("A", 'S'));
		if (hand.contains(new Card("1", 'S')))
			sorted.add(new Card("1", 'S'));
		if (hand.contains(new Card("2", 'S')))
			sorted.add(new Card("2", 'S'));
		if (hand.contains(new Card("3", 'S')))
			sorted.add(new Card("3", 'S'));
		if (hand.contains(new Card("4", 'S')))
			sorted.add(new Card("4", 'S'));
		if (hand.contains(new Card("5", 'S')))
			sorted.add(new Card("5", 'S'));
		if (hand.contains(new Card("6", 'S')))
			sorted.add(new Card("6", 'S'));
		if (hand.contains(new Card("7", 'S')))
			sorted.add(new Card("7", 'S'));
		if (hand.contains(new Card("8", 'S')))
			sorted.add(new Card("8", 'S'));
		if (hand.contains(new Card("9", 'S')))
			sorted.add(new Card("9", 'S'));
		if (hand.contains(new Card("10", 'S')))
			sorted.add(new Card("10", 'S'));
		if (hand.contains(new Card("J", 'S')))
			sorted.add(new Card("J", 'S'));
		if (hand.contains(new Card("Q", 'S')))
			sorted.add(new Card("Q", 'S'));
		if (hand.contains(new Card("K", 'S')))
			sorted.add(new Card("K", 'S'));

		return sorted;
	}
	
	//looks through the possible melds and sees if any of the lists are complete melds
	public ArrayList<ArrayList<Card>> findComplete(ArrayList<ArrayList<ArrayList<Card>>> possible)
	{
		//List of list of melds to be returned
		ArrayList<ArrayList<Card>> complete = new ArrayList<ArrayList<Card>>();
		//iterates through first list
		for(int q=0;q<possible.size()-1;q++)
		{
			//iterate through the middle list(list of possible lits)
			for(int w =0;w<possible.get(q).size();w++)
			{
				//iterates through individual cards in the lists
				for (int j = 0; j < possible.get(q).get(w).size() - 1; j++) {
					//this will hold a possible meld to see if complete
					ArrayList<Card> testComplete = new ArrayList<Card>();
					//iterates through the deepest list at a position further
					for (int i = j; i < possible.get(q).get(w).size() - 1; i++) {
						//adds them to the testComplete if the distance between them is 1 (in a row)
						//if first time, create new list and add first card
						//add the second card
						if (possible.get(q).get(w).get(i+1).getValue() - possible.get(q).get(w).get(i).getValue() == 1) {
							if(i==j)
							{
								testComplete = new ArrayList<Card>();
								testComplete.add(possible.get(q).get(w).get(i));
							}
							testComplete.add(possible.get(q).get(w).get(i+1));
						} 
						else{
							//if cards are not in a row, but 3 cards have been added
							//add the set to complete
							if(testComplete.size()>=3)
								complete.add(testComplete);
							j = i;//check the rest of the list
							break;
						}
						//if the last value in the possible meld was added to testComplete
						//and all the values have been checked, add the testComplete to complete
						if(i==possible.get(q).get(w).size()-2)
						{
							j=i;
							if(testComplete.size()>=3)
								complete.add(testComplete);
						}
					}
				}
			}
		}
		//iterates though to see if the sets are complete(have 3 or more
		//cards of the same value in them)
		for(int w = 0; w < possible.get(possible.size()-1).size();w++)
		{
			if(possible.get(possible.size()-1).get(w).size() >= 3)
			{
				complete.add(possible.get(possible.size()-1).get(w));
			}
		}
		
		return complete;
	}

	@Override
	public void setHand(ArrayList<Card> giveCard) {
		// TODO Auto-generated method stub
		
	}
	

}
