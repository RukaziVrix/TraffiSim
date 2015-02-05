package traffic;

import greenfoot.Actor;
import Enums.TrafficLightColors;

public class TrafficLight extends Actor {
	private TrafficLightColors color;

	public TrafficLightColors getColor() {
		return color;
	}

	public void setColor(TrafficLightColors color) {
		this.color = color;
	}

	public TrafficLight(TrafficLightColors color) {
		this.color = color;
		setImage(this.color.getImage());
	}
	
}
