package renderer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import primitives.*;

/**
 * class representing an Inverted grid
 */
public class InvertedGrid extends Grid {

	private Point[][] points;

	/**
	 * Constructs a InvertedGrid object with the specified parameters.
	 * 
	 * @param nXY      The number of Grid in each row/column of the grid.
	 * @param distance The distance of the grid from p0
	 * @param gs       the Grid size
	 * @param upVec    the up direction of the Grid
	 * @param toVec    direction of the Grid (to)
	 * @param p0       The head point to construcrt ray from
	 */
	public InvertedGrid(int nXY, double distance, double gs, Vector upVec, Vector toVec, Point p0,
			Function<Ray, Color> traceRay) {
		super(nXY, distance, gs, upVec, toVec, p0, traceRay);
		this.points = generateTargertAreaPoints();

	}

	/**
	 * Cast rays thought a grid
	 * 
	 * @param pointo to cast the rays through
	 * @return the averaged colors of the grid rays.
	 */
	public Color castGridRaysInverted(Point pointo) {

		Color color = Color.BLACK;

		for (int j = 0; j < this.nXY; j++)
			for (int i = 0; i < this.nXY; i++)
				color = color.add(traceRay.apply(new Ray(points[j][i], pointo.subtract(points[j][i]))));

		return color.reduce(points.length * points.length);
	}

	/**
	 * 
	 * @param focal    point to make rays through
	 * @param traceRay to trace the grids ray with
	 * @return the color of the beam of rays of the grid
	 */
	public Color superSamplingInverted(Point focal) {

		int num = this.nXY - 1;
		Point upLeft = points[0][0];
		Point upRight = points[0][num];
		Point downLeft = points[num][0];
		Point downRight = points[num][num];
		this.pointsColors = new HashMap<>();
		Ray ray;

		ray = new Ray(upLeft, focal.subtract(upLeft));
		pointsColors.put(upLeft, traceRay.apply(ray));

		ray = new Ray(upRight, focal.subtract(upRight));
		pointsColors.put(upRight, traceRay.apply(ray));

		ray = new Ray(downRight, focal.subtract(downRight));
		pointsColors.put(downRight, traceRay.apply(ray));

		ray = new Ray(downLeft, focal.subtract(downLeft));
		pointsColors.put(downLeft, traceRay.apply(ray));
		if (num > 1 && !(pointsColors.get(upLeft).equals(pointsColors.get(upRight))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downRight))
				&& pointsColors.get(upRight).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upRight).equals(pointsColors.get(downRight))
				&& pointsColors.get(downLeft).equals(pointsColors.get(downRight)))) {
			// ray = new Ray(location, center.subtract(location));
			// pointsColors.put(center, traceRay.apply(ray));
			int num1 = (this.nXY / 2);// -1;
			int upLeftJ = 0;
			int upLeftI = 0;
			int upMidJ = 0;
			int upMidI = num1;
			int leftMidJ = num1;
			int leftMidI = 0;
			int centerJ = num1;
			int centerI = num1;
			int upRightJ = 0;
			int upRightI = num;
			int rightMidJ = num1;
			int rightMidI = num;
			int downLeftJ = num;
			int downLeftI = 0;
			int dounMidJ = num;
			int dounMidI = num1;
			int downRighJ = num;
			int downRighI = num;
			// Point upMid = points[0][num1];
			// Point leftMid = points[num1][0];
			// Point rightMid =points[num][num1];
			// Point dounMid = points[num1][num];
			return superSamplingRecursiveInverted(focal, upLeftJ, upLeftJ, upMidJ, upMidI, leftMidJ, leftMidI, centerJ,
					centerI, num / 2)
					.add(superSamplingRecursiveInverted(focal, upMidJ, upMidI, upRightJ, upRightI, centerJ, centerI,
							rightMidJ, rightMidI, num / 2)
							.add(superSamplingRecursiveInverted(focal, leftMidJ, leftMidI, centerJ, centerI, downLeftJ,
									downLeftI, dounMidJ, dounMidI, num / 2)
									.add(superSamplingRecursiveInverted(focal, centerJ, centerI, rightMidJ, rightMidI,
											dounMidJ, dounMidI, downRighJ, downRighI, num / 2))))
					.reduce(4);

		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}

	private Color superSamplingRecursiveInverted(Point focal, int upLeftJ, int upLeftI, int upRightJ, int upRightI,
			int downLeftJ, int downLeftI, int downRightJ, int downRightI, int num) {

		Ray ray;
		if (!this.pointsColors.containsKey(points[upLeftJ][upLeftI])) {
			ray = new Ray(points[upLeftJ][upLeftI], focal.subtract(points[upLeftJ][upLeftI]));
			pointsColors.put(points[upLeftJ][upLeftI], traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(points[upRightJ][upRightI])) {
			ray = new Ray(points[upRightJ][upRightI], focal.subtract(points[upRightJ][upRightI]));
			pointsColors.put(points[upRightJ][upRightI], traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(points[downRightJ][downRightI])) {
			ray = new Ray(points[downRightJ][downRightI], focal.subtract(points[downRightJ][downRightI]));
			pointsColors.put(points[downRightJ][downRightI], traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(points[downLeftJ][downLeftI])) {
			ray = new Ray(points[downLeftJ][downLeftI], focal.subtract(points[downLeftJ][downLeftI]));
			pointsColors.put(points[downLeftJ][downLeftI], traceRay.apply(ray));
		}

		if (num > 0
				&& !(pointsColors.get(points[upLeftJ][upLeftI]).equals(pointsColors.get(points[upRightJ][upRightI])))
				&& pointsColors.get(points[upLeftJ][upLeftI]).equals(pointsColors.get(points[downLeftJ][downLeftI]))
				&& pointsColors.get(points[upLeftJ][upLeftI]).equals(pointsColors.get(points[downRightJ][downRightI]))
				&& pointsColors.get(points[upRightJ][upRightI]).equals(pointsColors.get(points[downLeftJ][downLeftI]))
				&& pointsColors.get(points[upRightJ][upRightI]).equals(pointsColors.get(points[downRightJ][downRightI]))
				&& pointsColors.get(points[downRightJ][downRightI])
						.equals(pointsColors.get(points[downLeftJ][downLeftI]))) {
			int num1 = (this.nXY / 2);// -1;
			// upLeftJ=0;
			// upLeftI=0;
			int upMidJ = upLeftJ;
			int upMidI = num1 + upLeftI;
			int leftMidJ = num1 + upLeftJ;
			int leftMidI = upLeftI;
			int centerJ = num1 + upLeftJ;
			int centerI = num1 + upLeftI;
			// upRightJ=upLeftJ;
			// upRightI=num+upLeftI;
			int rightMidJ = num1 + upLeftJ;
			int rightMidI = num + upLeftI;
			// downLeftJ=num;
			// downLeftI=0;
			int dounMidJ = num + upLeftJ;
			int dounMidI = num1 + upLeftI;
			int downRighJ = num + upLeftJ;
			int downRighI = num + upLeftI;
			// Point upMid = points[0][num1];
			// Point leftMid = points[num1][0];
			// Point rightMid =points[num][num1];
			// Point dounMid = points[num1][num];
			return superSamplingRecursiveInverted(focal, upLeftJ, upLeftJ, upMidJ, upMidI, leftMidJ, leftMidI, centerJ,
					centerI, num / 2)
					.add(superSamplingRecursiveInverted(focal, upMidJ, upMidI, upRightJ, upRightI, centerJ, centerI,
							rightMidJ, rightMidI, num / 2)
							.add(superSamplingRecursiveInverted(focal, leftMidJ, leftMidI, centerJ, centerI, downLeftJ,
									downLeftI, dounMidJ, dounMidI, num / 2)
									.add(superSamplingRecursiveInverted(focal, centerJ, centerI, rightMidJ, rightMidI,
											dounMidJ, dounMidI, downRighJ, downRighI, num / 2))))
					.reduce(4);

		}
		return pointsColors.get(points[upLeftJ][upLeftI]).add(pointsColors.get(points[upRightJ][upRightI]))
				.add(pointsColors.get(points[downLeftJ][downLeftI]))
				.add(pointsColors.get(points[downRightJ][downRightI])).reduce(4);
	}

	// old code
	public Color superSamplingRecursiveInverted(Point focal, Point upLeft, Point upRight, Point downLeft,
			Point downRight, double pixcelSize, Function<Ray, Color> traceRay, int num) {

		Ray ray;
		if (!this.pointsColors.containsKey(upLeft)) {
			ray = new Ray(upLeft, focal.subtract(upLeft));
			pointsColors.put(upLeft, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(upRight)) {
			ray = new Ray(upRight, focal.subtract(upRight));
			pointsColors.put(upRight, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(downRight)) {
			ray = new Ray(downRight, focal.subtract(downRight));
			pointsColors.put(downRight, traceRay.apply(ray));
		}

		if (!pointsColors.containsKey(downLeft)) {
			ray = new Ray(downLeft, focal.subtract(downLeft));
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
			return superSamplingRecursiveInverted(focal, upLeft, upMid, leftMid, center, space, traceRay, num / 4)
					.add(superSamplingRecursiveInverted(focal, upMid, upRight, center, rightMid, space, traceRay,
							num / 4))
					.add(superSamplingRecursiveInverted(focal, leftMid, center, downLeft, dounMid, space, traceRay,
							num / 4))
					.add(superSamplingRecursiveInverted(focal, center, rightMid, dounMid, downRight, space, traceRay,
							num / 4))
					.reduce(4);
		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}

	// old code
	/**
	 * rerurn the color of a grid invertedlly
	 * 
	 * @param focal
	 * @param center
	 * @param pixcelSize
	 * @param traceRay
	 * @param num
	 * @return
	 */
	public Color superSamplingInverted(Point focal, Point center, double pixcelSize, Function<Ray, Color> traceRay,
			int num) {

		double space = 0.5 * pixcelSize;
		Vector upv = this.upVec.scale(space);
		Vector rightv = this.rightVec.scale(space);
		Point upLeft = center.add(upv).subtract(rightv);
		Point upRight = center.add(upv).add(rightv);
		Point downLeft = center.subtract(upv).subtract(rightv);
		Point downRight = center.subtract(upv).add(rightv);
		this.pointsColors = new HashMap<>();
		Ray ray;

		ray = new Ray(upLeft, focal.subtract(upLeft));
		pointsColors.put(upLeft, traceRay.apply(ray));

		ray = new Ray(upRight, focal.subtract(upRight));
		pointsColors.put(upRight, traceRay.apply(ray));

		ray = new Ray(downRight, focal.subtract(downRight));
		pointsColors.put(downRight, traceRay.apply(ray));

		ray = new Ray(downLeft, focal.subtract(downLeft));
		pointsColors.put(downLeft, traceRay.apply(ray));
		if (num > 1 && !(pointsColors.get(upLeft).equals(pointsColors.get(upRight))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upLeft).equals(pointsColors.get(downRight))
				&& pointsColors.get(upRight).equals(pointsColors.get(downLeft))
				&& pointsColors.get(upRight).equals(pointsColors.get(downRight))
				&& pointsColors.get(downLeft).equals(pointsColors.get(downRight)))) {
			// ray = new Ray(location, center.subtract(location));
			// pointsColors.put(center, traceRay.apply(ray));
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			return superSamplingRecursiveInverted(focal, upLeft, upMid, leftMid, center, space, traceRay, num / 4).add(
					superSamplingRecursiveInverted(focal, upMid, upRight, center, rightMid, space, traceRay, num / 4)
							.add(superSamplingRecursiveInverted(focal, leftMid, center, downLeft, dounMid, space,
									traceRay, num / 4)
									.add(superSamplingRecursiveInverted(focal, center, rightMid, dounMid, downRight,
											space, traceRay, num / 4))))
					.reduce(4);

		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}

}
