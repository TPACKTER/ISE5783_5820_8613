package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.*;
import scene.*;
import primitives.*;

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
		return geoPoints != null ? calcColor(ray.findClosestGeoPoint(geoPoints), ray) : this.scene.background;
	}

	/***
	 * calculate the color of a given point
	 * 
	 * @param point
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
	}
	/***
	 * calculate the local effect of light on bory in specific point
	 * @param gp geo point on which the light hits
	 * @param ray of light
	 * @return the local effect of light on bory
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return color;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
			}
		}
		return color;
	}
/**
 * calc the Specular light effect on bory
 * @param material
 * @param n the normal to the bory
 * @param l the direction vector between the lightSource and the bory
 * @param nl the dotProduct of n*l
 * @param v the direction of the ray
 * @return the Specular light effect value
 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(l.subtract(n.scale(2*nl)))),material.nShininess));
	}
/**
 * calc the Diffusive light effect on bory
 * @param material to calc the diffusive effect on
 * @param nl the product of n*l
 * @return the Diffusive light effect value
 */
	private Double3 calcDiffusive(Material material, double nl) {
		return nl<0? material.kD.scale(-nl):material.kD.scale(nl);
	}
}
