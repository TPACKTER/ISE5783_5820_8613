package renderer;

import java.util.List;
import java.util.MissingResourceException;
import java.util.LinkedList;
import geometries.Plane;
import primitives.*;


/***
 * Class representing camera
 * 
 * @author Ayala and Tamar
 *
 */
public class Camera {
	/***
	 * location point of camera
	 */
	private Point location;
	/***
	 * the high of view plane
	 */
	private double height;
	/***
	 * the width of view plane
	 */
	private double width;
	/***
	 * the distance of camera from view plane
	 */
	private double distance;
	/***
	 * vector of direction of view plane
	 */
	private Vector to;
	/***
	 * up vector of view plane's up direction
	 */
	private Vector up;
	/***
	 * up vector of view plane's right direction
	 */
	private Vector right;
	/***
	 * in charge of writing the image of camera
	 */
	private ImageWriter imageWriter;
	/***
	 * in charge of the ray tracing of the point
	 */
	private RayTracerBase rayTracer;

	/** Depth Of Filed properties. **/

	/**
	 * boolean variable that determines whether to use depth of filed.
	 */
	boolean isDepthOfField = false;
	/**
	 * number with integer square for the matrix of points.
	 */
	private int numOfPointsOnAperture = 81;
	/**
	 * Declaring a variable called apertureSize of type double.
	 */
	private double apertureSize = 9;
	/**
	 * Creating an array of Point objects.
	 */
	private Point[] aperturePoints;

	private double apertureDistance = 0;

	/**
	 * Plane variable called FOCAL_PLANE .
	 */
	private Plane focalPlain;

	/***
	 * Constructor for camera based on location point v-up, and v-to
	 * 
	 * @param location point to locate the camera at
	 * @param to       vector of direction of view plane
	 * @param up       vector of view plane's up direction
	 */
	public Camera(Point location, Vector to, Vector up) {
		if (!Util.isZero(up.dotProduct(to)))
			throw new IllegalArgumentException("not rthogonal");
		this.up = up.normalize();
		this.to = to.normalize();
		this.right = this.to.crossProduct(this.up);
		this.location = location;
	}

	/***
	 * setting the view plane's size
	 * 
	 * @param width  the view plane's width
	 * @param height the view plane's width height
	 * @return the updated camera
	 */
	public Camera setVPSize(double width, double height) {
		if (Util.alignZero(height) <= 0)
			throw new IllegalArgumentException("illigal height");
		if (Util.alignZero(width) <= 0)
			throw new IllegalArgumentException("illigal  width");
		this.height = height;
		this.width = width;
		return this;

	}

	/**
	 * sets for the depth of field to true or false.
	 *
	 * @param isDepthOfField If true, the camera will have a depth of field effect.
	 * @return The camera object itself.
	 */
	public Camera setDepthOfField(boolean isDepthOfField) {
		this.isDepthOfField = isDepthOfField;
		return this;
	}

	/**
	 * set NumOfPointsOnAperture
	 * 
	 * @param num number with integer square for the appture
	 * @return the updated camera
	 */
	public Camera setNumOfPointsOnAperture(int num) {
		this.numOfPointsOnAperture = num;
		return this;
	}

	/**
	 * set apertureSize
	 * 
	 * @param size of row and calm of the Aperture
	 * @return the updated camera
	 */
	public Camera setApertureSize(double size) {
		this.apertureSize = size;
		return this;
	}

	/**
	 * sets the focal plane
	 *
	 * @param distance distance of focal plane from camera
	 * @return The camera object itself.
	 */
	public Camera setfocalPlaneDistance(double distance) {
		this.focalPlain = new Plane(this.location.add(to.scale(distance)), this.to);
		return this;
	}

	/**
	 * setter for the distance of Aperture from camera
	 * 
	 * @param distance distance to set
	 * @return updated camera
	 */
	public Camera setApertureDictance(double distance1) {
		this.apertureDistance = distance1;
		return this;
	}

	/**
	 * setter for number of points of DoF
	 * 
	 * @param numOfPoints to set
	 * @return the updated camera
	 */
	public Camera setNumOfPoints(int numOfPoints) {
		this.numOfPointsOnAperture = numOfPoints;
		return this;
	}

	/**
	 * 
	 *generate the Target Area Points
	 * @param numOfPoints to set on the target area
	 * @param targetSize the size of the target area
	 * @param jitter the level of jitter to get the point at
	 * @param p to build the target area from
	 * @param upvec the up direction
	 * @param tovec the to direction
	 * @return an array of the target area points.
	 * 
	 */
	private Point[] generateTargertAreaPoints(int numOfPoints, double targetSize, double jitter, Point p,Vector upvec,Vector tovec) {
		int numOfPointsOnLine = (int) Math.sqrt(numOfPoints);
		double space = targetSize / numOfPointsOnLine;
		upvec=upvec.normalize();
		tovec=tovec.normalize();
Vector rightvec=tovec.crossProduct(upvec).normalize();
		List<Point> TargerAreaPointList = new LinkedList<>();

		for (int j = 0; j < numOfPointsOnLine; j++) {
			TargerAreaPointList.add(p);
			for (int i = 1; i < numOfPointsOnLine; i++) {
				p = p.add(rightvec.scale(space + getRandom(-jitter, jitter)));
				TargerAreaPointList.add(p);
			}
			p = p.add(rightvec.scale(-space * (numOfPointsOnLine - 1) + getRandom(-jitter, jitter))
					.add(upvec.scale(-space + getRandom(-jitter, jitter))));
		}

		return TargerAreaPointList.toArray(new Point[TargerAreaPointList.size()]);
	}

	/**
	 * generate Aperture Points
	 * 
	 */
	private void generateAperturePoints() {
		int numOfPointsOnLine = (int) Math.sqrt(this.numOfPointsOnAperture);
		double space = apertureSize / numOfPointsOnLine;
		double jitter = 0.1;

		Point p = location.add(up.scale(apertureSize - space / 2 + getRandom(-jitter, jitter)))
				.add(right.scale(-apertureSize + space / 2 + getRandom(-jitter, jitter)))
				.add(apertureDistance == 0 ? to : to.scale(apertureDistance));

		this.aperturePoints = generateTargertAreaPoints(this.numOfPointsOnAperture, apertureSize, jitter, p,up,to);
	}
/**
 * gives a random number between 2 given numbers
 * @param min minimum number
 * @param max maximum number
 * @return random number between min and max
 */
	private double getRandom(double min, double max) {
		return min + Math.random() * (max - min);
	}

	/**
	 * calculates the average color dof
	 * 
	 * @param ray to find the averaged color for
	 * @return the averaged color (dof)
	 */
	private Color averageBeamColor(Ray ray) {
		this.generateAperturePoints();
		Color averageColor = Color.BLACK;
		Point focalPoint = this.focalPlain.findGeoIntersections(ray).get(0).point;// (new
																					// Ray(this.location,this.to)).get(0).point;
		for (Point aperturePoint : this.aperturePoints) {
			Ray apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
			Color apertureColor = rayTracer.traceRay(apertureRay);
			averageColor = averageColor.add(apertureColor);
		}

		averageColor = averageColor.reduce(this.numOfPointsOnAperture);
		return averageColor;
	}

	/***
	 * setting the view plane's distance
	 * 
	 * @param distance the view plane's to set
	 * @return the updated camera
	 */
	public Camera setVPDistance(double distance) {
		if (Util.alignZero(distance) <= 0)
			throw new IllegalArgumentException("illigal distance");
		this.distance = distance;
		return this;
	}

	/***
	 * construct a ray through a pixel
	 * 
	 * @param nX numbers of columns
	 * @param nY numbers of rows
	 * @param j  y index of pixel to construct the ray through
	 * @param i  x index of pixel to construct the ray through
	 * @return ray starts at the camera and go though the given pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pIJ = location.add(to.scale(distance));

		double rY = Util.alignZero(height / nY);
		double rX = Util.alignZero(width / nX);

		double yI = -(i - (nY - 1) / 2.0) * rY;
		double xJ = (j - (nX - 1) / 2.0) * rX;
		// avoiding zero vector cases
		if (!Util.isZero(xJ))
			pIJ = pIJ.add(right.scale(xJ));

		if (!Util.isZero(yI))
			pIJ = pIJ.add(up.scale(yI));

		Vector vIJ = pIJ.subtract(location);

		return new Ray(location, vIJ);
	}

	/***
	 * set for camera's ImageWriter
	 * 
	 * @param imageWriter to set imageWriter field
	 * @return the updated camera
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/***
	 * set for camera's RayTracerBase
	 * 
	 * @param rayTracerBase to set rayTracerBase field
	 * @return the updated camera
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracer = rayTracerBase;
		return this;
	}

	/***
	 * Throws an exception if one of the elements is missing
	 * 
	 * @return the rendered image
	 */
	public Camera renderImage() {

		if (this.location == null)
			throw new MissingResourceException("location is missing", "Point", "location");
		if (this.up == null)
			throw new MissingResourceException("up is missing", "Vector", "up");
		if (this.right == null)
			throw new MissingResourceException("right is missing", "Vector", "right");
		if (this.to == null)
			throw new MissingResourceException("to is missing", "Vector", "to");
		if (this.imageWriter == null)
			throw new MissingResourceException("imageWriter is missing", "ImageWriter", "imageWriter");
		if (this.rayTracer == null)
			throw new MissingResourceException("rayTracer is missing", "RayTracerBase", "rayTracer");
		if (this.isDepthOfField && this.focalPlain == null)
			throw new MissingResourceException("you must set a distance for focal plane", "Plane", "focal plane");
		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();
		for (int i = 0; i < nx; i++)
			for (int j = 0; j < ny; j++) {
				this.imageWriter.writePixel(i, j, caststRay(i, j, nx, ny));
			}
		return this;
	}

	/***
	 * calculate the color in a given indexed pixel
	 * 
	 * @param i - x index parameter
	 * @param j - j index parameter
	 * @return the color of a pixel in a given index
	 */
	private Color caststRay(int i, int j, int nx, int ny) {
		Ray ray = constructRay(nx, ny, i, j);
		if (this.isDepthOfField) {
			return averageBeamColor(ray);// the color calc with depth
		}

		return rayTracer.traceRay(ray);
	}

	/***
	 * prints grid's lines to camera's picture, the lines are in the given color
	 * 
	 * @param interval the size of a pixel
	 * @param color    color to color the lines at
	 */
	public void printGrid(int interval, Color color) {
		if (this.imageWriter == null)
			throw new MissingResourceException("imageWriter is missing", "ImageWriter", "imageWriter");
		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();
		for (int i = 0; i < nx; i++)
			for (int j = 0; j < ny; j++)
				if (i % interval == 0 || j % interval == 0)
					this.imageWriter.writePixel(i, j, color);

	}

	/***
	 * writes the picture to image
	 */
	public void writeToImage() {
		if (this.imageWriter == null)
			throw new MissingResourceException("imageWriter is missing", "ImageWriter", "imageWriter");
		this.imageWriter.writeToImage();
	}
}