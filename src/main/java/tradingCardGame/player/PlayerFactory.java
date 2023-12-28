package tradingCardGame.player;

public class PlayerFactory {

	public Player createPlayer(String name, int health, int mana, int maxMana) {
		return new Player(name, health, mana, maxMana);
	}

}
