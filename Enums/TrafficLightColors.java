package Enums;

public enum TrafficLightColors {
	Green("images/trafficLightGreen.png"),Yellow("images/trafficLightYellow.png"),Red("images/trafficLightRed.png");
	
	private String color;
	
	private TrafficLightColors(String color) {
		this.color = color;
	}
	public String getImage() {
		return color;
	}
}
