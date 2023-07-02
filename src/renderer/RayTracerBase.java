package renderer;

import java.util.List;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/***
 * abstract class for ray tracing
 * 
 * @author Ayala and Tamar
 *
 */
public abstract class RayTracerBase {
	/***
	 * the scene we want to trace ray whit
	 */
	protected final Scene scene;

	/***
	 * constructor for RayTracerBase based on scene
	 * 
	 * @param scene to initialize the field scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/***
	 * gets a ray and return the color at the hit point
	 * 
	 * @param ray to find the hit point
	 * @return the color at the hit point
	 */
	public abstract Color traceRay(Ray ray);
	/* public abstract Color traceRays(List<Ray> rays) ; */

}
