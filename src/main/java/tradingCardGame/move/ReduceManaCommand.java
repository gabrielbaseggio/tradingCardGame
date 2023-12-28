package tradingCardGame.move;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.player.Player;
import tradingCardGame.rule.ReduceManaRule;
import tradingCardGame.rule.Rule;

public class ReduceManaCommand implements Command {

	private Game game;
	private Card card;
	
	public ReduceManaCommand(Game game, Card card) {
		this.game = game;
		this.card = card;
	}
	
	@Override
	public void execute() {
		Player active   = game.active();
		Player opponent = game.opponent();
		int restoreMana    = opponent.mana();
		int restoreMaxMana = opponent.maxMana();
		opponent.setMana(active.mana() / 2);
		opponent.setMaxMana(active.maxMana() / 2);
		active.updateManaBy(-card.cost());
		
		Rule reduceMana = new ReduceManaRule(opponent
				,opponent.mana()
				,opponent.maxMana()
				,restoreMana
				,restoreMaxMana
				,2);
		
		game.addBeforeTurnRule(reduceMana);
	}
	
}
