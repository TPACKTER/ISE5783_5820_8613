package geometries;

import primitives.Point;
import primitives.Vector;

/***
 * representing sphere geometry has radius and a center point
 * 
 * @author Tamar @
 */
public class Sphere extends RadialGeometry {
	final private Point center;

	/**
	 * 
	 * @param p
	 * @param r gets a canter point and a radius and builds a sphere
	 */
	public Sphere(Point p, double r) {
		this.center = p;
		this.radius = r;
	}

	/**
	 * 
	 * @return the sphere center
	 */
	Point getCenter() {
		return this.center;
	}

	/**
	 * 
	 * @return the radius of the sphere
	 */
	double getRadius() {
		return this.radius;
	}

	@Override
	public String toString() {
		return "center: " + this.center.toString() + " radius: " + this.radius;
	}

	/***
	 * returning a normal to sphere in a given point: calculated by subtraction of
	 * the center point by the normal point
	 */
	@Override
	public Vector getNormal(Point p) {
		 return p.subtract(this.center).normalize();
	}
}
