package assignment2;


public class SolitaireCipher {
	public Deck key;
	
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}
	
	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		int[] keyStream = new int[size];
		String s = "";
		for(int i=0;i<size;i++) {
			keyStream[i] = this.key.generateNextKeystreamValue();
			s += keyStream[i] + " ";
		}
		//System.out.println(s);
		return keyStream;
	}
		
	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		String newMsg = msg.toUpperCase();
		newMsg = newMsg.replaceAll("[\\pP\\p{Punct}]","");
		newMsg = newMsg.replaceAll("\\p{C}","");
		newMsg = newMsg.replace(" ","");
		newMsg = newMsg.replaceAll("\\d+","");
		int [] keyStream = this.getKeystream(newMsg.length());
		String encode = "";
		for (int i=0;i<newMsg.length();i++) {
			int value = keyStream[i];
			char c = newMsg.charAt(i);
			encode += (char)(65+((c+value-65)%26));
			
		}
		
		/**** ADD CODE HERE ****/
		return encode;
	}
	
	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		
		String newMsg = msg.toUpperCase();
		newMsg = newMsg.replaceAll("[\\pP\\p{Punct}]","");
		newMsg = newMsg.replace(" ","");
		int [] keyStream = this.getKeystream(newMsg.length());
		String decode = "";
		for (int i=0;i<newMsg.length();i++) {
			int value = keyStream[i];
			char c = newMsg.charAt(i);
			decode += (char)(90-((90-c+value)%26));
			
		}
		
		/**** ADD CODE HERE ****/
		return decode;
	}
	
}
