package tradingCardGame.random;

public class DummyRandom implements GameRandom {

	@Override
	public int randomInt(int lowerBound, int upperBound) {
		return 0;
	}

	@Override
	public int chooseActive() {
		return 1;
	}
	
}
