package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import Engine.ActionCompleted;
import Engine.BasicElement;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;

public class DiggingGameKamen extends GridBlock{
	public static final float BRZINA_PADANJA = 4.0f;
	Grid playerGrid;
	boolean rezervacije[][];
	ArrayList<Position> mojeRezervacije = new ArrayList<>();
	
	/*
	 * TODO: ideja za sprijeciti bug sa rezervacijama
	 * 
	 */
	
	public DiggingGameKamen(Grid grid, Grid playerGrid, boolean rezervacije[][], int x, int y) {
		super(grid, x, y);
		this.playerGrid = playerGrid;
		Image img = Toolkit.getDefaultToolkit().getImage("/home/ecokeco/Asseti/rock.png");
		this.rezervacije = rezervacije;
		this.setImage(img);
	}

	public void padaj() {

	}
	
	private class ZavrsioPadati implements ActionCompleted{

		@Override
		public void actionCompleted() {

		}
		
	}

}
