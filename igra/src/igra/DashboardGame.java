package igra;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;

public class DashboardGame extends EngineEpisode{
	public static final int STR_LENGTH = 7;
	public int[] niz = new int[STR_LENGTH];
	public int[] unos = new int[STR_LENGTH];
	private int index = 0;
	private int pogodzeno = 0;
	private double postotak;
	private double pun = 0;
	
	//funkcije
	public void generiraj_niz()
	{
		Random rand = new Random();
		
		for (int i = 0; i < STR_LENGTH; i++)
		{
			int a = rand.nextInt(7);
			niz[i] = a;
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
		if(key == KeyEvent.VK_0 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 0;
		}
		if(key == KeyEvent.VK_1 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 1;
		}
		if(key == KeyEvent.VK_2 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 2;
		}
		if(key == KeyEvent.VK_3 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 3;;
		}
		if(key == KeyEvent.VK_4 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 4;
		}
		if(key == KeyEvent.VK_5 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 5;
		}
		if(key == KeyEvent.VK_6 && state == KeyEvent.KEY_PRESSED) {
			unos[index] = 6;
		}
		index++;
		if (index == STR_LENGTH)
		{
			System.out.println(Arrays.toString(unos));
			izracunaj();
			index = 0;
			pogodzeno = 0;
			System.out.println(postotak);
			napuni();
			if(pun >= 1)
			{
				System.exit(0);
			}
			generiraj_niz();
			System.out.println(Arrays.toString(niz));
		}
	}

	@Override
	public void init(EngineCore engineCore) {
		CenterCamera camera = new CenterCamera();
		this.setCameraController(camera);
		generiraj_niz();
		System.out.println(Arrays.toString(niz));
		
		bindKey(KeyEvent.VK_0, "vk_0");
		bindKey(KeyEvent.VK_1, "vk_1");
		bindKey(KeyEvent.VK_2, "vk_2");
		bindKey(KeyEvent.VK_3, "vk_3");
		bindKey(KeyEvent.VK_4, "vk_4");
		bindKey(KeyEvent.VK_5, "vk_5");
		bindKey(KeyEvent.VK_6, "vk_6");
	}
	
}
