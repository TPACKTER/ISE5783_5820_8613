package primitives;

import java.lang.Math;

/***
 * A fundamental object in geometry, a point with 3 coordinates, contains an
 * object of three Numbers
 * 
 * @author Ayala
 *
 */
public class Point {
	Double3 point;

	public Point(double coord1, double coord2, double coord3) {
		this.point = new Double3(coord1, coord2, coord3);
	}

	Point(Double3 dPoint) {
		this.point = dPoint;
	}
	/***
	 * 
	 * @param p
	 * @return a vector who is made by the  subtraction of a given 2 points 
	 */
	 public Vector subtract(Point p) {
		 return new Vector(this.point.subtract(p.point));
	 }
/***
 * 
 * @param vec 
 * @return a point who is the  addition of a given vector and a point
 */
	public Point add(Vector vec) {
		return new Point(this.point.add(vec.point));
	}
	// צריך מקרי קצה?

	/***
	 * 
	 * @param p the func gets a Point object
	 * @return the distance between them
	 */
	public double distance(Point p) {

		return Math.sqrt(this.distanceSqurde(p));
	}

	/***
	 * 
	 * @param p the func gets a Point object and
	 * @return the distance between them powered by 2
	 */
	public double distanceSquared(Point p) {
		return (this.point.d1 - p.point.d1) * (this.point.d1 - p.point.d1)
				+ (this.point.d2 - p.point.d2) * (this.point.d2 - p.point.d2)
				+ (this.point.d3 - p.point.d3) * (this.point.d3 - p.point.d3);
	}
}
