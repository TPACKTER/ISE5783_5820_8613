package lighting;

import primitives.*;

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
	private Vector direction;

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

	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity(p).scale(Math.max(0, this.direction.dotProduct(this.getL(p))));
	}
}
