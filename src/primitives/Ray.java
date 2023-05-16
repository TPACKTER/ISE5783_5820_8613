package primitives;

import static primitives.Util.*;

import java.util.List;

/***
 * This class will serve all geometries classes based on a ray - half a vector
 * represents two-dimensional ray n 3D Cartesian coordinate
 * 
 * @author Tamar and Ayala
 */

public class Ray {

	/** The head(start point) of the ray */
	final private Point head;

	/** the direction of the ray */
	final private Vector dir;

	/***
	 * Constructor to initialize Ray based object with its head point and direction
	 * vector values
	 * 
	 * @param point  the head point of the ray
	 * @param vector the direction of the ray.
	 */
	public Ray(Point point, Vector v) {
		this.head = point;
		this.dir = v.normalize();
	}

	/**
	 * gets list of points and return the closet point to ray's head
	 * 
	 * @param listpPoints list of points to find the closest from
	 * @return the closest point to ray's head in the list
	 */
	public Point findClosestPoint(List<Point> listpPoints) {
		if (listpPoints == null || listpPoints.isEmpty())
			return null;
		double minDistance = Double.POSITIVE_INFINITY;
		Point minPoint = null;
		for (Point point : listpPoints) {
			double dist = point.distance(this.head);
			if (dist < minDistance) {
				minPoint  = point;
				minDistance = dist;
			}
		}
		return minPoint ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Ray other && other.head.equals(this.head) && other.dir.equals(this.dir);
	}

	/**
	 * getter for head point
	 * 
	 * @return head of ray
	 */
	public Point getHead() {
		return this.head;
	}

	/**
	 * getter for direction vector
	 * 
	 * @return direction of ray
	 */
	public Vector getDir() {
		return this.dir;
	}

	/***
	 * calculates the hit point on ray in t distance from head point
	 * 
	 * @param t scalar to find the point
	 * @return point the hit point on ray in t distance from head point
	 */
	public Point getPoint(double t) {
		return isZero(t) ? this.head : this.head.add(this.dir.scale(t));
	}

	@Override
	public String toString() {
		return "head: " + this.head + " direction: " + this.dir;
	}
}
