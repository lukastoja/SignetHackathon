package igra;

import java.awt.Image;
import java.awt.Toolkit;

import Engine.CollisionGroup;
import Engine.Grid;
import Engine.GridBlock;

public class PackmanGameJedac extends GridBlock{
	public CollisionGroup grp;
	Image imgGore, imgDolje, imgLijevo, imgDesno;
	int lives;
	
	public PackmanGameJedac(Grid grid, int x, int y) {
		super(grid, x, y);
		imgGore = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		imgLijevo = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		imgDesno = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		imgDolje = Toolkit.getDefaultToolkit().getImage("../Assets/jedac_gore_64.png");
		this.setImage(imgGore);
	}

	void setLives(int val) {
		this.lives = val;
	}
	
	int getLives() {
		return this.lives;
	}
}
