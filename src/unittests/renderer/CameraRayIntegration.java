package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;

/***
 * integration testing camera &amp; ray through barriers
 * 
 * @author Ayala and Tamar
 *
 */
class CameraRayIntegration {
	/**
	 * interaction testing between sphere and rays made by camera
	 */
	@Test
	void CameraRayIntegrationSphere() {
		Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);

		// TC01: the sphere is in front of the view plane(2).
		CameraRayIntegrations(cam, new Sphere(new Point(0, 0, -3), 1), 2);

		// TC02: the view plane is inside the sphere, all rays should intersect
		// twice(18).
		CameraRayIntegrations(cam, new Sphere(new Point(0, 0, -3), 2.5), 18);

		// TC03: the view plane cross the sphere (10).
		CameraRayIntegrations(cam, new Sphere(new Point(0, 0, -2.5), 2), 10);

		// TC04: the camera is inside the sphere,all rays should intersect only once(9).
		CameraRayIntegrations(cam, new Sphere(new Point(0, 0, -1.1), 4), 9);

		// TC05: the sphere is behind the camera , no ray should intersect(0).
		CameraRayIntegrations(cam, new Sphere(new Point(0, 0, 1), 0.5), 0);
	}

	/***
	 * interaction testing between plane and rays made by camera
	 */
	@Test
	void CameraRayIntegrationPlane() {
		Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);

		// TC01:Plane is parallel to view plain(9)
		CameraRayIntegrations(cam, new Plane(new Point(0, 1, 2), new Point(1, 0, 2), new Point(0, 0, 2)), 9);

		// TC02:Plane is not parallel -tilted a bit towards the view plain(9)
		CameraRayIntegrations(cam, new Plane(new Point(0, 1, 1.75), new Point(1, 0, 2), new Point(0, 0, 2)), 9);

		// TC02:Plane is not parallel -tilted (big tilted ) towards the view plain(6)
		CameraRayIntegrations(cam, new Plane(new Point(0, 1, 4), new Point(1, 0, 2), new Point(0, 0, 2)), 6);
	}

	/***
	 * interaction testing between triangle and rays made by camera
	 */
	@Test
	void CameraRayIntegrationTryangle() {
		Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);

		// TC01:The triangle is Little (1)
		CameraRayIntegrations(cam, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 1);

		// TC02:The triangle is bigger-and go out through the view plane (1)
		CameraRayIntegrations(cam, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 2);
	}

	/***
	 * get a geometry, camera and expected number of intersections and checks
	 * whether the expect number is what we really get.
	 * 
	 * @param cam camera to shut rays from
	 * @param geo geometry to find interactions at
	 * @param res the expected number of intersections
	 */
	void CameraRayIntegrations(Camera cam, Geometry geo, int res) {
		int numInterctions = 0;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				Ray pixelRay = cam.constructRay(3, 3, j, i);
				numInterctions += geo.findIntersections(pixelRay) == null ? 0 : geo.findIntersections(pixelRay).size();
			}
		}
		assertEquals(res, numInterctions, "CameraRayIntegration");
	}


}
