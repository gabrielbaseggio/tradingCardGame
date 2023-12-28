package tradingCardGame.exception;

public class IllegalMoveException extends Exception {

	private static final long serialVersionUID = 1474215932093796873L;

	public IllegalMoveException() {
		super("Illegal move");
	}
}
