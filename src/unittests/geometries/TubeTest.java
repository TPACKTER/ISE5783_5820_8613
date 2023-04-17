/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * @author Ayala
 * Testing Tube
 *
 */
class TubeTest {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests =============
		//TC01:normal for tube(simple)
				Ray r= new Ray( new Point(0,0,0),new Vector(0,1,0));
				Tube t= new Tube(1,r);
				Point p = new Point(1,0,1);
				Vector n= t.getNormal(p);
				boolean bool=Util.isZero(r.getDir().dotProduct(n)); 
				assertTrue(bool,"bad normal to tube");//chek if it is good dir
				assertEquals(1,n.length(),0.000001,"bad normal to tube");
				// =============== Boundary Values Tests ==================
				  // TC10: The point "is in front of the head of the horn"
				Ray r1= new Ray( new Point(0,0,0),new Vector(0,1,0));
				Tube t1= new Tube(1,r1);
				Point p1 = new Point(0,0,1);
				Vector n1= t1.getNormal(p1);
				boolean bool1=Util.isZero(r1.getDir().dotProduct(n1)); 
				assertTrue(bool1,"bad normal to tube");//chek if it is good dir
				assertEquals(1,n1.length(),0.000001,"bad normal to tube");
	}

}
