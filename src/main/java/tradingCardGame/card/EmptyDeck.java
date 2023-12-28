package tradingCardGame.card;

import java.util.ArrayList;

public class EmptyDeck extends GameDeck {
	public EmptyDeck() {
		this.deck = new ArrayList<Card>();
	}
}
