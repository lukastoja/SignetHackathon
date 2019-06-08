package Engine;

import java.awt.Image;
import java.util.ArrayList;

public class GridBlock extends BasicElement{
	Grid grid;
	boolean moving = false;
	
	public GridBlock(Grid grid, int x, int y) {
		super(grid.getEngine());
		this.grid = grid;
		this.interSetPosition(x, y);
	}
	
	public void moveTo(int destX, int destY, double speed, ActionCompleted actionCompleted) {
		if(moving)return;
		moving = true;
		EngineThread thread = new EngineThread() {
			float interpolation=0;
			
			@Override
			public void loop(long deltaTime) {
				interpolation += speed * deltaTime/1000.0f;
				
				if(interpolation > 1.0f) {
					interpolation = 1.0f;
					GridBlock.this.interSetPosition(destX, destY);
					moving = false;
					if(actionCompleted != null)actionCompleted.actionCompleted();
					super.stop();
				}
				
				x = origx + interpolation * (destX - origx);
				y = origy + interpolation * (destY - origy);
			}
		};
		grid.core.attachThread(thread);
	}
	
	public void moveAlongPath(ArrayList<Position> path, double speed, ActionCompleted actionCompleted) {
		if(isMoving() == true)return;
		moving = true;
		EngineThread thread = new EngineThread() {
			int trenutnaTocka=0;
			float interpolation=0.0f;
			
			@Override
			public void loop(long deltaTime) {				
				if(interpolation >= 1.0f) {
					interpolation = 0;
					Position tocka = path.get(trenutnaTocka);
					GridBlock.this.interSetPosition(tocka.getX(), tocka.getY());
					trenutnaTocka++;
					if(trenutnaTocka >= path.size()) {
						moving = false;
						if(actionCompleted != null)actionCompleted.actionCompleted();
						super.stop();
						return;
					}
				}else{
					Position tocka = path.get(trenutnaTocka);
					interpolation += speed * deltaTime/1000.0f;
					x = origx + interpolation * (tocka.getX() - origx);
					y = origy + interpolation * (tocka.getY() - origy);
				}
				
			}
			
		};
		this.grid.core.attachThread(thread);
		thread.loop(0);
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	public void interSetPosition(int posx, int posy) {
		if(getGrid() != null) {
			this.getGrid().cache[this.origy][this.origx] = null;
			this.getGrid().cache[posy][posx] = this;
		}
		this.origx = posx;
		this.x = posx;
		this.origy = posy;
		this.y = posy;
	}
	
	public void moveUp(double speed, ActionCompleted actionCompleted) {
		if(y == 0)return;
		if(grid.get(origx, origy-1) != null)return;
		moveTo(origx, origy-1, speed, actionCompleted);
	}
	
	public void moveDown(double speed, ActionCompleted actionCompleted) {
		if(y == grid.rows - 1)return;
		if(grid.get(origx, origy+1) != null)return;
		moveTo(origx, origy+1, speed, actionCompleted);
	}
	
	public void moveLeft(double speed, ActionCompleted actionCompleted) {
		if(x == 0)return;
		if(grid.get(origx-1, origy) != null)return;
		moveTo(origx-1, origy, speed, actionCompleted);
	}
	
	public void moveRight(double speed, ActionCompleted actionCompleted) {
		if(x == grid.cols - 1)return;
		if(grid.get(origx+1, origy) != null)return;
		moveTo(origx+1, origy, speed, actionCompleted);
	}
	
	public boolean isMoving() {
		return moving;
	}

	@Override
	public Position getDrawingPosition() {
		if(getGrid() == null)System.out.println("[Error] Objekt nije u gridu");
		return new Position((int)(x * getGrid().width), (int)(y * getGrid().height));
	}
	
	public Position getPosition() {
		return new Position((int)x, (int)y);
	}

	@Override
	public int getWidth() {
		return img.getWidth(null);
	}

	@Override
	public int getHeight() {
		return img.getHeight(null);
	}

	@Override
	public void remove() {
		super.remove();
		
		this.getGrid().cache[this.origy][this.origx] = null;
	}

	@Override
	public EngineCore getEngine() {
		return getGrid().getEngine();
	}
	
	
}
