package lighting;

import primitives.*;

/**
 * class representing point light
 * 
 * @author Tamar and Ayala
 *
 */
public class PointLight extends Light implements LightSource {
	/*
	 * position point of light
	 */
	private final Point position;
	/*
	 * the square coefficient of the light
	 */
	private double kQ = 0;
	/*
	 * the linear coefficient of the light
	 */
	private double kL = 0;
	/*
	 * the constant coefficient of the light
	 */
	private double kC = 1;

	/**
	 * PointLight constructor based on color and point
	 * 
	 * @param color of light
	 * 
	 * @param point of the position of light
	 */
	public PointLight(Color color, Point point) {
		super(color);
		this.position = point;
	}

	/***
	 * setter for kQ
	 * 
	 * @param kQ param to set
	 * @return the updated this
	 */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}

	/***
	 * setter for kL
	 * 
	 * @param kL param to set
	 * @return the updated this
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/***
	 * setter for kC
	 * 
	 * @param kC param to set
	 * @return the updated this
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double distSquared = this.position.distanceSquared(p);
		return this.intensity.reduce((kC + kL * Math.sqrt(distSquared) + kQ * distSquared));
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(this.position).normalize();
	}

	@Override
	public double getDistance(Point p) {
		return this.position.distance(p);
	}

}
