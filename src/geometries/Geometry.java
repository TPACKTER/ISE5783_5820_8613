package geometries;

import primitives.Point;
import primitives.Vector;


/***
 * Geometry interface for general 3d/2d geometries in 3D Cartesian coordinate
 * 
 * @author Tamar and Ayala
 *
 */
public interface Geometry extends Intersectable {
	/**
	 * Finds the normal of the geometry at the given point
	 * 
	 * @param p Point point on the geometry to find the normal at
	 * @return normal vector to geometry at p point
	 */
	public Vector getNormal(Point p);
}
