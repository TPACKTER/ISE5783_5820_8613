package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

/**
 * Testing Cylinder
 * 
 * @author Tamar and Ayala
 *
 */
public class CylinderTest {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: point on surface
		Cylinder c = new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 5);
		Vector result = c.getNormal(new Point(0, 2, 1));
		// checks the result is orthogonal to the ray
		assertEquals(new Vector(0, 1, 0), result, "Cylinder's normal is not orthogonal to the ray");

		// TC02: Test when a point is on first base
		Vector result2 = c.getNormal(new Point(0, 1, 0));
		// checks the result is parallel to the ray
		assertEquals(new Vector(0, 0, -1), result2, "The resuls is not parallel to the ray");

		// TC03: Test when a point is on second base
		Vector result3 = c.getNormal(new Point(0, 1, 5));
		// checks the result is parallel to the ray
		assertEquals(new Vector(0, 0, 1), result3, "The resuls is not parallel to the ray");

		// =============== Boundary Values Tests ==================
		// TC11: Test when point is on the center of base 1
		Vector result4 = c.getNormal(new Point(0, 0, 0));
		// checks the result is parallel to the ray
		assertEquals(new Vector(0, 0, -1), result4, "The resuls is not parallel to the ray");

		// TC12: Test when point is on the center of base 2
		Vector result5 = c.getNormal(new Point(0, 0, 5));
		// checks the result is parallel to the ray
		assertEquals(new Vector(0, 0, 1), result5, "The resuls is not parallel to the ray");

	}
}
