package tradingCardGame.rule;

import java.util.List;

import tradingCardGame.player.Player;
import tradingCardGame.random.GameRandom;

public abstract class RulesFactory {
	
	public abstract List<Rule> createBeforeGameRules();
	public abstract List<Rule> createBeforeTurnRules();
	public abstract Rule createDrawCardRule(Player player, GameRandom gameRandom);

}
