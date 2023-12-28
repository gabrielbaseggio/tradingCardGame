package tradingCardGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tradingCardGame.card.Card;
import tradingCardGame.card.DeckFactory;
import tradingCardGame.card.GameDeck;
import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.exception.IllegalDeckTypeException;
import tradingCardGame.exception.IllegalMoveException;
import tradingCardGame.exception.NotEnoughManaException;
import tradingCardGame.move.Command;
import tradingCardGame.move.CommandFactory;
import tradingCardGame.move.Move;
import tradingCardGame.player.Player;
import tradingCardGame.player.PlayerFactory;
import tradingCardGame.random.DefaultRandom;
import tradingCardGame.random.GameRandom;
import tradingCardGame.rule.Rule;
import tradingCardGame.rule.RulesFactory;

public class Game implements Subject {
	
	private Player active;
	private Player opponent;
	
	private Map<String, GameDeck> decks;
	
	private GameRandom gameRandom;
	private RulesFactory rulesFactory;
	private PlayerFactory playerFactory;
	private CommandFactory commandFactory;
	private DeckFactory deckFactory;
	
	private List<Rule> beforeGame;
	private List<Rule> beforeTurn;
	private List<Observer> loggers;
	private Rule drawCardRule;

	public Game(RulesFactory rulesFactory, DeckFactory deckFactory) {
		playerFactory  = new PlayerFactory();
		this.rulesFactory   = rulesFactory;
		commandFactory = new CommandFactory();
		this.deckFactory    = deckFactory;
		
		decks = new HashMap<String, GameDeck>();
	}
	
	public void newGame(String namePlayer1, String namePlayer2) throws IllegalDeckTypeException, IllegalCardTypeException {
		Player player1 = playerFactory.createPlayer(namePlayer1, 30, 0, 0);
		Player player2 = playerFactory.createPlayer(namePlayer2, 30, 0, 0);
		
		gameRandom = new DefaultRandom();
		int active = gameRandom.chooseActive();
		
		setActive(player1, player2, active);
		decks.put(namePlayer1, deckFactory.createDeck());
		decks.put(namePlayer2, deckFactory.createDeck());
		
		beforeGame = rulesFactory.createBeforeGameRules();
		beforeTurn = rulesFactory.createBeforeTurnRules();
		loggers = new ArrayList<Observer>();
		loggers.add(new LoggerToSTDOUT());
		executeRules(beforeGame);
		executeRules(beforeTurn);
		
		updateView();
	}
	
	private void setActive(Player player1, Player player2, int active) {
		if(active == 1) {
			this.active   = player1;
			this.opponent = player2;
		} else {
			this.active   = player2;
			this.opponent = player1;
		}
	}

	/**
	 * Metodo para testing
	 */
	public void newGame(Player player1
			,Player player2
			,GameDeck deck1
			,GameDeck deck2
			,GameRandom gameRandom) {
		
		this.gameRandom = gameRandom;
		int whoIsActive = gameRandom.chooseActive();
		setActive(player1, player2, whoIsActive);
		
		decks.put(player1.name(), deck1);
		decks.put(player2.name(), deck2);
		
		beforeGame = rulesFactory.createBeforeGameRules();
		beforeTurn = rulesFactory.createBeforeTurnRules();
		loggers = new ArrayList<Observer>();
		loggers.add(new LoggerToSTDOUT());
		executeRules(beforeGame);
		executeRules(beforeTurn);
		
		updateView();
	}

	private void executeRules(List<Rule> rules) {
		for(Rule rule : rules) {
			rule.execute(this);
		}
	}

	public boolean isOver() {
		return thereIsAWinner() || thereIsADraw();
	}

	public void execute(Move move, int index) throws IllegalMoveException, IllegalCardTypeException, NotEnoughManaException {
		if( index < 0 || index >= active.hand().size() ) { 
			throw new IndexOutOfBoundsException(); 
		}
		
		Card card = active.hand().get(index);
		
		if(active.mana() >= card.cost()) {
			Command command = commandFactory.createCommand(this, move, card);
			active.hand().remove(index);
			command.execute();
			notifyMove(move, active, opponent, card);
		} else {
			throw new NotEnoughManaException();
		}
		
		updateView();
	}
	
	private void updateView() {
		notifyPlayerState(active,   decks.get(active.name()),   "Active");
		notifyPlayerState(opponent, decks.get(opponent.name()), "Opponent");
		if(thereIsAWinner())
			notifyWinner();
		if(thereIsADraw())
			notifyDraw();
	}
	
	private boolean thereIsADraw() {
		if(hasNoMoreCards(active) && hasNoMoreCards(opponent) && active.health() == opponent.health())
			return true;
		
		return false;
	}

	private boolean thereIsAWinner() {
		if( thereIsADead() ) {
			return true;
		} else if(hasNoMoreCards(active) && hasNoMoreCards(opponent) && active.health() > opponent.health()) {
			return true;
		} else if(hasNoMoreCards(active) && hasNoMoreCards(opponent) && opponent.health() > active.health()) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasNoMoreCards(Player player) {
		return player.hand().size() == 0 && decks.get(player.name()).size() == 0;
	}

	private boolean thereIsADead() {
		return active.isDead() || opponent.isDead();
	}

	public Player active() {
		return active;
	}

	public Player opponent() {
		return opponent;
	}

	public void passTurn() {
		notifyPassTurn();
		
		Player temp = active;
		active   = opponent;
		opponent = temp;
		
		executeRules(beforeTurn);
		updateView();
	}

	@Override
	public void registerObserver(Observer o) {
		loggers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		loggers.remove(o);
	}

	@Override
	public void notifyAttack(Player active, Player opponent, Card card) {
		for(Observer logger : loggers) {
			logger.logAttack(active, opponent, card);
		}
	}
	
	@Override
	public void notifyWinner() {
		Player winner;
		Player dead;
		
		if(active.isDead()) {
			winner = opponent;
			dead   = active;
		} else if(opponent.isDead()) {
			winner = active;
			dead   = opponent;
		} else {
			if(active.health() > opponent.health()) {
				winner = active;
				dead   = opponent;
			} else {
				winner = opponent;
				dead   = active;
			}
		}
		
		for(Observer logger : loggers) {
			logger.logWinner(winner, dead);
		}
	}

	@Override
	public void notifyPlayerState(Player player, GameDeck deck, String label) {
		for(Observer logger : loggers) {
			logger.logPlayerState(player, deck, label);
		}
	}

	@Override
	public void notifyHeal(Player active, Card card) {
		for(Observer logger : loggers) {
			logger.logHeal(active, card);
		}
	}

	public void showPlayerState() {
		notifyPlayerState(active,   decks.get(active.name()),   "Active");
		notifyPlayerState(opponent, decks.get(opponent.name()), "Opponent");
	}

	public boolean activePlayerCanContinuePlaying() {
		List<Card> hand = active.hand();
		int minCost = Integer.MAX_VALUE;
		for(Card card : hand) {
			if(card.cost() < minCost) {
				minCost = card.cost();
			}
		}
		
		return active.mana() >= minCost;
	}
	
	public GameDeck deck(Player player) {
		return decks.get(player.name());
	}
	
	public void drawCard(Player player) {
		drawCardRule = rulesFactory.createDrawCardRule(player, gameRandom);
		drawCardRule.execute(this);
	}

	@Override
	public void notifyPassTurn() {
		for(Observer logger : loggers) {
			logger.logPassTurn(active);
		}
	}

	@Override
	public void notifyCardDrawer(Player active, Card card) {
		for(Observer logger : loggers) {
			logger.logCardDrawer(active, card);
		}
	}

	@Override
	public void notifyDraw() {
		for(Observer logger : loggers) {
			logger.logDraw();
		}
	}

	public void notifyMove(Move move, Player active, Player opponent, Card card) {
		switch (move) {
			case ATTACK:
				notifyAttack(active, opponent, card);
				break;
			case HEAL:
				notifyHeal(active, card);
				break;
			case CARDDRAWER:
				notifyCardDrawer(active, card);
				break;
			default:
				notifyReduceMana(active, opponent, card);
				break;
		}
	}

	public void addBeforeTurnRule(Rule rule) {
		beforeTurn.add(rule);
	}

	@Override
	public void notifyReduceMana(Player active, Player opponent, Card card) {
		for(Observer logger : loggers) {
			logger.logReduceMana(active, opponent, card);
		}
	}
}
