package renderer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

	private final int nXY;

	private final Vector toVec;

	private final Vector upVec;

	private final Vector rightVec;

	private final double distance;

	private final double gridSize;

	private final Point p0;

	private Map<Ray, Color> pointsColors;

	/**
	 * Constructs a Blackboard object with the specified parameters.
	 *
	 * @param nXY      The number of pixels in each row/column of the grid.
	 * @param gs     The size of the grid.
	 * 
	 * @param distance The distance between the blackboard and the main ray's
	 *                 origin.
	 * 
	 */
	public Grid(int nXY, double distance, double gs, Vector upVec, Vector toVec, Point p0) {
		this.nXY = nXY;
		this.toVec = toVec.normalize();
		this.upVec = upVec.normalize();
		this.rightVec = toVec.crossProduct(upVec).normalize();
		this.distance = distance;
		this.gridSize = gs;
		this.p0 = p0;

	}

	/**
	 * Generates jittered grid rays for a given scene.
	 *
	 * @return A list of Ray objects representing the jittered grid rays.
	 */
	public List<Ray> gridRays(int direction, Point pointo) {
		List<Ray> rays = new LinkedList<>();

		Point[] p = generateTargertAreaPoints(p0, this.nXY, this.gridSize, 0.1, this.upVec, this.toVec, this.rightVec,
				this.distance);

		if (direction == 2)
			for (Point point : p)
				rays.add(new Ray(point, pointo.subtract(point)));

		if (direction == -1 | direction == 1)
			for (Point point : p) {
				Vector tempRayVector = point.subtract(p0);
				rays.add(new Ray(p0, tempRayVector));}
			
		if (direction == 0)
			for (Point point : p)
				rays.add(new Ray(p0, point.subtract(p0)));

		return rays;
	}

	/**
	 * gives a random number between 2 given numbers
	 * 
	 * @param min minimum number
	 * @param max maximum number
	 * @return random number between min and max
	 */
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

	/**
	 * 
	 * generate the Target Area Points
	 * 
	 * @param numOfPoints to set on the target area
	 * @param targetSize  the size of the target area
	 * @param jitter      the level of jitter to get the point at
	 * @param p           to build the target area from
	 * @param upvec       the up direction
	 * @param tovec       the to direction
	 * @return an array of the target area points.
	 * 
	 */
	public static Point[] generateTargertAreaPoints(Point location, int numOfPoints, double targetSize, double jitter,
			Vector upvec, Vector tovec, Vector rightvec, double distance) {

		int numOfPointsOnLine = numOfPoints;
		double space = targetSize / numOfPointsOnLine;

		Point p = location.add(upvec.scale(targetSize - space / 2)).add(rightvec.scale(-targetSize + space / 2))
				.add(distance == 0 ? tovec : tovec.scale(distance));
		List<Point> TargerAreaPointList = new LinkedList<>();

		for (int j = 0; j < numOfPointsOnLine; j++) {
			Vector vec;
			for (int i = 1; i < numOfPointsOnLine; i++) {
				p = p.add(rightvec.scale(space));
				 vec=rightvec.scale(getRandom(-jitter, jitter));
				TargerAreaPointList.add(p.add(vec));// .add(vec));
			}
			p = p.add(rightvec.scale(-space * (numOfPointsOnLine - 1)).add(upvec.scale(-space)));
			vec=upvec.scale(getRandom(-jitter,
			jitter)).add((rightvec).scale(getRandom(-jitter, jitter)));
			TargerAreaPointList.add(p.add(vec));// .add(vec));

		}

		return TargerAreaPointList.toArray(new Point[TargerAreaPointList.size()]);
	}


	public Color superSamplingRecursive(Point location, Point upLeft, Point upRight, Point downLeft, Point downRight,
			double pixcelSize, Function<Ray, Color> traceRay, int num) {
		Color color;

		Ray ray1 = new Ray(location, upLeft.subtract(location));
		if (!pointsColors.containsKey(ray1))
			pointsColors.put(ray1, traceRay.apply(ray1));

		Ray ray2 = new Ray(location, upRight.subtract(location));
		if (!pointsColors.containsKey(ray2)) {

			pointsColors.put(ray2, traceRay.apply(ray2));
		}

		Ray ray3 = new Ray(location, upRight.subtract(location));
		if (!pointsColors.containsKey(ray3)) {

			pointsColors.put(ray3, traceRay.apply(ray3));
		}

		Ray ray4 = new Ray(location, downRight.subtract(location));
		if (!pointsColors.containsKey(ray4)) {
			pointsColors.put(ray4, traceRay.apply(ray4));
		}

		if (num > 0 && !(pointsColors.get(ray1).equals(pointsColors.get(ray2))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray3).equals(pointsColors.get(ray4)))) {
			double space = pixcelSize / 2;
			Vector upv = this.upVec.scale(space);
			Vector rightv = this.rightVec.scale(space);
			Point center = upLeft.add(rightv).subtract(upv);
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			color = superSamplingRecursive(location, upLeft, upMid, leftMid, center, space, traceRay, num / 4)
					.add(superSamplingRecursive(location, upMid, upRight, center, rightMid, space, traceRay, num / 4))
					.add(superSamplingRecursive(location, leftMid, center, downLeft, dounMid, space, traceRay, num / 4))
					.add(superSamplingRecursive(location, center, rightMid, dounMid, downRight, space, traceRay,
							num / 4));
			color = color.reduce(4);
		} else {
			Color color1 = pointsColors.get(ray1);
			Color color2 = pointsColors.get(ray2);
			Color color3 = pointsColors.get(ray3);
			Color color4 = pointsColors.get(ray4);
			color = color1.add(color2).add(color3).add(color4).reduce(4);
		}
		return color;

	}

	public Color superSampling(Point location, Point center, double pixcelSize, Function<Ray, Color> traceRay,
			int num) {
		Color color;
		double space = 0.5 * pixcelSize;
		Vector upv = this.upVec.scale(space);
		Vector rightv = this.rightVec.scale(space);
		Point upLeft = center.add(upv).subtract(rightv);
		Point upRight = center.add(upv).add(rightv);
		Point downLeft = center.subtract(upv).subtract(rightv);
		Point downRight = center.subtract(upv).add(rightv);
		this.pointsColors = new HashMap<>();

		Ray ray0 = new Ray(location, center.subtract(location));
		if (!pointsColors.containsKey(ray0)) {

			pointsColors.put(ray0, traceRay.apply(ray0));
		}
		Ray ray1 = new Ray(location, upLeft.subtract(location));
		if (!pointsColors.containsKey(ray1))
			pointsColors.put(ray1, traceRay.apply(ray1));

		Ray ray2 = new Ray(location, upRight.subtract(location));
		if (!pointsColors.containsKey(ray2)) {

			pointsColors.put(ray2, traceRay.apply(ray2));
		}

		Ray ray3 = new Ray(location, upRight.subtract(location));
		if (!pointsColors.containsKey(ray3)) {

			pointsColors.put(ray3, traceRay.apply(ray3));
		}

		Ray ray4 = new Ray(location, downRight.subtract(location));
		if (!pointsColors.containsKey(ray4)) {
			pointsColors.put(ray4, traceRay.apply(ray4));
		}
		if (num > 1 && !(pointsColors.get(ray1).equals(pointsColors.get(ray2))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray3).equals(pointsColors.get(ray4)))) {
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			color = superSamplingRecursive(location, upLeft, upMid, leftMid, center, space, traceRay, (num / 2) - 1)
					.add(superSamplingRecursive(location, upMid, upRight, center, rightMid, space, traceRay,
							(num - 1) / 2)
							.add(superSamplingRecursive(location, leftMid, center, downLeft, dounMid, space, traceRay,
									(num - 1) / 2)
									.add(superSamplingRecursive(location, center, rightMid, dounMid, downRight, space,
											traceRay, (num - 1) / 2))));
			color = color.reduce(4);
		} else {
			Color color1 = pointsColors.get(ray1);
			Color color2 = pointsColors.get(ray2);
			Color color3 = pointsColors.get(ray3);
			Color color4 = pointsColors.get(ray4);
			Color color5 = pointsColors.get(ray0);
			color = color1.add(color2).add(color3).add(color4).add(color5).reduce(5);
		}
		return color;

	}

	public Color superSamplingRecursiveForAppture(Point focal, Point upLeft, Point upRight, Point downLeft,
			Point downRight, double pixcelSize, Function<Ray, Color> traceRay, int num) {
		Color color;

		Ray ray1 = new Ray(upLeft, focal.subtract(upLeft));
		if (!pointsColors.containsKey(ray1))
			pointsColors.put(ray1, traceRay.apply(ray1));

		Ray ray2 = new Ray(upRight, focal.subtract(upRight));
		if (!pointsColors.containsKey(ray2)) {

			pointsColors.put(ray2, traceRay.apply(ray2));
		}

		Ray ray3 = new Ray(downLeft, focal.subtract(downLeft));
		if (!pointsColors.containsKey(ray3)) {

			pointsColors.put(ray3, traceRay.apply(ray3));
		}

		Ray ray4 = new Ray(downRight, focal.subtract(downRight));
		if (!pointsColors.containsKey(ray4)) {
			pointsColors.put(ray4, traceRay.apply(ray4));
		}

		if (num > 1 && !(pointsColors.get(ray1).equals(pointsColors.get(ray2))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray3).equals(pointsColors.get(ray4)))) {
			double space = pixcelSize / 2;
			Vector upv = this.upVec.scale(space);
			Vector rightv = this.rightVec.scale(space);
			Point center = upLeft.add(rightv).subtract(upv);
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			color = superSamplingRecursiveForAppture(focal, upLeft, upMid, leftMid, center, space, traceRay, num / 4)
					.add(superSamplingRecursiveForAppture(focal, upMid, upRight, center, rightMid, space, traceRay,
							num / 4))
					.add(superSamplingRecursiveForAppture(focal, leftMid, center, downLeft, dounMid, space, traceRay,
							num / 4))
					.add(superSamplingRecursiveForAppture(focal, center, rightMid, dounMid, downRight, space, traceRay,
							num / 4));
			color = color.reduce(4);
		} else {
			Color color1 = pointsColors.get(ray1);
			Color color2 = pointsColors.get(ray2);
			Color color3 = pointsColors.get(ray3);
			Color color4 = pointsColors.get(ray4);
			color = color1.add(color2).add(color3).add(color4).reduce(4);
		}
		return color;

	}

	public Object[] superSamplingForAppture(Point focal, Point center, double pixcelSize, Function<Ray, Color> traceRay,
			int num) {
		Color color;
		double space = 0.5 * pixcelSize;
		Vector upv = this.upVec.scale(space);
		Vector rightv = this.rightVec.scale(space);
		Point upLeft = center.add(upv).subtract(rightv);
		Point upRight = center.add(upv).add(rightv);
		Point downLeft = center.subtract(upv).subtract(rightv);
		Point downRight = center.subtract(upv).add(rightv);
		this.pointsColors = new HashMap<>();

		Ray ray0 = new Ray(center, focal.subtract(center));
		if (!pointsColors.containsKey(ray0)) {

			pointsColors.put(ray0, traceRay.apply(ray0));
		}
		Ray ray1 = new Ray(upLeft, focal.subtract(upLeft));
		if (!pointsColors.containsKey(ray1)) {

			pointsColors.put(ray1, traceRay.apply(ray1));
		}
		Ray ray2 = new Ray(upRight, focal.subtract(upRight));
		if (!pointsColors.containsKey(ray2)) {
			pointsColors.put(ray2, traceRay.apply(ray2));
		}
		Ray ray3 = new Ray(downLeft, focal.subtract(downLeft));
		if (!pointsColors.containsKey(ray3)) {

			pointsColors.put(ray3, traceRay.apply(ray3));
		}
		Ray ray4 = new Ray(downRight, focal.subtract(downRight));
		if (!pointsColors.containsKey(ray4)) {

			pointsColors.put(ray4, traceRay.apply(ray4));
		}
		if (num > 4 && !(pointsColors.get(ray1).equals(pointsColors.get(ray2))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray1).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray3))
				&& pointsColors.get(ray2).equals(pointsColors.get(ray4))
				&& pointsColors.get(ray3).equals(pointsColors.get(ray4)))) {
			Point upMid = center.add(upv);
			Point leftMid = center.subtract(rightv);
			Point rightMid = center.add(rightv);
			Point dounMid = center.subtract(upv);
			color = superSamplingRecursiveForAppture(focal, upLeft, upMid, leftMid, center, space, traceRay,
					(num - 1) / 4)
					.add(superSamplingRecursiveForAppture(focal, upMid, upRight, center, rightMid, space, traceRay,
							(num - 1) / 4)
							.add(superSamplingRecursiveForAppture(focal, leftMid, center, downLeft, dounMid, space,
									traceRay, (num - 1) / 4)
									.add(superSamplingRecursiveForAppture(focal, center, rightMid, dounMid, downRight,
											space, traceRay, (num - 1) / 4))));
			color = color.reduce(4);
		} else {
			Color color1 = pointsColors.get(ray1);
			Color color2 = pointsColors.get(ray2);
			Color color3 = pointsColors.get(ray3);
			Color color4 = pointsColors.get(ray4);
			Color color5 = pointsColors.get(ray0);
			color = color1.add(color2).add(color3).add(color4).add(color5).reduce(5);
		}
		Object[] tuple = new Object[2];
		tuple[0] = color;
		tuple[1] = this.pointsColors;
		return tuple;

	}

}
