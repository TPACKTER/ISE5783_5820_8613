/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;


import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *  Testing Triangle
 * @author Ayala and Tamar
 */
class TriangleTest {

	/**
	 * Test method for
	 * {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testTriangle() {
		// =============== Boundary Values Tests ==================
		// TC10: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Triangle(new Point(0, 0, 1), new Point(0, 0, 3), new Point(0, 0, 20)),
				"Constructed a Triangle with Co-located points");
	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:testing getnormal(simple)
		Triangle tri = new Triangle(new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, 0, 0));
		boolean bool = (new Vector(0, 0, 1)).equals(tri.getNormal(null))
				|| (new Vector(0, 0, -1)).equals(tri.getNormal(null));
		assertTrue(bool, "ERROR: GetNormal()-triangle is wrong");
	}

	/***
	 * testing findIntersections
	 */
	@Test
	void testfindIntersections() {
		Triangle triangle = new Triangle(new Point(2, 0, 1), new Point(0, 0, 1), new Point(0, 2, 1));
		// ============ Equivalence Partitions Tests ==============
		// TC01:the intersections point is contained plane is inside the triangle(1
		// point)

		assertEquals(List.of(new Point(0.8, 0.4, 1)),
				triangle.findIntersections(new Ray(new Point(0.5, 0.5, 0.5), new Vector(1.5, -0.5, 2.5))),
				"TC01: Triangle findIntersections");
		assertEquals(List.of(new Point(1.056346414033179, 0.230281177984621, 1)), triangle.findIntersections(
				new Ray(new Point(3.209293107932101, 0, 0), new Vector(-2.152946693898922, 0.230281177984621, 1))),
				"TC01: Triangle findIntersections");
		// TC02:the intersections point is contained plane is outside the triangle
		// facing one of triangle's edge
		assertNull(triangle.findIntersections(new Ray(new Point(-0.5, -0.5, -0.5), new Vector(2.5, 0.5, 3.5))),
				"TC02: Triangle findIntersections");
		// TC03:the intersections point is contained plane is outside the triangle
		// facing one of triangle's vertex
		assertNull(triangle.findIntersections(new Ray(new Point(3, -0.5, 0.5), new Vector(1, 0, 3.5))),
				"TC03: Triangle findIntersections");
		// ============ Equivalence Partitions Tests ==============
		// TC11:The intersections point with contained plane is on one of the edges
		assertNull(triangle.findIntersections(new Ray(new Point(-0.5, -0.5, -0.5), new Vector(2.5, 2.5, 2.5))),
				"TC11: Triangle findIntersections");
		// TC12:The intersections point with contained plane is on one of the vertex
		assertNull(triangle.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 2))),
				"TC12: Triangle findIntersections");
		// TC13:The intersections point with contained plane is on on of the edges's
		// continuance
		assertNull(triangle.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-3, 0, 4))),
				"TC13: Triangle findIntersections");
	}
}
