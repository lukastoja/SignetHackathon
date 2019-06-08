package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.ActionCompleted;
import Engine.BasicElement;
import Engine.EngineThread;
import Engine.Grid;
import Engine.GridBlock;

public class PackmanGameIgrac extends GridBlock{
	Image likRavno, likDesno, likLijevo;
	Grid labirintGrid;
	
	public PackmanGameIgrac(Grid grid, Grid labirintGrid, int x, int y) {
		super(grid, x, y);
		this.labirintGrid = labirintGrid;
		/*
		try {
			//likRavno = ImageIO.read(new File("../Assets/lik_ravno_64.png"));
			
			this.setImage(likRavno);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		likRavno = Toolkit.getDefaultToolkit().getImage("../Assets/lik_ravno_64.png");
		this.setImage(likRavno);
	}
	
	

	@Override
	public void moveUp(double speed, ActionCompleted actionCompleted) {
		moveTo(origx, origy-1, speed, actionCompleted);
	}



	@Override
	public void moveDown(double speed, ActionCompleted actionCompleted) {
		moveTo(origx, origy+1, speed, actionCompleted);
	}



	@Override
	public void moveLeft(double speed, ActionCompleted actionCompleted) {
		moveTo(origx-1, origy, speed, actionCompleted);
	}



	@Override
	public void moveRight(double speed, ActionCompleted actionCompleted) {
		moveTo(origx+1, origy, speed, actionCompleted);
	}



	@Override
	public void moveTo(int destX, int destY, double speed, ActionCompleted actionCompleted) {
		BasicElement el = this.labirintGrid.get(destX, destY);
		if(el != null && el instanceof LabirintBlock)return;
		
		if(isMoving())return;
		moving = true;
		EngineThread thread = new EngineThread() {
			float interpolation=0;
			
			@Override
			public void loop(long deltaTime) {
				interpolation += speed * deltaTime/1000.0f;
				
				if(interpolation > 1.0f) {
					interpolation = 1.0f;
					PackmanGameIgrac.this.interSetPosition(destX, destY);
					moving = false;
					if(actionCompleted != null)actionCompleted.actionCompleted();
					super.stop();
				}
				
				x = origx + interpolation * (destX - origx);
				y = origy + interpolation * (destY - origy);
			}
		};
		this.getEngine().attachThread(thread);
	}
	
	

}
