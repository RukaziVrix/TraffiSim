package intersection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Vehicle.Car;
import Enums.Direction;
import Enums.TrafficLightColors;
import traffic.TrafficLight;
import traffic.TrafficWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Intersection extends Actor implements IntersectionListener {

	private GreenfootImage img;
	private final int maxTimer = 100;
	private int timer = getMaxTimer();
	Random ran = new Random();

	private ArrayList<TrafficLight> lightsH = new ArrayList<TrafficLight>();
	private ArrayList<TrafficLight> lightsV = new ArrayList<TrafficLight>();
	
	private TrafficWorld worldRef;

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getMaxTimer() {
		return maxTimer;
	}

	public ArrayList<TrafficLight> getLightsH() {
		return lightsH;
	}

	public void setLightsH(ArrayList<TrafficLight> lightsH) {
		this.lightsH = lightsH;
	}

	public ArrayList<TrafficLight> getLightsV() {
		return lightsV;
	}

	public void setLightsV(ArrayList<TrafficLight> lightsV) {
		this.lightsV = lightsV;
	}

	public Intersection(int amount, TrafficWorld world) {
		img = new GreenfootImage(50, 50);
		setImage(img);
		this.worldRef = world;
	}

	public void addLightV(TrafficLight light) {
		getLightsV().add(light);
	}

	public void addLightH(TrafficLight light) {
		getLightsH().add(light);
	}

	public void switchLights() {
		TrafficLightColors colorV = lightsV.get(0).getColor();
		TrafficLightColors colorH = lightsH.get(0).getColor();

		if (colorV == TrafficLightColors.Green) {
			colorV = TrafficLightColors.Yellow;
			colorH = TrafficLightColors.Red;

		} else if (colorV == TrafficLightColors.Yellow) {
			colorV = TrafficLightColors.Red;
			colorH = TrafficLightColors.Green;
		} else if (colorH == TrafficLightColors.Green) {
			colorH = TrafficLightColors.Yellow;
			colorV = TrafficLightColors.Red;
		} else if (colorH == TrafficLightColors.Yellow) {
			colorH = TrafficLightColors.Red;
			colorV = TrafficLightColors.Green;
		}

		for (TrafficLight light : lightsH) {
			light.setColor(colorH);
			light.setImage(light.getColor().getImage());
		}

		for (TrafficLight light : lightsV) {
			light.setColor(colorV);
			light.setImage(light.getColor().getImage());
		}
	}

	@Override
	public void act() {
		timer -= 1;
		if (timer == 0) {
			switchLights();
			timer = maxTimer;
		}
		stopCar();
	}

	public void stopCar() {
		enteringInteresection(Direction.UP,lightsV);
		enteringInteresection(Direction.DOWN,lightsV);
		enteringInteresection(Direction.LEFT,lightsH);
		enteringInteresection(Direction.RIGHT,lightsH);
	}

	public void CarTurningDecision(Car car) {
		int index = ran.nextInt(3) + 1;
		switch (index) {

		case 1:
			car.setCarInTurningLeft(true);
			break;
		case 2:
			car.setCarINturningRight(true);
			break;
		default:
			break;
		}
	}

	public void enteringInteresection(Direction direction, ArrayList<TrafficLight> lights) {
		List<Car> cars = this.getIntersectingObjects(Car.class);
		if (cars != null) {
			for (Car car : cars) {
				if (car.getRotation() == direction.getrotation()) {
					if (car.isActivateExplosion() == false) {
						for (TrafficLight light : lights) {

							if (light.getColor().equals(TrafficLightColors.Red)) {
								if (car.checkIfInInnerIntersection(worldRef) == true) {
									car.setSPEED(1);
									car.setStop(false);
								} else {
									car.setTouchingIntersection(false);
									
									if(car.isStop() == true) {
									car.stop();
									}
								}

							} else if (light.getColor().equals(
									TrafficLightColors.Green)) {

								if (car.isTouchingIntersection() == false)
									CarTurningDecision(car);
									//car.setCarINturningRight(true);

								car.setSPEED(1);

							} else if (light.getColor().equals(
									TrafficLightColors.Yellow)) {

								if (car.isTouchingIntersection() == false)
									//CarTurningDecision(car);

								car.setSPEED(1);
							}
						}
					}
				}
				
				if (car.checkIfInInnerIntersection(worldRef) == true) {
					car.setTouchingIntersection(true);
					car.setStop(false);
				}
			}
		}
	}

	@Override
	public void aproachingIntersection(Class<Car> car, Intersection intersection) {
		intersection.getObjectsInRange(25, car);

	}

}
