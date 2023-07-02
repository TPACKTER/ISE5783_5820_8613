package unittests.renderer;

import static java.awt.Color.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.*;

import org.junit.jupiter.api.Test;



/**
 * testing depth of field, anti aliasing, and adaptive super sampling
 * 
 * @author Ayala and Tamar
 *
 */
class MiniProgerctTest {
	private Scene scene = new Scene("Test scene");
	private  Scene scene1 = new Scene("Test scene");
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
			.setVPSize(150, 150).setVPDistance(1000);
	
			

	private static final int SHININESS = 301;
	private static final double KD = 0.5;


	private static final double KS = 0.5;


	private final Color sphereColor = new Color(BLUE).reduce(2);

	private final Point sphereCenter = new Point(0, 0, -50);
	private static final double SPHERE_RADIUS = 50d;



	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light

	private final Geometry sphere = new Sphere(sphereCenter, SPHERE_RADIUS).setEmission(sphereColor)
			.setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
	
	/***
	 * testing depth of field
	 */
	@Test
	void dof() {
		
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.geometries.add(
				new Tube(5, new Ray(new Point(1000, 10, 20), new Vector(-1, 0, 0.1))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(800, 10, 40), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
						new Sphere(new Point(800, 800, 400), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(900, 10, 30), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1000, 10, 20), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1100, 10, 10), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1200, 10, 0), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1300, 10, -10), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1400, 10, -20), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)));
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(1300, 20, 20)) //
				.setKl(4E-12).setKq(2E-10));

		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(200, 200).setVPDistance(800);// 800
		camera.setImageWriter(new ImageWriter("dof", 600, 600)) //
		.setRayTracer(new RayTracerBasic(scene))
				.setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81)//
				.renderImage() //
				.writeToImage();

	}

	/**
	 * tasting antialAlysing
	 */
	@Test
	void antialAlysing() {

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.geometries.add(
				new Tube(5, new Ray(new Point(1000, 10, 20), new Vector(-1, 0, 0.1))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(800, 10, 40), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(900, 10, 30), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1000, 10, 20), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1100, 10, 10), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1200, 10, 0), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1300, 10, -10), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1400, 10, -20), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)));
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(1300, 20, 20)) //
				.setKl(4E-12).setKq(2E-10));

		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(200, 200).setVPDistance(800);// 800
		camera.setImageWriter(new ImageWriter("antialaysing", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setNumOfRays(40)//
				.renderImage() //
				.writeToImage();

	}

	/**
	 * test for regular picture
	 */
	@Test
	void regular() {

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.geometries.add(
				new Tube(5, new Ray(new Point(1000, 10, 20), new Vector(-1, 0, 0.1))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(800, 10, 40), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(900, 10, 30), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1000, 10, 20), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1100, 10, 10), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1200, 10, 0), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1300, 10, -10), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1400, 10, -20), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)));
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(1300, 20, 20)) //
				.setKl(4E-12).setKq(2E-10));

		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(200, 200).setVPDistance(800);// 800
		camera.setImageWriter(new ImageWriter("reg", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();

	}

	/**
	 * test for adaptive depth of field
	 */
	@Test
	void adaptiveDof() {

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.geometries.add(
				new Tube(5, new Ray(new Point(1000, 10, 20), new Vector(-1, 0, 0.1))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(800, 10, 40), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(900, 10, 30), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1000, 10, 20), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1100, 10, 10), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1200, 10, 0), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1300, 10, -10), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1400, 10, -20), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)));
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(1300, 20, 20)) //
				.setKl(4E-12).setKq(2E-10));

		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(200, 200).setVPDistance(800);// 800
		camera.setImageWriter(new ImageWriter("adaptiveDof", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81).isAdeptive(true)//
				.renderImage() //
				.writeToImage();

	}

	/**
	 * tasting adeptiveAntialAlysing
	 */
	@Test
	void adeptiveAntialAlysing() {

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.geometries.add(
				new Tube(5, new Ray(new Point(1000, 10, 20), new Vector(-1, 0, 0.1))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(800, 10, 40), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(900, 10, 30), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1000, 10, 20), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1100, 10, 10), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1200, 10, 0), 10).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1300, 10, -10), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.4)),
				new Sphere(new Point(1400, 10, -20), 10).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.4)));
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(1300, 20, 20)) //
				.setKl(4E-12).setKq(2E-10));

		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(200, 200).setVPDistance(800);// 800
		camera.setImageWriter(new ImageWriter("adeptiveAntialaysing", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setNumOfRays(40)//
				.isAdeptive(true)
				.renderImage() //
				.writeToImage();

	}

}
