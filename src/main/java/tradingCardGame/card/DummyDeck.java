package tradingCardGame.card;

import java.util.LinkedList;
import java.util.List;

import tradingCardGame.exception.IllegalCardTypeException;

public class DummyDeck extends GameDeck {
	
	private CardFactory cardFactory;
	
	public DummyDeck() throws IllegalCardTypeException {
		deck = new LinkedList<Card>();
		cardFactory = new CardFactory();
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
	}

}
