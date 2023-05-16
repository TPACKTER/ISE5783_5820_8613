package geometries;

import primitives.*;
import primitives.Vector;

import java.util.*;

import geometries.Intersectable.GeoPoint;

/**
 * Tube class represents tree-dimensional tube in 3D Cartesian coordinate system
 * 
 * @author Ayala and Tamar
 *
 */
public class Tube extends RadialGeometry {
	/**
	 * tube's ray
	 */
	final protected Ray axisRay;

	/**
	 * Tube constructor based on radius and a ray.
	 * 
	 * @param r   tube's radius
	 * @param ray tube's ray
	 */
	public Tube(double r, Ray ray) {
		super(r);
		this.axisRay = ray;
	}

	@Override
	public Vector getNormal(Point p) {
		double t = this.axisRay.getDir().dotProduct(p.subtract(this.axisRay.getHead()));
		return p.subtract(this.axisRay.getPoint(t)).normalize();
	}

	/***
	 * get axis ray
	 * 
	 * @return the axis ray
	 */
	public Ray getAxisRay() {
		return this.axisRay;
	}
/*
	@Override

	public List<Point> findIntersections(Ray ray) {
		Point p0 = ray.getHead();
		Vector v = ray.getDir();
		Point pa = this.axisRay.getHead();
		Vector va = this.axisRay.getDir();
		double a, b, c; // coefficients for quadratic equation ax^2 + bx + c

		// a = (v-(v,va)va)^2
		// b = 2(v-(v,va)va,delP-(delP,va)va)
		// c = (delP-(delP,va)va)^2 - r^2
		// delP = p0-pa
		// (v,u) = v dot product u = vu = v*u
		// Step 1 - calculates a:
		Vector vecA = v;
		try {
			double vva = v.dotProduct(va); // (v,va)
			if (!Util.isZero(vva))
				vecA = v.subtract(va.scale(vva)); // v-(v,va)va
			a = vecA.lengthSquared(); // (v-(v,va)va)^2
		} catch (IllegalArgumentException e) {
			return null; // if a=0 there are no intersections because Ray is parallel to axisRay
		}
		// Step 2 - calculates deltaP (delP), b, c:
		try {
			Vector deltaP = p0.subtract(pa); // p0-pa
			Vector deltaPMinusDeltaPVaVa = deltaP;
			double deltaPVa = deltaP.dotProduct(va); // (delP,va)va)
			if (!Util.isZero(deltaPVa))
				deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); // (delP-(delP,va)va)
			b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); // 2(v-(v,va)va,delP-(delP,va)va)
			c = deltaPMinusDeltaPVaVa.lengthSquared() - radiusSquared; // (delP-(delP,va)va)^2 - r^2
		} catch (IllegalArgumentException e) {
			b = 0; // if delP = 0, or (delP-(delP,va)va = (0, 0, 0)
			c = -radiusSquared;
		}

		double descriminant = b * b - 4 * a * c;

		if (Util.alignZero(descriminant) <= 0)
			return null;
		double towa = 2 * a;

		double t2 = Util.alignZero((-b + Math.sqrt(descriminant)) / towa);
		if (t2 <= 0)
			return null;
		double t1 = Util.alignZero((-b - Math.sqrt(descriminant)) / towa);
		return t1 <= 0 ? List.of(ray.getPoint(t2)) : List.of(ray.getPoint(t1), ray.getPoint(t2));

	}
*/
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
			Point p0 = ray.getHead();
			Vector v = ray.getDir();
			Point pa = this.axisRay.getHead();
			Vector va = this.axisRay.getDir();
			double a, b, c; // coefficients for quadratic equation ax^2 + bx + c

			// a = (v-(v,va)va)^2
			// b = 2(v-(v,va)va,delP-(delP,va)va)
			// c = (delP-(delP,va)va)^2 - r^2
			// delP = p0-pa
			// (v,u) = v dot product u = vu = v*u
			// Step 1 - calculates a:
			Vector vecA = v;
			try {
				double vva = v.dotProduct(va); // (v,va)
				if (!Util.isZero(vva))
					vecA = v.subtract(va.scale(vva)); // v-(v,va)va
				a = vecA.lengthSquared(); // (v-(v,va)va)^2
			} catch (IllegalArgumentException e) {
				return null; // if a=0 there are no intersections because Ray is parallel to axisRay
			}
			// Step 2 - calculates deltaP (delP), b, c:
			try {
				Vector deltaP = p0.subtract(pa); // p0-pa
				Vector deltaPMinusDeltaPVaVa = deltaP;
				double deltaPVa = deltaP.dotProduct(va); // (delP,va)va)
				if (!Util.isZero(deltaPVa))
					deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); // (delP-(delP,va)va)
				b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); // 2(v-(v,va)va,delP-(delP,va)va)
				c = deltaPMinusDeltaPVaVa.lengthSquared() - radiusSquared; // (delP-(delP,va)va)^2 - r^2
			} catch (IllegalArgumentException e) {
				b = 0; // if delP = 0, or (delP-(delP,va)va = (0, 0, 0)
				c = -radiusSquared;
			}

			double descriminant = b * b - 4 * a * c;

			if (Util.alignZero(descriminant) <= 0)
				return null;
			double towa = 2 * a;

			double t2 = Util.alignZero((-b + Math.sqrt(descriminant)) / towa);
			if (t2 <= 0)
				return null;
			double t1 = Util.alignZero((-b - Math.sqrt(descriminant)) / towa);
			return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
		}
}

