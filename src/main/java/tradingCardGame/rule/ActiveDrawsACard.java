package tradingCardGame.rule;

import tradingCardGame.Game;

public class ActiveDrawsACard implements Rule {

	@Override
	public void execute(Game game) {
		game.drawCard(game.active());
	}

}
