package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.ActionCompleted;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;

public class DiggingGameIgrac extends GridBlock{
	Grid zemljaGrid;
	
	public DiggingGameIgrac(Grid grid, Grid zemljaGrid, int x, int y) {
		super(grid, x, y);
		
		this.zemljaGrid = zemljaGrid;
		Image imgPlayer = Toolkit.getDefaultToolkit().getImage("/home/ecokeco/Asseti/igrac.png");
		this.setImage(imgPlayer);
	}

	@Override
	public void moveTo(int destX, int destY, double speed, ActionCompleted actionCompleted) {		
		GridBlock blk = zemljaGrid.get(destX, destY);
		if(blk != null && blk instanceof DiggingGameKamen)return;
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
