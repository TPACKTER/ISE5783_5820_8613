package scene;
import lighting.AmbientLight;
import primitives.Color;

import java.util.concurrent.Flow.Publisher;

import org.junit.validator.PublicClassValidator;

import geometries.*;
public class Scene {
    public String name;
    public Color background ;
    public AmbientLight ambientLight= AmbientLight.NONE;
    public Geometries geometries =new Geometries ();
    
   public Scene (String name) {
	   this.name=name;
   }
 public Scene setBackground(Color color) {
	 this.background=color;
	 return this;
 }
 
 public Scene setAmbientLight(AmbientLight ambientLight) {
	 this.ambientLight=ambientLight;
	 return this;
 }
 public Scene setGeometries(Geometries geometries) {
	 this.geometries=geometries;
	 return this;
 }
}
