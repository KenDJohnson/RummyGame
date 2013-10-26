import java.util.Random;



public class Deck {
	private Node top;

	public Deck(boolean empty)
	{
		if (!empty)
		{
			top = null;

			Card h1 = new Card("1",'H');
			Card h2 = new Card("2",'H');
			Card h3 = new Card("3",'H');
			Card h4 = new Card("4",'H');
			Card h5 = new Card("5",'H');
			Card h6 = new Card("6",'H');
			Card h7 = new Card("7",'H');
			Card h8 = new Card("8",'H');
			Card h9 = new Card("9",'H');
			Card h10 = new Card("10",'H');
			Card hJ = new Card("J",'H');
			Card hQ = new Card("Q",'H');
			Card hK = new Card("K",'H');
			Card hA = new Card("A",'H');
			Card c1 = new Card("1",'C');
			Card c2 = new Card("2",'C');
			Card c3 = new Card("3",'C');
			Card c4 = new Card("4",'C');
			Card c5 = new Card("5",'C');
			Card c6 = new Card("6",'C');
			Card c7 = new Card("7",'C');
			Card c8 = new Card("8",'C');
			Card c9 = new Card("9",'C');
			Card c10 = new Card("10",'C');
			Card cJ = new Card("J",'C');
			Card cQ = new Card("Q",'C');
			Card cK = new Card("K",'C');
			Card cA = new Card("A",'C');
			Card s1 = new Card("1",'S');
			Card s2 = new Card("2",'S');
			Card s3 = new Card("3",'S');
			Card s4 = new Card("4",'S');
			Card s5 = new Card("5",'S');
			Card s6 = new Card("6",'S');
			Card s7 = new Card("7",'S');
			Card s8 = new Card("8",'S');
			Card s9 = new Card("9",'S');
			Card s10 = new Card("10",'S');
			Card sJ = new Card("J",'S');
			Card sQ = new Card("Q",'S');
			Card sK = new Card("K",'S');
			Card sA = new Card("A",'S');
			Card d1 = new Card("1",'D');
			Card d2 = new Card("2",'D');
			Card d3 = new Card("3",'D');
			Card d4 = new Card("4",'D');
			Card d5 = new Card("5",'D');
			Card d6 = new Card("6",'D');
			Card d7 = new Card("7",'D');
			Card d8 = new Card("8",'D');
			Card d9 = new Card("9",'D');
			Card d10 = new Card("10",'D');
			Card dJ = new Card("J",'D');
			Card dQ = new Card("Q",'D');
			Card dK = new Card("K",'D');
			Card dA = new Card("A",'D');
			this.push(h1);
			this.push(h2);
			this.push(h3);
			this.push(h4);
			this.push(h5);
			this.push(h6);
			this.push(h7);
			this.push(h8);
			this.push(h9);
			this.push(h10);
			this.push(hJ);
			this.push(hQ);
			this.push(hK);
			this.push(hA);
			this.push(c1);
			this.push(c2);
			this.push(c3);
			this.push(c4);
			this.push(c5);
			this.push(c6);
			this.push(c7);
			this.push(c8);
			this.push(c9);
			this.push(c10);
			this.push(cJ);
			this.push(cQ);
			this.push(cK);
			this.push(cA);
			this.push(s1);
			this.push(s2);
			this.push(s3);
			this.push(s4);
			this.push(s5);
			this.push(s6);
			this.push(s7);
			this.push(s8);
			this.push(s9);
			this.push(s10);
			this.push(sJ);
			this.push(sQ);
			this.push(sK);
			this.push(sA);
			this.push(d1);
			this.push(d2);
			this.push(d3);
			this.push(d4);
			this.push(d5);
			this.push(d6);
			this.push(d7);
			this.push(d8);
			this.push(d9);
			this.push(d10);
			this.push(dJ);
			this.push(dQ);
			this.push(dK);
			this.push(dA);
		}
		else
		{
			top = null;
		}
	}

	public Deck shuffle()
	{
		//Random r = new Random();
		Deck shuffled = new Deck(true);
		Deck topHalf;
		Deck bottomHalf;
		
		for(int i = 0; i <= 7; i++)
		{
			if (i == 0)
			{
				topHalf = split1();
				bottomHalf = split2();
			}
			else
			{
				topHalf = shuffled.split1();
				bottomHalf = shuffled.split2();
			}
			shuffled = combine(topHalf, bottomHalf);
		}
		return shuffled;
	}

	public Deck split1() //makes a Deck with the first 21 - 29 cards to be shuffled
	{
//		Random r = new Random();
		Deck firstHalf = new Deck(true);
		int randFirst = (int) (21 + (Math.random() * (29 - 21)));
				//r.nextInt(30-21);
		for (int r1 = 0; r1 < randFirst; r1++)	//iterates through the first (random) cards
		{
			firstHalf.push(this.pop()); //adds the top card to the first half
		}
		return firstHalf;
	}
	
	public Deck split2() //makes a Deck with the remaining cards
	{
		Deck secondHalf = new Deck(true);
		while(!this.isEmpty())
		{
			secondHalf.push(this.pop());
		}
		return secondHalf;
	}
	
	public Deck combine(Deck topHalf, Deck bottomHalf)
	{
		//Random r = new Random();
		Deck shuffling = new Deck(true);
		while(!topHalf.isEmpty())
		{
			//int rand2 = r.nextInt(7-1); //picks a random number from 1 - 6
			int rand2 = (int) (1 + (Math.random() * (6 - 1)));
			for(int f = 0; f < rand2 && topHalf.peek()!=null; f++)
			{
				if(!topHalf.isEmpty())
					shuffling.push(topHalf.pop());
			}
			int rand3 = (int) (1 + (Math.random() * (6 - 1)));
			for(int j =0; j < rand3 && bottomHalf.peek()!=null; j++)
			{
				if(!bottomHalf.isEmpty())
					shuffling.push(bottomHalf.pop());
			}
		}
		while(!bottomHalf.isEmpty())
		{
			if(bottomHalf.peek()!=null)
				shuffling.push(bottomHalf.pop());
		}
		return shuffling;
	}
	
	public void push(Card anEntry)
	{
		Node n1 = new Node(anEntry);
		n1.setNext(top);
		top = n1;
	}

	public Card peek()
	{
		if(!isEmpty())
		{
			return top.getData();
		}
		else
			return null;
	}

	public Card pop()
	{
		Card result;
		if (!isEmpty())
		{
			result = top.getData();
			top = top.getNext();
			return result;
		}
		else
		{
			return null;
		}
	}

	public void clear()
	{
		top = null;
	}

	public boolean isEmpty()
	{
		return top == null;
	}




	@Override
	public String toString() {
		return "Deck | " + top;
	}


	private class Node
	{
		private Card data;
		private Node prev;
		private Node next;

		public Node (Card anEntry)
		{
			this.data = anEntry;
			this.prev = null;
			this.next = null;
		}

		public Card getData() {
			return data;
		}

		public void setData(Card data) {
			this.data = data;
		}

		public Node getPrev() {
			return prev;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return  this.data + ", " + getNext();
		}



	}
}
