package primitives;

/***
 * class repesenting the material of bories in scene
 * 
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
	 * the body's shininess
	 */
	public int nShininess = 0;
	/**
	 * the body's transparency
	 */
	 public Double3 kT=Double3.ZERO;
	 /**
	  * the body's reflection
	  */
	 public Double3 kR=Double3.ZERO;
	 

		/**
		 * setter for kT parameter
		 * 
		 * @param kT of material
		 * @return the updated material
		 */
		public Material setKt(Double3 kT) {
			this.kT = kT;
			return this;
		}
		/**
		 * setter for kR parameter
		 * 
		 * @param kR of material
		 * @return the updated material
		 */
		public Material setKr(Double3 kR) {
			this.kR = kR;
			return this;
		}
		/**
		 * setter for kT parameter
		 * 
		 * @param kT of material
		 * @return the updated material
		 */
		public Material setKt(double kT) {
			this.kT = new Double3(kT);
			return this;
		}
		/**
		 * setter for kR parameter
		 * 
		 * @param kR of material
		 * @return the updated material
		 */
		public Material setKr(double kR) {
			this.kR = new Double3(kR);
			return this;
		}
	/**
	 * setter for kD parameter
	 * 
	 * @param kD of material
	 * @return the updated material
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * setter for kD parameter
	 * 
	 * @param kD of material
	 * @return the updated material
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * setter for kS parameter
	 * 
	 * @param kS of material
	 * @return the updated material
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * setter for kS parameter
	 * 
	 * @param kS of material
	 * @return the updated material
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * setter for nShininess parameter
	 * 
	 * @param nShininess of material
	 * @return the updated material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
