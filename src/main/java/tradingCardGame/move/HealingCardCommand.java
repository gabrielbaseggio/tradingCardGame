package tradingCardGame.move;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.player.Player;

public class HealingCardCommand implements Command {
	
	private Game game;
	private Card card;

	public HealingCardCommand(Game game, Card card) {
		this.game = game;
		this.card = card;
	}

	@Override
	public void execute() {
		int cost = card.cost();
		int pts  = card.damage();
		Player active = game.active();
		active.increaseHealthBy(pts);
		
		if(active.health() > 30) {
			active.setHealth(30);
		}
		
		active.updateManaBy(-cost);
	}

}
