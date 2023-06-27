package renderer;

import java.util.List;
import java.util.MissingResourceException;
import java.util.function.Function;
import java.util.LinkedList;
import geometries.Plane;
import primitives.*;
import java.util.HashMap;
import java.util.Map;

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
	 * number with integer square for the matrix of points.
	 */
	private int numOfRays = 1;
	boolean isAdeptive = false;
	Grid apature;
	boolean anti = false;
/*
	public Camera SetAnti(boolean bool, int num) {
		this.anti = bool;
		this.numOfRays = num;
		return this;
	}
	*/

	private int numOfPointsOnAperture = 81;
	/**
	 * Declaring a variable called apertureSize of type double.
	 */
	private double apertureSize = 0;
	/**
	 * Creating an array of Point objects.
	 */


	private double apertureDistance = 0;

	/**
	 * Plane variable called FOCAL_PLANE .
	 */
	private double focalPlain = 10;

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
		this.right = this.to.crossProduct(this.up).normalize();
		this.location = location;
	}

	/**
	 * setter for NumOfRays
	 * 
	 * @param num to ser for NumOfRays
	 * @return the updated camera
	 */
	public Camera setNumOfRays(int num) {
		this.numOfRays = num;
		return this;
	}

	/**
	 * setter for adeptive
	 * 
	 * @param adp to set adeptive
	 * @return the updated camera
	 */
	public Camera isAdeptive(boolean adp) {
		this.isAdeptive = true;
		return this;
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
/*
	/**
	 * sets for the depth of field to true or false.
	 *
	 * @param isDepthOfField If true, the camera will have a depth of field effect.
	 * @return The camera object itself.
	 */
	/*
	public Camera setDepthOfField(boolean isDepthOfField) {
		this.isDepthOfField = isDepthOfField;
		return this;
	}
	*/

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
	public Camera setfocalPlaneDistance(double distance1) {
		this.focalPlain = distance1;
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
/*
	/**
	 * setter for number of points of DoF
	 * 
	 * @param numOfPoints to set
	 * @return the updated camera
	 */
	/*
	public Camera setNumOfRays(int numOfPoints) {
		this.numOfRays = numOfPoints;
		return this;
	}
	*/

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

	private Color constructthoughtBeemRay(int nX, int nY, int j, int i) {
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

		Vector vIJ = pIJ.subtract(location).normalize();

		Grid anti = new Grid(this.numOfRays, this.distance, rY, up, vIJ, this.location);
		if (this.isAdeptive) {
			/*
			 * if(this.apertureSize>0&&this.numOfRays>1) {
			 * 
			 * return anti.superSampling(location, pIJ,
			 * rY,ray->this.adaptiveDofAnti(ray),this.numOfRays); }
			 */
			if (this.numOfRays > 1)
				return anti.superSampling(this.location, pIJ, rY, ray -> this.rayTracer.traceRay(ray), 1);
			if (this.apertureSize > 0) {

				double top = pIJ.distance(this.location) * (this.focalPlain / this.distance);
				return (Color) this.apature.superSamplingForAppture(this.location.add(vIJ.scale(top)),
						this.location.add(apertureDistance == 0 ? to : to.scale(this.apertureDistance)),
						this.apertureSize, ray1 -> this.rayTracer.traceRay(ray1), this.numOfPointsOnAperture)[0];
			}

		}

		if (this.apertureSize > 0 && this.numOfRays > 1) {
			List<Ray> rays = anti.gridRays(0, null);
			List<Ray> dof = new LinkedList<Ray>();
			for (Ray ray : rays)
				dof.addAll(this.apature.gridRays(2, ray.getPoint(focalPlain)));
			return rayTracer.traceRays(dof);
		}
		if (this.apertureSize > 0) {

			double top = pIJ.distance(this.location) * (this.focalPlain / this.distance);

			return this.rayTracer.traceRays(this.apature.gridRays(2, this.location.add(vIJ.scale(top))));
		}
		return this.rayTracer.traceRays(anti.gridRays(0, null));

	}
	/*
	 * private Color adaptiveDofAnti(Ray ray) {
	 * 
	 * Function<Ray,List<Ray>>
	 * traceRays2=ray1->(List<Ray>)(this.apature.superSamplingForAppture(ray1.
	 * getPoint(top),this.location.add(apertureDistance==0?to:to.scale(this.
	 * apertureDistance)),
	 * this.apertureSize,traceRay1,this.numOfPointsOnAperture)[1])));
	 * Function<Ray,List<Ray>> traceRays3=this.rayTracer.traceRay()
	 * 
	 * double top= pIJ.distance(this.location)*(this.focalPlain/this.distance);
	 * Function<Ray,Color> traceRay1=ray2->this.rayTracer.traceRay(ray2);
	 * Function<Ray,Color>
	 * fun=ray1->(Color)(this.rayTracer.traceRays((List<Ray>)(this.apature.
	 * superSamplingForAppture(ray1.getPoint(top),this.location.add(apertureDistance
	 * ==0?to:to.scale(this.apertureDistance)),
	 * this.apertureSize,traceRay1,this.numOfPointsOnAperture)[0]))); return null; }
	 */

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

		Vector vIJ = pIJ.subtract(location).normalize();

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
		if (this.apertureSize > 0 && this.focalPlain == 0)
			throw new MissingResourceException("you must set a distance for focal plane", "Plane", "focal plane");
		if (this.apertureSize > 0) {

			this.apature = new Grid((int) Math.sqrt(this.numOfPointsOnAperture), this.apertureDistance,
					this.apertureSize, this.up, this.to, this.location);
		}
		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();
		for (int i = 0; i < nx; i++)
			for (int j = 0; j < ny; j++) {
				this.imageWriter.writePixel(i, j, castRay(i, j, nx, ny));
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
	private Color castRay(int i, int j, int nx, int ny) {

		if (this.numOfRays > 1 || this.isAdeptive || this.apertureSize > 0)
			return constructthoughtBeemRay(nx, ny, i, j);

		Ray ray = constructRay(nx, ny, i, j);

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