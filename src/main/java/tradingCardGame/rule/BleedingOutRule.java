package tradingCardGame.rule;

import tradingCardGame.Game;
import tradingCardGame.card.GameDeck;
import tradingCardGame.player.Player;

public class BleedingOutRule implements Rule {

	@Override
	public void execute(Game game) {
		Player active = game.active();
		GameDeck deck = game.deck( active );
		if( deck.isEmpty() ) {
			active.decreaseHealthBy(1);
		}
	}
	
	

}
