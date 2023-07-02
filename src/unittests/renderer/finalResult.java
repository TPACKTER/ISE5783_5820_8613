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
import lighting.AmbientLight;
import lighting.DirectionalLight;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class finalResult {
	private final Scene scene1 = new Scene("Test scene");

	private final Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
			.setVPSize(150, 150).setVPDistance(1000);

	private static final int SHININESS = 301;
	private static final double KD = 0.5;
	private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

	private static final double KS = 0.5;
	private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

	private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);

	@Test
	public void airBaloonTest() {
		String path ="C:/Users/S/Desktop/air balloon.stl";
		List<Triangle> geo = convertSTLToTriangles(path);
		for (Intersectable g : geo) 
		{ 
			scene1.geometries.add(g);
		}		
		scene1.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("airBaloonTest", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
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
