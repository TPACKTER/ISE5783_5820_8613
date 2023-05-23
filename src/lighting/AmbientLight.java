package lighting;

import primitives.*;

/***
 * class representing ambient light
 * 
 * @author Ayala and Tamar
 *
 */
public class AmbientLight extends Light {
	
	/***
	 * no ambient light
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructor for ambient light based on color and Double3
	 * 
	 * @param ia base color
	 * @param ka (Double3)attenuation coefficient of Ia
	 */
	public AmbientLight(Color ia, Double3 ka) {
		super(ia.scale(ka));

	}

	/***
	 * Constructor for ambient light based on color and double
	 * 
	 * @param ia Ia base color
	 * @param ka (double) attenuation coefficient of Ia
	 */
	public AmbientLight(Color ia, double ka) {
		super(ia.scale(ka));
	}
}
