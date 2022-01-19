package assignment1;
public class Fruit extends MarketProduct{
	private double weight;
	private int price;
	public Fruit(String name,double weight,int price) {
		super(name);
		this.weight = weight;
		this.price = price;
	}
	@Override
	public int getCost() {
		return (int) (this.weight*this.price);
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Fruit)) {
			return false;
		}
		Fruit fruitObj = (Fruit) obj;
		if (this.getName().equals(fruitObj.getName())&&this.weight == fruitObj.weight&&
				this.getCost() == fruitObj.getCost()) {
			return true;
		}
		return false;
		
		
	}
}
