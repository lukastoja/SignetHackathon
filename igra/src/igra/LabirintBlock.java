package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.Grid;
import Engine.GridBlock;

public class LabirintBlock extends GridBlock{

	public LabirintBlock(Grid grid, int x, int y) {
		super(grid, x, y);
		Image img = Toolkit.getDefaultToolkit().getImage("../Assets/cigla_zid_64.png");
		this.setImage(img);
	}

}
