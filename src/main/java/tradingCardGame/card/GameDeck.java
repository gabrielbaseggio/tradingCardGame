package tradingCardGame.card;

import java.util.ArrayList;
import java.util.List;

public class GameDeck {
	
	protected List<Card> deck = new ArrayList<Card>();
	
	public void addCard( Card card ) {
		deck.add(card);
	}
	
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	public Card drawCard(int index) {
		if(illegalIndex(index)) throw new IllegalArgumentException(); 
		Card card = deck.get(index);
		deck.remove(index);
		return card;
	}
	
	protected boolean illegalIndex(int index) {
		return index < 0 || index >= deck.size();
	}

	public List<Card> cards() {
		return deck;
	}
	
	public int size() {
		return deck.size();
	}
}
