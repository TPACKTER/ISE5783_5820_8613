package primitives;

import java.lang.Math;

/***
 * This class will serve all classes base on a point with 3 coordinates.
 * 
 * @author Ayala and Tamar
 */
public class Point {
	/***
	 * Double3 point. x-first number,y second number,z third number
	 */
	final protected Double3 xyz;

	/***
	 * Constructor to initialize Point base object-and its filed-Double3 object with
	 * its three number values
	 * 
	 * @param d1 first number value of xyz (Double3)
	 * @param d2 second number value value of xyz (Double3)
	 * @param d3 third number value value of xyz (Double3)
	 */
	public Point(double d1, double d2, double d3) {
		this.xyz = new Double3(d1, d2, d3);
	}

	/***
	 * Constructor to initialize Point based object-with double3 as input.
	 * 
	 * @param dPoint for xyz filed(Double3)
	 */
	Point(Double3 dPoint) {
		this.xyz = new Double3(dPoint.d1, dPoint.d2, dPoint.d3);

	}

	/**
	 * Subtraction between 2 points
	 * 
	 * @param p right handle side operand for Subtraction
	 * @return a new vector -result of Subtraction of a point from vector
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	/**
	 * adding a vector to point
	 * 
	 * @param vec right handle side operand for addition
	 * @return new vector-result of addition
	 */
	public Point add(Vector vec) {
		return new Point(this.xyz.add(vec.xyz));
	}

	/***
	 * Finds the distance between 2 points.
	 * 
	 * @param p second point-to find the distance for
	 * @return the distance
	 */
	public double distance(Point p) {

		return Math.sqrt(this.distanceSquared(p));
	}

	/***
	 * Finds the squared distance between 2 points.
	 * 
	 * @param p second point-to find the squared distance for
	 * @return result of Squared distance
	 */
	public double distanceSquared(Point p) {
		double dx = this.xyz.d1 - p.xyz.d1;
		double dy = this.xyz.d2 - p.xyz.d2;
		double dz = this.xyz.d3 - p.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
	}

	/***
	 * get x coordinate
	 * 
	 * @return  x coordinate
	 */
	public double getX() {
		return this.xyz.d1;
	}

	/***
	 * get y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return this.xyz.d2;
	}

	/***
	 * get z coordinate
	 * 
	 * @return z coordinate
	 */
	public double getZ() {
		return this.xyz.d3;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Point other && this.xyz.equals(other.xyz);
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "" + xyz;
	}

}
