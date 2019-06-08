package Engine;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class FixedContainer extends ViewComponent{
	ArrayList<BasicElement> elements = new ArrayList<>();
	
	public FixedContainer(EngineCore engineCore) {
		super(engineCore);
	}
	
	public void add(BasicElement el) {
		elements.add(el);
	}
	
	@Override
	public void draw(Graphics2D g2d, JFrame frame, CameraController controller) {
		Position offset = this.getDrawingPosition();

		for(int i=0; i < elements.size(); i++) {
			BasicElement el = elements.get(i);
			
			if(el.removeFlag == true) {
				elements.remove(i);
				i--;
				continue;
			}
			
			Position pos = el.getDrawingPosition();
			
			Composite oldComposite = g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - el.transparency);
			g2d.setComposite(ac);
			g2d.drawImage(el.getImage(), pos.getX() + offset.x, pos.getY() + offset.y, frame);
			
			g2d.setComposite(oldComposite);
		}
		
	}

}
