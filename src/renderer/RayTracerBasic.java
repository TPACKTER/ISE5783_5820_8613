package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.*;
import scene.*;
import primitives.*;
import static primitives.Util.*;

/*
 * class for ray tracing
 * 
 * @author Ayala and Tamar
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final double DELTA = 0.1;

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

	/*
	 * checks whether a point should be shaded or not
	 * 
	 * @param gp the geoPoint we check on
	 * 
	 * @param l the direction of light
	 * 
	 * @param n the normal vector to the shape
	 * 
	 * @return true if the point should'nt be shaded
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, double distance) {
		Vector lightDirection = l.scale(-1); // from point to light source

		Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = gp.point.add(epsVector);
		Ray lightRay = new Ray(point, lightDirection);

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,distance);
		return intersections == null || intersections.isEmpty();
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
	 * calculate the local effect of light on body in specific point
	 * 
	 * @param gp  geo point on which the light hits
	 * @param ray of light
	 * @return the local effect of light on body
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
			double distance = lightSource.getDistance(gp.point);
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				if (unshaded(gp, l, n, nl,distance)) {
					Color iL = lightSource.getIntensity(gp.point);
					color = color.add(iL.scale(calcDiffusive(material, nl)),
							iL.scale(calcSpecular(material, n, l, nl, v)));
				}
			}
		}
		return color;
	}

	/**
	 * calc the Specular light effect on bory
	 * 
	 * @param material
	 * @param n        the normal to the bory
	 * @param l        the direction vector between the lightSource and the bory
	 * @param nl       the dotProduct of n*l
	 * @param v        the direction of the ray
	 * @return the Specular light effect value
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		double vr = alignZero(v.dotProduct(l.subtract(n.scale(2 * nl))));
		return vr >= 0 ? Double3.ZERO : material.kS.scale(Math.pow(-vr, material.nShininess));
	}

	/**
	 * calc the Diffusive light effect on bory
	 * 
	 * @param material to calc the diffusive effect on
	 * @param nl       the product of n*l
	 * @return the Diffusive light effect value
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl < 0 ? -nl : nl);
	}
}
