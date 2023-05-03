package geometries;

import primitives.*;


import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
	public List<Point> findIntersections(Ray ray) {
		Vector u = null;
		double tm = 0;
		double dSquared = 0;
		if (!center.equals(ray.getHead())) {
			u = center.subtract(ray.getHead());
			tm = ray.getDir().dotProduct(u);
			dSquared = u.lengthSquared() - tm * tm;
		}
		// Check if the ray misses the sphere
		if (dSquared > radius * radius) {
			return null;
		}

		double th = Math.sqrt(radius * radius - dSquared);
		double t1 = Util.alignZero(tm + th);
		double t2 = Util.alignZero(tm - th);

		// Check if the intersections are behind the ray's head
		if (t1 <= 0 && t2 <= 0) {
			return null;
		}

		// Return the intersection point(s)
		if (t1 > 0 && t2 > 0 && !Util.isZero(th)) {
			Point p1 = ray.getPoint(t1);
			Point p2 = ray.getPoint(t2);
			return List.of(p1, p2);
		} else if (t1 > 0) {
			Point p1 = ray.getPoint(t1);
			return List.of(p1);
		} else {
			Point p2 = ray.getPoint(t2);
			return List.of(p2);
		}
	}


}
