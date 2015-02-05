package Enums;

public enum Direction {
	DOWN(270),UP(90),LEFT(180),RIGHT(0);
	
	private int rotation;
	
	private Direction(int rotation) {
		this.rotation = rotation;
	}
	
	public int getrotation() {
		return rotation;
	}

}
