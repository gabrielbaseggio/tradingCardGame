package tradingCardGame.random;

import java.util.Random;

public class DefaultRandom implements GameRandom {
	
	private Random random;
	
	public DefaultRandom() {
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public int randomInt(int lowerBound, int upperBound) {
		return random.nextInt(upperBound);
	}

	@Override
	public int chooseActive() {
		return random.nextInt(2) + 1;
	}

}
