package gameClient;

import java.util.Comparator;

public class Comperator implements Comparator<Fruit> {

	@Override
	public int compare(Fruit f1, Fruit f2) {
		
		if(f1.getValue()==f2.getValue()) {
			return 0;
		}
		if(f1.getValue()<f2.getValue()) {
			return 1;
		}
		return -1;
	}
	

}