package tradingCardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.player.Player;
import tradingCardGame.player.PlayerFactory;

public class PlayerTest {
	
	private Player player;
	private PlayerFactory factory;
	
	@BeforeEach
	private void setUp() throws IllegalCardTypeException {
		factory = new PlayerFactory();
	}
	
	@Test void playerInitialization1() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		assertEquals(30, player.health());
		assertEquals(0,  player.mana());
	}
	
	@Test void playerInitialization2() {
		player = factory.createPlayer("Pete Zahut", 25, 10, 10);
		assertEquals(25, player.health());
		assertEquals(10, player.mana());
		assertEquals(10, player.maxMana());
	}
	
	@ParameterizedTest
	@CsvSource({
		"1, 29",
		"2, 28"
	})
	void decreasePlayerHealth(int damage, int expected) {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.decreaseHealthBy(damage);
		
		assertEquals(expected, player.health());
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1",
		"-2"
	})
	void cannotDecreaseHealthByANegative(int damage) {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> player.decreaseHealthBy(damage));
	}
	
	@ParameterizedTest
	@CsvSource({
		"11", 
		"12"
	})
	void manaCannotExceedTenParameterized(int increaseBy) {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.increaseMaxManaBy(5);
		player.updateManaBy(increaseBy);
		assertEquals(5, player.mana());
	}
	
	@Test void manaCannotExceedMaxMana1() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.increaseMaxManaBy(5);
		player.updateManaBy(6);
		assertEquals(5, player.mana());
	}
	
	@Test void manaCannotExceedMaxMana2() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.increaseMaxManaBy(5);
		player.updateManaBy(1);
		player.updateManaBy(5);
		assertEquals(5, player.mana());
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 1, 1",
		"0, 2, 2",
		"10, -1, 9",
		"10, -2, 8"
	})
	void updateMaxManaBy(int initial, int delta, int expected) {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.increaseMaxManaBy(10);
		player.updateManaBy(initial);
		player.updateManaBy(delta);
		assertEquals(expected, player.mana());
	}
	
	@Test void maxManaCannotBeNegative1() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> player.increaseMaxManaBy(-1));
	}
	
	@Test void maxManaCannotBeNegative2() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> player.increaseMaxManaBy(-2));
	}
	
	@Test void currentMana1() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.increaseMaxManaBy(10);
		assertEquals(0,  player.mana());
		assertEquals(10, player.maxMana());
	}
	
	@Test void currentManaCannotBeNegative() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> player.updateManaBy(-1));
	}
	
	@ParameterizedTest
	@CsvSource({
		"0,  true",
		"30, false",
		"31, false",
		"32, false"
	})
	void playerState(int decreaseBy, boolean expected) {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.decreaseHealthBy(decreaseBy);
		assertEquals(player.isAlive(), expected);
	}
	
	@Test void increaseHealth1() {
		player = factory.createPlayer("Pete Zahut", 30, 0, 0);
		player.decreaseHealthBy(10);
		player.increaseHealthBy(5);
		assertEquals(25, player.health());
	}
}