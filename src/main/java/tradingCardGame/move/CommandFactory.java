package tradingCardGame.move;

import tradingCardGame.Game;
import tradingCardGame.card.Card;
import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.exception.IllegalMoveException;

public class CommandFactory {

	public Command createCommand(Game game, Move move, Card card) throws IllegalMoveException, IllegalCardTypeException {
		switch (move) {
			case ATTACK:
				return new DamageCardCommand(game, card);
			case HEAL:
				return new HealingCardCommand(game, card);
			case CARDDRAWER:
				if( !card.isCardDrawer() ) {
					throw new IllegalCardTypeException();
				} else {
					return new CardDrawerCommand(game, card);
				}
			case REDUCEMANA:
				if( !card.isReduceMana() ) {
					throw new IllegalCardTypeException();
				} else {
					return new ReduceManaCommand(game, card);
				}
			default:
				throw new IllegalMoveException();
		}
	}

}
