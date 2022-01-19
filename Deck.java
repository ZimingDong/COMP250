package assignment2;

import java.util.Random;

public class Deck {
 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck

 /* 
  * TODO: Initializes a Deck object using the inputs provided
  */
 
 private void get() {
	 Card c = head;
	 Card d = head.prev;
	 String good = "";
	 String bad = "";
	 for (int i=0; i<this.numOfCards; i++) {
		 good = good+c.toString()+" ";
		 bad = bad+d.toString()+" ";
		 d = d.prev;
		 c = c.next;
	 }
	 System.out.println(good+" ");
	 //System.out.println(bad+" ");
 }
 
 public Deck(int numOfCardsPerSuit, int numOfSuits) {
	 if((numOfCardsPerSuit >13)||(numOfSuits>suitsInOrder.length)||(numOfCardsPerSuit < 1)||(numOfSuits < 1)) {
			throw  new IllegalArgumentException("Sorry, this is not a valid input.");
		}
	 Joker black = new Joker("black");
	 Joker red = new Joker("red");
	 for (int i=0; i<numOfSuits; i++) {
		 for (int j=1; j<=numOfCardsPerSuit; j++) {
			 Card c = new PlayingCard(suitsInOrder[i],j);
			 this.addCard(c);
		 }
		
	 }
	 this.addCard(red);
	 this.addCard(black);

	
  /**** ADD CODE HERE ****/
 }




/* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {
	 Card starter =  d.head.getCopy();
	 head = starter;
	 for (int i=0; i < d.numOfCards; i++) {
		 head.next = d.head.next.getCopy();
		 d.head = d.head.next;
		 head.next.prev = head;
		 head = head.next;
	 }
	 head.prev.next = starter;
	 starter.prev = head.prev;
	 head = starter;
	 starter.prev = head.prev;
	 starter.next = head.next;
	 head.next.prev = starter;
	 
	 numOfCards = d.numOfCards;
  /**** ADD CODE HERE ****/
 }

 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {
	 if ((this.numOfCards == 0)||(this.head == null)) {
		 head = c;
		 c.next = c;
		 c.prev = c;
		 this.numOfCards = 1;
		 return;
	 }
	 if (this.numOfCards == 1) {
		 head.next = c;
		 head.prev = c;
		 c.next = head;
		 c.prev = head;
		 this.numOfCards = 2;
		 return;
	 }
	 Card last = head.prev;
	 c.next = head;
	 last.next = c;
	 head.prev = c;
	 c.prev = last;
	 this.numOfCards = this.numOfCards + 1;
	 
  /**** ADD CODE HERE ****/
 }

 /*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */
 public void shuffle() {
	 if (head == null){
		 head = null;
		 return;
	 }
	 Card[] deckCopy = new Card[this.numOfCards];
	 for (int i=0; i<this.numOfCards;i++) {
		 deckCopy[i] = head;
		 head = head.next;
	 }
	 for (int i=this.numOfCards-1; i>=1; i--) {
		 int j = gen.nextInt(i+1);
		 Card temp = deckCopy[i];
		 deckCopy[i] = deckCopy[j];
		 deckCopy[j] = temp;
	 }
	 Card starter = deckCopy[0];
	 for (int i=0; i<this.numOfCards;i++) {
		 if (i == this.numOfCards-1) {
			 deckCopy[i].next = starter;
			 starter.prev = deckCopy[i];
			 break;
		 }
		 deckCopy[i].next = deckCopy[i+1];
		 deckCopy[i+1].prev = deckCopy[i];
	 }
	 head = starter;

	 
	 
	 
  /**** ADD CODE HERE ****/ 
 }

 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {
	 Joker thisJoker = null;
	 Card starter = this.head;
	 if ((!color.equalsIgnoreCase("red"))&&(!color.equalsIgnoreCase("black"))) return null;
	 for (int i=0; i<this.numOfCards; i++) {
		 head = head.next;
		 if (head.toString().equals(new Joker(color).toString())){
			 thisJoker = (Joker) this.head;
		 }
	 }
	 head = starter;
	 return thisJoker;
  /**** ADD CODE HERE ****/
 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */
 public void moveCard(Card c, int p) {
	 for (int i=0; i<p; i++) {
		 Card point1 = c.next.next;
		 Card point2 = c.next;
		 Card point3 = c.prev;
		 point3.next = point2;
		 point2.prev = point3;
		 point2.next = c;
		 c.prev = point2;
		 c.next = point1;
		 point1.prev = c;
		 
	 }
	 //if (c.equals(head)) {
		 //head = head.next;
	 //}
  /**** ADD CODE HERE ****/
 }

 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */
 public void tripleCut(Card firstCard, Card secondCard) {
	 Card c1 = secondCard.next;
	 Card c2 = this.head;
	 Card c3 = firstCard.prev;
	 Card c4 = head.prev;
	 if (c1.equals(c2)) {
		 c2.prev = secondCard;
		 secondCard.next = c2;
		 head = firstCard;
		 return;
	 }
	 if (c1.equals(c3)) {
		 head = c1;
		 return;
	 }
	 if (head.equals(firstCard)) {
		 head = c1;
		 return;
		 
	 }
	 c1.prev = c3;
	 c3.next = c1;
	 c2.prev = secondCard;
	 secondCard.next = c2;
	 c4.next = firstCard;
	 firstCard.prev = c4;
	 this.head = c1;
	 
	 
  /**** ADD CODE HERE ****/
 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {
	 int moveCards = head.prev.getValue();
	 
	 Card c1 = head.prev;
	 Card c2 = c1.prev;
	 Card c3 = head;
	 Card c4 = head.next;
	 if ((moveCards == this.numOfCards-1) ||(moveCards == this.numOfCards)){
		 head = c3;
		 return;
	 }
	 while (moveCards > this.numOfCards) {
		 moveCards = moveCards - this.numOfCards;
	 }
	 for (int i=0; i< moveCards; i++) {
		 c1.next = c4;
		 c4.prev = c1;
		 c1.prev = c3;
		 c3.next = c1;
		 c2.next = c3;
		 c3.prev = c2;
		 c2 = c1.prev;
		 c3 = c4;
		 c4 = c3.next;
	 }
	 head = c3;
	 c1 = c3.prev;
	 c1.next = c3;

  /**** ADD CODE HERE ****/
 }

 /*
  * TODO: Returns the card that can be found by looking at the value of the 
  * card on the top of the deck, and counting down that many cards. If the 
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {
	 int value = head.getValue();
	 Card find = head;
	 Joker temp = new Joker("black");
	 if (find.getClass().equals(temp.getClass())){
		 return head.prev;
	 }
	 for (int i=0; i< value; i++) {
		 find = find.next;
	 }
	 if(find.getClass() == temp.getClass()) {
		 find = null;
	 }
	 
  /**** ADD CODE HERE ****/
  return find;
 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */
 public int generateNextKeystreamValue() {
	 Card red = this.locateJoker("red");
	 Card black = this.locateJoker("black");
	 if ((red == null)||(black == null)) {
		 throw new IllegalArgumentException("We need two jokers.");
	 }
	 //1
	 
	 this.moveCard(red,1);
	 //this.get();
	 
	 //2

	 this.moveCard(black, 2);
	 //this.get();
	 
	 //3
	 Card temp = this.head;
	 for (int i=0; i<this.numOfCards; i++) {
		 if (temp.equals(red)) {
			 this.tripleCut(red, black);
			 break;
		 }
		 else if (temp.equals(black)) {
			 this.tripleCut(black,red);
			 break;
		 }
		 else {
			 temp = temp.next;
		 }
	 }
	 //this.get();
	 //4
	 this.countCut();
	 //this.get();
	 //5
	 if (this.lookUpCard() == null) {
		 //System.out.println("skip");
		 return this.generateNextKeystreamValue();
	 }
	 
	 //System.out.println(this.lookUpCard().getValue());
	 return this.lookUpCard().getValue();
	 
	 
  /**** ADD CODE HERE ****/
  
 }


 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
