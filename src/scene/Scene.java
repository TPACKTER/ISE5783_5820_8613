package scene;

import lighting.AmbientLight;
import primitives.Color;

import geometries.*;

/***
 * 
 * @author Ayala and Tamar class represnting scene and contains all scene
 *         relevant data
 */
public class Scene {
	/***
	 * name of scene
	 */
	public final String name;
	/***
	 * background color
	 */
	public Color background;
	/***
	 * the ambient light of scene
	 */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/***
	 * the geometries in scene
	 */
	public Geometries geometries = new Geometries();

	/**
	 * constructor for scene based on name of scene
	 * 
	 * @param name to name the current scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * setter for background color of scene
	 * 
	 * @param color of scene
	 * @return the updated scene
	 */
	public Scene setBackground(Color color) {
		this.background = color;
		return this;
	}

	/**
	 * setter for AmbientLight of scene
	 * 
	 * @param ambientLight of scene
	 * @return the updated scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/***
	 * setter for geometries in scene
	 * 
	 * @param geometries in the scene
	 * @return the updated scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
}
