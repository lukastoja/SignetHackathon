package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.EngineCore;
import Engine.Grid;
import Engine.GridBlock;

public class DiggingGameBlokZemlje extends GridBlock{

	public DiggingGameBlokZemlje(Grid grid, int x, int y) {
		super(grid, x, y);
		Image zemlja = Toolkit.getDefaultToolkit().getImage("../Assets/pijesak_40.jpg");
		this.setImage(zemlja);
	}

}
