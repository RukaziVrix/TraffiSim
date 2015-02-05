package traffic;

import intersection.CollisionBox;
import intersection.Intersection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import Vehicle.Car;
import Enums.Direction;
import Enums.TrafficLightColors;
import greenfoot.World;

public class TrafficWorld extends World {
	final static int zero = 0;
	final static int one = 1;
	final static int CAR_DISTANCE = 24;
	final static int fif = 50;
	final static int calx = 158;
	final static int caly = 175;
	public final static int WORLD_WIDTH = 1000;
	public final static int WORLD_HEIGHT = 750;

	final static int CAR_AMOUNT = 2;
	final static int HorzRoadAmount = 5;
	final static int VertRoadAmount = 7;

	private ArrayList<Car> cars;
	private ArrayList<Intersection> intersection;
	private ArrayList<CollisionBox> box;
	

	static Random randle = new Random();

	public TrafficWorld() {
		super(WORLD_WIDTH, WORLD_HEIGHT, one);
		this.getBackground().setColor(Color.GREEN);
		this.getBackground().fill();
		box = new ArrayList<CollisionBox>();
		cars = new ArrayList<Car>();
		intersection = new ArrayList<Intersection>();
		buildRoads();
		buildAllIntersections();
		buildCars();

	}

	public void buildCars() {
		
		int StartY = 14;
		int StartX = 13;
		
		for (int y = 0; y < CAR_AMOUNT; y++) {
			for (int x = 0; x < HorzRoadAmount; x++) {
				int positionX = randle.nextInt(WORLD_WIDTH - 1) + 1;
				int positionY = StartY + (x * caly);
				Car car = new Car(this);
				this.addObject(car, positionX, positionY);
				this.addCars(car);
				
				while(car.checkIfInIntersection(this) == true) {
					positionX = randle.nextInt(WORLD_WIDTH - 1) + 1;
					positionY = StartY + (x * caly);
					car.setLocation(positionX, positionY);
				}

				if ((y % 2) == 0) {
					car.setRotation(Direction.LEFT.getrotation());
				} else {
					positionY += CAR_DISTANCE;
					car.setLocation(positionX, positionY);
					car.setRotation(Direction.RIGHT.getrotation());
				}
				
			}
		}

		for (int y = 0; y < CAR_AMOUNT; y++) {
			for (int x = 0; x < VertRoadAmount; x++) {
				int positionY = randle.nextInt(WORLD_HEIGHT - 1) + 1;
				int positionX = StartX + (x * calx);
				Car car = new Car(this);
				this.addObject(car, positionX, positionY);
				this.addCars(car);
				
				while(car.checkIfInIntersection(this) == true) {
					positionY = randle.nextInt(WORLD_HEIGHT - 1) + 1;
					positionX = StartX + (x * calx);
					car.setLocation(positionX, positionY);
				}

				if ((y % 2) == 0) {
					car.setRotation(Direction.UP.getrotation());
				} else {
					positionX += CAR_DISTANCE;
					car.setLocation(positionX, positionY);
					car.setRotation(Direction.DOWN.getrotation());
				}
				
			}
		}
	}

	public void buildAllIntersections() {
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 5; y++) {
				buildIntersection(x, y);
			}
		}
	}

	private void buildIntersection(int x, int y) {
		int startX = 25;
		int startY = 25;

		Intersection intersection = new Intersection(4, this);
		CollisionBox box = new CollisionBox();
	
		this.addObject(intersection, startX + (x * calx), startY + (y * caly));
		this.addObject(box, startX + (x * calx), startY + (y * caly));
		
		createDiffTrafficLightColor(intersection, x, y);
		addIntersection(intersection);
		addCollisionBox(box);
		
		

	}

	public void createDiffTrafficLightColor(Intersection intersection, int x,
			int y) {
		if (y % 2 == 0) {
			setIntersectionColor(intersection, x, y, TrafficLightColors.Green,
					TrafficLightColors.Red);
		} else {
			setIntersectionColor(intersection, x, y, TrafficLightColors.Red,
					TrafficLightColors.Green);
		}
	}

	private void setIntersectionColor(Intersection intersection, int x, int y,
			TrafficLightColors colorv, TrafficLightColors colorh) {
		TrafficLight light;

		if (y != 0) {
			light = buildLight(intersection.getX(), intersection.getY() - 32,
					Direction.LEFT, colorv);
			intersection.addLightV(light);
		}
		if (y != 4) {
			light = buildLight(intersection.getX(), intersection.getY() + 32,
					Direction.RIGHT, colorv);
			intersection.addLightV(light);
		}
		if (x != 0) {
			light = buildLight(intersection.getX() - 32, intersection.getY(),
					Direction.UP, colorh);
			intersection.addLightH(light);
		}
		if (x != 6) {
			light = buildLight(intersection.getX() + 32, intersection.getY(),
					Direction.DOWN, colorh);
			intersection.addLightH(light);
		}
	}

	private TrafficLight buildLight(int x, int y, Direction dir,
			TrafficLightColors color) {
		TrafficLight light = new TrafficLight(color);
		light.setRotation(dir.getrotation());
		this.addObject(light, x, y);

		return light;
	}

	public void buildRoads() {
		int n = zero;
		int d = zero;
		for (int i = zero; i < 7; i++) {
			roads(n, zero, fif, WORLD_HEIGHT);
			n += calx;
		}
		for (int i = zero; i < 5; i++) {
			roads(zero, d, WORLD_WIDTH, fif);
			d += caly;
			
		}

	}

	public void roads(int x, int y, int sizex, int sizey) {
		this.getBackground().setColor(Color.GRAY);
		this.getBackground().fillRect(x, y, sizex, sizey);
	}

	public ArrayList<Intersection> getIntersection() {
		return intersection;
	}

	public void setIntersection(ArrayList<Intersection> intersection) {
		this.intersection = intersection;
	}

	public void addIntersection(Intersection intersection) {
		getIntersection().add(intersection);
	}

	public ArrayList<CollisionBox> getBox() {
		return box;
	}

	public void setBox(ArrayList<CollisionBox> box) {
		this.box = box;
	}
	
	public void addCollisionBox(CollisionBox box) {
		getBox().add(box);
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}
	public void addCars(Car car) {
		getCars().add(car);
	}
	
	public void removeCars(int index) {
		getCars().remove(index);
		
	}

	

}