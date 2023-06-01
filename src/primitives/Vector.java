package primitives;

/***
 * This class will serve all geometries and primitives based on a vector
 * 
 * @author Ayala and Tamar
 */
public class Vector extends Point {
	/***
	 * Constructor to initialize Vector base object-and its filed-Double3 object
	 * with its three number values
	 * 
	 * @param d1 first number value of xyz (Double3)
	 * @param d2 second number value value of xyz (Double3)
	 * @param d3 third number value value of xyz (Double3)
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (Double3.ZERO.equals(this.xyz)) {
			throw new IllegalArgumentException("zero-illigal");
		}
	}

	/***
	 * Constructor to initialize Vector based object-with double3 as input.
	 * 
	 * @param dPoint for xyz filed(Double3)
	 */
	Vector(Double3 dPoint) {
		super(dPoint);
		if (this.xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("zero-illigal");
		}
	}

	/**
	 * Add another vector to this vector
	 * 
	 * @param vec right handle side operand for addition
	 * @return a new vector-result of add in a new Vector object
	 */
	public Vector add(Vector vec) {
		return new Vector(this.xyz.add(vec.xyz));
	}

	/**
	 * scaling Vector and number
	 * 
	 * @param x handle double operand for scaling a vector
	 * @return a new vector result of scaling
	 */
	public Vector scale(double x) {
		return new Vector(this.xyz.scale(x));
	}

	/***
	 * dot-Product 2 vectors
	 * 
	 * @param vec right handle side operand for dot-Product
	 * @return a dot product of 2 given vectors
	 */
	public double dotProduct(Vector vec) {
		return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + this.xyz.d3 * vec.xyz.d3;
	}

	/***
	 * crossing product 2 vectors into a new vector which is the normal vector for
	 * both
	 * 
	 * @param vec right handle side operand for cross product
	 * @return a new vector-cross product of the 2 given vectors -a normal vector
	 *         for both.
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector(this.xyz.d2 * vec.xyz.d3 - this.xyz.d3 * vec.xyz.d2,
				this.xyz.d3 * vec.xyz.d1 - this.xyz.d1 * vec.xyz.d3,
				this.xyz.d1 * vec.xyz.d2 - this.xyz.d2 * vec.xyz.d1);
	}

	/***
	 * Finds the Squared length of vector
	 * 
	 * @return result of Squared length
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/***
	 * Finds the length of vector
	 * 
	 * @return the length
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/***
	 * Finds the normalized vector for the given vector- which have the same
	 * direction but it'ss length is 1
	 * 
	 * @return a new vector- (normalized)
	 */
	public Vector normalize() {
		return this.scale(1 / this.length());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Vector other && super.equals(other);
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "->" + super.toString();
	}
}
