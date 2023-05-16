package geometries;

import java.util.LinkedList;
import java.util.List;

import org.junit.platform.commons.util.ToStringBuilder;

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
	final public List<Point> findIntersections(Ray ray){
		List<GeoPoint> templisGeoPoints=findGeoIntersections(ray);
		if(templisGeoPoints!= null) {
			List<Point> resList = new LinkedList<>();
			for (GeoPoint geoPoint : templisGeoPoints) {
				resList.add(geoPoint.point);
			}
			return resList;
		}
		return null;
	}

	final public List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

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
			return obj instanceof GeoPoint other && this.point.equals(other.point)
					&& this.geometry.equals(other.geometry);
		}

		@Override
		public String toString() {
			return "geometry: " + this.geometry + " point: " + this.point;
		}
	}
}
