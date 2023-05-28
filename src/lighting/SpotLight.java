package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * class representing spot light
 * 
 * @author Tamar and Ayala
 *
 */
public class SpotLight extends PointLight {
	/*
	 * direction of light
	 */
	private final Vector direction;
	/**
	 * the power of the light narrowness
	 */
	private double narrowBeam = 1.0;

	/**
	 * SpotLight constructor based on color point and direction of the spotlight
	 * 
	 * @param color     of light
	 * @param point     of position of light
	 * @param direction of spotlight
	 */
	public SpotLight(Color color, Point point, Vector direction) {
		super(color, point);
		this.direction = direction.normalize();
	}

	/*
	 * setter for narrowBeam parameter
	 * 
	 * @param narrowBeam param to set
	 * 
	 * @return the updated this
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double dirL = alignZero(this.direction.dotProduct(this.getL(p)));
		return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dirL, this.narrowBeam));

	}
}
