package tradingCardGame.card;

import tradingCardGame.exception.IllegalCardTypeException;

public abstract class DeckFactory {
	public abstract GameDeck createDeck() throws IllegalCardTypeException;
}
