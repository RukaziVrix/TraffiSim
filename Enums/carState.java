package Enums;

public enum carState {
	NEAR(0), IN(1),FAR(2), OUTSIDE(3);
	
	private int carState;
	
	private carState(int carState) {
		this.carState = carState;
	}
	
	public int getState() {
		return carState;
	}
}
