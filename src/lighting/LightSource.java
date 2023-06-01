package lighting;

import primitives.*;

/**
 * interface for all outside lights
 * @author Tamar and Ayala
 *
 */
public interface LightSource {
	/**
	 * calculate the intensity of light which comes from this lightSource to a
	 * specific point from
	 * 
	 * @param p point to find intensity at
	 * @return the intensity in a specific point
	 */
	public Color getIntensity(Point p);

	/**
	 * calculate the direction of light from this lightSource to a specific point
	 * 
	 * @param p point to find direction to
	 * @return the direction vector
	 */
	public Vector getL(Point p);
	
	/**
	 * calc the distance from lightSource to point
	 * @param p the point to calc distance from
	 * @return the distance from lightSource to point
	 */
	double getDistance(Point p);
}
