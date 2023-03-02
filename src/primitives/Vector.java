package primitives;

public class Vector extends Point {
	public Vector(double coord1, double coord2, double coord3) {
		super(coord1, coord2, coord3);
		if (Double3.ZERO.equals(this.point)) {
			this.point = null;
			throw new IllegalArgumentException("zero-illigal");
		}
	}

	Vector(Double3 dPoint) {
		super(dPoint);
		if (Double3.ZERO.equals(this.point)) {
			this.point = null;
			throw new IllegalArgumentException("zero-illigal");
		}
	}
/***
 * @param vec 
 * @return a new vector that added the vector we got to the vector
 */
	public Vector add(Vector vec) {

		return new Vector(this.point = this.point.add(vec.point));

	}
/***
 * 
 * @param x
 * @return the scalded vector (scalded with x)
 */
	public Vector scale(double x) {

		return new Vector(this.point.scale(x));
	}
	/***
	 * 
	 * @param vec
	 * @return a dot product of 2 given vectors
	 */
	public double dotProduct(Vector vec) {
		return this.point.d1*vec.point.d1+this.point.d2*vec.point.d2+this.point.d3*vec.point.d3;
	}
	/***
	 * 
	 * @param vec
	 * @return a vecrot which is the cross product of the 2 given vectors (a normal vector for both)
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector (this.point.d2*vec.point.d3-this.point.d3*vec.point.d2,this.point.d3*vec.point.d1-this.point.d1*vec.point.d3,this.point.d1*vec.point.d2-this.point.d2*vec.point.d1);
	}
	/***
	 * 
	 * @return the Squared distance of the vector
	 */
	public double lengthSquared()
	{
		Point p=new Point(Double3.ZERO);
		return this.distanceSquared(p);
	}
	/***
	 * 
	 * @return the length of the  vector
	 */
	public double lengt()
	{
	return	Math.sqrt(this.lengthSquared());
	}
	/***
	 * 
	 * @return the normalized vector
	 */
	public Vector normalize()
	{
		double normal =1/this.lengt();
		return this.scale(normal);
	}
}
