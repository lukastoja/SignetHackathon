package Engine;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public abstract class Animation {
	ArrayList<Image> images = new ArrayList<>();
	float animationSpeed = 0;
	BasicElement target;
	PlayOn playon=null;
	PlayOnceOn playOnceOn=null;
	
	public void addFrameFromPath(String path) {
		Image img = Toolkit.getDefaultToolkit().getImage(path);
		images.add(img);
	}
	
	public ArrayList<Image> getFrames() {
		return images;
	}
	
	public void playOn(BasicElement el, float speed) {
		this.animationSpeed = speed;
		this.target = el;
		
		playon = new PlayOn();
		target.getEngine().attachThread(playon);
	}
	
	public void playOnceOn(BasicElement el, float speed) {
		this.animationSpeed = speed;
		this.target = el;
		
		playOnceOn = new PlayOnceOn();
		target.getEngine().attachThread(playOnceOn);
	}
	
	public void stop() {
		if(playon != null) {
			playon.stop();
		}
		
		if(playOnceOn != null) {
			playOnceOn.stop();
		}
	}
	
	private class PlayOn extends EngineThread{
		float frame=0;
		@Override
		public void loop(long deltaTime) {
			frame += animationSpeed * deltaTime/1000.0f;
			int id = (int)frame;
			
			if(id >= images.size()) {
				frame = 0.0f;
				id = 0;
			}
			target.setImage(images.get(id));
		}
	}
	
	private class PlayOnceOn extends EngineThread{
		float frame=0;
		@Override
		public void loop(long deltaTime) {
			frame += animationSpeed * deltaTime/1000.0f;
			int id = (int)frame;
			
			if(id >= images.size()) {
				frame = 0.0f;
				id = 0;
				target.setImage(images.get(id));
				super.stop();
				return;
			}
			target.setImage(images.get(id));
		}
	}
}
