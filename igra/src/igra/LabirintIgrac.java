package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.ActionCompleted;
import Engine.Grid;
import Engine.GridBlock;

public class LabirintIgrac extends GridBlock{
	Image imgRavno, imgDesno, imgLijevo, imgDolje;
	
	public LabirintIgrac(Grid grid, int x, int y) {
		super(grid, x, y);
		imgRavno = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/lik_odozgo_gore_64.png");
		imgLijevo = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/lik_odozgo_lijevo_64.png");
		imgDesno = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/lik_odozgo_desno_64.png");
		imgDolje = Toolkit.getDefaultToolkit().getImage("../Assets/xcf/lik_odozgo_dolje_64.png");
		
		//Image img = Toolkit.getDefaultToolkit().getImage("../Assets/lik_desno_50x50.png");
		this.setImage(imgRavno);
	}

	@Override
	public void moveUp(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgRavno);
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
		this.setImage(imgLijevo);
		super.moveLeft(speed, actionCompleted);
	}



	@Override
	public void moveRight(double speed, ActionCompleted actionCompleted) {
		// TODO Auto-generated method stub
		this.setImage(imgDesno);
		super.moveRight(speed, actionCompleted);
	}
}
