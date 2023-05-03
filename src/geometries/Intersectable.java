package geometries;

import java.util.List;

import primitives.*;

/***
 * this interface will serve all Intersection-able shapes
 * 
 * @author Ayala and Tamar
 * 
 */
public interface Intersectable {
	/***
	 * find all intersection with shape
	 * 
	 * @param ray
	 * @return list of intersection points
	 */
	List<Point> findIntersections(Ray ray);

}
