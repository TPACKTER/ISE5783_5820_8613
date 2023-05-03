/**
 * 
 */
package unittests.geometries;

import geometries.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * @author Ayala and Tamar Testing plane
 */
class PlaneTest {
	/**
	 * Test contractor
	 */
	@Test
	void testConstructor() {
		// =============== Boundary Values Tests ==================
		// TC01: ctor 2 point the same
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(0, 0, 0)),
				"ERROR:Constructor() didnt throw an excaption");

		// TC02: all points on the same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)),
				"ERROR: Constructor() didnt throw an excaption");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:testing get normal for plane(simple)
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 0, 0), new Point(0, 1, 0));
		boolean bool = new Vector(0, 0, 1).equals(plane.getNormal(new Point(0, 0, 0)))
				|| new Vector(0, 0, -1).equals(plane.getNormal(new Point(0, 0, 0)));
		assertTrue(bool, "ERROR: GetNormalPoint() worng result");
	}

	/**
	 * testing findIntersections
	 */
	@Test
	void testfindIntersections() {
		Plane plane = new Plane(new Point(1, 1, 1), new Point(1, 2, 1), new Point(0, 0, 1));
		// ============ Equivalence Partitions Tests ==============
		// **** Group: the ray does'n tangent or parral to the plane
		// TC01:the ray have one intersection whit the plane
		assertEquals(List.of(new Point(3, -1, 1)),
				plane.findIntersections(new Ray(new Point(0, 0, -1), new Vector(3, -1, 2))),
				"TC01 intersections plane");
		// TC02:the ray starts after the plane(0 points)
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 2), new Vector(6, 6, 4))),
				"TC02 intersections plane");
		// =============== Boundary Values Tests ==================
		// TC11:the ray is contained in the plain(and parrol)
		assertNull(plane.findIntersections(new Ray(new Point(-0.912983377134449, 0.500012298750177, 1),
				new Vector(0.812457033999266, -0.977716999223221, 0))), "TC11 intersections plane");
		// TC12:the ray is paroll to the plane
		assertNull(
				plane.findIntersections(
						new Ray(new Point(0, 0, 2), new Vector(0.812457033999266, -0.977716999223221, 0))),
				"TC12 intersections plane");
		// TC13: the ray is parol to the plane and starts at the plane
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1))),
				"TC13 intersections plane");
		// TC14:the ray starts before the plane, and paroll the plane(1 point)
		// assertNull(plane.findIntersections(new Ray(new Point(0, 0, -0.5), new
		// Vector(0, 0, 2.5))),
		// "TC14 intersections plane");
		// TC15:the ray starts after the plain and parrol to the plan (0 points)
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1.5), new Vector(0, 0, 5))),
				"TC15 intersections plane");
		// TC16:the ray starts at the plain but not parrol or tangent to the plane
		assertNull(plane.findIntersections(new Ray(new Point(-0.100526343135183, -0.477704700473044, 1),
				new Vector(0.100526343135183, 0.477704700473044, 1))), "TC16 intersections plane");
		// TC17:the ray starts at the retresenting stert piont of the plane
		assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 2, 3))),
				"TC17 intersections plane");
	}

}
