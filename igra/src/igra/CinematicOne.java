package igra;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FixedContainer;
import Engine.ImageContainer;
import Engine.StaticCamera;
import Engine.StaticImage;

public class CinematicOne extends EngineEpisode{
	
	@Override
	public void mainLoop(long renderDeltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EngineCore engineCore) {
		StaticCamera camera = new StaticCamera(0, 0);
		this.setCameraController(camera);
		Image img0;
		try {
			img0 = ImageIO.read(new File("../Assets/dio1.jpg"));
			//StaticImage menu = new StaticImage(engineCore, img0, engineCore.getWidth()/2 - img0.getWidth(null)/2, engineCore.getHeight()/2 - img0.getHeight(null)/2);
			StaticImage menu = new StaticImage(engineCore, img0, 0, 0);
			ImageContainer container = new ImageContainer(engineCore);
			container.add(menu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
