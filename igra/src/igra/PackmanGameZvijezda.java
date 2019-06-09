package igra;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.Grid;
import Engine.GridBlock;

public class PackmanGameZvijezda extends GridBlock{

	public PackmanGameZvijezda(Grid grid, int x, int y) {
		super(grid, x, y);
		try {
			Image img = ImageIO.read(new File("../Assets/xcf/zvijezda_64.png"));
			this.setImage(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
