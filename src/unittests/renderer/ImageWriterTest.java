/**
 * 
 */
package unittests.renderer;
import renderer.*;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing Image Writer class
 * @author Ayala and Tamar
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	void testImageWriter() {
		ImageWriter imageWriter=new ImageWriter("testImageWriter",800,500);
	
	for (int i =0;i<imageWriter.getNx();i++)
		for(int j=0;j<imageWriter.getNy(); j++)
			if(i%50==0||j%50==0)
				  imageWriter.writePixel(i, j, Color.Aqua);
			else 
	        	imageWriter.writePixel(i, j, Color.Silver);
	
	imageWriter.writeToImage();
	}

}
