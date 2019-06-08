package igra;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.ImageContainer;
import Engine.StaticImage;
import Engine.TextView;

public class CinematicTwo extends EngineEpisode{
	public float SCALE_FACTOR = 1f;

	@Override
	public void mainLoop(long renderDeltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		if(key == KeyEvent.VK_ENTER && state == KeyEvent.KEY_PRESSED)
		{

			CinematicThree game = new CinematicThree();
			game.setEngine(getEngine());
			getEngine().setEpisode(game);
		}
		
	}

	@Override
	public void init(EngineCore engineCore) {
		CenterCamera camera = new CenterCamera();
		this.setCameraController(camera);
		
		ImageContainer container = new ImageContainer(engineCore);
		this.addViewComponent(container);
		
		
		Image img0=null;
		try {
			img0 = ImageIO.read(new File("../Assets/teleskopscena.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int posx = engineCore.getWidth()/2 - (int)(img0.getWidth(null)*SCALE_FACTOR/2);
		int posy = engineCore.getHeight()/2 - (int)(img0.getHeight(null)*SCALE_FACTOR/2);
		
		StaticImage menu = new StaticImage(engineCore, img0, posx, posy);
		container.add(menu);
		
		TextView txt = new TextView(engineCore);
		txt.writeText("Erik hola la", 10.0f);
		txt.setColor(Color.green);
		txt.setPosition(140, getEngine().getHeight()-50);
		this.addViewComponent(txt);
		bindKey(KeyEvent.VK_ENTER, "vk_enter");
		
	}

}
