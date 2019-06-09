package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.EngineCore;
import Engine.Grid;
import Engine.GridBlock;

public class DiggingGameZid extends GridBlock{

	public DiggingGameZid(Grid grid, int x, int y) {
		super(grid, x, y);
		Image zemlja = Toolkit.getDefaultToolkit().getImage("../Assets/cigla_zid_40.jpg");
		this.setImage(zemlja);
	}

}
