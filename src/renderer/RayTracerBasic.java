package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/***
 * class for ray tracing
 * 
 * @author Ayala and Tamar
 *
 */
public class RayTracerBasic extends RayTracerBase {
	/***
	 * constructor for RayTracerBasic based on scene
	 * 
	 * @param scene to initialize the field scene
	 */

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		var geoPoints = this.scene.geometries.findGeoIntersections(ray);
		return geoPoints != null ? calcColor(ray.findClosestGeoPoint(geoPoints)) : this.scene.background;
	}

	/***
	 * calculate the color of a given point
	 * 
	 * @param point
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint geoPoint) {
		return this.scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
	}
}
