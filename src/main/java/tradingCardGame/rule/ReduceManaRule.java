package tradingCardGame.rule;

import tradingCardGame.Game;
import tradingCardGame.player.Player;

public class ReduceManaRule implements Rule {
	
	private Player player;
	private int reduceMana;
	private int reduceMaxMana;
	private int restoreMana;
	private int restoreMaxMana;
	private int numberOfTurns;
	private int counter;
	
	public ReduceManaRule(Player player, int reduceMana, int reduceMaxMana, int restoreMana, int restoreMaxMana, int numberOfTurns) {
		this.player  = player;
		this.reduceMana     = reduceMana;
		this.reduceMaxMana  = reduceMaxMana;
		this.restoreMana    = restoreMana;
		this.restoreMaxMana = restoreMaxMana;
		this.numberOfTurns = numberOfTurns;
		this.counter = 0;
	}

	@Override
	public void execute(Game game) {
		if(counter < numberOfTurns) {
			player.setMana(reduceMana);
			player.setMaxMana(reduceMaxMana);
		} else if(counter == numberOfTurns) {
			player.setMana(restoreMana);
			player.setMaxMana(restoreMaxMana);
		}
		
		counter++;
	}

}
