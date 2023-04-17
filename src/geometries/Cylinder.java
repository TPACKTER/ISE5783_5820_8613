package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
/**
 * class which represent a Cylinder
 * @author Ayala
 *
 */
public class Cylinder extends Tube{
final private double hight;
/**
 * 
 * @param radius
 * @param ray
 * @param h
 * get radius,ray and specific  height and builds a Cylinder
 */
public Cylinder(double radius,Ray ray,double h)
{
	super(radius, ray);
	if(Util.alignZero(h)<=0) {
		throw new IllegalArgumentException("illegal height");
	}
	else {
		this.hight=h;
	}
	
}


}
