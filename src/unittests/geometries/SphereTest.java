/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * @author Ayala 
 * Testing Sphere
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
		Vector v = p1.subtract(p).normalize();// normal vector
		assertEquals(v, s.getNormal(p), "ERROR: GetNormal() of sphere not correct");

	}

}
