package scene;

import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

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
	 * background color if not changed = black
	 */
	public Color background = Color.BLACK;
	/***
	 * the ambient light of scene
	 */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/***
	 * the geometries in scene
	 */
	public Geometries geometries = new Geometries();

	/**
	 * list of light sources in the scene
	 */
	public  List<LightSource> lights =  new LinkedList<>();
	
	
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
	/**
	 * setter for lights
	 * 
	 * @param lights of scene
	 * @return the updated scene
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}
}
