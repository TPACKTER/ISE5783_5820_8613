package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Tamar and Ayala
 */
public class PolygonTests {

	/** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
		Polygon pol = new Polygon(pts);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pol.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 3; ++i)
			assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
					"Polygon's normal is not orthogonal to one of the edges");
	}

	/***
	 * testing findIntersections
	 */
	@Test
	void testfindIntersections() {
		Polygon polygon = new Polygon(new Point(2, 0, 1), new Point(0, 0, 1), new Point(-5, 2, 1), new Point(2, 2, 1));

		// ============ Equivalence Partitions Tests ==============
		// TC01:the intersections point is contained plane is inside the triangle(1
		// point)

		assertEquals(List.of(new Point(0.8, 0.4, 1)),
				polygon.findIntersections(new Ray(new Point(0.5, 0.5, 0.5), new Vector(1.5, -0.5, 2.5))),
				"TC01: polygon findIntersections");
		assertEquals(List.of(new Point(1.056346414033179, 0.230281177984621, 1)), polygon.findIntersections(
				new Ray(new Point(3.209293107932101, 0, 0), new Vector(-2.152946693898922, 0.230281177984621, 1))),
				"TC01: polygon findIntersections");
		// TC02:the intersections point is contained plane is outside the triangle
		// facing one of triangle's edge
		assertNull(polygon.findIntersections(new Ray(new Point(-0.5, -0.5, -0.5), new Vector(2.5, 0.5, 3.5))),
				"TC02: polygon findIntersections");
		// TC03:the intersections point is contained plane is outside the triangle
		// facing one of triangle's vertex
		assertNull(polygon.findIntersections(new Ray(new Point(3, -0.5, 0.5), new Vector(1, 0, 3.5))),
				"TC03: polygon findIntersections");
		// ============ Equivalence Partitions Tests ==============
		// TC11:The intersections point with contained plane is on one of the edges
		assertNull(polygon.findIntersections(new Ray(new Point(1, 0, -1), new Vector(0, 0, 2))),
				"TC11: polygon findIntersections");
		// TC12:The intersections point with contained plane is on one of the vertex
		assertNull(polygon.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 2))),
				"TC12: polygon findIntersections");
		// TC13:The intersections point with contained plane is on on of the edges's
		// continuance
		assertNull(polygon.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-8, 0, 10))),
				"TC13: polygon findIntersections");

	}
}
