
package lighting;

import primitives.*;

/**
 * class representing Light
 * 
 * @author Tamar and Ayala
 *
 */
abstract class Light {
	protected final Color intensity;

	/**
	 * Light constructor based on intensity of light
	 * 
	 * @param intensity of light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * getter for intensity field
	 * 
	 * @return intensity
	 */
	public Color getIntensity() {
		return this.intensity;
	}

}
