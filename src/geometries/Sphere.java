package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/***
 * Sphere representing sphere geometry has radius and a center point in 3D
 * Cartesian coordinate system
 * 
 * @author Tamar and Ayala
 */
public class Sphere extends RadialGeometry {
	/***
	 * center of the sphere
	 */
	final private Point center;

	/**
	 * Contractor for sphere base on center point and radius
	 * 
	 * @param p center point of the sphere
	 * @param r radius of the sphere
	 */
	public Sphere(Point p, double r) {
		super(r);
		this.center = p;
	}

	/**
	 * get the sphere's center
	 * 
	 * @return the sphere center
	 */
	Point getCenter() {
		return this.center;
	}

	/**
	 * get the sphere's radius
	 * 
	 * @return the radius of the sphere
	 */
	double getRadius() {
		return this.radius;
	}

	@Override
	public String toString() {
		return "center: " + this.center + " radius: " + this.radius;
	}

	/***
	 * calculates normal to sphere in a given point
	 * 
	 * @param p point on the sphere to return normal at
	 * @return normal to sphere at point p
	 */
	@Override
	public Vector getNormal(Point p) {
		return p.subtract(this.center).normalize();
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		if (center.equals(ray.getHead()))
			return List.of(new GeoPoint(this, ray.getPoint(this.radius)));

		Vector u = center.subtract(ray.getHead());
		double tm = ray.getDir().dotProduct(u);
		double dSquared = u.lengthSquared() - tm * tm;

		double thSquared = alignZero(radiusSquared - dSquared);
		// Check if the ray misses the sphere
		if (thSquared <= 0)
			return null;

		double th = Math.sqrt(thSquared);
		double t2 = Util.alignZero(tm + th);
		if (t2 <= 0)
			return null;

		double t1 = Util.alignZero(tm - th);
		return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2)))
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}
}
