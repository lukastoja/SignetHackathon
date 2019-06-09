package Engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class FadeView extends ViewComponent{
	private float transparency = 1.0f;
	private boolean fadingInProgress = false;
	float fadeSpeed;
	ActionCompleted listener=null;
	
	public FadeView(EngineCore engineCore) {
		super(engineCore);
	}
	
	
	@Override
	public void draw(Graphics2D g2d, JFrame frame, CameraController controller) {
		Composite oldComposite = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - this.transparency);
		g2d.setComposite(ac);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getEngine().getWidth(), getEngine().getHeight());
		
		g2d.setComposite(oldComposite);
	}
	
	public void setListener(ActionCompleted l) {
		this.listener = l;
	}
	
	public void setFaded(boolean faded) {
		if(faded)
			this.transparency = 0.0f;
		else
			this.transparency = 1.0f;
	}
	
	public void fadeIn(float speed) {
		if(fadingInProgress)return;
		this.fadingInProgress = true;
		this.fadeSpeed = speed;
		FadeIn fadeInEffect = new FadeIn();
		getEngine().attachThread(fadeInEffect);
	}
	
	public void fadeOut(float speed) {
		if(fadingInProgress)return;
		this.fadingInProgress = true;
		this.fadeSpeed = speed;
		FadeOut fadeOutEffect = new FadeOut();
		getEngine().attachThread(fadeOutEffect);
	}
	
	private class FadeIn extends EngineThread{
		@Override
		public void loop(long deltaTime) {
			transparency += fadeSpeed * deltaTime/1000.0f;
			if(transparency >= 1.0f) {
				transparency = 1.0f;
				fadingInProgress = false;
				
				if(listener != null)listener.actionCompleted();
				super.stop();
				return;
			}
		}
		
	}
	
	private class FadeOut extends EngineThread{
		@Override
		public void loop(long deltaTime) {
			transparency -= fadeSpeed * deltaTime/1000.0f;
			if(transparency <= 0.0f) {
				transparency = 0.0f;
				fadingInProgress = false;
				if(listener != null)listener.actionCompleted();
				super.stop();
				return;
			}
		}
		
	}
}
