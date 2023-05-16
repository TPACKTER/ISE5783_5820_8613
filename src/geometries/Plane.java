package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Plane class represent a plane in 3D Cartesian coordinate system
 * 
 * @author Ayala and Tamar
 *
 */
public class Plane extends Geometry {
	/** A point on the plane */
	private final Point p0;
	/** a normal vector for the plane */
	private final Vector normal;

	/***
	 * Plane constructor based on a point and vector
	 * 
	 * @param p A point on the plane
	 * @param n normal vector for the plane n
	 */
	public Plane(Point p, Vector n) {
		this.p0 = p;
		this.normal = n.normalize();
	}

	/***
	 * Plane constructor based on a point on 3 points
	 * 
	 * @param p1 first point on the plane
	 * @param p2 second point on the plane
	 * @param p3 third point on the plane
	 * @throws IllegalArgumentException at the case of:
	 *                                  <ul>
	 *                                  <li>the 3 given points are co-lined</li>
	 *                                  </ul>
	 */
	public Plane(Point p1, Point p2, Point p3) {
		this.normal = (p2.subtract(p3)).crossProduct(p1.subtract(p3)).normalize();
		this.p0 = p1;
	}

	/**
	 * getter for normal
	 * 
	 * @return the field normal of plane
	 */
	public Vector getNormal() {
		return this.normal;
	}

	@Override
	public Vector getNormal(Point p) {
		return this.normal;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		double nv = this.normal.dotProduct(ray.getDir());
		// checks if head point is parallel or inside the plane/ ray's head point equals
		// to p0
		if (Util.isZero(nv) || this.p0.equals(ray.getHead()))
			return null;
		double t = this.normal.dotProduct(this.p0.subtract(ray.getHead())) / nv;
		return Util.alignZero(t) > 0 ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
	}

}
