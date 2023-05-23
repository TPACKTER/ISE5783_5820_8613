package lighting;

import primitives.*;

/**
 * class representing directional light
 * 
 * @author Tamar and Ayala
 *
 */
public class DirectionalLight extends Light implements LightSource {
	/*
	 * direction of light
	 */
	private final Vector direction;

	/*
	 * DirectionalLight constructor based on color and direction of the spotlight
	 * 
	 * @param color of light
	 * 
	 * @param direction of directionLight
	 */
	public DirectionalLight(Color color, Vector direction) {
		super(color);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return this.intensity;
	}

	@Override
	public Vector getL(Point p) {
		return this.direction;

	}
}
