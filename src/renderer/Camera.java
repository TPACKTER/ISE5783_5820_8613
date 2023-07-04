package renderer;

import java.util.MissingResourceException;
//import java.util.Vector;

//import javax.imageio.ImageWriter;

import primitives.*;
import java.util.stream.*;

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

	/**
	 * number with integer square for the matrix of points.
	 */
	private int numOfRays = 1;
	/**
	 * flag for adaptive super-sapling
	 */
	private boolean isAdaptive = false;
	/**
	 * an instance of a grid-for aperture
	 */
	InvertedGrid aperture;
	/**
	 * numOfPointsOnAperture (Dof)
	 */
	private int numOfPointsOnAperture = 0;
	/**
	 * Declaring a variable called apertureSize of type double.
	 */
	private double apertureSize = 0;

	/**
	 * focal Plane Distance
	 */
	private double focalDistance = 10;

	/*
	 * number of threads
	 */
	private int threads = 0;
	/*
	 * interval of debug prints option
	 */
	private double print = 0;

	private final int SPARE_THREADS = 2;

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

	/**************** setters ****************/

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
		this.isAdaptive = adp;
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
		this.focalDistance = distance1;
		return this;
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

	/**
	 * setter for multithreading
	 * 
	 * @param threads number of threads
	 * @return the updated camera
	 */
	public Camera setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading parameter must be 0 or bigger");
		else if (threads > 0)
			this.threads = threads;
		else {
			// number of cores less the spare threads is taken
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			if (cores <= 2)
				this.threads = 1;
			else
				this.threads = cores;
		}
		return this;
	}

	/**
	 * interval setter for debug print
	 * 
	 * @param interval the print interval
	 * @return the updated camera
	 */
	public Camera setDebugPrint(double interval) {
		if (interval < 0)
			throw new IllegalArgumentException("print interval must not be negative");
		this.print = interval;
		return this;
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

	/**************** operations ****************/

	/**
	 * calculate the color in a given indexed pixel for beam of rays
	 * 
	 * @param nX numbers of columns
	 * @param nY numbers of rows
	 * @param j  y index of pixel to construct the ray through
	 * @param i  x index of pixel to construct the ray through
	 * @return the color of the beam of rays starts at the camera and go though the
	 *         given pixel
	 */
	private Color castRayBeam(int nX, int nY, int j, int i) {
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

		Grid anti = new Grid((int) Math.sqrt(this.numOfRays), this.distance, rY, up, vIJ, this.location,
				ray -> this.rayTracer.traceRay(ray));
		if (this.numOfRays > 1)
			return this.isAdaptive //
					? anti.superSampling(pIJ)
					: anti.castGridRays();

		double distanceToFocalPoint = pIJ.distance(this.location) * (this.focalDistance / this.distance);
		Point focalPoint = this.location.add(vIJ.scale(distanceToFocalPoint));
		return this.isAdaptive //
				? this.aperture.superSampling(focalPoint)
				: this.aperture.castGridRaysInverted(focalPoint);
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

		Vector vIJ = pIJ.subtract(location).normalize();

		return new Ray(location, vIJ);
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
		if (this.apertureSize > 0 && this.focalDistance == 0)
			throw new MissingResourceException("you must set a distance for focal plane", "Plane", "focal plane");
		if (this.apertureSize > 0)
			this.aperture = new InvertedGrid((int) Math.sqrt(this.numOfPointsOnAperture), 0, this.apertureSize, this.up,
					this.to, this.location, ray -> this.rayTracer.traceRay(ray));

		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();
		PixelManager pixel = new PixelManager(ny, nx, print);
		IntStream.range(0, ny).parallel().forEach(i -> IntStream.range(0, nx).parallel().forEach(j -> {
			this.imageWriter.writePixel(i, j, //
					this.numOfRays > 1 || this.numOfPointsOnAperture > 1 //
							? castRayBeam(nx, ny, i, j) //
							: castRay(i, j, nx, ny));

			pixel.pixelDone();
		}));
		return this;
	}

	/***
	 * calculate the color in a given indexed pixel
	 * 
	 * @param i - x index parameter
	 * @param j - j index parameter
	 * @param nx - the resulution num of rows
	 * @param ny - the resulution num of colums
	 * @return the color of the ray
	 */
	private Color castRay(int i, int j, int nx, int ny) {
				return rayTracer.traceRay(constructRay(nx, ny, i, j));
	}

	/***
	 * prints grid's lines to camera's picture, the lines are in the given color
	 * 
	 * @param interval the size of a pixel
	 * @param color    color to color the lines at
	 * @return the camera
	 */
	public Camera printGrid(int interval, Color color) {
		if (this.imageWriter == null)
			throw new MissingResourceException("imageWriter is missing", "ImageWriter", "imageWriter");
		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();
		for (int i = 0; i < nx; i++)
			for (int j = 0; j < ny; j++)
				if (i % interval == 0 || j % interval == 0)
					this.imageWriter.writePixel(i, j, color);
		return this;
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