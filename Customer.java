package assignment1;
public class Customer {
	private String name;
	private int balance;
	private Basket productLikeToBuy;
	public Customer(String name, int balance) {
		Basket emptyBasket = new Basket();
		this.name = name;
		this.balance = balance;
		this.productLikeToBuy = emptyBasket;
	}
	public String getName() {
		return this.name;
	}
	public int getBalance() {
		return this.balance;
	}
	public Basket getBasket() {
		return this.productLikeToBuy;
	}
	public int addFunds(int addFunds) {
		if(addFunds<0) {
			throw  new IllegalArgumentException("This is not working");
		}
		this.balance += addFunds;
		return this.balance;
	}
	public void addToBasket(MarketProduct productWant) {
		this.productLikeToBuy.add(productWant);
	}
	public boolean removeFromBasket(MarketProduct productDontWant) {
		return this.productLikeToBuy.remove(productDontWant);
	}
	public String checkOut() {
		if(this.balance < this.productLikeToBuy.getTotalCost()) {
			throw  new IllegalStateException("Sorry, you don't have enough balance for products.This is not working");
		}
		this.balance -= this.productLikeToBuy.getTotalCost();
		String receipt = this.productLikeToBuy.toString();
		this.productLikeToBuy.clear();
		return receipt;
	}

}
