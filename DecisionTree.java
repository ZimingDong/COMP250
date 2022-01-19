import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	
	// Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf

		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}
		


		private String getStr(ArrayList<Datum> datalist) {
			String str = "";
			for (Datum d:datalist) {
				str += d.toString() + "\n";
			}
			return str;
		}

		
		// this method takes in a datalist (ArrayList of type datum). It returns the calling DTNode object 
		// as the root of a decision tree trained using the datapoints present in the datalist variable and minSizeDatalist.
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {
			int min = datalist.size();
			if (min == 0) {
				//this.label = this.findMajority(datalist);
				return null;
			}
			int sameLabel = datalist.get(0).y;
			
			if (min >= minSizeDatalist) {
				for (int i=0; i<min; i++) {
					if(datalist.get(i).y != sameLabel) {
						break;
					}
					if(i==min-1) {
						this.label = sameLabel;
						return this;
					}
				
				}
				double bestAvgEntropy = 2;
				double bestAttr = -1;
				double bestThre = -1;
				ArrayList<Datum> part1 = null;
				ArrayList<Datum> part2 = null;
				for (int i=0;i<datalist.get(0).x.length;i++) {
					for (Datum d1:datalist) {
						ArrayList<Datum> dl1 = new ArrayList<>();
						ArrayList<Datum> dl2 = new ArrayList<>();
						double attr = i;
						double thre = d1.x[i];
						for (Datum d2:datalist) {
							if (d2.x[i]<thre) {
								dl1.add(d2);
							}
							else {
								dl2.add(d2);
							}
						}
						double entr1 = calcEntropy(dl1)*dl1.size()/(dl1.size()+dl2.size());
						double entr2 = calcEntropy(dl2)*dl2.size()/(dl1.size()+dl2.size());
						double entr = entr1+entr2;
						
						if (entr < bestAvgEntropy) {
							bestAvgEntropy = entr;
							bestAttr = attr;
							bestThre = thre;
							part1 = dl1;
							part2 = dl2;
						}
						
					}
					
				}
				if (bestAvgEntropy == calcEntropy(datalist)) {
					this.label = this.findMajority(datalist);
					return this;
				}
				
				this.attribute = (int) bestAttr;
				this.threshold = bestThre;
				this.leaf = false;
				DTNode leftChild = new DTNode();
				DTNode rightChild = new DTNode();
				this.left = leftChild.fillDTNode(part1);
				this.right = rightChild.fillDTNode(part2);
				return this;
				
					
					
			}
			else {this.label = this.findMajority(datalist); return this;}

			//ADD CODE HERE

			
		}



		// This is a helper method. Given a datalist, this method returns the label that has the most
		// occurrences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist) {
			
			int [] votes = new int[2];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			
			if (votes[0] >= votes[1])
				return 0;
			else
				return 1;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
			if (this.leaf) {
				return this.label;
			}
			else {
				if (xQuery[this.attribute]<this.threshold) {
					return this.left.classifyAtNode(xQuery);
				}
				else {return this.right.classifyAtNode(xQuery);}
			}
			
			//ADD CODE HERE

		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2){
			try {
				DTNode Dt2 = (DTNode)dt2;
			}
			catch(Exception e) {
				return false;
			}
			if (dt2 == null) {
				return false;
			}
			DTNode Dt2 = (DTNode)dt2;
			if (this.leaf != Dt2.leaf) {
				return false;
			}
			if (this.leaf){
				if (this.label != Dt2.label) {
					return false;
				}
				return true;
				
			}
			if ((!(this.attribute == Dt2.attribute))||(!(this.threshold == Dt2.threshold))){
				return false;
			}
			if (this.left.equals(Dt2.left)== false) {
				return false;
			}
			if (this.right.equals(Dt2.right)== false) {
				return false;
			}

			//ADD CODE HERE

			return true; //dummy code.  Update while completing the assignment.
		}
	}



	//Given a dataset, this returns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist) {
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		return this.rootDTNode.classifyAtNode( xQuery );
	}

	// Checks the performance of a DecisionTree on a dataset
	// This method is provided in case you would like to compare your
	// results with the reference values provided in the PDF in the Data
	// section of the PDF
	String checkPerformance( ArrayList<Datum> datalist) {
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
