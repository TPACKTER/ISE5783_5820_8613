package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;

import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a bird,shoes affect of reflaction and refrection
	 */
	@Test
	public void TransparentReflactionAngryBirdsTest() {
		Camera camera = new Camera(new Point(1600, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1))

				.setVPSize(200, 200).setVPDistance(1000);
		// points for fist crest
		Point a = new Point(-10, -10, 80);
		Point b = new Point(-10, 10, 80);
		Point c = new Point(-20, 10, 80);
		Point d = new Point(-20, -10, 80);

		Point a1 = new Point(1, -8, 75);
		Point b1 = new Point(1, 8, 75);
		Point c1 = new Point(-6, 8, 75);
		Point d1 = new Point(-6, -8, 75);

		Point a2 = new Point(8, -5, 70);
		Point b2 = new Point(8, 5, 70);
		Point c2 = new Point(2, 5, 70);
		Point d2 = new Point(2, -5, 70);
////points for secound crest	
		Point aa = new Point(-10, 80, 10);
		Point bb = new Point(-10, 80, -10);
		Point cc = new Point(-20, 80, -10);
		Point dd = new Point(-20, 80, 10);

		Point aa1 = new Point(1, 75, -8);
		Point bb1 = new Point(1, 75, 8);
		Point cc1 = new Point(-6, 75, 8);
		Point dd1 = new Point(-6, 75, -8);

		Point aa2 = new Point(8, 70, -5);
		Point bb2 = new Point(8, 70, 5);
		Point cc2 = new Point(2, 70, 5);
		Point dd2 = new Point(2, 70, -5);
//points for third crest
		Point aaa = new Point(-10, -80, 10);
		Point bbb = new Point(-10, -80, -10);
		Point ccc = new Point(-20, -80, -10);
		Point ddd = new Point(-20, -80, 10);

		Point aa11 = new Point(1, -75, -8);
		Point bb11 = new Point(1, -75, 8);
		Point cc11 = new Point(-6, -75, 8);
		Point dd11 = new Point(-6, -75, -8);

		Point aa21 = new Point(8, -70, -5);
		Point bb21 = new Point(8, -70, 5);
		Point cc21 = new Point(2, -70, 5);
		Point dd21 = new Point(2, -70, -5);
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //

				new Sphere(new Point(0, 0, 0), 60d).setEmission(new Color(RED)) // head
						.setMaterial(new Material().setKs(0.15).setShininess(10).setKd(0.2).setKt(0)),
				// right eye
				new Sphere(new Point(60, 20, 20), 15).setEmission(new Color(WHITE)) //
						.setMaterial(new Material().setKd(0.1).setKs(0).setShininess(10).setKt(0)),
				// Pupil
				new Sphere(new Point(75, 20, 20), 5).setEmission(new Color(BLACK))
						.setMaterial(new Material().setKd(0.1).setKs(0).setShininess(10).setKt(0)),
				// left eye
				new Sphere(new Point(60, -20, 20), 15).setEmission(new Color(WHITE))
						.setMaterial(new Material().setKd(0.1).setKs(0).setShininess(10).setKt(0)),
				// Pupil
				new Sphere(new Point(75, -20, 20), 5).setEmission(new Color(BLACK))
						.setMaterial(new Material().setKd(0.1).setKs(0).setShininess(10).setKt(0)),
				// help triangle
				new Triangle(new Point(60, -15, -3), new Point(60, 15, -3), new Point(60, 0, -20)),
				new Triangle(new Point(60, -15, -3), new Point(60, 15, -3), new Point(60, 0, 20)),
				// upper beak
				new Triangle(new Point(60, -15, -3), new Point(60, 15, -3), new Point(80, 0, 10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				new Triangle(new Point(60, -15, -3), new Point(60, 0, 20), new Point(80, 0, 10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				new Triangle(new Point(60, 0, 20), new Point(60, 15, -3), new Point(80, 0, 10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				// Under beak
				new Triangle(new Point(60, -15, -3), new Point(60, 15, -3), new Point(80, 0, -10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				new Triangle(new Point(60, -15, -3), new Point(60, 0, -20), new Point(80, 0, -10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				new Triangle(new Point(60, 0, -20), new Point(60, 15, -3), new Point(80, 0, -10))
						.setEmission(new Color(225, 175, 0))
						.setMaterial(new Material().setKd(0.5).setKs(10).setShininess(10).setKt(0)),
				// right eyebrow
				new Polygon(new Point(75, 5, 28), new Point(75, 35, 30), new Point(75, 35, 45), new Point(75, 5, 35)),
				// left eyebrow
				new Polygon(new Point(75, -5, 28), new Point(75, -35, 30), new Point(75, -35, 45),
						new Point(75, -5, 35)),
				// crest
//0
				new Polygon(a, b, c, d).setEmission(new Color(GREEN)).setMaterial(new Material().setKd(0.05)),
				new Polygon(a, b, new Point(4, 0, 55)).setEmission(new Color(GREEN)),
				new Polygon(c, d, new Point(4, 0, 55)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(b, d, new Point(4, 0, 55)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(c, a, new Point(4, 0, 55)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 1
				new Polygon(a1, b1, c1, d1).setMaterial(new Material().setKd(0.1).setKs(0.1)),
				new Polygon(a, b, new Point(4, 0, 55)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(c1, d1, new Point(4, 0, 55)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(b1, d1, new Point(4, 0, 55)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(c1, a1, new Point(4, 0, 55)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 2
				new Polygon(a2, b2, c2, d2).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(a2, b2, new Point(4, 0, 55)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(c2, d2, new Point(4, 0, 55)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(b2, d2, new Point(4, 0, 55)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(c2, a2, new Point(4, 0, 55)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// right wing
//0
				new Polygon(aa, bb, cc, dd).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aa, bb, new Point(4, 55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc, dd, new Point(4, 55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bb, cc, new Point(4, 55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc, aa, new Point(4, 55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 1
				new Polygon(aa1, bb1, cc1, dd1).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aa1, bb1, new Point(4, 55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc1, dd1, new Point(4, 55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bb1, dd1, new Point(4, 55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc1, aa1, new Point(4, 55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 2
				new Polygon(aa2, bb2, cc2, dd2).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aa2, bb2, new Point(4, 55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc2, dd2, new Point(4, 55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bb2, dd2, new Point(4, 55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc2, aa2, new Point(4, 55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// left wing
				// 0
				new Polygon(aaa, bbb, ccc, ddd).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aaa, bbb, new Point(4, -55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(ccc, ddd, new Point(4, -55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bbb, ddd, new Point(4, -55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(ccc, aaa, new Point(4, -55, 0)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 1
				new Polygon(aa11, bb11, cc11, dd11).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aa11, bb11, new Point(4, -55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc11, dd11, new Point(4, -55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bb11, dd11, new Point(4, -55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc11, aa11, new Point(4, -55, 0)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.05).setKs(0.1)),
				// 2
				new Polygon(aa21, bb21, cc21, dd21).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(aa21, bb21, new Point(4, -55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc21, dd21, new Point(4, -55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(bb21, dd21, new Point(4, -55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1)),
				new Polygon(cc21, aa21, new Point(4, -55, 0)).setMaterial(new Material().setKd(0.05).setKs(0.1))
				// plane
				,
				new Plane(new Point(0, 0, -63), new Point(1, 0, -63), new Point(0, 1, -63))
						.setEmission(new Color(30, 30, 30))

						.setMaterial(new Material().setKr(1)),
				new Sphere(new Point(0, 80, 20), 20d).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.2).setKs(0).setShininess(5).setKt(1)),
				new Tube(2, new Ray(new Point(0, 80, 20), new Vector(1, 1, -0.5))).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKr(1)));

		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(250, 0, 125)) //
				.setKl(4E-12).setKq(2E-10));

		ImageWriter imageWriter = new ImageWriter("angryBirdsTest", 600, 600);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage() //
				.writeToImage();
	}

}
