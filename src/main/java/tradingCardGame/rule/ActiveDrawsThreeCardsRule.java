package tradingCardGame.rule;

import tradingCardGame.Game;

public class ActiveDrawsThreeCardsRule implements Rule {

	@Override
	public void execute(Game game) {
		game.drawCard(game.active());
		game.drawCard(game.active());
		game.drawCard(game.active());
	}

}
