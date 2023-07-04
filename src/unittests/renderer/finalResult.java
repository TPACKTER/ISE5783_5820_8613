package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import lighting.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
import static java.awt.Color.*;

public class finalResult {
	private final ImageWriter imageWriter = new ImageWriter("AngryBirdsButBetter", 800, 800);

	private final Camera camera = new Camera(new Point(0,-1000,0), new Vector(0,1000,0),new Vector(-1000, 0, 0).crossProduct(new Vector(0,1000,0))) //
			.setVPDistance(1000).setVPSize(200, 200) //
			.setImageWriter(imageWriter) //
			.setMultithreading(3).setDebugPrint(0.1);

	private final Scene scene = new Scene("Test scene");

	private static final Color color = new Color(200, 0, 0);
	private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);

	@Test
	public void AngryBirdsButBetterTest() {
		String pathOfPattal = "C:/Users/S/eclipse-workspace/ISE5873_5820_8613/flower up1.stl";
		String pathOfLeaves = "C:/Users/S/eclipse-workspace/ISE5873_5820_8613/flower doun1.stl";
		List<Triangle> geo = convertSTLToTriangles(pathOfPattal);
		for (Intersectable g : geo) 
		{ 
			((Triangle)g).setEmission(color).setMaterial(mat);
			scene.geometries.add(g);
		}
		List<Triangle> geo1 = convertSTLToTriangles(pathOfLeaves);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			try {
			scene.geometries.add(g);
			}
			catch(Exception e) {

			}
		}	
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("AngryBirdsButBetter", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setApertureSize(9).setNumOfRays(81).setfocalPlaneDistance(1000).isAdeptive(true)//
				.renderImage() //
				.writeToImage(); //
	}
	
    private static List<Triangle> convertSTLToTriangles(String filePath) {
        List<Triangle> triangles = new LinkedList<>();

        try (RandomAccessFile binaryFile = new RandomAccessFile(filePath, "r")) {
            byte[] header = new byte[80];
            binaryFile.read(header); // Read the binary STL file header (80 bytes)

            byte[] numTrianglesBytes = new byte[4];
            binaryFile.read(numTrianglesBytes); // Read the number of triangles (4 bytes)
            int numTriangles = ByteBuffer.wrap(numTrianglesBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();


            for (int i = 0; i < numTriangles; i++) {


                byte[] normalBytes = new byte[12];
                binaryFile.read(normalBytes); // Read the normal vector (12 bytes)
                float[] normal = new float[3];
                ByteBuffer.wrap(normalBytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(normal);

                float[][] vertices = new float[3][3];
                for (int j = 0; j < 3; j++) {
                    byte[] vertexBytes = new byte[12];
                    binaryFile.read(vertexBytes); // Read each vertex of the triangle (12 bytes per vertex)
                    ByteBuffer.wrap(vertexBytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(vertices[j]);
                }

                byte[] garbage = new byte[2];
                binaryFile.read(garbage); // Read the garbage data (2 bytes)
                try {
                // Create a triangle using the extracted vertex coordinates
                Triangle triangle = new Triangle(
                        new Point(vertices[0][0], vertices[0][1], vertices[0][2]),
                        new Point(vertices[1][0], vertices[1][1], vertices[1][2]),
                        new Point(vertices[2][0], vertices[2][1], vertices[2][2])
                );

                triangles.add(triangle);
                }
                catch(Exception e) {
                	System.out.println("1");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return triangles;
    }

}
