package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.ActionCompleted;
import Engine.CollisionGroup;
import Engine.Grid;
import Engine.GridBlock;

public class PackmanGameJedac extends GridBlock{
	public CollisionGroup grp;
	Image imgGore, imgDolje, imgLijevo, imgDesno;
	int lives;
	
	public PackmanGameJedac(Grid grid, int x, int y) {
		super(grid, x, y);
		imgGore = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/jedac_gore_64.png");
		imgLijevo = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		imgDesno = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		imgDolje = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/jedac_dolje_64.png");
		this.setImage(imgGore);
	}

	
	@Override
	public void moveUp(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgGore);
		super.moveUp(speed, actionCompleted);
	}



	@Override
	public void moveDown(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgDolje);
		super.moveDown(speed, actionCompleted);
	}
	
	@Override
	public void moveLeft(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgGore);
		super.moveLeft(speed, actionCompleted);
	}


	@Override
	public void moveRight(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgGore);
		super.moveRight(speed, actionCompleted);
	}

	void setLives(int val) {
		this.lives = val;
	}
	
	int getLives() {
		return this.lives;
	}
}
