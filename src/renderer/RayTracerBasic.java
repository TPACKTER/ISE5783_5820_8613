package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.*;
import scene.*;
import primitives.*;
import static primitives.Util.*;

/**
 * class for ray tracing
 * 
 * @author Ayala and Tamar
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

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
		var geoPoint = this.findClosestIntersection(ray);
		return geoPoint == null ? this.scene.background : calcColor(geoPoint, ray);
	}

	/***
	 * calculate the local effect of light on body in specific point
	 * 
	 * @param gp  geo point on which the light hits
	 * @param ray of light
	 * @param k   accumulated recursive reflection\refraction factor
	 * @return the local effect of light on body
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return color;

		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				Double3 ktr = transparency(gp, lightSource, l, n);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(material, nl)),
							iL.scale(calcSpecular(material, n, l, nl, v)));
				}
			}
		}
		return color;
	}

	/**
	 * checks whether a point should be shaded or not
	 * 
	 * @param gp the geoPoint we check on
	 * @param l  the direction of light
	 * @param n  the normal vector to the shape
	 * @return true if the point should'nt be shaded
	 */
	@SuppressWarnings("unused")
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source

		Ray lightRay = new Ray(gp.point, lightDirection, n);
		double distance = lightSource.getDistance(lightRay.getHead());

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);
		if (intersections == null)
			return true;
		for (GeoPoint g : intersections)
			if (g.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;

		return true;
	}

	/**
	 * 
	 * @param geoPoint the geoPoint we calc
	 * @param ls       light source whom direction we calc
	 * @param l        the direction of light
	 * @param n        the normal vector to the shape
	 * @return the transparency value of the point
	 */
	private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1); // from point to light source

		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
		double distance = ls.getDistance(lightRay.getHead());

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);
		if (intersections == null)
			return Double3.ONE;

		Double3 ktr = Double3.ONE;
		for (GeoPoint g : intersections) {
			ktr = ktr.product(g.geometry.getMaterial().kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}
		return ktr;
	}

	/**
	 * calculating the closest intersection point to ray's head
	 * 
	 * @param ray ray to check point near by
	 * @return closest intersection point to ray's head
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * calculate the color of a given point recursive wrap method
	 * 
	 * @param geoPoint point to calc the color of
	 * @param ray      ray who hits the point
	 * @return the color of a given point
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K))
				.add(scene.ambientLight.getIntensity());
	}

	/**
	 * calculate the color of a given point recursive method
	 * 
	 * @param geoPoint point to calc the color of
	 * @param ray      ray who hits the point
	 * @param level    level of recurs
	 * @param k        attenuation coefficient of ray's influence
	 * @return the color of a given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * calculate the global effect of light on body in specific point
	 * 
	 * @param gp    geo point on which the light hits
	 * @param ray   of light
	 * @param level level of recurse
	 * @param k     attenuation coefficient of ray's influence
	 * @return the global effect of light on body
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcColorGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
				.add(calcColorGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	/**
	 * creates a Refraction ray
	 * 
	 * @param gp geoPoint of shape
	 * @param v  refracted ray direction
	 * @param n  normal to the shape
	 * @return the refracted ray
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
		return new Ray(gp.point, v, n);
	}

	/**
	 * creates a reflection ray
	 * 
	 * @param gp geoPoint of shape
	 * @param v  reflected ray direction
	 * @param n  normal to the shape
	 * @return the reflected ray
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
		return new Ray(gp.point, v.subtract(n.scale(2 * v.dotProduct(n))), n);
	}

	/**
	 * calc the color of global effect
	 * 
	 * @param ray   ray of
	 * @param level level of recurs
	 * @param k     attenuation coefficient of ray's influence
	 * @param kx    transperancy's coefficient
	 * @return color of global effect
	 */
	private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;

		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);

		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
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
	 * calc the Diffusive light effect on body
	 * 
	 * @param material to calc the diffusive effect on
	 * @param nl       the product of n*l
	 * @return the Diffusive light effect value
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl < 0 ? -nl : nl);
	}
}
