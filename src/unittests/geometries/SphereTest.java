/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Sphere
 * 
 * @author Ayala and Tamar
 */
class SphereTest {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============
		// TC01:normal for Sphere(simple)
		Point p = new Point(1, 1, 6);
		Point p1 = new Point(1, 1, 1);
		Sphere s = new Sphere(p1, 5);

		assertEquals(new Vector(0, 0, 1), s.getNormal(p), "ERROR: GetNormal() of sphere not correct");

	}

	/**
	 * testing findIntersections
	 */
	@Test
	void testfindIntersections() {
		Sphere s = new Sphere(new Point(1, 1, 1), 1);
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray starts outside and has two intersection points

		Ray r = new Ray(new Point(0.5, 0, 0), new Vector(2.5, 3, 3));
		List<Point> result = s.findIntersections(r);
		assertEquals(2, result.size(), "Wrong number of points");

		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));

		assertEquals(
				List.of(new Point(0.761134993192959, 0.31336199183155, 0.31336199183155),
						new Point(1.733710367631784, 1.48045244115814, 1.48045244115814)),
				result, "TC01:Ray crosses sphere");

		// TC02: ray starts outside the sphere and has opposite direction of sphere (no
		// intersection)
		assertNull(s.findIntersections(new Ray(new Point(4, 4, 4), new Vector(2, 2, 2))), "TC02:Ray crosses sphere");

		// TC03: ray start inside the sphere and has one intersection
		assertEquals(List.of(new Point(1.810577754624734, 1.414103672832978, 1.414103672832978)),
				s.findIntersections(new Ray(new Point(1.5, 1, 1), new Vector(1.5, 2, 2))), "TC03:Ray crosses sphere");

		// TC04: ray starts outside the sphere and has no intersection with it
		assertNull(s.findIntersections(new Ray(new Point(4, 4, 4), new Vector(0, -1, -2))), "TC04:Ray crosses sphere");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		assertEquals(List.of(new Point(1.738916256157636, 0.891625615763547, 1.665024630541872)),
				s.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0.75, -0.11, -0.34))),
				"TC11:Ray crosses sphere");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(s.findIntersections(new Ray(new Point(1, 1, 2), new Vector(3, 3, 2))), "TC12:Ray crosses sphere");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)

		List<Point> result1 = s.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(5, 5, 5)));
		assertEquals(2, result1.size(), "Wrong number of points");
		if (result1.get(0).getX() > result1.get(1).getX())
			result1 = List.of(result1.get(1), result1.get(0));
		assertEquals(
				List.of(new Point(0.4226497308103745, 0.4226497308103745, 0.4226497308103745),
						new Point(1.5773502691896248, 1.5773502691896248, 1.5773502691896248)),
				result1, "TC13:Ray crosses sphere");

		// TC14: Ray starts at sphere and goes inside (1 points)
		assertEquals(List.of(new Point(1, 1, 0)),
				s.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 0, -1))), "TC14:Ray crosses sphere");

		// TC15: Ray starts inside (1 points)
		assertEquals(List.of(new Point(1.5773502691896257, 1.5773502691896257, 1.5773502691896257)),
				s.findIntersections(new Ray(new Point(1.5, 1.5, 1.5), new Vector(2.5, 2.5, 2.5))),
				"TC15:Ray crosses sphere");

		// TC16: Ray starts at the center (1 points)
		assertEquals(List.of(new Point(1.5773502691896257, 1.5773502691896257, 1.5773502691896257)),
				s.findIntersections(new Ray(new Point(1, 1, 1), new Vector(3, 3, 3))), "TC16:Ray crosses sphere");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(s.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 0, 1))), "TC17:Ray crosses sphere");

		// TC18: Ray starts after sphere (0 points)
		assertNull(s.findIntersections(new Ray(new Point(1, 1, 3), new Vector(0, 0, 1))), "TC18:Ray crosses sphere");

		// **** Group: Ray's line is tangent to the sphere
		// TC19: Ray starts before the tangent point (1 point)!
		assertNull(s.findIntersections(new Ray(new Point(1, 0, 2), new Vector(0, 2, 0))),
				"TC19:Ray crosses tangent point at sphere");

		// TC20: Ray starts at the tangent point (0 points)
		assertNull(s.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 2, 0))), "TC20:Ray crosses sphere");

		// TC21: Ray starts after the tangent point (0 points)
		assertNull(s.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, 2, 0))), "TC21:Ray crosses sphere");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(s.findIntersections(new Ray(new Point(1, 1, 3), new Vector(0, 1, 0))), "TC22:Ray crosses sphere");
	}

}
