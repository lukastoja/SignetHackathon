package igra;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.EngineThread;
import Engine.FixedContainer;
import Engine.StaticImage;

public class DashboardGame extends EngineEpisode{
	public static final int STR_LENGTH = 7;
	public int[] niz = new int[STR_LENGTH];
	public int[] unos = new int[STR_LENGTH];
	private int index = 0;
	private int pogodzeno = 0;
	private double postotak;
	private double pun = 0;
	public float SCALE_FACTOR = 0.35f;
	public Image imgLampica1=null;
	public Image imgLampica2=null;
	public Image imgLampica3=null;
	public Image imgLampica4=null;
	public Image imgLampica5=null;
	public Image imgLampica6=null;
	public Image imgLampica7=null;
	StaticImage lampica1;
	StaticImage lampica2;
	StaticImage lampica3;
	StaticImage lampica4;
	StaticImage lampica5;
	StaticImage lampica6;
	StaticImage lampica7;
	StaticImage crta;
	public int posx;
	public int posy;
	
	//funkcije
	public void generiraj_niz()
	{
		Random rand = new Random();
		
		for (int i = 0; i < STR_LENGTH; i++)
		{
			int a = rand.nextInt(7) + 1;
			niz[i] = a;
			if(a == 0)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica1, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 1");
			}
			if(a == 1)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica2, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 2");
			}
			if(a == 2)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica3, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 3");
			}
			if(a == 3)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica4, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 4");
			}
			if(a == 4)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica5, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 5");
			}
			if(a == 5)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica6, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 6");
			}
			if(a == 6)
			{
				BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica7, 1500 * i);
				getEngine().attachThread(blinkanje);
				System.out.println("palim lampicu 7");
			}
			System.out.println(a);
			
		}
	}
	
	public void izracunaj()
	{
		for (int i = 0; i < STR_LENGTH; i++)
		{
			if(niz[i] == unos[i])
			{
				pogodzeno++;
			}
		}
		postotak = (double)pogodzeno/(double)STR_LENGTH;
	}
	public void napuni()
	{
		if(postotak >= 0.9)
		{
			pun = pun + 0.5;
		} else if(postotak >= 0.7)
		{
			pun = pun + 0.3;
		} else if(postotak >= 0.5)
		{
			pun = pun + 0.1;
		} else if(postotak > 0.3)
		{
			pun = pun - 0.1;
		} else if(postotak > 0.1)
		{
			pun = pun - 0.3;
		} else 
		{
			pun = pun - 0.5;
		}
		if(pun < 0)
		{
			pun = 0;
		}
		if(pun >= 1)
		{
			System.out.println("Pobijeda!!!!!!!!");
		}
		crta.setPosition(posx, (int)(posy - (510)*pun));
		System.out.println(pun);
	}
	
	@Override
	public void mainLoop(long renderDeltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, int state) {
		if(state == KeyEvent.KEY_RELEASED)
		{
			return;
		}
		if(key == KeyEvent.VK_1 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 1;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica1, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_2 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 2;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica2, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_3 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 3;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica3, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_4 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 4;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica4, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_5 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 5;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica5, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_6 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 6;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica6, 0);
			getEngine().attachThread(blinkanje);
		}
		if(key == KeyEvent.VK_7 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 7;
			BlinkanjeJedne blinkanje = new BlinkanjeJedne(lampica7, 0);
			getEngine().attachThread(blinkanje);
		}
		index++;
		
		if (index == STR_LENGTH)
		{
			System.out.println("unos " + Arrays.toString(unos));
			izracunaj();
			index = 0;
			pogodzeno = 0;
			System.out.println("postotak " + postotak);
			napuni();
			if(pun >= 1)
			{
				System.exit(0);
			}
			InicijalnoBlinkanje blinkanje = new InicijalnoBlinkanje(2000);
			getEngine().attachThread(blinkanje);
			System.out.println("niz " + Arrays.toString(niz));
		}
	}

	@Override
	public void init(EngineCore engineCore) {
		CenterCamera camera = new CenterCamera();
		this.setCameraController(camera);
		
		FixedContainer container = new FixedContainer(engineCore);
		this.addViewComponent(container);
		container.setDistanceFromCamera(0.02f);
		
		Image imgDashboard=null;
		try {
			imgDashboard = ImageIO.read(new File("../Assets/dashboard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica1 = ImageIO.read(new File("../Assets/dashboard_lampica_0.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica2 = ImageIO.read(new File("../Assets/dashboard_lampica_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica3 = ImageIO.read(new File("../Assets/dashboard_lampica_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		};
		try {
			imgLampica4 = ImageIO.read(new File("../Assets/dashboard_lampica_3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica5 = ImageIO.read(new File("../Assets/dashboard_lampica_4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica6 = ImageIO.read(new File("../Assets/dashboard_lampica_5.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgLampica7 = ImageIO.read(new File("../Assets/dashboard_lampica_6.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image imgCrta=null;
		try {
			imgCrta = ImageIO.read(new File("../Assets/dashboard_level.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		posx = engineCore.getWidth()/2 - (int)(imgDashboard.getWidth(null)*SCALE_FACTOR/2);
		posy = engineCore.getHeight()/2 - (int)(imgDashboard.getHeight(null)*SCALE_FACTOR/2);
		
		StaticImage dashboard = new StaticImage(engineCore, imgDashboard, posx, posy);
		lampica1 = new StaticImage(engineCore, imgLampica1, posx, posy);
		lampica2 = new StaticImage(engineCore, imgLampica2, posx, posy);
		lampica3 = new StaticImage(engineCore, imgLampica3, posx, posy);
		lampica4 = new StaticImage(engineCore, imgLampica4, posx, posy);
		lampica5 = new StaticImage(engineCore, imgLampica5, posx, posy);
		lampica6 = new StaticImage(engineCore, imgLampica6, posx, posy);
		lampica7 = new StaticImage(engineCore, imgLampica7, posx, posy);
		crta = new StaticImage(engineCore, imgCrta, posx, posy);
		
		dashboard.setScale(SCALE_FACTOR);
		lampica1.setScale(SCALE_FACTOR);
		lampica2.setScale(SCALE_FACTOR);
		lampica3.setScale(SCALE_FACTOR);
		lampica4.setScale(SCALE_FACTOR);
		lampica5.setScale(SCALE_FACTOR);
		lampica6.setScale(SCALE_FACTOR);
		lampica7.setScale(SCALE_FACTOR);
		crta.setScale(SCALE_FACTOR);
		
		lampica1.setTransparancy(1.0f);
		lampica2.setTransparancy(1.0f);
		lampica3.setTransparancy(1.0f);
		lampica4.setTransparancy(1.0f);
		lampica5.setTransparancy(1.0f);
		lampica6.setTransparancy(1.0f);
		lampica7.setTransparancy(1.0f);
		
		container.add(dashboard);
		container.add(lampica1);
		container.add(lampica2);
		container.add(lampica3);
		container.add(lampica4);
		container.add(lampica5);
		container.add(lampica6);
		container.add(lampica7);
		container.add(crta);
		
		
		
		
		bindKey(KeyEvent.VK_1, "vk_1");
		bindKey(KeyEvent.VK_2, "vk_2");
		bindKey(KeyEvent.VK_3, "vk_3");
		bindKey(KeyEvent.VK_4, "vk_4");
		bindKey(KeyEvent.VK_5, "vk_5");
		bindKey(KeyEvent.VK_6, "vk_6");
		bindKey(KeyEvent.VK_7, "vk_7");
		
		InicijalnoBlinkanje blinkanje = new InicijalnoBlinkanje(0);
		engineCore.attachThread(blinkanje);
	}
	
	private class InicijalnoBlinkanje extends EngineThread{
		long counter = 0;
		int delay;
		
		public InicijalnoBlinkanje(int delay) {
			this.delay = delay;
		}
		
		@Override
		public void loop(long deltaTime) {
			counter = counter + deltaTime;
			if(counter > delay) {
				if(counter < 2000+delay)
				{
					lampica1.setTransparancy(1.0f);
					lampica2.setTransparancy(1.0f);
					lampica3.setTransparancy(1.0f);
					lampica4.setTransparancy(1.0f);
					lampica5.setTransparancy(1.0f);
					lampica6.setTransparancy(1.0f);
					lampica7.setTransparancy(1.0f);
				} else if(counter < 3000 + delay)
				{
					lampica1.setTransparancy(0.0f);
					lampica2.setTransparancy(0.0f);
					lampica3.setTransparancy(0.0f);
					lampica4.setTransparancy(0.0f);
					lampica5.setTransparancy(0.0f);
					lampica6.setTransparancy(0.0f);
					lampica7.setTransparancy(0.0f);
				} else if (counter < 5000 + delay)
				{
					lampica1.setTransparancy(1.0f);
					lampica2.setTransparancy(1.0f);
					lampica3.setTransparancy(1.0f);
					lampica4.setTransparancy(1.0f);
					lampica5.setTransparancy(1.0f);
					lampica6.setTransparancy(1.0f);
					lampica7.setTransparancy(1.0f);
				}else {
					generiraj_niz();
					System.out.println(Arrays.toString(niz));
					super.stop();
				}
			}
		}
		
	}
	
	private class BlinkanjeJedne extends EngineThread{
		StaticImage mojaLampica;
		int delay;
		
		public BlinkanjeJedne(StaticImage lampica, int delay) {
			this.mojaLampica = lampica;
			this.delay = delay;
		}
		
		long counter = 0;
		@Override
		public void loop(long deltaTime) {
			counter = counter + deltaTime;
			if(counter <= delay)
			{
				
			} else if(counter <= delay+1500) {
				mojaLampica.setTransparancy(0.0f);
			} else
			{
				mojaLampica.setTransparancy(1.0f);
				super.stop();
			}
		}
		
	}
}
