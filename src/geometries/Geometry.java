package geometries;

import primitives.*;
import primitives.Point;
import primitives.Vector;

/***
 * Geometry interface for general 3d/2d geometries in 3D Cartesian coordinate
 * 
 * @author Tamar and Ayala
 *
 */
public abstract class Geometry extends Intersectable {
	/***
	 * the color of the bory
	 */
	protected Color emission = Color.BLACK;

	/**
	 * Finds the normal of the geometry at the given point
	 * 
	 * @param p Point point on the geometry to find the normal at
	 * @return normal vector to geometry at p point
	 */
	public abstract Vector getNormal(Point p);

	/**
	 * getter for the emission filed
	 * 
	 * @return emission
	 */
	public Color getEmission() {
		return this.emission;
	}

	/**
	 * setter for the emission filed
	 */
	public void setEmission(Color color) {
		this.emission = color;
	}
}
