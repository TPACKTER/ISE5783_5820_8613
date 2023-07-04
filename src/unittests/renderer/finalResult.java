package unittests.renderer;

import static java.awt.Color.*;
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
import renderer.*;
import scene.*;
/**
 * 
 * @author Ayala AND Tamar
 * 
 */
public class finalResult {
//	private final ImageWriter imageWriter = new ImageWriter("AngryBirdsButBetter", 800, 800);

	private final Camera camera = new Camera(new Point(0,-700,0), new Vector(0,700,0),new Vector(-700, 0, 0).crossProduct(new Vector(0,-700,0))) //
			.setVPDistance(1000).setVPSize(200, 200) //
			.setMultithreading(3).setDebugPrint(0.1);

	private final Scene scene = new Scene("Test scene");

	private static final Color color = new Color(200, 0, 0);
	private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);
/**
 * testing Adaptive DoF
 */
	@Test
	public void chesDofAddeptive() {
	
		String pathOfLeaves ="C:/Users/User/Downloads/Chess set1.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(pathOfLeaves);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			
			scene.geometries.add(g);
		
		}	
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("ches dof adeptive", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setApertureSize(15).setNumOfPointsOnAperture(513).setfocalPlaneDistance(700).isAdeptive(true)//
				.renderImage() //
				.writeToImage(); //
	}
	/*
	 * testing Dof
	 */
	@Test
	public void chesDof() {

		String pathOfLeaves ="C:/Users/User/Downloads/Chess set1.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(pathOfLeaves);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
		
			scene.geometries.add(g);
			
		}	
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("chesDof", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setApertureSize(9).setNumOfRays(513).setfocalPlaneDistance(700)//
				.renderImage() //
				.writeToImage(); //
	}
	/**
	 * testing another adaptive DoF
	 */
	@Test
	public void ches1DofAddeptive() {
	
		String ches1 ="C:/Users/User/Downloads/ch1.stl";
		String ches2 ="C:/Users/User/Downloads/ch2.stl";
		String ches3 ="C:/Users/User/Downloads/ch3.stl";
		String ches4 ="C:/Users/User/Downloads/ch4.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(ches1);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,0,43)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}	
		
		 geo1 = convertSTLToTriangles(ches2);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches3);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(75,0,130)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches4);
			for (Intersectable g : geo1) 
			{ 
				((Triangle)g).setEmission(color).setMaterial(mat);
				
				scene.geometries.add(g);
				
			}
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-7, 5, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(10, -10, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(15, -10, 10)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, -1, 20)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-15, -1, 20)));

		ImageWriter imageWriter = new ImageWriter("ches1 dof adeptive", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setApertureSize(15).setNumOfPointsOnAperture(513).setfocalPlaneDistance(700).isAdeptive(true)//
				.renderImage() //
				.writeToImage(); //
	}
/**
 * testing another Dof 
 */
	@Test
	public void ches1Dop() {
	
		String ches1 ="C:/Users/User/Downloads/ch1.stl";
		String ches2 ="C:/Users/User/Downloads/ch2.stl";
		String ches3 ="C:/Users/User/Downloads/ch3.stl";
		String ches4 ="C:/Users/User/Downloads/ch4.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(ches1);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,0,43)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}	
		
		 geo1 = convertSTLToTriangles(ches2);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches3);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(75,0,130)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches4);
			for (Intersectable g : geo1) 
			{ 
				((Triangle)g).setEmission(color).setMaterial(mat);
				
				scene.geometries.add(g);
				
			}
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-7, 5, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(10, -10, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(15, -10, 10)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, -1, 20)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-15, -1, 20)));

		ImageWriter imageWriter = new ImageWriter("ches1 dof ", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setApertureSize(15).setNumOfPointsOnAperture(513).setfocalPlaneDistance(700)//
				.renderImage() //
				.writeToImage(); //
	}
	
/**
 * testing anti-aliasing 
 */
	@Test
	public void ches1anti() {
	
		String ches1 ="C:/Users/User/Downloads/ch1.stl";
		String ches2 ="C:/Users/User/Downloads/ch2.stl";
		String ches3 ="C:/Users/User/Downloads/ch3.stl";
		String ches4 ="C:/Users/User/Downloads/ch4.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(ches1);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,0,43)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}	
		
		 geo1 = convertSTLToTriangles(ches2);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches3);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(75,0,130)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches4);
			for (Intersectable g : geo1) 
			{ 
				((Triangle)g).setEmission(color).setMaterial(mat);
				
				scene.geometries.add(g);
				
			}
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-7, 5, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(10, -10, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(15, -10, 10)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, -1, 20)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-15, -1, 20)));

		ImageWriter imageWriter = new ImageWriter("ches1 anti", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setNumOfRays(513)//
				.renderImage() //
				.writeToImage(); //
	}
	/**
	 * testing anti-super
	 */
	@Test
	public void ches1antisuper() {
	
		String ches1 ="C:/Users/User/Downloads/ch1.stl";
		String ches2 ="C:/Users/User/Downloads/ch2.stl";
		String ches3 ="C:/Users/User/Downloads/ch3.stl";
		String ches4 ="C:/Users/User/Downloads/ch4.stl";
	
	
		
		List<Triangle> geo1 = convertSTLToTriangles(ches1);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,0,43)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}	
		
		 geo1 = convertSTLToTriangles(ches2);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(0,100,0)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches3);
		for (Intersectable g : geo1) 
		{ 
			((Triangle)g).setEmission(new Color(75,0,130)).setMaterial(mat);
			
			scene.geometries.add(g);
			
		}
		 geo1 = convertSTLToTriangles(ches4);
			for (Intersectable g : geo1) 
			{ 
				((Triangle)g).setEmission(color).setMaterial(mat);
				
				scene.geometries.add(g);
				
			}
		scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-7, 5, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(10, -10, -5)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(15, -10, 10)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(1, -1, 20)));
				scene.lights.add(new DirectionalLight(Color.Mustred, new Vector(-15, -1, 20)));
				
				

		ImageWriter imageWriter = new ImageWriter("ches1 anti and super", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setNumOfRays(513).isAdeptive(true)//
				.renderImage() //
				.writeToImage(); //
	}
	
	/**
	 * gets a path of STL file and converting it to triangles
	 * @param filePath path of file to convert
	 * @return List of Triangles
	 */
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
                	System.out.println("error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return triangles;
    }


}
