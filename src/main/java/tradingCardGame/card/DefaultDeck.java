package tradingCardGame.card;

import java.util.LinkedList;
import java.util.List;

import tradingCardGame.exception.IllegalCardTypeException;

public class DefaultDeck extends GameDeck {
	
	private CardFactory cardFactory;
	
	public DefaultDeck() throws IllegalCardTypeException {
		deck = new LinkedList<Card>();
		cardFactory = new CardFactory();
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 1, 1));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 1, 1));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 5, 5));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 5, 5));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 6, 6));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 6, 6));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 7, 7));
		deck.add(cardFactory.createCard(CardType.DEFAULT, 8, 8));
	}
}
