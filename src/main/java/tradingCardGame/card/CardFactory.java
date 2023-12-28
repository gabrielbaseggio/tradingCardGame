package tradingCardGame.card;

import tradingCardGame.exception.IllegalCardTypeException;

public class CardFactory {
	
	public Card createCard(CardType type, int ... args) throws IllegalCardTypeException {
		switch (type) {
			case DEFAULT:
				return new Card(args[0], args[1]);
			case CARDDRAWER:
				return new Card(args[0], true, args[1]);
			case REDUCEMANA:
				return new Card(args[0], false, true, 0);
			default:
				throw new IllegalCardTypeException();
		}
	}
}
