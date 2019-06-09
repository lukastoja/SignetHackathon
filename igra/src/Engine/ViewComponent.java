package Engine;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public abstract class ViewComponent{
	EngineCore core;
	public int origx, origy;
	public float x, y;
	public float distanceFromCamera=1.0f;
	
	public ViewComponent(EngineCore engineCore) {
		this.core = engineCore;
	}
	
	public Position getDrawingPosition() {
		return new Position((int)x, (int)y);
	}
	public Position getPosition() {
		return new Position(origx, origy);
	}
	
	public void setPosition(int x, int y) {
		this.origx = (int) (this.x = x);
		this.origy = (int) (this.y = y);
	}
	
	public EngineCore getEngine() {
		return this.core;
	}
	
	public void setDistanceFromCamera(float distanceFromCamera) {
		this.distanceFromCamera = distanceFromCamera;
	}
	
	public float getDistanceFromCamera() {
		return this.distanceFromCamera;
	}
	
	public abstract void draw(Graphics2D g2d, JFrame frame, CameraController controller);
}
