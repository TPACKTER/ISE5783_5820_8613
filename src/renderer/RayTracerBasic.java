package renderer;

import java.util.List;

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
		List<Point> points = this.scene.geometries.findIntersections(ray);
		return points != null?calcColor(ray.findClosestPoint(points)):this.scene.background;
	
	}

	/***
	 * calculate the color of a given point
	 * 
	 * @param point
	 * @return the color of the point
	 */
	private Color calcColor(Point point) {
		return this.scene.ambientLight.getIntensity();
	}
}
