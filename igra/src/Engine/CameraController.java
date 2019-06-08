package Engine;

public abstract class CameraController {
	EngineCore core;
	float zoom=1.0f;
	
	public void setEngine(EngineCore core) {
		this.core = core;
	}
	
	public EngineCore getEngine() {
		return core;
	}
	
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public float getZoom() {
		return this.zoom;
	}
	
	public abstract Position getPosition();
}
