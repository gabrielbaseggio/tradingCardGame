package tradingCardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tradingCardGame.card.Card;
import tradingCardGame.card.CardFactory;
import tradingCardGame.card.CardType;
import tradingCardGame.card.DefaultDeck;
import tradingCardGame.card.DefaultDeckFactory;
import tradingCardGame.card.DummyDeck;
import tradingCardGame.card.EmptyDeck;
import tradingCardGame.card.GameDeck;
import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.exception.IllegalDeckTypeException;
import tradingCardGame.exception.IllegalMoveException;
import tradingCardGame.exception.NotEnoughManaException;
import tradingCardGame.move.Move;
import tradingCardGame.player.Player;
import tradingCardGame.player.PlayerFactory;
import tradingCardGame.random.DummyRandom;
import tradingCardGame.rule.DefaultRulesFactory;

public class GameTest {
	
	private Game game;
	private CardFactory cardFactory;
	private PlayerFactory playerFactory;
	
	@BeforeEach
	private void setUp() {
		game = new Game(new DefaultRulesFactory(), new DefaultDeckFactory());
		cardFactory   = new CardFactory();
		playerFactory = new PlayerFactory();
	}
	
	@Test void gameInitialization() throws IllegalDeckTypeException, IllegalCardTypeException {
		game.newGame("player 1", "player 2");
		
		Player active   = game.active();
		Player opponent = game.opponent();
		
		assertEquals(30, active.health());
		assertEquals(1,  active.mana());
		assertEquals(1,  active.maxMana());
		assertEquals(4,  active.hand().size());
		assertEquals(16, game.deck(active).size());
		
		assertEquals(30, opponent.health());
		assertEquals(0,  opponent.mana());
		assertEquals(0,  opponent.maxMana());
		assertEquals(4,  opponent.hand().size());
		assertEquals(16, game.deck(opponent).size());
	}
	
	@Test void gameIsNotOver1() throws IllegalDeckTypeException, IllegalCardTypeException {
		game.newGame("player 1", "player 2");
		assertFalse(game.isOver());
	}
	
	@Test void PlayerVSPlayer_1() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card = active.hand().get(0); // active.getHandCard(0);
		
		game.execute(Move.ATTACK, 0);
		
		int expectedOpponentHealth = 30 - card.damage();
		int expectedActiveMana     = 10 - card.cost();
		
		assertEquals(expectedOpponentHealth, opponent.health());
		assertEquals(expectedActiveMana,     active.mana());
	}
	
	@Test void PlayerVSPlayer_2() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card1 = active.hand().get(0); // active.getHandCard(0);
		Card card2 = active.hand().get(1); // active.getHandCard(0);
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		int expectedOpponentHealth = 30 - card1.damage() - card2.damage();
		int expectedActiveMana     = 10 - card1.cost()   - card2.cost();
		
		assertEquals(expectedOpponentHealth, opponent.health());
		assertEquals(expectedActiveMana,     active.mana());
	}
	
	@Test void PlayerVSPlayer_3() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 25, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card = active.hand().get(0); // active.getHandCard(0);
		
		game.execute(Move.HEAL, 0);
		
		int expectedActiveHealth = 25 + card.damage();
		int expectedActiveMana   = 10 - card.cost();
		
		assertEquals(expectedActiveHealth, active.health());
		assertEquals(expectedActiveMana,   active.mana());
	}
	
	@Test void PlayerVSPlayer_4() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 28, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card card1 = active.hand().get(0); // active.getHandCard(0);
		Card card2 = active.hand().get(1); // active.getHandCard(0);
		
		game.execute(Move.HEAL, 0);
		game.execute(Move.HEAL, 0);
		
		int expectedActiveHealth = 28 + card1.damage() + card2.damage();
		int expectedActiveMana   = 10 - card1.cost() - card2.cost();
		
		game.showPlayerState();
		
		assertEquals(expectedActiveHealth, active.health());
		assertEquals(expectedActiveMana,   active.mana());
	}
	
	@Test void PlayerVSPlayer_5() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		Card activeCard1 = active.hand().get(0); // active.getHandCard(0);
		Card activeCard2 = active.hand().get(1); // active.getHandCard(0);
		
		Card opponentCard1 = opponent.hand().get(0); // active.getHandCard(0);
		Card opponentCard2 = opponent.hand().get(1); // active.getHandCard(0);
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		int expectedActiveHealth = 30 - opponentCard1.damage() - opponentCard2.damage();
		int expectedActiveMana   = 10 - activeCard1.cost() - activeCard2.cost();
		int expectedOpponentHealth = 30 - activeCard1.damage() - activeCard2.damage();
		int expectedOpponentMana   = 10 - opponentCard1.cost() - opponentCard2.cost();
		
		assertEquals(expectedActiveHealth, active.health());
		assertEquals(expectedActiveMana,   active.mana());
		assertEquals(expectedOpponentHealth, opponent.health());
		assertEquals(expectedOpponentMana,   opponent.mana());
	}
	
	@Test void PlayerVSPlayer() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("ColinB",     30, 10, 10);
		Player opponent = playerFactory.createPlayer("SrGabriedo", 30, 10, 10);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(2, 2));
		deck.addCard(new Card(3, 3));
		deck.addCard(new Card(4, 4));
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertEquals(27, opponent.health());
		assertEquals(7,  active.mana());
	}
	
	@Test void activeKillsOpponent() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("ColinB",     30, 10, 10);
		Player opponent = playerFactory.createPlayer("SrGabriedo", 4, 10, 10);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(1, 1));
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertEquals(6, active.mana());
		assertTrue(game.isOver());
	}
	
	@Test void gameIsOver1() throws IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 0, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 0, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		assertTrue(game.isOver());
	}
	
	@Test void gameIsOver2() throws IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("", 0, 10, 10);
		Player opponent = playerFactory.createPlayer("", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		assertTrue(game.isOver());
	}
	
	@Test void gameIsOver3() throws IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 0, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		assertTrue(game.isOver());
	}
	
	@Test void activeKillsOpponent1() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 1, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertTrue(game.isOver());
	}
	
	@Test void activeKillsOpponent2() throws IllegalMoveException, IllegalDeckTypeException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 1, 0, 0);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		assertTrue(game.isOver());
	}
	
	@Test void activePlayerCanContinuePlaying1() throws IllegalDeckTypeException, IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 2, 0, 0);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		assertTrue(game.activePlayerCanContinuePlaying());
	}
	
	@Test void bleedingOutRule() throws IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DummyDeck(), new DefaultDeck(), new DummyRandom());
		
		assertEquals(29, active.health());
	}
	
	@Test void overloadRule() throws IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		game.newGame(active, opponent, new DefaultDeck(), new DefaultDeck(), new DummyRandom());
		
		assertEquals(4, active.hand().size());
		assertEquals(16, game.deck(active).size());
		
		game.passTurn();
		
		/* opponent turn */
		
		game.passTurn();
		
		assertEquals(5, active.hand().size());
		assertEquals(15, game.deck(active).size());
		
		game.passTurn();
		
		/* opponent turn */
		
		game.passTurn();
		
		assertEquals(5,  active.hand().size());
		assertEquals(14, game.deck(active).size());
	}
	
	@Test void playerCanContinuePlaying1() throws IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("player 1", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("player 2", 30, 0, 0);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard(new Card(3, 3));
		deck.addCard(new Card(3, 3));
		deck.addCard(new Card(4, 4));
		deck.addCard(new Card(4, 4));
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		
		assertFalse(game.activePlayerCanContinuePlaying());
	}
	
	@Test void playerCanContinuePlaying2() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("player 1", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("player 2", 30, 10, 10);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard(new Card(2, 2));
		deck.addCard(new Card(2, 2));
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(1, 1));
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertFalse(game.activePlayerCanContinuePlaying());
	}
	
	@Test void ActiveChoosesACardForWhichHeDoesNotHaveEnoughMana() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("Pete zahut", 30, 0, 0);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard(new Card(2, 2));
		deck.addCard(new Card(2, 2));
		deck.addCard(new Card(1, 1));
		deck.addCard(new Card(1, 1));
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		assertThrows(NotEnoughManaException.class, () -> game.execute(Move.ATTACK, 0));
	}
	
	@Test void CardDrawer() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("Pete zahut", 30, 0, 0);
		
		GameDeck deck = new EmptyDeck();
		deck.addCard( cardFactory.createCard(CardType.CARDDRAWER, 1, 2) );
		deck.addCard( cardFactory.createCard(CardType.DEFAULT, 1, 1) );
		deck.addCard( cardFactory.createCard(CardType.DEFAULT, 1, 1) );
		deck.addCard( cardFactory.createCard(CardType.DEFAULT, 1, 1) );
		deck.addCard( cardFactory.createCard(CardType.DEFAULT, 1, 1) );
		deck.addCard( cardFactory.createCard(CardType.DEFAULT, 1, 1) );
		
		game.newGame(active, opponent, deck, new DefaultDeck(), new DummyRandom());
		game.execute(Move.CARDDRAWER, 0);
		assertEquals(5, active.hand().size());
	}
	
	@Test void gameIsOver() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertTrue(active.isAlive());
		assertTrue(opponent.isAlive());
		assertTrue(game.isOver());
	}
	
	@Test void illegalCardIndex() throws IllegalCardTypeException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		assertThrows(IndexOutOfBoundsException.class, () -> game.execute(Move.ATTACK, 5));
	}
	
	@Test void winner1() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   25, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertTrue(game.isOver());
	}
	
	@Test void winner2() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 25, 0, 0);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertTrue(game.isOver());
	}
	
	@Test void thereIsADraw() throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 0, 0);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		game.passTurn();
		
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		game.execute(Move.ATTACK, 0);
		
		assertTrue(game.isOver());
	}
	
	@Test void activePlayerHealsHimself() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 5, 5) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		game.execute(Move.HEAL, 0);
		assertEquals(30, active.health());
		assertEquals(5,  active.mana());
	}
	
	@Test void theCardIsNotOfTheTypeCardDrawer() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 0, 0);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 5, 5) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		
		GameDeck opponentDeck = new EmptyDeck();
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		opponentDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 0, 0) );
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		assertThrows(IllegalCardTypeException.class, () -> game.execute(Move.CARDDRAWER, 0));
	}
	
	@Test void reduceManaMove() throws IllegalCardTypeException, IllegalMoveException, NotEnoughManaException {
		Player active   = playerFactory.createPlayer("KingSlayer69", 30, 10, 10);
		Player opponent = playerFactory.createPlayer("SrGabriedo",   30, 10, 10);
		
		GameDeck activeDeck = new EmptyDeck();
		activeDeck.addCard( cardFactory.createCard(CardType.REDUCEMANA, 5) );
		activeDeck.addCard( cardFactory.createCard(CardType.DEFAULT, 10, 5) );
		
		GameDeck opponentDeck = new EmptyDeck();
		
		game.newGame(active, opponent, activeDeck, opponentDeck, new DummyRandom());
		game.execute(Move.REDUCEMANA, 0);
		
		assertEquals(5, opponent.mana());
		assertEquals(5, opponent.maxMana());
		assertEquals(5, active.mana());
		
		game.passTurn();
		
		assertEquals(5, opponent.mana());
		assertEquals(5, opponent.maxMana());
		
		game.passTurn();
		
		assertEquals(5, opponent.mana());
		assertEquals(5, opponent.maxMana());
		
		game.passTurn();
		
		assertEquals(10, opponent.mana());
		assertEquals(10, opponent.maxMana());
	}
}
