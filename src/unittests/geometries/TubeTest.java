/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;

import primitives.Vector;

/**
 * @author Ayala Testing Tube
 *
 */
class TubeTest {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests =============
		// TC01:normal for tube(simple)
		Ray r = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
		Tube t = new Tube(1, r);
		Point p = new Point(-0.8, 0.99, 0.6);
		Vector n = t.getNormal(p);
		assertEquals(new Vector(-0.8, 0, 0.6), n, "bad normal to tube");

		// =============== Boundary Values Tests ==================
		// TC10: The point "is in front of the head of the ray"
		Ray r1 = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
		Tube t1 = new Tube(1, r1);
		Point p1 = new Point(0, 0, 1);
		Vector n1 = t1.getNormal(p1);
		assertEquals(new Vector(0, 0, 1), n1, "bad normal to tube");// chek if it is good dir

	}

	/**
	 * Test method for findIntersections
	 */
	@Test
	void testfindIntersections() {
		Tube tube = new Tube(1, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)));
		List<Point> lst;
		String error = "ERROR: geometries.Tube.findIntersections() -- bad result";

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's axis is outside Tube (0 points)
		lst = tube.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC02: Ray starts before Tube and crosses it (2 points)
		lst = tube.findIntersections(new Ray(new Point(-1, 2, 3), new Vector(1, -1, 0)));
		assertEquals(2, lst.size(), error);
		assertEquals(new Point(0, 1, 3), lst.get(0), error); // closest Point first
		assertEquals(new Point(1, 0, 3), lst.get(1), error); // farthest Point second

		// TC03: Ray starts inside Tube (1 point)
		lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 3), new Vector(1, -1, 0)));
		assertEquals(1, lst.size(), error);
		assertEquals(new Point(1, 0, 3), lst.get(0), error);

		// TC04: Ray starts after Tube (0 points)
		lst = tube.findIntersections(new Ray(new Point(-1, 2, 3), new Vector(-1, 1, 0)));
		assertNull(lst, error);

		// =============== Boundary Values Tests ==================
		// *********** Group: Ray's axis crosses Tube (but not at center) ***********
		// TC11: Ray starts at Tube and goes inside (1 point)
		lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(1, -1, 0)));
		assertEquals(1, lst.size(), error); // list's size is 1 because it does not consider p0 as intersection point
		assertEquals(new Point(1, 0, 3), lst.get(0), error);

		// TC12: Ray starts at Tube and goes outside (0 points)
		lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(-1, 1, 0)));
		assertNull(lst, error);

		// *********** Group: Ray's axis goes through center ***********
		// TC13: Ray starts before Tube (2 points)
		lst = tube.findIntersections(new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0)));
		assertEquals(2, lst.size(), error);
		assertEquals(new Point(-1, 0, 1), lst.get(0), error); // closest Point first
		assertEquals(new Point(1, 0, 1), lst.get(1), error); // farthest Point second

		// TC14: Ray starts on Tube and goes inside (1 point)
		lst = tube.findIntersections(new Ray(new Point(-1, 0, 2), new Vector(1, 0, 0)));
		assertEquals(1, lst.size(), error);
		assertEquals(new Point(1, 0, 2), lst.get(0), error);

		// TC15: Ray starts inside Tube (1 point)
		lst = tube.findIntersections(new Ray(new Point(-0.5, 0, 2.5), new Vector(1, 0, 0)));
		assertEquals(1, lst.size(), error);
		assertEquals(new Point(1, 0, 2.5), lst.get(0), error);

		// TC16: Ray starts at center of Tube (1 point)
		lst = tube.findIntersections(new Ray(new Point(0, 0, 3), new Vector(3, 0, 3)));
		assertEquals(1, lst.size(), error);
		assertEquals(new Point(1, 0, 4), lst.get(0), error);

		// TC17: Ray starts after Tube (0 points)
		lst = tube.findIntersections(new Ray(new Point(-2, 2, 0), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// *********** Group: Ray's axis is tangent to Tube (all tests 0 points)
		// ***********
		// TC18: Ray starts before tangent point
		lst = tube.findIntersections(new Ray(new Point(-2, 1, 3), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// TC19: Ray starts at tangent point
		lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// TC20: Ray starts after tangent point
		lst = tube.findIntersections(new Ray(new Point(2, 1, 3), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// *********** Group: Special cases ***********
		// TC21: Ray's axis is outside, Ray is orthogonal to line starts at Tube's
		// center
		lst = tube.findIntersections(new Ray(new Point(-2, 0, 1), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC22: Ray's axis is Tube's axis
		lst = tube.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC23: Ray's direction is Tube's axis direction
		lst = tube.findIntersections(new Ray(new Point(0, 0, -9), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// *********** Group: Ray's axis is parallel to Tube's axis (all tests 0 points)
		// ***********
		// TC24: Ray starts inside Tube, but no coalesces with the cylinder axis
		tube = new Tube(1, new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));
		lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 0.5), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC25: Ray is outside Tube (0 points)
		lst = tube.findIntersections(new Ray(new Point(0.5, -0.5, 0.5), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC26: Ray is at Tube surface (0 points)
		lst = tube.findIntersections(new Ray(new Point(2, 1, 0.5), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC27: Ray is inside Tube, and starts against axis head. but no coalesces with
		// the cylinder axis (0 points)
		lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC28: Ray is outside Tube, and starts against axis head (0 points)
		lst = tube.findIntersections(new Ray(new Point(0.5, -0.5, 1), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC29: Ray is at Tube surface, and starts against axis head (0 points)
		lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// TC30: Ray is inside Tube and starts at axis head and coalesces with the
		// cylinder axis (0 points)
		lst = tube.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));
		assertNull(lst, error);

		// *********** Group: Ray is orthogonal to cylinder axis no intersect the tube
		// (0 point) ***********
		// TC31: ray head is before and above cylinder axis head (0 point)
		tube = new Tube(1, new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));
		lst = tube.findIntersections(new Ray(new Point(-1, 1, 2), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC32: ray head is after and above cylinder axis head (0 point)
		lst = tube.findIntersections(new Ray(new Point(3, 1, 2), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC33: ray head is in same x coordinates and above cylinder axis head (0
		// point)
		lst = tube.findIntersections(new Ray(new Point(1, 3, 2), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// TC34: ray head is before and under cylinder axis head (0 point)
		lst = tube.findIntersections(new Ray(new Point(-1, 1, -1), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC35: ray head is after and under cylinder axis head (0 point)
		lst = tube.findIntersections(new Ray(new Point(3, 1, -1), new Vector(0, 1, 0)));
		assertNull(lst, error);
		// TC36: ray head is in same x coordinates and under cylinder axis head (0
		// point)
		lst = tube.findIntersections(new Ray(new Point(1, 3, -1), new Vector(1, 0, 0)));
		assertNull(lst, error);

		// TC37: ray head is before and same height as cylinder axis head (0 point)
		lst = tube.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC38: ray head is after and same height as cylinder axis head (0 point)
		lst = tube.findIntersections(new Ray(new Point(3, 1, 1), new Vector(0, 1, 0)));
		assertNull(lst, error);

		// TC39: ray head is in same x coordinates and same height as cylinder axis head
		// (0 point)
		lst = tube.findIntersections(new Ray(new Point(1, 3, 1), new Vector(1, 0, 0)));
		assertNull(lst, error);

	}

}
