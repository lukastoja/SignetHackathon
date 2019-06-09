package Engine;

public class CenterCamera extends CameraController{

	@Override
	public Position getPosition() {
		EngineCore core = this.getEngine();
		return new Position(core.getWidth()/2, core.getHeight()/2);
	}

}
