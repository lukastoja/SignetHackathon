package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.Grid;
import Engine.GridBlock;

public class LabirintShip extends GridBlock{

	public LabirintShip(Grid grid, int x, int y) {
		super(grid, x, y);
		Image img = Toolkit.getDefaultToolkit().getImage("../Assets/dio1.jpg");
		this.setImage(img);
	}

}
