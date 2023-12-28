package tradingCardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tradingCardGame.card.Card;
import tradingCardGame.card.CardFactory;
import tradingCardGame.card.CardType;
import tradingCardGame.card.DefaultDeck;
import tradingCardGame.card.GameDeck;
import tradingCardGame.exception.IllegalCardTypeException;

public class DeckTest {
	
	private GameDeck deck;
	private CardFactory cardFactory;
	
	@Test void deckCreation() throws IllegalCardTypeException {
		
		deck = new DefaultDeck();
		cardFactory = new CardFactory();
		List<Card> expected = new LinkedList<Card>();
		
		expected.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 0, 0));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 1, 1));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 1, 1));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 2, 2));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 3, 3));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 4, 4));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 5, 5));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 5, 5));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 6, 6));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 6, 6));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 7, 7));
		expected.add(cardFactory.createCard(CardType.DEFAULT, 8, 8));
		
		assertEquals(deck.cards(), expected);
	}
	
	@Test void pickCard1() throws IllegalCardTypeException {
		deck = new DefaultDeck();
		cardFactory = new CardFactory();
		Card card     = deck.drawCard(0);
		Card expected = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		
		assertEquals(card, expected);
		assertEquals(deck.size(), 19);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, -1",
		"0, -2",
		"1, 19",
		"2, 18",
		"3, 17",
		"0, 50"
	})
	void illegalIndex(int nCards, int illegalIndex) throws IllegalCardTypeException {
		deck = new DefaultDeck();
		drawNCards(deck, nCards, 19);
		assertThrows(IllegalArgumentException.class, () -> deck.drawCard(illegalIndex));
	}
	
	private void drawNCards(GameDeck deck, int n, int from) {
		for(int i = 0; i < n; i++) {
			deck.drawCard(from - i);
		}
	}
}
