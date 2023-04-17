package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;
/**
 * represents a tube
 * @author Ayala
 *
 */
public class Tube extends RadialGeometry {
final protected Ray axisRay;
/**
 * 
 * @param r
 * @param ray
 * gets a radius and a ray and builds a tube
 */
public Tube (double r,Ray ray) {
	this.axisRay=ray;
	this.radius=r;
}


@Override
public Vector getNormal(Point p) {
	
	  	double t=this.axisRay.getDir().dotProduct(p.subtract(this.axisRay.getHead()));
	  //if the point is not on the same level then get the point
        //and return the normal
        if(!Util.isZero(t))
        {
        	Point o=this.axisRay.getHead().add(this.axisRay.getDir().scale(t));
        	return p.subtract(o);
        }

        //if the point is on the same level then return normal
        return p.subtract(axisRay.getHead()).normalize();

}

}
