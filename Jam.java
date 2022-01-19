package assignment1;
public class Jam extends MarketProduct{
	private int number;
	private int price;
	public Jam(String name,int number,int price) {
		super(name);
		this.number = number;
		this.price = price;
	}
	@Override
	public int getCost() {
		return (int) (this.number*this.price);
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Jam)) {
			return false;
		}
		Jam jamObj = (Jam) obj;
		if (this.getName().equals(jamObj.getName())&&this.number == jamObj.number&&
				this.getCost() == jamObj.getCost()&&this.price == jamObj.price) {
			return true;
		}
		return false;
		
	}
}
