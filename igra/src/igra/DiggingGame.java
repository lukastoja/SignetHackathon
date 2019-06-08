package igra;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Engine.ActionCompleted;
import Engine.BasicElement;
import Engine.CameraController;
import Engine.CollisionGroup;
import Engine.CollisionListener;
import Engine.DollyCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FadeView;
import Engine.FollowingCamera;
import Engine.Grid;
import Engine.GridBlock;

public class DiggingGame extends EngineEpisode{
	int MAZE_WIDTH = 40;
	int MAZE_HEIGHT = 40;
	int KAMENJA_COUNT = 350;
	final static float SPEED = 5.5f;
	DiggingGameIgrac player;
	GridBlock cilj;
	Grid zemljaGrid;
	Grid playerGrid;
	Random random = new Random();
	ArrayList<DiggingGameKamen> kamenja = new ArrayList<>();
	boolean mapa[][] = new boolean[MAZE_HEIGHT][MAZE_WIDTH];					// NEKA SE PRVO IZGENERIRA OVDJE
	boolean rezervacije[][] = new boolean[MAZE_HEIGHT][MAZE_WIDTH];
	
	@Override
	public void init(EngineCore engineCore) {
		/* Grid u kojem drzimo blokove koje kopamo */
		zemljaGrid = new Grid(engineCore, 40, 40, MAZE_WIDTH, MAZE_HEIGHT);
		addViewComponent(zemljaGrid);
		
		for(int i=0; i < MAZE_WIDTH; i++) {
			for(int j=0; j < MAZE_HEIGHT; j++) {
				if(i == 0 && j == 0)continue;
				DiggingGameBlokZemlje blk = new DiggingGameBlokZemlje(zemljaGrid, i, j);
				zemljaGrid.add(blk);
			}
		}
		
		/* Grid po kojem se player krece */
		playerGrid = new Grid(engineCore, 40, 40, MAZE_WIDTH, MAZE_HEIGHT);
		addViewComponent(playerGrid);
		player = new DiggingGameIgrac(playerGrid, zemljaGrid, 0, 0);
		player.setBoundingRadius(20);
		playerGrid.add(player);
		
		/* Za detekciju kolizije playera i kamenja koja padaju */
		CollisionGroup colGrp = new CollisionGroup();
		colGrp.addToSubgroup1(player);
		
		/* Generiraj kamenja */
		for(int i=0; i < KAMENJA_COUNT; i++) {
			int x = random.nextInt(MAZE_WIDTH);
			int y = random.nextInt(MAZE_HEIGHT);
			if(x == player.getPosition().getX() && y == player.getPosition().getY())continue;
			
			BasicElement el = zemljaGrid.get(x, y);
			if((el instanceof DiggingGameBlokZemlje) == false) {
				continue;
			}
			
			el.remove();
			DiggingGameKamen kamen = new DiggingGameKamen(zemljaGrid, playerGrid, rezervacije, x, y);
			kamen.setBoundingRadius(20);
			zemljaGrid.add(kamen);
			kamenja.add(kamen);
			
			colGrp.addToSubgroup2(kamen);
		}
		
		colGrp.setCollisionListener(new KamenPogodioIgraca());
		this.addCollisionGroup(colGrp);
		
		/* Fade in na pocetku */
		FadeView fadeView = new FadeView(engineCore);
		fadeView.setFaded(true);
		addViewComponent(fadeView);
		fadeView.fadeIn(0.3f);
		
		/* Postavi kameru */
		DollyCamera camera = new DollyCamera();
		camera.setStartZoom(1.3f);
		camera.setEndZoom(1.0f);
		camera.setStartPosition(getEngine().getWidth(), getEngine().getHeight());
		camera.setEndPosition((int)(player.getDrawingPosition().getX() + player.getWidth()/2.0f), (int)(player.getDrawingPosition().getY() + player.getHeight()/2.0f));
		this.setCameraController(camera);
		camera.setEngine(getEngine());
		camera.startTransition(0.2f);
		//camera.startTransition(5.2f);
		camera.setTransitionCompletedListener(new DollyCamGotov());			// Kada tranzicija zavrsi, pusti igraca da igra
		
		/*
		TextView txt = new TextView(engineCore);
		txt.writeText("Erik hola la", 4.0f);
		txt.setColor(Color.green);
		this.addViewComponent(txt);
		*/
	}
	
	@Override
	public void mainLoop(long renderDeltaTime) {
		if(isKeyPressed(KeyEvent.VK_UP) == true) {
			player.moveUp(SPEED, new PlayerStigao());
		}else if(isKeyPressed(KeyEvent.VK_DOWN) == true) {
			player.moveDown(SPEED, new PlayerStigao());
		}else if(isKeyPressed(KeyEvent.VK_LEFT) == true) {
			player.moveLeft(SPEED, new PlayerStigao());
		}else if(isKeyPressed(KeyEvent.VK_RIGHT) == true) {
			player.moveRight(SPEED, new PlayerStigao());
		}
		
		for(DiggingGameKamen kamen: kamenja) {
			kamen.padaj();
		}
	}
	
	public void bindKeys() {
		bindKey(KeyEvent.VK_UP, "key_up");
		bindKey(KeyEvent.VK_DOWN, "key_down");
		bindKey(KeyEvent.VK_LEFT, "key_left");
		bindKey(KeyEvent.VK_RIGHT, "key_right");
	}
	
	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		
	}
	
	private class DollyCamGotov implements ActionCompleted{
		@Override
		public void actionCompleted() {
			bindKeys();
			CameraController camera = new FollowingCamera(player);
			DiggingGame.this.setCameraController(camera);
		}
	}
	
	private class PlayerStigao implements ActionCompleted{
		@Override
		public void actionCompleted() {
			GridBlock blk = zemljaGrid.get(player.origx, player.origy);
			if(blk != null) {
				System.out.println("Uklanjam");
				blk.remove();
			}
		}
	}
	
	private class KamenPogodioIgraca implements CollisionListener{

		@Override
		public void onCollision(BasicElement el1, BasicElement el2) {
			//System.exit(0);
		}
		
	}
}
