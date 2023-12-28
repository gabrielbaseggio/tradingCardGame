package tradingCardGame;

import java.util.Scanner;

import tradingCardGame.card.DefaultDeckFactory;
import tradingCardGame.exception.IllegalCardTypeException;
import tradingCardGame.exception.IllegalDeckTypeException;
import tradingCardGame.move.Move;
import tradingCardGame.player.Player;
import tradingCardGame.rule.DefaultRulesFactory;

public class Main {
	public static void main(String[] args) throws IllegalDeckTypeException, IllegalCardTypeException  {
		int index;
		Game game = new Game(new DefaultRulesFactory(), new DefaultDeckFactory());
		game.newGame("SrGabriedo", "Pete Zahut");
		while ( !game.isOver() ) {
			showOptions();
			
			Move move = getMove();
			while( move != Move.PASS
					&& game.activePlayerCanContinuePlaying() ) {
				index = chooseCard(game.active());
				
				try {
					game.execute(move, index);
				} catch(Exception e) {
					System.out.println("Try again!");
				}
				
				showOptions();
				move = getMove();
			}
			
			game.passTurn();
		}
}

	private static int chooseCard(Player player) {
		int index;
		do {
			System.out.println("Choose a card");
			Scanner scanner = new Scanner(System.in);
			index = scanner.nextInt();
		} while(index < 0 || index >= player.hand().size());
		
		return index;
	}

	private static Move getMove() {
		int option;
		do {
			Scanner scanner = new Scanner(System.in);
			option = scanner.nextInt();
		} while(option < 0 || option > 4);
		
		switch (option) {
			case 1:
				return Move.ATTACK;
			case 2:
				return Move.HEAL;
			case 3:
				return Move.CARDDRAWER;
			default:
				return Move.PASS;
		}
	}

	private static void showOptions() {
		System.out.println("Choose an option");
		System.out.println("1. ATTACK");
		System.out.println("2. HEAL");
		System.out.println("3. CARD DRAWER");
		System.out.println("4. PASS");
	}
	
}
