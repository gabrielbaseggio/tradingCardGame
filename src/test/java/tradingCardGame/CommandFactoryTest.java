package tradingCardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tradingCardGame.card.Card;
import tradingCardGame.card.CardFactory;
import tradingCardGame.card.CardType;
import tradingCardGame.card.DefaultDeck;
import tradingCardGame.card.DefaultDeckFactory;
import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.exception.IllegalDeckTypeException;
import tradingCardGame.exception.IllegalMoveException;
import tradingCardGame.move.Command;
import tradingCardGame.move.CommandFactory;
import tradingCardGame.move.Move;
import tradingCardGame.player.Player;
import tradingCardGame.player.PlayerFactory;
import tradingCardGame.random.DefaultRandom;
import tradingCardGame.random.DummyRandom;
import tradingCardGame.rule.DefaultRulesFactory;

public class CommandFactoryTest {
	
	Game game;
	PlayerFactory playerFactory;
	CommandFactory commandFactory;
	CardFactory cardFactory;
	
	@BeforeEach
	private void setUp() {
		game = new Game(new DefaultRulesFactory(), new DefaultDeckFactory());
		playerFactory  = new PlayerFactory();
		commandFactory = new CommandFactory();
		cardFactory    = new CardFactory();
	}
	
	@Test void createDamageCommand() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card = cardFactory.createCard(CardType.DEFAULT, 1, 1);
		Command damageCardCommand = commandFactory.createCommand(game
																,Move.ATTACK
																,card);
		
		damageCardCommand.execute();
		assertEquals(29, opponent.health());
		assertEquals(9, active.mana());
	}
	
	@Test void createHealingCommand() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("", 25, 10, 10);
		Player opponent = playerFactory.createPlayer("", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card = cardFactory.createCard(CardType.DEFAULT, 1, 1);
		Command healingCardCommand = commandFactory.createCommand(game
																 ,Move.HEAL
																 ,card);
		
		healingCardCommand.execute();
		assertEquals(26, active.health());
		assertEquals(9,  active.mana());
	}
	
	@Test void createCardDrawerCommand() throws IllegalMoveException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("", 25, 10, 10);
		Player opponent = playerFactory.createPlayer("", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card = cardFactory.createCard(CardType.CARDDRAWER, 1, 1);
		Command cardDrawerCommand = commandFactory.createCommand(game
				 ,Move.CARDDRAWER
				 ,card);
		cardDrawerCommand.execute();
		assertEquals(5, active.hand().size());
	}
	
	@Test void illegalMoveException() throws IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("", 25, 10, 10);
		Player opponent = playerFactory.createPlayer("", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DefaultRandom());
		
		Card card = cardFactory.createCard(CardType.DEFAULT, 1, 1);
		assertThrows(IllegalMoveException.class, () -> commandFactory.createCommand(
				 game
				,Move.PASS
				,card));
	}
}
