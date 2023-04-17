package primitives;

/***
 * 
 * @author Ayala and tamar
 * class representing a vector entity
 */
public class Vector extends Point {
	/***
	 * 
	 * @param coord1
	 * @param coord2
	 * @param coord3
	 * first constructor - gets three coordinates and builds a vector
	 */
	public Vector(double coord1, double coord2, double coord3) {
		super(coord1, coord2, coord3);
		if (Double3.ZERO.equals(this.xyz)) {
			throw new IllegalArgumentException("zero-illigal");
		}
	}

	/***
	 * 
	 * @param dPoint double3
	 * second constructor - gets doble3 point and builds a vector
	 */
	Vector(Double3 dPoint) {
		super(dPoint);
		if (this.xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("zero-illigal");
		}
	}

	/***
	 * @param vec
	 * @return a new vector that added the vector we got to the vector
	 */
	public Vector add(Vector vec) {
		return new Vector(this.xyz.add(vec.xyz));
	}

	/***
	 * 
	 * @param x
	 * @return the scalded vector (scalded with x)
	 */
	public Vector scale(double x) {
	return 	new Vector(this.xyz.scale(x));
	}

	/***
	 * 
	 * @param vec
	 * @return a dot product of 2 given vectors
	 */
	public double dotProduct(Vector vec) {
		return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + this.xyz.d3 * vec.xyz.d3;
	}

	/***
	 * 
	 * @param vec
	 * @return a vecrot which is the cross product of the 2 given vectors (a normal
	 *         vector for both)
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector(this.xyz.d2 * vec.xyz.d3 - this.xyz.d3 * vec.xyz.d2,
				this.xyz.d3 * vec.xyz.d1 - this.xyz.d1 * vec.xyz.d3,
				this.xyz.d1 * vec.xyz.d2 - this.xyz.d2 * vec.xyz.d1);
	}

	/***
	 * 
	 * @return the Squared distance of the vector
	 */
	public double lengthSquared() {
			return this.dotProduct(this);
	}

	/***
	 * 
	 * @return the length of the vector
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/***
	 * 
	 * @return the normalized vector
	 */
	public Vector normalize() {
		return this.scale(1 / this.length());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Vector other)
			return super.equals(other);
		return false;
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "‐>" + super.toString();
	}
}
