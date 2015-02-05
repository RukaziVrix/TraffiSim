package intersection;

import java.util.Random;

public class SingeltonRandom {
	private static Random randle = new Random();
	
	public static Random get() {
		return randle;
	}
	
}
