package igra;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FixedContainer;
import Engine.StaticImage;
import Engine.TextView;

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
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.2f * getEngine().getHeight()));
			} else if(pos_kursor == pos_setting)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.31f * getEngine().getHeight()));
			} else if(pos_kursor == pos_about)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.41f * getEngine().getHeight()));
			} else if(pos_kursor == pos_exit)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.51f * getEngine().getHeight()));
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
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.2f * getEngine().getHeight()));
			} else if(pos_kursor == pos_setting)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.31f * getEngine().getHeight()));
			} else if(pos_kursor == pos_about)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.41f * getEngine().getHeight()));
			} else if(pos_kursor == pos_exit)
			{
				kursor.setPosition((int)(0.36f * getEngine().getWidth()), (int)(0.51f * getEngine().getHeight()));
			}
		}
		if(key == KeyEvent.VK_ENTER && state == KeyEvent.KEY_PRESSED)
		{
			if(pos_kursor == pos_exit)
			{
				System.exit(0);
			}
			if(pos_kursor == pos_play)
			{
				CinematicOne game = new CinematicOne();
				game.setEngine(getEngine());
				getEngine().setEpisode(game);
			}
			if(pos_kursor == pos_about) {
				TextView txt = new TextView(getEngine());
				String authorsArr[] = new String[3];
				authorsArr[0] = "Erik Otovic";
				authorsArr[1] = "Luka Otovic";
				authorsArr[2] = "Ivana Zuzic";
				Random rnd = new Random();
				for(int i=0; i < 10; i++) {
					int a = rnd.nextInt(3);
					int b = rnd.nextInt(3);
					String tmp = authorsArr[a];
					authorsArr[a] = authorsArr[b];
					authorsArr[b] = tmp;
				}
				
				txt.writeText("This game was (successfully) developed @SignetHackathon on 8.6.2019!\n", 16.0f);
				txt.setColor(Color.BLACK);
				txt.setPosition(250, 650);
				this.addViewComponent(txt);
				
				TextView txtAuthors = new TextView(getEngine());
				txtAuthors.writeText("Authors in random order: " + authorsArr[0] + ", " + authorsArr[1] + " and " + authorsArr[2], 16.0f);
				txtAuthors.setColor(Color.BLACK);
				txtAuthors.setPosition(250, 680);
				this.addViewComponent(txtAuthors);
			}
		}
	}

	@Override
	public void init(EngineCore engineCore) {
		CenterCamera camera = new CenterCamera();
		this.setCameraController(camera);
		
		FixedContainer container = new FixedContainer(engineCore);
		this.addViewComponent(container);
		
		Image imgMenu=null;
		try {
			imgMenu = ImageIO.read(new File("../Assets/menu1280x720.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image covjeculjak = null;
		try {
			covjeculjak = ImageIO.read(new File("../Assets/lik_desno_50x50.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StaticImage menu = new StaticImage(engineCore, imgMenu, engineCore.getWidth()/2 - imgMenu.getWidth(null)/2, engineCore.getHeight()/2 - imgMenu.getHeight(null)/2);
		kursor = new StaticImage(engineCore, covjeculjak, (int)(0.36f * engineCore.getWidth()), (int)(0.2f * engineCore.getHeight()));
		
		container.add(menu);
		container.add(kursor);
		
		bindKey(KeyEvent.VK_Q, "vk_q");
		bindKey(KeyEvent.VK_UP, "vk_up");
		bindKey(KeyEvent.VK_DOWN, "vk_down");
		bindKey(KeyEvent.VK_ENTER, "vk_enter");
	}

}
