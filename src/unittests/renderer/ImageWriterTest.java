/**
 * 
 */
package unittests.renderer;

import renderer.*;
import primitives.*;

import org.junit.jupiter.api.Test;

/**
 * Testing Image Writer class
 * 
 * @author Ayala and Tamar
 */
class ImageWriterTest {
int width=800;
int length=500;
int sizeOfPixel=50;

	
	/**
	 * Test method for
	 * {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	void testImageWriter() {
		ImageWriter imageWriter = new ImageWriter("testImageWriter", width, length);

		for (int i = 0; i <  width; i++)
			for (int j = 0; j <length; j++)
				if (i % sizeOfPixel == 0 || j % sizeOfPixel == 0)
					imageWriter.writePixel(i, j, Color.Aqua);
				else
					imageWriter.writePixel(i, j, Color.Silver);

		imageWriter.writeToImage();
	}

}
