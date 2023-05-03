/**
 * 
 */
package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Geometries class representing bunch of shapes
 * 
 * @author Ayala and Tamar
 */
public class Geometries implements Intersectable {
	/***
	 * List of intersectable geometries
	 */
	private List<Intersectable> geometries = new LinkedList<Intersectable>();

	/***
	 * default constructor of geometries
	 */
	public Geometries() {
	}

	/***
	 * constructor of geometries based on unlimited number of geometries
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/***
	 * adding unlimited number of geometries to the group
	 * 
	 * @param geometries - unlimited number of intersectable geometries to add to
	 *                   the group
	 */
	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> resList = null;
		for (Intersectable intersectable : geometries) {// for each geometry
			List<Point> tempList = intersectable.findIntersections(ray);
			if (tempList != null) { // if there are intersections continue to shape
				if (resList == null) // if there were no intersection with any shape yet
					resList = new LinkedList<>();
				resList.addAll(tempList);
			}
		}
		return resList;
	}
}
