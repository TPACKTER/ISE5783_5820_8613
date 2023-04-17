/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**Testing Vector
 * @author Ayala
 */
class VectorTest {
	Vector v1 = new Vector(1,1,1);
	Vector v2 = new Vector(-1,-1,1);
	Vector v3 = new Vector(-1,-1,-1);
	
	/**
	 * Test method for constractor
	 */
	@Test
	public void testConstractor() {
    	  // =============== Boundary Values Tests ==================
    	//TC10:testing zero vector
    	assertThrows(IllegalArgumentException.class,()->new Vector(0, 0, 0), "Constractor() should throw an exception, but it failed");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============ Equivalence Partitions Tests ==============
		//TC01:There is a simple single test here-Adding 2 vectors
		assertEquals(new Vector(0,0,2),v1.add(v2),"Add() wrong result");
		  // =============== Boundary Values Tests ==================
		//TC10:Test Zero Vector from adding Two vectors on the same line, opposite directions, equal length
		assertThrows(IllegalArgumentException.class,()->v1.add(v3), "Add() should throw an exception, but it failed");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
	      // ============ Equivalence Partitions Tests ==============
		//TC01:Testing Scale a vector whit a negetive num (simple)
		assertEquals(new Vector(-1,-1,-1),v1.scale(-1),"Scale() wrong result");
		//TC01:Testing Scale a vector whit a positive num (simple)
		assertEquals(new Vector(2,2,2),v1.scale(2),"Scale() wrong result");
	      // =============== Boundary Values Tests ==================
		//TC10:Testing Scale vector whith 0
		assertThrows(IllegalArgumentException.class,()->v1.scale(0), "Scale() should throw an exception, but it failed");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v4=new Vector(0, 0, 2);
		Vector v5=new Vector(0, 1, 2);
		Vector v6=new Vector(0, 1, -2);
		Vector v7=new Vector(0, 2, 0);
		Vector v8=new Vector(0, 1, 0);
	      // ============ Equivalence Partitions Tests ==============
		//TC01:Testing DotProduct for 2 vectors with sharp angle
		assertEquals(4,v4.dotProduct(v5),"DotProduct() wrong result");
		//TC02:Testing DotProduct for 2 vectors with obtuse angle 
		assertEquals(-4,v4.dotProduct(v6),"DotProduct() wrong result");
	      // =============== Boundary Values Tests ==================
		//TC10:Testing DotProduct between orthogonal vectors
		assertEquals(0,v4.dotProduct(v7),"DotProduct() wrong result");
		//TC11:Testing DotProduct while one of them is 1 length
		assertEquals(1,v5.dotProduct(v8),"DotProduct() wrong result");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v4=new Vector(0, 0, 1);
		Vector v5=new Vector(0, 1,0);
		Vector v6=new Vector(0, 0,2);
	      // ============ Equivalence Partitions Tests ==============
		//TC01:Testing CrossProduct between 2 Vectors  with different direction(simple)
		assertEquals(new Vector(-1, 0, 0),v4.crossProduct(v5),"CrossProduct() wrong result");
	     // =============== Boundary Values Tests ==================
		//TC10:Testing CrossProduct between 2 Vectors  with the same direction
		assertThrows(IllegalArgumentException.class,()->v4.crossProduct(v6), "CrossProduct() should throw an exception, but it failed");
		
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
	      // ============ Equivalence Partitions Tests ==============
			//TC01:Testing LengthSquared of vector (simple)
			assertEquals(3,v1.lengthSquared(),"lengthSquared() wrong result");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		 // ============ Equivalence Partitions Tests ==============
		//TC01:Testing Length of vector (simple)
		assertEquals(Math.sqrt(3),v1.length(),"Length() wrong result");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		 // ============ Equivalence Partitions Tests ==============
		//TC01:Testing normalizing of vector (simple)
		assertEquals(1,v1.normalize().length(),"normalize() wrong result");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		 // ============ Equivalence Partitions Tests ==============
		//TC01:Vectors which are'nt on the same  (direction and size )
		assertEquals(new Vector(-2,-2,0),v2.subtract(v1),"Subtract() wrong result");
		  // =============== Boundary Values Tests ==================
	  //TC10:Vectors with the same direction and size
		assertThrows(IllegalArgumentException.class,()->v1.subtract(v1), "Subtract()  wrong result");
		
	}

}
