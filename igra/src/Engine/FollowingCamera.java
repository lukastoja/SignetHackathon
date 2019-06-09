package Engine;

public class FollowingCamera extends CameraController{
	BasicElement target;
	
	public FollowingCamera(BasicElement target) {
		this.target = target;
	}
	
	public void follow(BasicElement target) {
		this.target = target;
	}
	
	@Override
	public Position getPosition() {
		Position drawingPosition = target.getDrawingPosition();
		return new Position(drawingPosition.getX() + target.getWidth()/2, drawingPosition.getY() + target.getHeight()/2);
	}

}
