package Engine;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

public class StaticImage extends BasicElement{
	int x, y;
	public StaticImage(EngineCore core, Image img, int x, int y) {
		super(core);
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
	public void setImage(Image img) {
		this.img = img;
	}
	
	public Image getImage() {
		return this.img;
	}
	
	public void setPosition(int x, int y) {
		this.origx = x;
		this.x = x;
		this.origy = y;
		this.y = y;
	}
	

	@Override
	public Position getDrawingPosition() {
		return new Position(x, y);
	}

	@Override
	public Position getPosition() {
		return new Position(x, y);
	}

	@Override
	public int getWidth() {
		return img.getWidth(null);
	}

	@Override
	public int getHeight() {
		return img.getHeight(null);
	}
	
}
