package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.ActionCompleted;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;

public class DiggingGameIgrac extends GridBlock{
	Grid zemljaGrid;
	Image imgRavno, imgDesno, imgLijevo; 
	
	
	public DiggingGameIgrac(Grid grid, Grid zemljaGrid, int x, int y) {
		super(grid, x, y);
		
		this.zemljaGrid = zemljaGrid;
		imgRavno = Toolkit.getDefaultToolkit().getImage("../Assets/lik_ravno_40.jpg");
		imgLijevo = Toolkit.getDefaultToolkit().getImage("../Assets/lik_lijevo_40.jpg");
		imgDesno = Toolkit.getDefaultToolkit().getImage("../Assets/lik_desno_40.jpg");
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
		this.setImage(imgRavno);
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



	@Override
	public void moveTo(int destX, int destY, double speed, ActionCompleted actionCompleted) {		
		GridBlock blk = zemljaGrid.get(destX, destY);
		if(blk != null && blk instanceof DiggingGameKamen)return;
		if(blk != null && blk instanceof DiggingGameZid)return;
		super.moveTo(destX, destY, speed, actionCompleted);
	}

	@Override
	public void interSetPosition(int posx, int posy) {
		// TODO Auto-generated method stub
		super.interSetPosition(posx, posy);
		System.out.println("Igrac na poziciji " + new Position(posx, posy));
		if(getGrid() != null)System.out.println("Nemam grid");
		if(getGrid() != null && getGrid().get(posx,  posy) == null)System.out.println("Vec je null");
	}

	
}
