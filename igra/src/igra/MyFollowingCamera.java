package igra;

import Engine.BasicElement;
import Engine.FollowingCamera;
import Engine.Position;

public class MyFollowingCamera extends FollowingCamera{
	public int dx, dy;
	
	
	public MyFollowingCamera(BasicElement target) {
		super(target);
	}
	
	public void setCorrection(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		Position pos = super.getPosition();
		pos.x = pos.x + dx;
		pos.y = pos.y + dy;
		return pos;
	}
	
	

}
