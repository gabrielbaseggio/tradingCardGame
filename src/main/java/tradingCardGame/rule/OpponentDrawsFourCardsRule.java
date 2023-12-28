package tradingCardGame.rule;

import tradingCardGame.Game;

public class OpponentDrawsFourCardsRule implements Rule {

	@Override
	public void execute(Game game) {
		game.drawCard(game.opponent());
		game.drawCard(game.opponent());
		game.drawCard(game.opponent());
		game.drawCard(game.opponent());
	}

}
