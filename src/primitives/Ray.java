package primitives;
/***
 *class represent Ray - half a vector
 * @author Tamar
 *
 */
public class Ray {
Point head;
Vector dir;

public Ray(Vector vec,Point p0) {
	this.head = p0;
	this.dir = vec.normalize();
}

@Override
public boolean equals(Object o) {
	Ray other = (Ray)o;
	return other.head.equals(this.head)&& other.dir.equals(this.dir);
}

@Override
public String toString() {
return "head: "+this.head.toString()+" direction: " + this.dir.toString();
}
}
