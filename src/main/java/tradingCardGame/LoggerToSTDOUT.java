package tradingCardGame;

import tradingCardGame.card.Card;
import tradingCardGame.card.GameDeck;
import tradingCardGame.player.Player;

public class LoggerToSTDOUT implements Observer {
	
	public LoggerToSTDOUT() {
		
	}

	@Override
	public void logAttack(Player active, Player opponent, Card card) {
		System.out.println(active.name() 
				+ " attacked " 
				+ opponent.name() 
				+ " and caused " 
				+ card.damage() 
				+ " pts of damage");
	}

	@Override
	public void logWinner(Player winner, Player dead) {
		System.out.println(dead.name() + " is dead");
		System.out.println("Winner: " + winner.name());
	}

	@Override
	public void logPlayerState(Player player, GameDeck deck, String label) {
		int health  = player.health();
		int mana    = player.mana();
		int maxMana = player.maxMana();
		
		System.out.println(label + ": " + player.name());
		System.out.println(">> Health: " + health);
		System.out.println(">> Mana: " + mana);
		System.out.println(">> Max mana: " + maxMana);
		System.out.println(">> Hand:");
		for(Card card : player.hand()) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
		System.out.println("Deck size: " + deck.size());
		System.out.println();
	}

	@Override
	public void logHeal(Player active, Card card) {
		System.out.println(active.name() + " healed himself by " + card.damage() + " pts");
	}

	@Override
	public void logPassTurn(Player active) {
		System.out.println("the player " + active.name() + " passed the turn");
	}

	@Override
	public void logCardDrawer(Player active, Card card) {
		System.out.println(active.name() + " draws " + card.numberOfCards() + " cards");
	}

	@Override
	public void logDraw() {
		System.out.println("Game is over!");
		System.out.println("Result: Draw");
	}

	@Override
	public void logReduceMana(Player active, Player opponent, Card card) {
		System.out.println(active.name() + " has reduced " + opponent.name() + "'s mana in half");
	}
	
}
