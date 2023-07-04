package renderer;

import java.util.HashMap;
import java.util.Map;
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
	 * @param p0       The head point to construct ray from
	 * @param traceRay the function to trace ray with
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
	 * @return the color of the beam of rays of the grid
	 */
	@Override 
	public Color superSampling(Point focal) {

		int num = this.nXY - 1;
		Point upLeft = points[0][0];
		Point upRight = points[0][num];
		Point downLeft = points[num][0];
		Point downRight = points[num][num];
		Map<Point, Color> pointsColors = new HashMap<>();
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
			return superSamplingRecursiveInverted(focal, upLeftJ, upLeftI, upMidJ, upMidI, leftMidJ, leftMidI, centerJ,
					centerI, num / 2,pointsColors)
					.add(superSamplingRecursiveInverted(focal, upMidJ, upMidI, upRightJ, upRightI, centerJ, centerI,
							rightMidJ, rightMidI, num / 2,pointsColors)
							.add(superSamplingRecursiveInverted(focal, leftMidJ, leftMidI, centerJ, centerI, downLeftJ,
									downLeftI, dounMidJ, dounMidI, num / 2,pointsColors)
									.add(superSamplingRecursiveInverted(focal, centerJ, centerI, rightMidJ, rightMidI,
											dounMidJ, dounMidI, downRighJ, downRighI, num / 2,pointsColors))))
					.reduce(4);

		}
		return pointsColors.get(upLeft).add(pointsColors.get(upRight)).add(pointsColors.get(downLeft))
				.add(pointsColors.get(downRight)).reduce(4);

	}
/**
 * calc the color for the grid
 * 
 * @param focal the focal point to castray for
 * @param upLeftJ the upLeft Y val of grid
 * @param upLeftI the upLeft X val of grid
 * @param upRightJ the upRight Y val of grid
 * @param upRightI the upRight X val of grid
 * @param downLeftJ the downLeft Y val of grid
 * @param downLeftI the downLeft X val of grid
 * @param downRightJ the downRight Y val of grid
 * @param downRightI the downRight X val of grid
 * @param num num of points on the grid 
 * @param pointsColors hashMap for saving point's color
 * @return the color of the grid
 */
	private Color superSamplingRecursiveInverted(Point focal, int upLeftJ, int upLeftI, int upRightJ, int upRightI,
			int downLeftJ, int downLeftI, int downRightJ, int downRightI, int num, Map<Point, Color> pointsColors) {

		Ray ray;
		if (!pointsColors.containsKey(points[upLeftJ][upLeftI])) {
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
			int upMidJ = upLeftJ;
			int upMidI = num1 + upLeftI;
			int leftMidJ = num1 + upLeftJ;
			int leftMidI = upLeftI;
			int centerJ = num1 + upLeftJ;
			int centerI = num1 + upLeftI;
					int rightMidJ = num1 + upLeftJ;
			int rightMidI = num + upLeftI;
			
			int dounMidJ = num + upLeftJ;
			int dounMidI = num1 + upLeftI;
			int downRighJ = num + upLeftJ;
			int downRighI = num + upLeftI;
			
			return superSamplingRecursiveInverted(focal, upLeftJ, upLeftI, upMidJ, upMidI, leftMidJ, leftMidI, centerJ,
					centerI, num / 2,pointsColors)
					.add(superSamplingRecursiveInverted(focal, upMidJ, upMidI, upRightJ, upRightI, centerJ, centerI,
							rightMidJ, rightMidI, num / 2,pointsColors)
							.add(superSamplingRecursiveInverted(focal, leftMidJ, leftMidI, centerJ, centerI, downLeftJ,
									downLeftI, dounMidJ, dounMidI, num / 2,pointsColors)
									.add(superSamplingRecursiveInverted(focal, centerJ, centerI, rightMidJ, rightMidI,
											dounMidJ, dounMidI, downRighJ, downRighI, num / 2, pointsColors))))
					.reduce(4);

		}
		return pointsColors.get(points[upLeftJ][upLeftI]).add(pointsColors.get(points[upRightJ][upRightI]))
				.add(pointsColors.get(points[downLeftJ][downLeftI]))
				.add(pointsColors.get(points[downRightJ][downRightI])).reduce(4);
	}

}
