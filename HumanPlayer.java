/* 11/25 update: -now forced to play last card picked up from discard if more than 1 is drawn
 * 				 -hand and played cards are now shown as cards are being selected to play
 * 12/8 update: can now play 0 cards (so can't get stuck if you say you want to play cards but only have 1 card in your hand)
 */

import java.util.*;

public class HumanPlayer implements Player{
	
	private ArrayList<Card> hand;
	private int score;
	private Card cantDiscard;

	public HumanPlayer()
	{
		hand = Controller.giveCard("deck start", 7);
		
//		hand = new ArrayList<Card>();
//		hand.add(new Card("2",'C'));
//		hand.add(new Card("3",'C'));
//		hand.add(new Card("4",'C'));
//		hand.add(new Card("9",'C'));
		
		score=0;
		cantDiscard = null;
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



	public void draw()
     {
		 Scanner scan = new Scanner(System.in); // create scanner to get info from player
		 int numCards = 1; // this will be the number of cards drawn, defaults to 1 and only changes if drawing from discard
		 String source = null; // this will be the source of the drawn card
		 
		 boolean legalDraw = false;
		 
		 while(!legalDraw)
		 {
			 boolean validResponse = false;
			 while(!validResponse) // loop keeps asking for input until it receives a valid response
			 {
				 System.out.println("Where would you like to draw from?(discard or deck)");
				 source = scan.next();
				 if(source.equals("discard")) // source is discard, how many cards to draw?
				 {
					 while(!validResponse) // another loop asking for input until it receives a valid response
					 {
		                 System.out.println("How many cards would you like to draw?");
		                 try
		                 {
		                 numCards = scan.nextInt();
		                 }
		                 catch(InputMismatchException e) // this exception will be thrown if anything but an integer is input
		                 {
		                	 System.out.println("Please enter an integer number of cards.");
		                	 scan.nextLine(); // clears scanner for new input (important)
		                	 continue; // start next loop iteration
		                 }
		                 if(numCards>0 && numCards<=Controller.discardPile.size()) // must draw at least 1 card and can't draw more cards than available
		                 {
			                 validResponse = true; 
		                 }
		                 else // invalid response, try again
		                 {
		                	 System.out.println("You can't draw that many cards.");
		                 }
					 }
				 }
				 else if(source.equals("deck")) // source is deck, draw 1 card
				 {
					 validResponse = true;
				 }
				 else // invalid source, try again
				 {
					 System.out.println("Invalid source. Please choose discard or deck.");
				 }
			 }
			 
			 ArrayList<Card> temp = Controller.giveCard(source, numCards); // pass results on to Controller to get cards
			
			 hand.addAll(temp); // add new cards to hand
			 
			 if(source.equals("deck")) // if drew card from deck, can discard anything, legal draw
			 {
				 cantDiscard = null;
				 legalDraw = true;
			 }
			 else // else, drew from discard
			 {
				 
				 cantDiscard = temp.get(temp.size()-1); // can't discard the last card you picked up from the discard pile
				 
				 if(temp.size()==1) // if drew 1 card, then done, legal draw
				 {
					 legalDraw = true;
				 }
				 else // if drew more than 1 card from discard, must play last card picked up (currently stored in cantDiscard)
				 {
					 legalDraw = play(cantDiscard);
					 
					 if(!legalDraw) // illegal draw means couldn't play last card picked up
					 {
						 for(int i=temp.size()-1;i>=0;i--) // need to re-discard picked up cards (stored in temp)
						 {
							 Controller.discard(temp.get(i));
							 hand.remove(temp.get(i));
						 }
						 
						 System.out.println("Since you cannot play the last card you picked up, you must draw again.");
						 System.out.println("\nHand: " + hand + "\nDiscard Pile: " + Controller.discardPile + "\nPool\n---------------------\n" + Controller.pool + "---------------------\n");

					 }
				 }
				 
			 }
			 
		 }
     }
	 
	 public void play()
	 {
		 Scanner scan = new Scanner(System.in); // create scanner to get info from player
		 int numCards=0; // this will be the number of cards to be played
		 Card[] cards; // this will be the array of cards being played
		 int value;
		 boolean alreadyPlayed;
		 
		 boolean keepPlaying = true;
		 while(keepPlaying) // this loop runs as long as the player wants to keep playing cards
		 {
			 System.out.println("Would you like to play any cards?");
			 String response = scan.next();
			 if(response.equals("no")) // if don't want to play, then done
			 {
				 keepPlaying = false;
				 continue;
			 }
			 else if(!response.equals("yes")) // if response is not yes or no, ask again
			 {
				 System.out.println("Invalid response. Please answer yes or no.");
				 continue;
			 }
			 // only get here if response is yes
			 
			 // find out how many cards to play
			 boolean validResponse = false;
			 while(!validResponse) // loop asking for input until it receives a valid response
			 {
	             System.out.println("How many cards would you like to play?");
	             try
	             {
	            	 numCards = scan.nextInt();
	             }
	             catch(InputMismatchException e) // this exception will be thrown if anything but an integer is input
	             {
	            	 System.out.println("Please enter an integer number of cards.");
	            	 scan.nextLine(); // clears scanner for new input (important)
	            	 continue; // start next loop iteration
	             }
	             if(numCards>=0 && numCards<=hand.size()-1)// can't play negative number of cards and must leave at least 1 card in hand to discard
	             {
	            	 validResponse = true; 
	             }
	             else // invalid input, ask again
	             {
	            	 System.out.println("You can't play that many cards.");
	             }
			 }
			 
			 if(numCards == 0) // if you want to play 0 cards, then:
			 {
				 System.out.println("You successfully played no cards."); // print that you successfully played 0 cards
				 // print 'new' states of hand, discard pile, and pool
				 System.out.println("\nHand: " + hand + "\nDiscard Pile: " + Controller.discardPile + "\nPool\n---------------------\n" + Controller.pool + "---------------------\n");
				 continue; // continue to next iteration of while(keepPlaying) loop
			 }
			 
			 // get cards to be played
			 cards = new Card[numCards];
			 Card tempCard = null;
			 
			 for(int i=0;i<numCards;i++)
			 {  
				 validResponse = false;
				 
				 while(!validResponse)// loop asking for input until it receives a valid response
				 {
					 if(i==0) // first time asking
					 {
						 System.out.println("What is the first card? (Example: 6 H)");
					 }
					 else // asking otherwise
					 {
						 System.out.print("\nWhat is the next card?" + "\nHand: " + hand + "\nPlaying: [");
						 for(int j=0;j<i;j++)
						 {
							 if(j!=i-1)
								 System.out.print(cards[j] + ", ");
							 else
								 System.out.print(cards[j]);
						 }
						 System.out.print("]\n");
					 }
					 
					 tempCard = new Card(scan.next(),scan.next().charAt(0)); // create temporary card based on input
					 
					 alreadyPlayed = false;
					 for(int j=0;j<i && !alreadyPlayed;j++)
					 {
						 alreadyPlayed = cards[j].equals(tempCard);
					 }
					 
					 if(hand.contains(tempCard) && !alreadyPlayed) // valid input if this card is in your hand
					 {
						 cards[i] = tempCard; // add card to cards
						 validResponse = true; // got a valid response
						 scan.nextLine(); // clear scan for next input
					 }
					 else // invalid card
					 {
						 System.out.println("Invalid card. Please enter a valid card in the form [rank] [suit].");
						 scan.nextLine(); // clear scan for next input
					 }
				 }
			}
			  // try to play your cards
			 value = Controller.playCards(cards);
			 if(value>0) // legal play
			 {
				 score += value; // add to score
				 System.out.println("\nYou scored " + value + " points. Your current score is " + score + "."); // you got points
				 for(int i=0; i<cards.length; i++) // remove played cards from hand
					{
						hand.remove(cards[i]);
					}
			 }
			 else // illegal play
			 {
				 System.out.println("That is not a legal play."); // display this, then start over
			 }
			 System.out.println("\nHand: " + hand + "\nDiscard Pile: " + Controller.discardPile + "\nPool\n---------------------\n" + Controller.pool + "---------------------\n");
		}
	}
	 
	public void DiscardFromHand()
	{
		Scanner scan = new Scanner(System.in); // scanner to get input
		Card tempCard; // temporary storage variable
		
		boolean validResponse = false;
		while(!validResponse) // loop asking for input until it receives a valid response
		{
			System.out.println("Which card do you want to discard? (Example: 6 H)");
			
			tempCard = new Card(scan.next(),scan.next().charAt(0)); // create temporary card based on input
			
			if(hand.contains(tempCard) && !tempCard.equals(cantDiscard)) // discard is valid if it's in your hand and it's not cantDiscard
			{
				Controller.discard(tempCard);
				
				hand.remove(tempCard); // remove discarded card 
				validResponse = true; // valid response
			}
			else // invalid response, try again
			{
				System.out.println("You can't discard that card.");
				scan.nextLine();
			}
		}
	}
	
	/* This method is similar to play, but requires that the card mustPlay is played by the player. 
	 * True is returned if a successful play is made. False is returned if the player chooses not to
	 * make a play using the card mustPlay.
	 */
	private boolean play(Card mustPlay)
	{
		 boolean successfulPlay = false;
		
		 Scanner scan = new Scanner(System.in); // create scanner to get info from player
		 int numCards=0; // this will be the number of cards to be played
		 Card[] cards; // this will be the array of cards being played
		 int value;
		 boolean alreadyPlayed;
		 
		 boolean keepPlaying = true;
		 while(keepPlaying) // this loop runs as long as the player wants to keep playing cards
		 {
			 System.out.println("You must play the card: " + mustPlay + ". Would you like to try to play any cards?");
			 String response = scan.next();
			 if(response.equals("no")) // if don't want to play, then done
			 {
				 keepPlaying = false;
				 successfulPlay = false;
				 continue;
			 }
			 else if(!response.equals("yes")) // if response is not yes or no, ask again
			 {
				 System.out.println("Invalid response. Please answer yes or no.");
				 continue;
			 }
			 // only get here if response is yes
			 
			 // find out how many cards to play
			 boolean validResponse = false;
			 while(!validResponse) // loop asking for input until it receives a valid response
			 {
	             System.out.println("You must play the card: " + mustPlay + ". How many other cards would you like to play?");
	             try
	             {
	            	 numCards = scan.nextInt();
	             }
	             catch(InputMismatchException e) // this exception will be thrown if anything but an integer is input
	             {
	            	 System.out.println("Please enter an integer number of cards.");
	            	 scan.nextLine(); // clears scanner for new input (important)
	            	 continue; // start next loop iteration
	             }
	             if(numCards>=0 && numCards<=hand.size()-2)// can't play negative number of cards and must leave at least 1 card in hand to discard (note: 1 card has already been chosen so use hand.size()-2)
	             {
	            	 validResponse = true; 
	             }
	             else // invalid input, ask again
	             {
	            	 System.out.println("You can't play that many cards.");
	             }
			 }
			 
			 // get cards to be played
			 cards = new Card[numCards+1];
			 cards[0] = mustPlay;
			 Card tempCard = null;
			 
			 for(int i=1;i<cards.length;i++)
			 {  
				 validResponse = false;
				 
				 while(!validResponse)// loop asking for input until it receives a valid response
				 {
					 System.out.print("\nWhat is the next card?" + "\nHand: " + hand + "\nPlaying: [");
					 for(int j=0;j<i;j++)
					 {
						 if(j!=i-1)
							 System.out.print(cards[j] + ", ");
						 else
							 System.out.print(cards[j]);
					 }
					 System.out.print("]\n");
					 
					 tempCard = new Card(scan.next(),scan.next().charAt(0)); // create temporary card based on input
					 
					 alreadyPlayed = false;
					 for(int j=0;j<i && !alreadyPlayed;j++)
					 {
						 alreadyPlayed = cards[j].equals(tempCard);
					 }
					 
					 if(hand.contains(tempCard) && !alreadyPlayed) // valid input if this card is in your hand
					 {
						 cards[i] = tempCard; // add card to cards
						 validResponse = true; // got a valid response
						 scan.nextLine(); // clear scan for next input
					 }
					 else // invalid card
					 {
						 System.out.println("Invalid card. Please enter a valid card in the form [rank] [suit].");
						 scan.nextLine(); // clear scan for next input
					 }
				 }
			}
			  // try to play your cards
			 value = Controller.playCards(cards);
			 if(value>0) // legal play
			 {
				 score += value; // add to score
				 System.out.println("You scored " + value + " points. Your current score is " + score + "."); // you got points
				 for(int i=0; i<cards.length; i++) // remove played cards from hand
					{
						hand.remove(cards[i]);
					}
				 
				 successfulPlay = true; // legal play means the card was successfully played and the play is complete (additional plays are done by play() method)
				 keepPlaying = false;
			 }
			 else // illegal play
			 {
				 System.out.println("That is not a legal play."); // display this, then start over
			 }
			 //			 System.out.println(hand);
		}
		 
		 return successfulPlay;
	}
		 
		 
}
