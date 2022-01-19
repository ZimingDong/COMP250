package assignment1;

public class Basket {
	private MarketProduct[] basket;
	public Basket() {
		this.basket = new MarketProduct[0];
	}
	public MarketProduct[] getProducts() {
		MarketProduct[] shallowbasket = basket.clone();
		return shallowbasket;
	}
	public void add(MarketProduct item) {
		{
			if (item.equals(null)) {
				return;
			}
		}
		MarketProduct[] newBasket = new MarketProduct[this.basket.length+1];
		for(int i=0;i<this.basket.length;i++) {
			newBasket[i] = this.basket[i];
		}
		newBasket[this.basket.length] = item;
		this.basket = newBasket;
	}
	public boolean remove(MarketProduct item) {
		if (this.basket.length == 0) {
			return false;
		}
		int skipNumber = -1;
		MarketProduct[] newShelf = new MarketProduct[this.basket.length-1];
		for(int i=0;i<this.basket.length;i++) {
			if (this.basket[i].equals(item)) {
				skipNumber = i;
				break;
			}	 
		}
		if (skipNumber == -1) {
			return false;
		}
		
		for(int i=0;i<this.basket.length-1;i++) {
			if (i < skipNumber){
				newShelf[i] = this.basket[i];
			}
			if (i >= skipNumber) {
				newShelf[i] = this.basket[i+1];
			}
		}
		this.basket = newShelf;
		return true;
	}
	public void clear() {
		this.basket = new MarketProduct[0];
	}
	public int getNumOfProducts() {
		return this.basket.length;
	}
	public int getSubTotal() {
		int subTotal = 0;
		for(int i=0;i<this.basket.length;i++) {
			subTotal += this.basket[i].getCost();
		}
		return subTotal;
	}
	public int getTotalTax() {
		double taxRate = 0.15;
		int totalTax = 0;
		for(int i=0;i<this.basket.length;i++) {
			if (this.basket[i] instanceof Jam) {
				totalTax += (int)(this.basket[i].getCost()*taxRate);
			}
		}
		return totalTax;
	}
	public int getTotalCost() {
		return (int)(this.getSubTotal()+this.getTotalTax());
		
	}
	public String toString() {
		String productCost = "";
		String subTotal = "";
		String totalTax = "";
		String totalCost = "";
		for(int i=0;i<this.basket.length;i++) {
			if(this.basket[i].getCost() > 0) {
				productCost += (this.basket[i].getName() + "\t" + String.format("%.2f", (float)((this.basket[i].getCost())/100.0))) + "\n";
			}
			else {
				productCost += (this.basket[i].getName() + "\t" + "-" + "\n");
			}
		}
		if (this.getSubTotal() > 0) {
			subTotal = String.format("%.2f", (float)(this.getSubTotal()/100.0));
		}
		else {
			subTotal = "-";
		}
		if (this.getTotalTax() > 0) {
			totalTax = String.format("%.2f", (float)(this.getTotalTax()/100.0));
		}
		else {
			totalTax = "-";
		}
		if (this.getTotalCost() > 0) {
			totalCost = String.format("%.2f", (float)(this.getTotalCost()/100.0));
		}
		else {
			totalCost = "-";
		}
		String finalString = productCost + "\n" + "Subtotal" + "\t" + subTotal + "\n" 
		+ "Total Tax" + "\t" + totalTax + "\n" 
		+ "\n"
		+ "Total Cost" + "\t" + totalCost;
		return finalString;
	}

}
