package tradingCardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tradingCardGame.card.Card;
import tradingCardGame.card.CardFactory;
import tradingCardGame.card.CardType;
import tradingCardGame.exception.IllegalCardTypeException;

public class CardTest {
	
	private CardFactory cardFactory;
	
	@BeforeEach
	private void setUp() {
		cardFactory = new CardFactory();
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1",
		"-2"
	})
	void cardCostCannotBeNegative1(int cost) {
		assertThrows(IllegalArgumentException.class, () -> cardFactory.createCard(CardType.DEFAULT, cost, cost));
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1",
		"-2"
	})
	void cardCostCannotBeNegative2(int cost) {
		assertThrows(IllegalArgumentException.class, () -> cardFactory.createCard(CardType.CARDDRAWER, cost, cost));
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1",
		"-2"
	})
	void numberOfCardsCostCannotBeNegative2(int numberOfCards) {
		assertThrows(IllegalArgumentException.class, () -> cardFactory.createCard(CardType.CARDDRAWER, 0, numberOfCards));
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 0",
		"1, 1"
	})
	void cardCreation(int cost, int expected) throws IllegalCardTypeException {
		Card card = cardFactory.createCard(CardType.DEFAULT, cost, cost);
		assertEquals(card.cost(), expected);
	}
	
	@Test void equals1() throws IllegalCardTypeException {
		Card card1 = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		boolean expected = card1.equals(null);
		assertFalse(expected);
	}
	
	@Test void equals2() throws IllegalCardTypeException {
		Card card1 = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		Card card2 = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		boolean expected = card1.equals(card2);
		assertTrue(expected);
	}
	
	@Test void equals3() throws IllegalCardTypeException {
		Card card1 = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		Card card2 = cardFactory.createCard(CardType.DEFAULT, 1, 1);
		boolean expected = card1.equals(card2);
		assertFalse(expected);
	}
	
	@Test void equals4() throws IllegalCardTypeException {
		Card card = cardFactory.createCard(CardType.DEFAULT, 0, 0);
		boolean expected = card.equals("this is not a card");
		assertFalse(expected);
	}
	
	@Test void illegalCardType() {
		assertThrows(IllegalCardTypeException.class, () -> cardFactory.createCard(CardType.DUMMY, 0, 0));
	}
}
