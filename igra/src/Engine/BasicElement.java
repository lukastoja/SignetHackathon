package Engine;

import java.awt.Image;

public abstract class BasicElement {
	public int origx, origy;
	public float x, y;
	public boolean removeFlag = false;
	public float transparency=0.0f;
	public float boundingRadius;
	Image img;
	EngineCore engineCore;
	public abstract Position getDrawingPosition();
	public abstract Position getPosition();
	public abstract int getWidth();
	public abstract int getHeight();
	
	public BasicElement(EngineCore engineCoreArg) {
		if(engineCoreArg == null)System.out.println("Arg je null");
		this.engineCore = engineCoreArg;
	}
	
	public EngineCore getEngine() {
		return this.engineCore;
	}
	
	public void remove() {
		this.removeFlag = true;
	}
	
	public void setTransparancy(float val) {
		this.transparency = val;
	}
	
	public void setBoundingRadius(float radius) {
		this.boundingRadius = radius;
	}
	
	public float getBoundingRadius() {
		return this.boundingRadius;
	}
	
	public void setImage(Image img) {
		this.img = img;
	}
	
	public Image getImage() {
		return img;
	}
}
