package assignment1;

public class SeasonalFruit extends Fruit{
	public SeasonalFruit(String name,double weight,int price) {
		super(name,weight,price);
	}
	@Override
	public int getCost() {
		double discount = 0.85;
		return (int) (super.getCost()*discount);
		
	}

}
