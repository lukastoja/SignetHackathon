package Engine;

public class StaticCamera extends CameraController{
	int x, y;
	
	public StaticCamera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCenter(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Position getPosition() {
		return new Position(x, y);
	}

}
