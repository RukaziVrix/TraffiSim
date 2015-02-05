package Vehicle;

import greenfoot.Actor;
import intersection.CollisionBox;
import intersection.Intersection;

import java.util.ArrayList;
import java.util.Random;

import traffic.TrafficWorld;
import Enums.Direction;

public class Car extends Actor {
	Random ran = new Random();
	private int SPEED = 1;
	private final int maxTimer = 30;
	private final int maxTurnRTimer = 28;
	private final int maxTurnLTimer = 55;
	private int timer = getMaxTimer();
	private final int maxDestroyTimer = 10;
	private int destroyTimer = maxDestroyTimer;
	private int LeftTurnTimer = maxTurnLTimer;
	private int RightTurnTimer = maxTurnRTimer;
	int index = 0;
	private boolean destroyCar = false;
	private boolean activateExplosion = false;
	private boolean CarInTurningLeft = false;
	private boolean CarINturningRight = false;
	private boolean isTouchingIntersection = false;
	private boolean stop = true;
	
	private TrafficWorld worldRef;

	public Car(TrafficWorld world) {
		String[] filePath = new String[] { "images/topCarBlue.png",
				"images/topCarRed.png", "images/topCarPurple.png",
				"images/topCarYellow.png" };
		int index = ran.nextInt(filePath.length);
		setImage(filePath[index]);
		this.worldRef = world;
	}

	public void explosion() {
		String[] explosionAnimation = new String[] { "images/explosion1.png",
				"images/explosion2.png", "images/explosion3.png" };

		timer -= 1;
		this.stop();
		if (timer == 0 && isDestroyCar() == false) {
			setImage(explosionAnimation[index]);
			setRotation(Direction.RIGHT.getrotation());
			index += 1;
			if (index == 3) {
				setDestroyCar(true);
			}

			timer = getMaxTimer();
		}
	}

	public void destroyCar() {
		destroyTimer--;

		if (destroyTimer <= 0) {
			this.getWorld().removeObject(this);
			worldRef.getCars().remove(this);
		}
	}

	public boolean checkIfInIntersection(TrafficWorld world) {
		for (Intersection intersection : world.getIntersection()) {
			if (this.intersects(intersection)) {
				return true;
			}
		}

		return false;
	}

	public boolean checkIfInInnerIntersection(TrafficWorld world) {
		for (CollisionBox box : world.getBox()) {
			if (this.intersects(box)) {
				return true;
			}
		}

		return false;
	}

	public boolean checkCarCollision(TrafficWorld world) {
		for (Car car : world.getCars()) {
			if (this.intersects(car) && !this.equals(car)) {
				return true;
			}
		}
		return false;

	}

	public void act() {
		move(SPEED);
		carWarp();
		if (activateExplosion == false)
			activateExplosion = checkCarCollision(worldRef);

		if (activateExplosion == true) {
			explosion();
		}

		if (isDestroyCar() == true) {
			destroyCar();
		}
		if(isTouchingIntersection == true) {
			if (CarInTurningLeft == true) {
				LeftTurnTimer--;
				if (LeftTurnTimer == 0) {
					turnLeft();
					
				}
			}
			if (CarINturningRight == true) {
				RightTurnTimer--;
				if (RightTurnTimer == 0) {
					turnRight();
					
				}
			}
			else {
				RightTurnTimer = maxTurnRTimer;
				LeftTurnTimer = maxTurnLTimer;
			}
		}
		if(checkIfInIntersection(worldRef) == false) {
			setStop(true);
		}
	}

	public boolean isAtEdge() {
		if (getX() >= TrafficWorld.WORLD_WIDTH - 1
				|| getX() <= 0) {
			return true;
		}

		if (getY() >= TrafficWorld.WORLD_HEIGHT - 1
				|| getY() <= 0) {
			return true;
		}
		return false;
	}

	public void carWarp() {
		int x = getX();
		int y = getY();

		if (isAtEdge() == true) {

			if (getX() >= TrafficWorld.WORLD_WIDTH - 1) {
				setLocation(1, y);
			}
			if (getX() <= 0) {
				setLocation(TrafficWorld.WORLD_WIDTH - 1, y);
			}

			if (getY() >= TrafficWorld.WORLD_HEIGHT - 1) {
				setLocation(x, 1);
			}
			if (getY() <= 0) {
				setLocation(x, TrafficWorld.WORLD_HEIGHT - 1);
			}

		}
	}

	public void turning(Direction newTurnRotation) {
		stop();
		setRotation(newTurnRotation.getrotation());
		setCarInTurningLeft(false);
		setCarINturningRight(false);
		
	}

	public void turnLeft() {
		switch (getRotation()) {
		case 0:
			turning(Direction.DOWN);
			break;
		case 90:
			turning(Direction.RIGHT);
			break;
		case 180:
			turning(Direction.UP);
			break;
		case 270:
			turning(Direction.LEFT);
			break;
		}
	}

	public void turnRight() {
		switch (getRotation()) {
		case 0:
			turning(Direction.UP);
			break;
		case 90:
			turning(Direction.LEFT);
			break;
		case 180:
			turning(Direction.DOWN);
			break;
		case 270:
			turning(Direction.RIGHT);
			break;
		}
	}

	public int getSPEED() {
		return SPEED;
	}

	public void setSPEED(int sPEED) {
		SPEED = sPEED;
	}

	public int getMaxTimer() {
		return maxTimer;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isDestroyCar() {
		return destroyCar;
	}

	public void setDestroyCar(boolean destroyCar) {
		this.destroyCar = destroyCar;
	}

	public int getDestroyTimer() {
		return destroyTimer;
	}

	public void setDestroyTimer(int destroyTimer) {
		this.destroyTimer = destroyTimer;
	}

	public boolean isActivateExplosion() {
		return activateExplosion;
	}

	public void setActivateExplosion(boolean activateExplosion) {
		this.activateExplosion = activateExplosion;
	}

	public int getMaxDestroyTimer() {
		return maxDestroyTimer;
	}

	public int getLeftTurnTimer() {
		return LeftTurnTimer;
	}

	public void setLeftTurnTimer(int leftTurnTimer) {
		LeftTurnTimer = leftTurnTimer;
	}

	public int getRightTurnTimer() {
		return RightTurnTimer;
	}

	public void setRightTurnTimer(int rightTurnTimer) {
		RightTurnTimer = rightTurnTimer;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isCarInTurningLeft() {
		return CarInTurningLeft;
	}

	public void setCarInTurningLeft(boolean carInTurningLeft) {
		CarInTurningLeft = carInTurningLeft;
	}

	public boolean isCarINturningRight() {
		return CarINturningRight;
	}

	public void setCarINturningRight(boolean carINturningRight) {
		CarINturningRight = carINturningRight;
	}

	public boolean isTouchingIntersection() {
		return isTouchingIntersection;
	}

	public void setTouchingIntersection(boolean isTouchingIntersection) {
		this.isTouchingIntersection = isTouchingIntersection;
	}

	public void stop() {
		this.setSPEED(0);

	}
}
