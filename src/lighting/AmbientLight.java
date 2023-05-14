package lighting;

import primitives.*;
/***
 * class representing ambient light 
 * @author Ayala and Tamar
 *
 */
public class AmbientLight {
	/***
	 * no ambient light
	 */
	public static AmbientLight NONE=new AmbientLight(Color.BLACK,Double3.ZERO);
	/***
	 * ambient's light intensity
	 */
	private Color intensity;
	
	/**
	 * Constructor for ambient light based on color and Double3 
	 * @param Ia base color
	 * @param ka (Double3)attenuation coefficient of Ia
	 */
	public AmbientLight(Color Ia,Double3 ka) {
		this.intensity=Ia.scale(ka);
	}
/***
 * Constructor for ambient light based on color and double
 * @param Ia Ia base color
 * @param ka (double) attenuation coefficient of Ia
 */
	public AmbientLight(Color Ia,double ka) {
		this.intensity=Ia.scale(ka);
	}
	
	/***
	 * Get intensity
	 * @return ambirnt's light intensity
	 */
	public Color getIntensity()
	{
		return this.intensity;
	}
}
