package tradingCardGame;

import tradingCardGame.card.Card;
import tradingCardGame.card.GameDeck;
import tradingCardGame.move.Move;
import tradingCardGame.player.Player;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyAttack(Player active, Player opponent, Card card);
	public void notifyHeal(Player active, Card card);
	public void notifyCardDrawer(Player active, Card card);
	public void notifyReduceMana(Player active, Player opponent, Card card);
	public void notifyWinner();
	public void notifyDraw();
	public void notifyPlayerState(Player player, GameDeck deck, String label);
	public void notifyPassTurn();
}
