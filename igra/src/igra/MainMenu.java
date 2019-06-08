package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FixedContainer;
import Engine.StaticImage;

public class MainMenu extends EngineEpisode{
	StaticImage kursor;
	int pos_play = 0;
	int pos_setting = 1;
	int pos_about = 2;
	int pos_exit = 3;
	int pos_kursor = 0;
	
	@Override
	public void mainLoop(long renderDeltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, int state) {
		if(key == KeyEvent.VK_Q && state == KeyEvent.KEY_PRESSED) {
			System.exit(0);
		}
		if(key == KeyEvent.VK_UP && state == KeyEvent.KEY_PRESSED ) {
			pos_kursor--;
			if(pos_kursor < 0)
			{
				pos_kursor = 3;
			}
			
			if(pos_kursor == pos_play)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.365f * getEngine().getHeight()));
			} else if(pos_kursor == pos_setting)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.41f * getEngine().getHeight()));
			} else if(pos_kursor == pos_about)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.455f * getEngine().getHeight()));
			} else if(pos_kursor == pos_exit)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.499f * getEngine().getHeight()));
			}
		}
		if(key == KeyEvent.VK_DOWN && state == KeyEvent.KEY_PRESSED ) {
			pos_kursor++;
			if(pos_kursor > 3)
			{
				pos_kursor = 0;
			}
			
			if(pos_kursor == pos_play)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.365f * getEngine().getHeight()));
			} else if(pos_kursor == pos_setting)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.41f * getEngine().getHeight()));
			} else if(pos_kursor == pos_about)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.455f * getEngine().getHeight()));
			} else if(pos_kursor == pos_exit)
			{
				kursor.setPosition((int)(0.431f * getEngine().getWidth()), (int)(0.499f * getEngine().getHeight()));
			}
		}
		if(key == KeyEvent.VK_ENTER && state == KeyEvent.KEY_PRESSED)
		{
			if(pos_kursor == pos_exit)
			{
				System.exit(0);
			}
		}
	}

	@Override
	public void init(EngineCore engineCore) {
		CenterCamera camera = new CenterCamera();
		this.setCameraController(camera);
		
		FixedContainer container = new FixedContainer(engineCore);
		this.addViewComponent(container);
		
		//Image imgMenu = Toolkit.getDefaultToolkit().getImage("../Assets/menu_640.png");
		Image imgMenu=null;
		try {
			imgMenu = ImageIO.read(new File("../Assets/menu_640.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image covjeculjak = null;
		try {
			covjeculjak = ImageIO.read(new File("../Assets/lik_desno_100x100.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StaticImage menu = new StaticImage(engineCore, imgMenu, engineCore.getWidth()/2 - imgMenu.getWidth(null)/2, engineCore.getHeight()/2 - imgMenu.getHeight(null)/2);
		kursor = new StaticImage(engineCore, covjeculjak, (int)(0.431f * engineCore.getWidth()), (int)(0.365f * engineCore.getHeight()));
		
		container.add(menu);
		container.add(kursor);
		
		//menu.setPosition((int)(0.3f * engineCore.getWidth()), 10);
		
		bindKey(KeyEvent.VK_Q, "vk_q");
		bindKey(KeyEvent.VK_UP, "vk_up");
		bindKey(KeyEvent.VK_DOWN, "vk_down");
		bindKey(KeyEvent.VK_ENTER, "vk_enter");
	}

}