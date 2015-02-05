package intersection;

import Vehicle.Car;

public interface IntersectionListener {
	void aproachingIntersection(Class<Car> car, Intersection intersection);
	
}
