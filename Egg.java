package assignment1;
public class Egg extends MarketProduct{
	private int number;
	private int price;
	public Egg(String name,int number,int price) {
		super(name);
		this.number = number;
		this.price = price;
	}
	@Override
	public int getCost() {
		double percent = ((double)this.number)/12;
		return (int) (percent*this.price);
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Egg)) {
			return false;
		}
		Egg eggObj = (Egg) obj;
		if (this.getName().equals(eggObj.getName())&&this.number == eggObj.number&&
				this.getCost() == eggObj.getCost()&&this.price == eggObj.price) {
			return true;
		}
		return false;
		
		
	}
}
