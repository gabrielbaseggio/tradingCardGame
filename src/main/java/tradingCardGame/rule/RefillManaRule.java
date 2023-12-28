package tradingCardGame.rule;

import tradingCardGame.Game;
import tradingCardGame.player.Player;

public class RefillManaRule implements Rule {

	@Override
	public void execute(Game game) {
		Player active = game.active();
		active.updateManaBy(active.maxMana());
	}

}
