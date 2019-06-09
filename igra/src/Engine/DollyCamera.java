package Engine;

public class DollyCamera extends CameraController{
	Position pos = new Position(0, 0);
	Position startPos = new Position(0, 0);
	Position endPos = new Position(0, 0);
	float startZoom = 1.0f;
	float endZoom = 1.0f;
	float interpolation = 0.0f;
	boolean inTransition=false;
	float transitionSpeed;
	ActionCompleted listener;
	
	public void setStartPosition(int x, int y) {
		startPos.x = x;
		startPos.y = y;
	}
	
	public void setEndPosition(int x, int y) {
		endPos.x = x;
		endPos.y = y;
	}
	
	public void setStartZoom(float zoom) {
		this.startZoom = zoom;
	}
	
	public void setEndZoom(float zoom) {
		this.endZoom = zoom;
	}
	
	public void setTransitionCompletedListener(ActionCompleted listener) {
		this.listener = listener;
	}
	
	@Override
	public Position getPosition() {
		pos.x = (int) (startPos.getX() * (1.0f-interpolation) + endPos.getX() * interpolation);
		pos.y = (int) (startPos.getY() * (1.0f-interpolation) + endPos.getY() * interpolation);
		return pos;
	}

	@Override
	public float getZoom() {
		return startZoom * (1.0f - interpolation) + endZoom * interpolation;
	}
	
	public void startTransition(float speed) {
		if(this.inTransition)return;
		this.inTransition = true;
		this.transitionSpeed = speed;
		
		TransitionEffect transitionEffect = new TransitionEffect();
		this.getEngine().attachThread(transitionEffect);
	}
	
	private class TransitionEffect extends EngineThread{

		@Override
		public void loop(long deltaTime) {
			interpolation += transitionSpeed * deltaTime/1000.0f;
			if(interpolation >= 1.0f) {
				interpolation = 1.0f;
				inTransition=false;
				if(listener != null)listener.actionCompleted();
				super.stop();
				return;
			}
		}
	}

}
