package geometries;

import static primitives.Util.*;

/***
 * a general class will serve for all radial geometries
 * 
 * @author Tamar and Ayala
 *
 */
public abstract class RadialGeometry extends Geometry {
	/***
	 * the length which represents a radius
	 */
	protected final double radius;
	/***
	 * the squared radius
	 */
	protected final double radiusSquared;

	/**
	 * constructor for building a shape base radius
	 * 
	 * @param radius the length of the radius
	 * @throws IllegalArgumentExceptio at the case of non-positive radius
	 */
	public RadialGeometry(double radius) {
		if (alignZero(radius) <= 0)
			throw new IllegalArgumentException("illegal length");
		this.radius = radius;
		this.radiusSquared = radius * radius;
	}
}
