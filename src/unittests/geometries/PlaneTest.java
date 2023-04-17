/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Vector;
/**
 * @author Ayala 
 * Testing plane
 */
class PlaneTest {
	/**
	 * Test constractor
	 */
	@Test
	void testConstructor() {
		// =============== Boundary Values Tests ==================
		//TC01:  ctor 2 point the same
		assertThrows(IllegalArgumentException.class,() -> new Plane(new Point(1, 1, 1), new Point(1, 1,1), new Point(0, 0, 0)),"ERROR: ctor()didnt throw an excaption");

		//TC02: all points on the same line
		assertThrows(IllegalArgumentException.class,() -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3,3)),"ERROR: ctor()didnt throw an excaption");
	}


	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		 // ============ Equivalence Partitions Tests ==============
		//TC01:testing get normal for plane(simple)
		Plane plane= new Plane(new Point(1,0,0),new Point(0,0,0),new Point(0,1,0));
		boolean bool= new Vector(0,0,1).equals(plane.getNormal(new Point(0, 0, 0)))||new Vector(0,0,-1).equals(plane.getNormal(new Point(0, 0, 0)));
	      assertTrue(bool, "ERROR: GetNormalPoint() worng result");
	}

}
