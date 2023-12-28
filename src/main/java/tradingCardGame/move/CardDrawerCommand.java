package tradingCardGame.move;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.player.Player;

public class CardDrawerCommand implements Command {
	
	private Game game;
	private Card card;

	public CardDrawerCommand(Game game, Card card) {
		this.game = game;
		this.card = card;
	}
	
	@Override
	public void execute() {
		Player active = game.active();
		int numberOfCards = card.numberOfCards();
		while(numberOfCards > 0) {
			game.drawCard(active);
			numberOfCards--;
		}
		
		active.updateManaBy(-card.cost());
	}

}
