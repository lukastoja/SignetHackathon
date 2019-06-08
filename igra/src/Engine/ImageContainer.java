package Engine;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ImageContainer extends ViewComponent{
	ArrayList<StaticImage> images = new ArrayList<>();
	int width, height;
	int cols, rows;
	
	public ImageContainer(EngineCore core) {
		super(core);
	}
	
	public void add(StaticImage img) {
		images.add(img);
	}
	
	
	@Override
	public void draw(Graphics2D g2d, JFrame frame, CameraController cameraCtl) {
		Position offset = this.getDrawingPosition();
		Position cameraPosition = cameraCtl.getPosition();

		int cameraOffsetX = (int) (cameraPosition.getX()*getDistanceFromCamera());
		int cameraOffsetY = (int) (cameraPosition.getY()*getDistanceFromCamera());
		
		float scaleFactor = cameraCtl.getZoom() * distanceFromCamera;
		g2d.scale(scaleFactor, scaleFactor);
		for(int i=0; i < images.size(); i++) {
			StaticImage img = images.get(i);
			
			if(img.removeFlag == true) {
				images.remove(i);
				i--;
				continue;
			}
			
			Position pos = img.getDrawingPosition();
			
			Composite oldComposite = g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - img.transparency);
			g2d.setComposite(ac);
			g2d.drawImage(img.getImage(), pos.getX() + offset.x - cameraOffsetX + getEngine().getWidth()/2, pos.getY() + offset.y - cameraOffsetY + getEngine().getHeight()/2, frame);
			
			g2d.setComposite(oldComposite);
		}
		
		g2d.scale(1.0f/scaleFactor, 1.0f/scaleFactor);
	}
	
	
}
