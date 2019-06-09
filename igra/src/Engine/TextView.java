package Engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class TextView extends ViewComponent{
	String completeText;
	String currentlyWritten;
	float writeSpeed;
	Color color = Color.BLACK;
	
	public TextView(EngineCore engineCore) {
		super(engineCore);
	}

	@Override
	public void draw(Graphics2D g2d, JFrame frame, CameraController controller) {
		Composite oldComposite = g2d.getComposite();
		//AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - blk.transparency);
		//g2d.setComposite(ac);
		Font font = g2d.getFont();
		g2d.setColor(color);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 19)); 
		if(currentlyWritten == null)currentlyWritten="";
		g2d.drawString(currentlyWritten, this.getDrawingPosition().x, this.getDrawingPosition().y);
		
		g2d.setFont(font);
		//g2d.setComposite(oldComposite);
		
	}
	
	public void setColor(Color newColor) {
		this.color = newColor;
	}

	public void writeText(String text, float speed) {
		this.completeText = text;
		this.currentlyWritten = "";
		this.writeSpeed = speed;
		
		TypewriterEffect effect = new TypewriterEffect();
		this.getEngine().attachThread(effect);
	}

	private class TypewriterEffect extends EngineThread{
		float counter=0.0f;
		@Override
		public void loop(long deltaTime) {
			counter += writeSpeed * deltaTime/1000.0f;
			int count = (int)counter;
			
			if(count >= completeText.length()) {
				currentlyWritten = completeText;
				super.stop();
				return;
			}
			
			currentlyWritten = completeText.substring(0, count);
		}
		
	}
}
