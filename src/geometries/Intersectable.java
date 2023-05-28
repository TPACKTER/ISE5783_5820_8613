package geometries;

import java.util.List;
import primitives.*;

/***
 * this abstarct class will serve all Intersection-able shapes
 * 
 * @author Ayala and Tamar
 * 
 */
public abstract class Intersectable {
	/***
	 * find all intersection with shape
	 * 
	 * @param ray ray to intersect
	 * @return list of intersection points
	 */
	final public List<Point> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}
	
	/**
	 * find all intersection with specific shape
	 * 
	 * @param ray ray to intersect
	 * @return list of intersection geoPoints
	 */
	final public List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
	}
	
	/**
	 * find all intersection with specific shape within a bounder distance
	 * 
	 * @param double distance the value of upper bounder
	 * @param ray ray to intersect
	 * @return list of intersection geoPoints within the given distance
	 */
	final public List<GeoPoint> findGeoIntersections(Ray ray, double distance) {
		return findGeoIntersectionsHelper(ray,distance);
	}

	/**
	 * find all intersection with specific shape within a bounder distance
	 * @param double distance the value of upper bounder
	 * @param ray ray to intersect
	 * @return list of intersection geoPoints within the given distance
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double distance);

	/***
	 * class which keeps a point on a specific geometry
	 * 
	 * @author Ayala and tamar
	 *
	 */
	public static class GeoPoint {
		/**
		 * the geometry the point is on
		 */
		public Geometry geometry;
		/***
		 * the point on the geometry
		 */
		public Point point;

		/***
		 * constructor for geoPoint based on geometry and point
		 * 
		 * @param geometry the current geometry
		 * @param point    the point on the geometry
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other && this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "geometry: " + this.geometry + " point: " + this.point;
		}
	}
}
