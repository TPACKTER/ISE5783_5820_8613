package unittests.renderer;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import lighting.AmbientLight;
import lighting.PointLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * test calss foe dof
 * 
 * @author Ayala and Tamar
 *
 */
class DepthOfFieldTesr {
	private Scene scene = new Scene("Test scene");

	/***
	 * testing dof
	 */
	@Test
	void dof() {

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
			camera.setImageWriter(new ImageWriter("dof", 600, 600)) //
					.setRayTracer(new RayTracerBasic(scene)) //
				//.renderImage() //
				.setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81)//
				.renderImage() //
				.writeToImage();

	}

	@Test
	void antialaysing() {

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
	
	@Test
	void reg() {

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
	/*
	@Test
	void adaptiveantiandddof() {

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
		camera.setImageWriter(new ImageWriter("adaptiveantiandddof", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setDepthOfField(true).setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81).SetAnti(true, 40).isAdeptive(true)
			.renderImage() //
			.writeToImage();

}
*/
	@Test
	void adaptiveanti() {

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
		camera.setImageWriter(new ImageWriter("adaptiveandanti", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.isAdeptive(true).setNumOfRays(40)//
			.renderImage() //
			.writeToImage();

}
	@Test
	void adaptivedof() {

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
		camera.setImageWriter(new ImageWriter("adaptiveanfdof", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene))
				.setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81)//
				.isAdeptive(true)//
			.renderImage() //
			.writeToImage();

}
	@Test
	void supersamplinganddof() {

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
		camera.setImageWriter(new ImageWriter("antialaysinganddof", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setfocalPlaneDistance(400)//
				.setApertureSize(9).setNumOfPointsOnAperture(81).setNumOfRays(40)//
			.renderImage() //
			.writeToImage();

}
	
	/*
	@Test
	public void reflactiomamdrefrecction() {
		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1))

				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //

				new Sphere(new Point(0, 0, 0), 60d).setEmission(new Color(RED)) // head
						.setMaterial(new Material().setKs(0.15).setShininess(10).setKd(0.2).setKt(0)),
				
				new Plane(new Point(0, 0, -63), new Point(1, 0, -63), new Point(0, 1, -63))
						.setEmission(new Color(30, 30, 30))

						.setMaterial(new Material().setKr(0.5).setGs(40)));
			
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(250, 0, 125)) //
				.setKl(4E-12).setKq(2E-10));

		ImageWriter imageWriter = new ImageWriter("refray", 600, 600);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage() //
				.writeToImage();
	}*/

}


