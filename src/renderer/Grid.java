package renderer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

/**
 * class for representing grid
 */
public class Grid {
	/** The resolution of the Blackboard */
	protected final int nXY;

	/** The vTo Vector of the Grid */
	protected final Vector toVec;

	/** The vUp Vector of the Grid */
	protected final Vector upVec;
	/** The vRight Vector of the Grid */
	protected final Vector rightVec;
	/** The distance to the Grid */
	protected final double distance;

	/** The length and height size of the Grid **/
	protected final double gridSize;
	/** The head point to construct ray from **/
	protected final Point p0;


	/**
	 * Faction to trace the rays with
	 */
	protected final Function<Ray, Color> traceRay;

	/**
	 * Constructs a Grid object with the specified parameters.
	 * 
	 * @param nXY      The number of Grid in each row/column of the grid.
	 * @param distance The distance of the grid from p0
	 * @param gs       the Grid size
	 * @param upVec    the up direction of the Grid
	 * @param toVec    direction of the Grid (to)
	 * @param p0       The head point to construcrt ray from
	 * @param traceRay - trace the ray with
	 */
	public Grid(int nXY, double distance, double gs, Vector upVec, Vector toVec, Point p0,
			Function<Ray, Color> traceRay) {
		this.nXY = nXY;
		this.toVec = toVec.normalize();
		this.upVec = upVec.normalize();
		this.rightVec = toVec.crossProduct(upVec).normalize();
		this.distance = distance;
		this.gridSize = gs;
		this.p0 = p0;
		this.traceRay = traceRay;

	}

	/**
	 * Cast rays thought a grid
	 * 
	 * @return the averaged colors of the grid rays.
	 */
	public Color castGridRays() {

		Point[][] p = generateTargertAreaPoints();
		Color color = Color.BLACK;
		for (int j = 0; j < this.nXY; j++)
			for (int i = 0; i < this.nXY; i++)
				color = color.add(traceRay.apply(new Ray(p0, p[j][i].subtract(p0))));

		return color.reduce(p.length * p.length);
	}

	/**
	 * gives a random number between 2 given numbers
	 * 
	 * @param min minimum number
	 * @param max maximum number
	 * @return random number between min and max
	 */
	public static double getRandom(double min, double max) {
		min = min + Math.random() * (max - min);
		return min == 0 ? max : min;
	}

	protected Point[][] generateTargertAreaPoints() {
		double space = this.gridSize / this.nXY;
		Point p = p0
				.add(distance != 0
						? toVec.scale(distance).add(upVec.scale((this.gridSize - space) / 2))
								.add(rightVec.scale((-this.gridSize + space) / 2))
						: upVec.scale((this.gridSize - space) / 2))
				.add(rightVec.scale((-this.gridSize + space) / 2));
		Point TargerAreaPoint[][] = new Point[this.nXY][this.nXY];
		for (int j = 0; j < this.nXY; j++) {
			for (int i = 0; i < this.nXY; i++) {
				p = p.add(rightVec.scale(space));

				TargerAreaPoint[j][i] = p;
			}
			p = p.add(rightVec.scale(-space * (this.nXY)).add(upVec.scale(-space)));
		}
		
	/**
	 * cast rays thought the grid adaptively
	 * 
	 * @param center   center point of the grid
	 * @return the color of the beam of rays of the grid
	 */
	public Color superSampling(Point center) {
		int num = this.nXY * this.nXY;
		Map<Point, Color> pointsColors = new HashMap<>();
		double space = 0.5 * gridSize;
		Vector upv = this.upVec.scale(space);
		Vector rightv = this.rightVec.scale(space);
		Point upLeft = center.add(upv).subtract(rightv);
		Point upRight = center.add(upv).add(rightv);
		Point downLeft = center.subtract(upv).subtract(rightv);
		Point downRight = center.subtract(upv).add(rightv);
		Ray ray;

		ray = new Ray(p0, upLeft.subtract(p0));
		pointsColors.put(upLeft, traceRay.apply(ray));

		ray = new Ray(p0, upRight.subtract(p0));
		pointsColors.put(upRight, traceRay.apply(ray));

		ray = new Ray(p0, downRight.subtract(p0));
		pointsColors.put(downRight, traceRay.apply(ray));

		ray = new Ray(p0, downLeft.subtract(p0));
		pointsColors.put(downLeft, traceRay.apply(ray));

		if (num > 1 && !(pointsColors.get(upLeft).equals(pointsColors.get(upRight))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downRight))
				&& pointsColors.get(upRight).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upRight).equals(pointsColors.get(downRight))
				&& pointsColors.get(downLeft).equals(pointsColors.get(downRight)))) {
			
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			return superSamplingRecursive(upLeft, upMid, leftMid, center, space, num / 4,pointsColors )
					.add(superSamplingRecursive(upMid, upRight, center, rightMid, space, num / 4,pointsColors )
							.add(superSamplingRecursive(leftMid, center, downLeft, dounMid, space, num / 4,pointsColors ))
							.add(superSamplingRecursive(center, rightMid, dounMid, downRight, space, num / 4,pointsColors )))
					.reduce(4);

		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}
/**
 * 
 * @param upLeft
 * @param upRight
 * @param downLeft
 * @param downRight
 * @param pixcelSize
 * @param num
 * @param pointsColors
 * @return
 */
	private Color superSamplingRecursive(Point upLeft, Point upRight, Point downLeft, Point downRight,
			double pixcelSize, int num, Map<Point, Color> pointsColors ) {

		Ray ray;
		if (!pointsColors.containsKey(upLeft)) {
			ray = new Ray(p0, upLeft.subtract(p0));
			pointsColors.put(upLeft, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(upRight)) {
			ray = new Ray(p0, upRight.subtract(p0));
			pointsColors.put(upRight, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(downRight)) {
			ray = new Ray(p0, downRight.subtract(p0));
			pointsColors.put(downRight, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(downLeft)) {
			ray = new Ray(p0, downLeft.subtract(p0));
			pointsColors.put(downLeft, traceRay.apply(ray));
		}

		if (num > 0 && !(pointsColors.get(upLeft).equals(pointsColors.get(upRight))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downRight))
				&& pointsColors.get(upRight).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upRight).equals(pointsColors.get(downRight))
				&& pointsColors.get(downLeft).equals(pointsColors.get(downRight)))) {

			double space = pixcelSize / 2;
			Vector upv = this.upVec.scale(space);
			Vector rightv = this.rightVec.scale(space);
			Point center = upLeft.add(rightv).subtract(upv);
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			return superSamplingRecursive(upLeft, upMid, leftMid, center, space, num / 4,pointsColors)
					.add(superSamplingRecursive(upMid, upRight, center, rightMid, space, num / 4,pointsColors))
					.add(superSamplingRecursive(leftMid, center, downLeft, dounMid, space, num / 4,pointsColors))
					.add(superSamplingRecursive(center, rightMid, dounMid, downRight, space, num / 4,pointsColors)).reduce(4);

		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}

}
