package intersection;

import java.awt.Color;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class CollisionBox extends Actor{

	private static final int IMAGE_SIZE = 48;
	private GreenfootImage img;
	
	public CollisionBox() {
		img = new GreenfootImage(IMAGE_SIZE, IMAGE_SIZE);
		setImage(img);
	}
}
