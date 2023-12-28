package tradingCardGame.rule;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.card.GameDeck;
import tradingCardGame.player.Player;
import tradingCardGame.random.GameRandom;

public class DefaultDrawCardRule implements Rule {
	
	private Player player;
	private GameRandom gameRandom;
	
	public DefaultDrawCardRule(Player player, GameRandom gameRandom) {
		this.player     = player;
		this.gameRandom = gameRandom;
	}

	@Override
	public void execute(Game game) {
		GameDeck deck = game.deck(player);
		if( !deck.isEmpty() ) {
			Card card = deck.drawCard(gameRandom.randomInt(0, deck.size()));
			if( player.hand().size() < 5 ) {
				player.putCardInHand(card);
			}
		}
	}

}
