package tradingCardGame.move;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.player.Player;

public class DamageCardCommand implements Command {
	
	private Game game;
	private Card card;

	public DamageCardCommand(Game game, Card card) {
		this.game = game;
		this.card = card;
	}

	@Override
	public void execute() {
		int cost   = card.cost();
		int damage = card.damage();
		Player active   = game.active();
		Player opponent = game.opponent();
		opponent.decreaseHealthBy(damage);
		active.updateManaBy(-cost);
	}

}
