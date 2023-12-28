package tradingCardGame.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import tradingCardGame.card.Card;

public class Player {
	String name;
	int health  = 30;
	int mana    = 0;
	int maxMana = 0;
	List<Card> hand = null;
	
	private PlayerState state = PlayerState.ALIVE;
	
	public Player(String name, int health, int mana, int maxMana) {
		this.name = name;
		this.health = health;
		this.mana = mana;
		this.maxMana = maxMana;
		this.hand = new LinkedList<Card>();
		
		updateState();
	}
	
	public int health() {
		return health;
	}
	
	public int mana() {
		return mana;
	}
	
	public int maxMana() {
		return maxMana;
	}

	public void decreaseHealthBy(int damage) {
		if(illegalDamage(damage)) { throw new IllegalArgumentException(); }
		health -= damage;
		updateState();
	}

	private boolean illegalDamage(int damage) {
		return damage < 0;
	}

	private void updateState() {
		if(health <= 0) {
			state = PlayerState.DEAD;
		}
	}
	
	public void updateManaBy(int delta) {
		if(illegalResultingMana(delta)) throw new IllegalArgumentException();
		if(mana + delta > maxMana) {
			mana = maxMana;
		} else {
			mana += delta;
		}
	}

	private boolean illegalResultingMana(int delta) {
		return delta + mana < 0;
	}

	public void increaseMaxManaBy(int increaseBy) {
		if(illegalIncrease(increaseBy)) throw new IllegalArgumentException();
		maxMana += increaseBy;
	}

	private boolean illegalIncrease(int increaseBy) {
		return increaseBy < 0;
	}
	
	public List<Card> hand() {
		return hand;
	}

	public boolean isAlive() {
		return state == PlayerState.ALIVE;
	}

	public boolean isDead() {
		return !isAlive();
	}

	public void increaseHealthBy(int increaseBy) {
		health = health + increaseBy;
	}
	
	public String name() {
		return name;
	}

	public void putCardInHand(Card card) {
		hand.add( card );
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
		
	}
}
