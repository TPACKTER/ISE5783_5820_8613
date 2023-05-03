package geometries;

import primitives.*;
import primitives.Vector;

import java.util.*;

/**
 * Tube class represents tree-dimensional tube in 3D Cartesian coordinate system
 * 
 * @author Ayala and Tamar
 *
 */
public class Tube extends RadialGeometry {
	/**
	 * tube's ray
	 */
	final protected Ray axisRay;

	/**
	 * Tube constructor based on radius and a ray.
	 * 
	 * @param r   tube's radius
	 * @param ray tube's ray
	 */
	public Tube(double r, Ray ray) {
		super(r);
		this.axisRay = ray;
	}

	@Override
	public Vector getNormal(Point p) {
		Point p0 = this.axisRay.getHead();
		Vector dir = this.axisRay.getDir();

		double t = dir.dotProduct(p.subtract(p0));

		// if the point is on the same level then return normal
		// if the point is not on the same level then get the point
		// and return the normal
		Point o = Util.isZero(t) ? p0 : p0.add(dir.scale(t));
		return p.subtract(o).normalize();

	}

	/***
	 * get axis ray
	 * 
	 * @return the axis ray
	 */
	public Ray getAxisRay() {
		return this.axisRay;
	}

	@Override
	
	public List<Point> findIntersections(Ray ray) {
	     Point p0 = ray.getHead();
	        Vector v = ray.getDir();
	        Point pa = this.axisRay.getHead();
	        Vector va = this.axisRay.getDir();
	        double a, b, c; //coefficients for quadratic equation ax^2 + bx + c

	        // a = (v-(v,va)va)^2
	        // b = 2(v-(v,va)va,delP-(delP,va)va)
	        // c = (delP-(delP,va)va)^2 - r^2
	        // delP = p0-pa
	        //(v,u) = v dot product u = vu = v*u
	      //Step 1 - calculates a:
	        Vector vecA = v;
	        try {
	            double vva = v.dotProduct(va); //(v,va)
	            if (!Util.isZero(vva)) vecA = v.subtract(va.scale(vva)); //v-(v,va)va
	            a = vecA.lengthSquared(); //(v-(v,va)va)^2
	        } catch (IllegalArgumentException e) {
	            return null; //if a=0 there are no intersections because Ray is parallel to axisRay
	        }
	        //Step 2 - calculates deltaP (delP), b, c:
	        try {
	            Vector deltaP = p0.subtract(pa); //p0-pa
	            Vector deltaPMinusDeltaPVaVa = deltaP;
	            double deltaPVa = deltaP.dotProduct(va); //(delP,va)va)
	            if (!Util.isZero(deltaPVa)) deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); //(delP-(delP,va)va)
	            b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); //2(v-(v,va)va,delP-(delP,va)va)
	            c = deltaPMinusDeltaPVaVa.lengthSquared() - radius*radius; //(delP-(delP,va)va)^2 - r^2
	        } catch (IllegalArgumentException e) {
	            b = 0; //if delP = 0, or (delP-(delP,va)va = (0, 0, 0)
	            c = -1 * radius*radius;
	        }

    double descrimimamt=b*b-4*a*c;
   
    if(Util.alignZero(descrimimamt)<=0)
    	return null;
    double t1=(-1*b+Math.sqrt(descrimimamt))/(2*a);
    double t2=(-1*b-Math.sqrt(descrimimamt))/(2*a);
    
    if(t1==t2)
    	if(Util.alignZero(t1)>0)
    		return List.of(ray.getPoint(t1));
    	else
    		return null;
    else  if (t1 > 0 && t2 > 0) {
    	Point p1=ray.getPoint((-1*b+Math.sqrt(descrimimamt))/(2*a));
    Point p2=ray.getPoint((-1*b-Math.sqrt(descrimimamt))/(2*a));
    	return p1.getX()<p2.getX()?List.of(p1,p2):List.of(p2,p1);}
    if (Util.alignZero(t1) > 0) return List.of(ray.getPoint(t1));
    if (Util.alignZero(t2)>0) return List.of(ray.getPoint(t2));
    
		
	return null;

	}
}
