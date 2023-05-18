package primitives;

/***
 * class repesenting the material of bories in scene
 * @author Tamar and Ayala
 *
 */
public class Material {
	/**
	 * diffuse coefficient
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * specular coefficient
	 */
	public Double3 kS = Double3.ZERO;
	/**
	 * the bory shininess
	 */
	public int nShininess = 0;

	/**
	 * setter for kD parameter
	 * @param double3 kD of material
	 * @return the updated material
	 */
	public Material setKD(Double3 kD) {
		this.kD = kD;
		return this;
	}
	
	/**
	 * setter for kD parameter
	 * @param double kD of material
	 * @return the updated material
	 */
	public Material setKD(double kD) {
		this.kD = new Double3(kD);
		return this;
	}
	
	/**
	 * setter for kS parameter
	 * @param double3 kS of material
	 * @return the updated material
	 */
	public Material setKS(Double3 kS) {
		this.kS = kS;
		return this;
	}
	
	/**
	 * setter for kS parameter
	 * @param double kS of material
	 * @return the updated material
	 */
	public Material setKS(double kS) {
		this.kS = new Double3(kS);
		return this;
	}
	

	/**
	 * setter for nShininess parameter
	 * @param nShininess of material
	 * @return the updated material
	 */
	public Material setNShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	

}
