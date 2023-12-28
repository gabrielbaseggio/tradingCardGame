package tradingCardGame.rule;

import tradingCardGame.Game;
import tradingCardGame.player.Player;

public class IncreaseManaRule implements Rule {

	@Override
	public void execute(Game game) {
		Player active = game.active();
		if(active.maxMana() < 10) {
			active.increaseMaxManaBy(1);
		}
	}

}
