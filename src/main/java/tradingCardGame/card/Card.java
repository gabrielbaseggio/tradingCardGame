package tradingCardGame.card;

public class Card {

	private int cost   = 0;
	private int damage = 0;
	private boolean isCardDrawer;
	private boolean isReduceMana;
	private int numberOfCards;
	
	public Card(int cost, int damage) {
		if(illegalCost(cost)) { throw new IllegalArgumentException(); }
		this.cost   = cost;
		this.damage = damage;
	}
	
	public Card(int cost, boolean isCardDrawer, int numberOfCards) {
		if(illegalCost(cost)) { throw new IllegalArgumentException(); }
		if(illegalNumberOfCards(numberOfCards)) { throw new IllegalArgumentException(); }
		this.cost   = cost;
		this.isCardDrawer  = isCardDrawer;
		this.numberOfCards = numberOfCards;
	}
	
	public Card(int cost, boolean isCardDrawer, boolean isReduceMana, int numberOfCards) {
		if(illegalCost(cost)) { throw new IllegalArgumentException(); }
		if(illegalNumberOfCards(numberOfCards)) { throw new IllegalArgumentException(); }
		this.cost   = cost;
		this.isCardDrawer  = isCardDrawer;
		this.isReduceMana  = isReduceMana;
		this.numberOfCards = numberOfCards;
	}

	private boolean illegalNumberOfCards(int numberOfCards) {
		return numberOfCards < 0;
	}

	private boolean illegalCost(int cost) {
		return cost < 0;
	}
	
	public int cost() {
		return cost;
	}
	
	@Override
	public String toString() {
		if(isCardDrawer) {
			return "[Cost: " + cost + ", is card drawer]";
		} else {
			return "[Cost: " + cost + ", Damage: " + damage + "]";
		}
	}
	
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Card other = (Card) obj;
		return cost() == other.cost();
	}

	public int damage() {
		return damage;
	}
	
	public boolean isCardDrawer() {
		return isCardDrawer;
	}

	public int numberOfCards() {
		return numberOfCards;
	}

	public boolean isReduceMana() {
		return isReduceMana;
	}
}
