package primitives;

import static primitives.Util.isZero;

import java.lang.Math;

/***
 * A fundamental object in geometry, a point with 3 coordinates, contains an
 * object of three Numbers
 * 
 * @author Ayala
 *
 */
public class Point {
	final protected Double3 xyz;

	/***
	 * 
	 * @param coord1
	 * @param coord2
	 * @param coord3 first constructor - gets three coordinates and build point
	 */
	public Point(double coord1, double coord2, double coord3) {
		this.xyz = new Double3(coord1, coord2, coord3);
	}

	/***
	 * @param dPoint double3 second constructor - gets double3 point and build a
	 *               point
	 */
	Point(Double3 dPoint) {
		this.xyz = new Double3(dPoint.d1, dPoint.d2, dPoint.d3);

	}

	/***
	 * 
	 * @param p point
	 * @return a vector who is made by the subtraction of a given 2 points
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	/***
	 * 
	 * @param vec vector
	 * @return a point who is the addition of a given vector and a point
	 */
	public Point add(Vector vec) {
		return new Point(this.xyz.add(vec.xyz));
	}

	/***
	 * 
	 * @param p point the func gets a Point object
	 * @return the distance between them
	 */
	public double distance(Point p) {

		return Math.sqrt(this.distanceSquared(p));
	}

	/***
	 * 
	 * @param p point the function gets a Point object and
	 * @return the distance between them powered by 2
	 */
	public double distanceSquared(Point p) {
		double dx=this.xyz.d1 - p.xyz.d1;
		double dy=this.xyz.d2 - p.xyz.d2;
		double dz=this.xyz.d3 - p.xyz.d3;
		return dx*dx+dy*dy+dz*dz;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
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
