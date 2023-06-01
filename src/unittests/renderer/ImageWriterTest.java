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
	private int width = 800;
	private int length = 500;
	private int step = 50;

	/**
	 * Test method for
	 * {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	void testImageWriter() {
		ImageWriter imageWriter = new ImageWriter("testImageWriter", width, length);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < length; j++)
				imageWriter.writePixel(i, j, i % step == 0 || j % step == 0 ? Color.AQUA : Color.Silver);
		imageWriter.writeToImage();
	}

}
