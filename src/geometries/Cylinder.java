package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Cylinder class represent a 3d cylinder in 3D Cartesian coordinate system
 * 
 * @author Ayala and Tamar
 *
 */
public class Cylinder extends Tube {
	/** Cylinder height */
	final private double height;

	/**
	 * Cylinder constructor based on radius,ray, and height
	 * 
	 * @param radius Cylinder's radius
	 * @param ray    Cylinder's ray
	 * @param h      Cylinder's height
	 * 
	 * @throws IllegalArgumentException
	 *                                  <li>height is not positive</li>
	 */
	public Cylinder(double radius, Ray ray, double h) {
		super(radius, ray);
		this.height = h;
	}

	@Override
	public Vector getNormal(Point p) {
		Point p0 = this.axisRay.getHead();
		Vector dir = this.axisRay.getDir();
		Point pTop = p0.add(dir.scale(this.height));

		// if the point is at the top of the cylinder
		if (p.equals(pTop) || Util.isZero(dir.dotProduct(p.subtract(pTop))))
			return dir;

		// if the point is at the base of the cylinder
		if (p.equals(p0) || Util.isZero(dir.dotProduct(p.subtract(p0))))
			return dir.scale(-1);

		return super.getNormal(p);
	}

	@Override
	public String toString() {
		return "Cylinder{" + super.toString() + "height=" + height + '}';
	}

}
