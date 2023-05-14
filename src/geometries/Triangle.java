package geometries;

import java.util.List;

import primitives.*;

/**
 * Triangle class represent two-dimensional triangle in 3D Cartesian coordinate
 * system
 * 
 * @author Ayala and Tamar
 *
 */

public class Triangle extends Polygon {
	/**
	 * Triangle constructor based on 3 points-the points musn't be at the same line
	 * 
	 * @param p1 first vertex
	 * @param p2 second vertex
	 * @param p3 third vertex
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// based on the equation ray.head+t*d=(1-u-v)*v0+u*v1+v*v2

		// e1=v1-v0
		Vector e1 = this.vertices.get(1).subtract(this.vertices.get(0));
		// e2=v2-v0
		Vector e2 = this.vertices.get(2).subtract(this.vertices.get(0));
		// s=ray.head-v0
		Vector s = ray.getHead().subtract(vertices.get(0));
		// p=ray.dir-x-e2
		Vector p = ray.getDir().crossProduct(e2);
		// q=s-x-e1
		Vector q = s.crossProduct(e1);
		// t=q*e2/(ray.head*e1)
		double t = q.dotProduct(e2) / p.dotProduct(e1);
		if (Util.alignZero(t) <= 0)
			return null;
		// u=p*s/(ray.head*e1)
		double u = p.dotProduct(s) / p.dotProduct(e1);
		if (Util.alignZero(u) <= 0)
			return null;
		// u=q*d/(ray.head*e1)
		double v = q.dotProduct(ray.getDir()) / p.dotProduct(e1);
		if (Util.alignZero(v) <= 0)
			return null;
		if (Util.alignZero(u + v - 1) >= 0)
			return null;

		return List.of(vertices.get(0).add(e1.scale(u)).add(e2.scale(v)));
	}
}
