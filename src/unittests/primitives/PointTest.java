package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * Testing Point
 * 
 * @author Ayala and Tamar
 */
class PointTest {
	Point p1 = new Point(1, 2, 3);
	Point p2 = new Point(2, 4, 6);
	Point p3 = new Point(-1, -2, -3);
	Vector v1 = new Vector(-1, -2, -3);

	/** Test method for {@link primitives.Point#subtract(primitives.Point)}. */
	@Test
	void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: testing the subtract between two points (simple test)
		assertEquals(new Vector(1, 2, 3), p2.subtract(p1), "the subtracting does not work correctly");
		// =============== Boundary Values Tests ==================
		// TC11: testing the subtract between 2 same points-vector
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "the subtracting does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// =======Equivalence Partitions Tests=======
		// TC01: testing the adding of point and vector (simple test)
		assertEquals(new Point(1, 2, 3), p2.add(v1), "the adding does not work correctly");
		// =============== Boundary Values Tests ==================
		// TC10:testing the adding of a point and vector-while the vector is the
		// same-but negative
		assertEquals(new Point(0, 0, 0), p1.add(v1), "the adding does not work correctly");

	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// =======Equivalence Partitions Tests=======
		// TC01: testing if the distance between two points -with sqrt- is correct
		assertEquals(Math.sqrt(14), p1.distance(p2), 0.00001, "ERROR: Distance doesn't work correctly");
		// =============== Boundary Values Tests ==================
		// TC11: same point- distance 0
		assertEquals(0, p1.distance(p1), 0.00001, "ERROR: Distance doesn't work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// =======Equivalence Partitions Tests=======
		// TC01: testing if the Squared distance between two points is correct (simple
		// test)
		assertEquals(14, p1.distanceSquared(p2), 0.00001, "ERROR: DistanceSquared doesn't work correctly");
	}

}
