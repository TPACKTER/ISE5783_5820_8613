/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Geometries
 * @author Ayala and Tamar
 *
 */
class GeometriesTest {

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Sphere sphere = new Sphere(new Point(1, 1, 1), 1);
		Triangle triangle = new Triangle(new Point(2, 0, 1), new Point(0, 0, 1), new Point(0, 2, 1));
		Plane plane = new Plane(new Point(1, 1, 1), new Point(0, 1, 1), new Point(0, 0, 2));
		Geometries g = new Geometries(sphere, triangle, plane);
		Geometries g1 = new Geometries();

		// ============ Equivalence Partitions Tests ==============

		// TC01: some shapes are intersects but not all;

		Ray ray = new Ray(new Point(5, 5, 5), new Vector(-12, -13, -14));
		assertEquals(3, g.findIntersections(ray).size());

		// =============== Boundary Values Tests ==================

		// ********** Group of intersections ***********

		// TC11: one shape intersect
		ray = new Ray(new Point(5, 5, 3), new Vector(-12, -13, -12));
		assertEquals(1, g.findIntersections(ray).size());

		// TC12: all shapes intersect
		ray = new Ray(new Point(2, 2, 2), new Vector(-3, -3.5, -3));
		assertEquals(4, g.findIntersections(ray).size());

		// ********** Group of no intersections ***********

		// TC13: no shapes
		ray = new Ray(new Point(2, 2, 2), new Vector(-3, -3.5, -3));
		assertNull(g1.findIntersections(ray));

		// TC14: there are shapes but no intersections

		ray = new Ray(new Point(5, 5, 5), new Vector(2, 3, 4));
		assertNull(g.findIntersections(ray));

	}

}
