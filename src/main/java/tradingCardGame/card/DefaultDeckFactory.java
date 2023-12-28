package tradingCardGame.card;

import tradingCardGame.exception.IllegalCardTypeException;

public class DefaultDeckFactory extends DeckFactory {

	@Override
	public GameDeck createDeck() throws IllegalCardTypeException {
		return new DefaultDeck();
	}
	
}
