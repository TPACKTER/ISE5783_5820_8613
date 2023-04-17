package geometries;

import primitives.Point;
/**
 * calss wich represent a triangle
 * @author Ayala
 *
 */
public class Triangle extends Polygon{
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * gets three points and builds a triangle
	 */
public Triangle( Point p1,Point p2,Point p3) {
	super(p1,p2,p3);
}
}
