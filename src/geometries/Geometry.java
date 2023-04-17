package geometries;

import primitives.Point;
import primitives.Vector;
/***
 * an interface for general goemetries
 * @author Tamar
 *
 */
public interface Geometry {
	/**
	 * 
	 * @param p Point
	 * @return the normal to geometry at that specific point
	 */
public Vector getNormal(Point p);
}
