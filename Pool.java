/* 11/14 update: changed to accommodate Card having String value and char suit
 * 11/25 update: fixed issue with printing
 */

import java.util.*;

public class Pool { 
	
	private ArrayList<Meld> pool;
	
	public Pool()
	{
		pool = new ArrayList<Meld>();
	}
	
	public int score(Card[] playedCards)
	{
		/* Scoring Algorithm:
		 * 
		 * Loop through pool, until can successfully add playedCards to an already played sequence or set. If successfully added, stop looping
		 * and return valueCards(playedCards).
		 * If cannot add, check if play is valid on its own. If valid, add to pool and return
		 * valueCards(playedCards).
		 * If can't add and not valid by itself, return -1.
		 * 
		 */
		boolean isLegal = false;
		
		for(int i=0; i<pool.size() && !isLegal; i++)// loop through pool and try to append to existing melds, stop if successful append
		{
			isLegal = pool.get(i).append(playedCards);// update isLegal with success/failure of each append
		}
		
		if(isLegal)// if cards were legally played, return score
		{
			return valueCards(playedCards);
		}
		
		// cards cannot be appended to existing meld, can they be played by themselves?
		
		if(playedCards.length<3)// cannot form a legal meld with less than 3 cards, illegal play, return -1
		{
			return -1;
		}
		
		Card[] remainingCards = Arrays.copyOfRange(playedCards, 1, playedCards.length); // put all but first card of playedCards into a new array
		
		// try playing cards as a sequence
		Sequence seq = new Sequence(playedCards[0]);
		if(seq.append(remainingCards)) // if cards can be legally played as a sequence, add the new sequence to pool and return score
		{
			pool.add(seq);
			return valueCards(playedCards);
		}
		
		// try playing cards as a set
		Set set = new Set(playedCards[0]);
		if(set.append(remainingCards)) // if cards can be legally played as a set, add the new set to pool and return score
		{
			pool.add(set);
			return valueCards(playedCards);
		}
		
		// cards cannot be played on an existing meld and are not legal on their own, illegal play, return -1		
		return -1;
	}
	
	private int valueCards(Card[] cards)
	{
		int value = 0;
		for(int i=0; i<cards.length; i++)
		{
			value += cards[i].getValue();
		}
		return value;
	}
	
	@Override
	public String toString() 
	{
		String result = "";
		
		for(int i=0;i<pool.size();i++)
		{
			result+= pool.get(i).getCards() + "\n";
		}
		
		return result;
	}
	
	private interface Meld
	{
		
		/* append(Cards[] cards) attempts to add cards to hand
		 * updates hand and returns true if legal
		 * does not update hand and returns false if illegal 
		 */
		public boolean append(Card[] cards);
		
		// getCards() returns the cards in this meld in an ArrayList of Cards
		public ArrayList<Card> getCards();
	}
	
	private class Sequence implements Meld
	{
		private ArrayList<Integer> values;
		private char suit;
		
		public Sequence(Card card) // Sequence constructor: all sequences start out as 1 card, more can then be appended
		{
			values = new ArrayList<Integer>();
			values.add(card.getValue());
			suit = card.getSuit();
		}
		
		public boolean append(Card[] cards)
		{
				for(int i=0; i<cards.length; i++) // loop through cards to determine if they have the correct suit
				{
					if(suit!=(cards[i].getSuit())) // if suit does not match, failed to append
							return false;
				}
				
				ArrayList<Integer> temp = new ArrayList<Integer>(); // create temporary ArrayList containing all values
				temp.addAll(values);
				for(int i=0; i<cards.length; i++)
				{
					temp.add(cards[i].getValue());
				}
				
				Collections.sort(temp); // sort temp
				
				for(int i=0; i<temp.size()-1; i++) // if values of temp are not consecutive, failed to append
				{
					if(temp.get(i+1)-temp.get(i)!=1)
						return false;
				}
				
				values = temp; // everything has same suit and is consecutive, set values equal to temp
			
				return true; // successful append
		}
		
		public ArrayList<Card> getCards()
		{
			ArrayList<Card> cards = new ArrayList<Card>();
			for(int i=0;i<values.size();i++)
			{
				if(values.get(i) == 1)
				{
					cards.add(new Card("A",suit));
				}
				else if(values.get(i) == 11)
				{
					cards.add(new Card("J",suit));
				}
				else if(values.get(i) == 12)
				{
					cards.add(new Card("Q",suit));
				}
				else if(values.get(i) == 13)
				{
					cards.add(new Card("K",suit));
				}
				else
				{
					cards.add(new Card(Integer.toString(values.get(i)),suit));
				}
			}
			return cards;
		}
	}
	
	private class Set implements Meld
	{
		private int value;
		private ArrayList<Character> suits;
		
		public boolean append(Card[] cards)
		{
			for(int i=0; i<cards.length; i++) // loop through cards to determine if they have the correct value
			{
				if(value != (cards[i].getValue())) // if value does not match, failed to append
						return false;
			}
			
			for(int i=0; i<cards.length; i++) // values are the same, add new suits
			{
				suits.add(cards[i].getSuit());
			}
			
			return true; // successful append
		}
		
		public ArrayList<Card> getCards()
		{
			ArrayList<Card> cards = new ArrayList<Card>();
			String tempValue;
			if(value == 1)
			{
				tempValue = "A";
			}
			else if(value == 11)
			{
				tempValue = "J";
			}
			else if(value == 12)
			{
				tempValue = "Q";
			}
			else if(value == 13)
			{
				tempValue = "K";
			}
			else
			{
				tempValue = Integer.toString(value);
			}
			
			for(int i=0;i<suits.size();i++)
			{
				
				cards.add(new Card(tempValue,suits.get(i)));
			}
			return cards;
		}
		
		public Set(Card card) // Set constructor: all sets start out as 1 card, more can then be appended
		{
			suits = new ArrayList<Character>();
			suits.add(card.getSuit());
			value = card.getValue();
		}
	}	
}
	
