package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
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
class DepthOfFieldTes {
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
		camera.setImageWriter(new ImageWriter("focus1", 600, 600)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.setDepthOfField(true).setfocalPlaneDistance(400) //
				.renderImage() //
				.writeToImage();

	}

}
