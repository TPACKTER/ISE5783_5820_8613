/**
 * 
 */
package unittests.primitives;

import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * Testing Ray
 * 
 * @author Ayala and Tamar
 *
 */
class RayTest {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		Ray ray = new Ray(new Point(1, 1, 1), new Vector(0, 0, 1));
		// ============ Equivalence Partitions Tests ==============
		// TC01: t is positive(simple) point is on the ray.
		assertEquals(new Point(1, 1, 2), ray.getPoint(1), "GetPoint does not work correctly");
		// TC12:t is negative
		assertEquals(new Point(1, 1, 0), ray.getPoint(-1), "GetPoint does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: distance=0 (returns the head point
		assertEquals(new Point(1, 1, 1), ray.getPoint(0), "GetPoint does not work correctly");

	}

	@Test
	void testFindClosestPoint() {
		List<Point> listpoints = List.of(new Point(0, 0, 0), new Point(2, 0, 0), new Point(0, 0, 1));

		// ============ Equivalence Partitions Tests ==============
		// TC01:the closest point is in the middle of the list
		Ray ray = new Ray(new Point(1.9, 0, 0), new Vector(0, 1, 0));
		assertEquals(new Point(2, 0, 0), ray.findClosestPoint(listpoints), "TC01 testFindClosestPoint");
		// =============== Boundary Values Tests ==================
		// TC11:the list is empty(returns null)
		listpoints = null;
		assertNull(ray.findClosestPoint(listpoints), "TC11 testFindClosestPoint");
		// TC12:the closest point is in the start of the list
		listpoints = List.of(new Point(2, 0, 0), new Point(0, 0, 0), new Point(0, 0, 1));
		assertEquals(new Point(2, 0, 0), ray.findClosestPoint(listpoints), "TC12 testFindClosestPoint");
		// TC13:the closest point is in the end of the list
		listpoints = List.of(new Point(0, 0, 0), new Point(0, 0, 1), new Point(2, 0, 0));
		assertEquals(new Point(2, 0, 0), ray.findClosestPoint(listpoints), "TC13 testFindClosestPoint");
	}

}
