/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * @author Ayala
 *
 */
class TriangleTest {

	/**
	 * Test method for
	 * {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testTriangle() {
		// =============== Equivalence Partitions Tests ==============
		// TC01: constructor (simple)
		try {
			new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct triangle");
		}
		  // =============== Boundary Values Tests ==================
		  // TC10: Co-located points
	      assertThrows(IllegalArgumentException.class, //
	                   () -> new Triangle(new Point(0, 0, 1), new Point(0, 0, 3), new Point(0, 0,20)),
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
		boolean bool= (new Vector(0, 0, 1)).equals(tri.getNormal(null)) || (new Vector(0,0,-1)).equals(tri.getNormal(null));
		assertTrue(bool, "ERROR: GetNormal()-triangle is wrong");
	}

}
