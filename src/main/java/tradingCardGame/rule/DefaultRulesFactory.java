package tradingCardGame.rule;

import java.util.LinkedList;
import java.util.List;

import tradingCardGame.player.Player;
import tradingCardGame.random.GameRandom;

public class DefaultRulesFactory extends RulesFactory {
	public List<Rule> createBeforeGameRules() {
		List<Rule> rules = new LinkedList<Rule>();
		rules.add(new ActiveDrawsThreeCardsRule());
		rules.add(new OpponentDrawsFourCardsRule());
		
		return rules;
	}

	public List<Rule> createBeforeTurnRules() {
		List<Rule> rules = new LinkedList<Rule>();
		rules.add(new ActiveDrawsACard());
		rules.add(new IncreaseManaRule());
		rules.add(new RefillManaRule());
		rules.add(new BleedingOutRule());
		
		return rules;
	}
	
	public Rule createDrawCardRule(Player player, GameRandom gameRandom) {
		return new DefaultDrawCardRule(player, gameRandom);
	}
}
