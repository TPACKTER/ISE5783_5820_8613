package primitives;

/***
 * class represent Ray - half a vector
 * 
 * @author Tamar
 *
 */
public class Ray {
	final private Point head;
	final private Vector dir;

	/***
	 * 
	 * @param point vector
	 * @param p0  point gets starting point and direction vector and build a ray
	 */
	public Ray(Point point, Vector v) {
		this.head = point;
		this.dir = v.normalize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other) {
			return other.head.equals(this.head) && other.dir.equals(this.dir);
		}
		return false;
	}

	/**
	 * 
	 * @return head of ray
	 */
	public Point getHead() {
		return this.head;
	}

	/**
	 * 
	 * @return diraction of ray
	 */
	public Vector getDir() {
		return this.dir;
	}

	@Override
	public String toString() {
		return "head: " + this.head + " direction: " + this.dir;
	}
}
