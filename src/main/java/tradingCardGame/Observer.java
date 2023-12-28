package tradingCardGame;

import tradingCardGame.card.Card;
import tradingCardGame.card.GameDeck;
import tradingCardGame.player.Player;

public interface Observer {
	public void logAttack(Player active, Player opponent, Card card);
	public void logHeal(Player active, Card card);
	public void logWinner(Player winner, Player dead);
	public void logPlayerState(Player player, GameDeck deck, String label);
	public void logPassTurn(Player active);
	public void logCardDrawer(Player active, Card card);
	public void logReduceMana(Player active, Player opponent, Card card);
	public void logDraw();
}
