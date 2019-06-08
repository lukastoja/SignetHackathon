package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.Grid;
import Engine.GridBlock;

public class LabirintIgrac extends GridBlock{
	public LabirintIgrac(Grid grid, int x, int y) {
		super(grid, x, y);
		Image img = Toolkit.getDefaultToolkit().getImage("../Assets/lik_desno_50x50.png");
		this.setImage(img);
	}

}
