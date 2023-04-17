package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class which represent a plane
 * 
 * @author Ayala
 *
 */
public class Plane implements Geometry {
	private final Point p0;
	private final Vector normal;

	/***
	 * 
	 * @param p point
	 * @param n normal vector
	 * first constructor - gets a point and a normal vector and builds a plain
	 */
	public Plane(Point p, Vector n) {
		this.p0 = p;
		this.normal = n;
	}

	/***
	 * 
	 * @param p1 point
	 * @param p2 point
	 * @param p3 point
	 * second constructor - gets three points and builds a plain
	 */
	public Plane(Point p1, Point p2, Point p3) {
		try {
			this.normal = (p2.subtract(p3)).crossProduct(p1.subtract(p3)).normalize();
			this.p0 = p1;
		    } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: Plane() the points are on the same line ");
		    }
		
	}
	

	/**
	 * 
	 * @return the fild normal of plane
	 */
	public Vector getNormal() {
		return this.normal;
	}

	@Override
	public Vector getNormal(Point p) {
		return this.normal;
	}

}
