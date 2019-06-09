package igra;

import java.awt.Color;
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
import Engine.Position;
import Engine.TextView;

public class DiggingGame extends EngineEpisode{
	int MAZE_WIDTH = 40;
	int MAZE_HEIGHT = 40;
	int KAMENJA_COUNT = 350;
	final static float SPEED = 5.5f;
	final static int OBJECTIVES_COUNT = 10;
	DiggingGameIgrac player;
	GridBlock cilj;
	Grid zemljaGrid;
	Grid playerGrid;
	Random random = new Random();
	TextView txtParts;
	ArrayList<DiggingGameKamen> kamenja = new ArrayList<>();
	ArrayList<DiggingGameBlokPronalazak> objectives = new ArrayList<>();
	int mapa[][] = new int[MAZE_HEIGHT][MAZE_WIDTH];
	int originalMapa[][] = new int[MAZE_HEIGHT][MAZE_WIDTH];
	boolean rezervacije[][] = new boolean[MAZE_HEIGHT][MAZE_WIDTH];
	
	@Override
	public void init(EngineCore engineCore) {
		/* Grid u kojem drzimo blokove koje kopamo */
		zemljaGrid = new Grid(engineCore, 40, 40, MAZE_WIDTH, MAZE_HEIGHT);
		addViewComponent(zemljaGrid);
		
		/* Grid po kojem se player krece */
		playerGrid = new Grid(engineCore, 40, 40, MAZE_WIDTH, MAZE_HEIGHT);
		addViewComponent(playerGrid);
		player = new DiggingGameIgrac(playerGrid, zemljaGrid, 1, 1);
		player.setBoundingRadius(20);
		playerGrid.add(player);
		
		/* Za detekciju kolizije playera i kamenja koja padaju */
		CollisionGroup colGrp = new CollisionGroup();
		colGrp.addToSubgroup1(player);
		
		for(int i=0; i < MAZE_WIDTH; i++) {
			for(int j=0; j < MAZE_HEIGHT; j++) {
				if(i==0 || i == MAZE_WIDTH-1 || j == 0 || j == MAZE_HEIGHT-1) {
					DiggingGameZid zid = new DiggingGameZid(zemljaGrid, i, j);
					zemljaGrid.add(zid);
					continue;
				}
				
				if(i == 1 && j == 1)continue;
				float f = random.nextFloat();
				if(f < 0.15f) {
					DiggingGameKamen kamen = new DiggingGameKamen(zemljaGrid, playerGrid, rezervacije, i, j);
					kamen.player = player;
					kamen.setBoundingRadius(20);
					zemljaGrid.add(kamen);
					kamenja.add(kamen);
					mapa[j][i] = 1;
					
					colGrp.addToSubgroup2(kamen);
				}else if(f < 0.99f){
					DiggingGameBlokZemlje blk = new DiggingGameBlokZemlje(zemljaGrid, i, j);
					zemljaGrid.add(blk);
					mapa[j][i] = 0;
				}else {
					// Prazno
					mapa[j][i] = 0;
				}
			}
		}
		
		while(objectives.size() < OBJECTIVES_COUNT) {
			int x = random.nextInt(mapa[0].length);
			int y = random.nextInt(mapa.length);
			
			if(zemljaGrid.get(x, y) != null && zemljaGrid.get(x, y) instanceof DiggingGameBlokZemlje) {
				zemljaGrid.get(x, y).remove();
				DiggingGameBlokPronalazak p = new DiggingGameBlokPronalazak(zemljaGrid, x, y);
				zemljaGrid.add(p);
				objectives.add(p);
			}
		}
		
		for(int i=0; i < mapa.length; i++) {
			for(int j=0; j < mapa[0].length; j++) {
				this.originalMapa[i][j] = mapa[i][j];
			}
		}
		
		// Provjeri jesi li svi objektivi dostupni
		for(int i=0; i < objectives.size(); i++) {
			for(int k=0; k < mapa.length; k++) {
				for(int j=0; j < mapa[0].length; j++) {
					mapa[k][j] = this.originalMapa[k][j];
				}
			}
			
			Position pos = objectives.get(i).getPosition();
			if(dfs(1, 1, pos) == true)continue;
			else {
				objectives.get(i).remove();
				objectives.remove(i);
				System.out.println("Nije dostupan, uklanjam");
				i--;
			}
		}
		System.out.println("Dostupno je " + objectives.size());
		
		
		colGrp.setCollisionListener(new KamenPogodioIgraca());
		this.addCollisionGroup(colGrp);
		
		/* Fade in na pocetku */
		FadeView fadeView = new FadeView(engineCore);
		fadeView.setFaded(true);
		addViewComponent(fadeView);
		fadeView.fadeIn(0.3f);
		
		FollowingCamera cam = new FollowingCamera(player);
		
		/* Postavi kameru */
		DollyCamera camera = new DollyCamera();
		camera.setStartZoom(1.3f);
		camera.setEndZoom(1.0f);
		camera.setStartPosition(getEngine().getWidth(), getEngine().getHeight());
		camera.setEndPosition((int)(player.getDrawingPosition().getX() + 40/2.0f), (int)(player.getDrawingPosition().getY() + 40/2.0f));
		//camera.setEndPosition(cam.getPosition().getX(), cam.getPosition().getY());
		this.setCameraController(camera);
		camera.setEngine(getEngine());
		camera.startTransition(0.2f);
		//camera.startTransition(5.2f);
		camera.setTransitionCompletedListener(new DollyCamGotov());			// Kada tranzicija zavrsi, pusti igraca da igra
		
		
		TextView txt = new TextView(engineCore);
		txt.writeText("Collect all the telescope parts!", 16.0f);
		txt.setColor(Color.green);
		txt.setPosition(50, 650);
		this.addViewComponent(txt);
		
		txtParts = new TextView(engineCore);
		txtParts.writeText(objectives.size() + " parts remaining!", 16.0f);
		txtParts.setColor(Color.green);
		txtParts.setPosition(50, 680);
		this.addViewComponent(txtParts);
	}
	
	private boolean dfs(int x, int y, Position pos) {
		if(x < 0 || y < 0)return false;
		if(x >= mapa[0].length || y >= mapa.length)return false;
		if(mapa[y][x] == 1)return false;
		mapa[y][x] = 1;
		if(x == pos.getX() && y == pos.getY()) {
			return true;
		}
		//if(zemljaGrid.get(x, y) != null)zemljaGrid.get(x, y).remove();
		
		if(dfs(x+1, y, pos))return true;
		if(dfs(x-1, y, pos))return true;
		if(dfs(x, y+1, pos))return true;
		if(dfs(x, y-1, pos))return true;
		return false;
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
		}else if(isKeyPressed(KeyEvent.VK_O) == true) {
			pobjeda();
		}
		
		for(DiggingGameKamen kamen: kamenja) {
			boolean ret = kamen.padaj();
			if(ret == true) {
				System.out.println("Gameover");
				GameOver gameOver = new GameOver();
				gameOver.setEngine(getEngine());
				this.getEngine().setEpisode(gameOver);
			}
		}
		
		for(int i=0; i < objectives.size(); i++) {
			Position pos = objectives.get(i).getPosition();
			if(pos.getX() == player.getPosition().getX() && pos.getY() == player.getPosition().getY()) {
				objectives.remove(i);
				i--;
				txtParts.writeText(objectives.size() + " parts remaining!", 16.0f);
				continue;
			}
		}
		
		if(objectives.size() == 0) {
			pobjeda();
		}
	}
	
	public void pobjeda() {
		FadeView fadeView = new FadeView(getEngine());
		fadeView.setFaded(false);
		addViewComponent(fadeView);
		fadeView.fadeOut(0.3f);
		fadeView.setListener(new ActionCompleted() {

			@Override
			public void actionCompleted() {
				CinematicTwo c = new CinematicTwo();
				c.setEngine(getEngine());
				getEngine().setEpisode(c);
			}
			
		});
	}
	
	public void bindKeys() {
		bindKey(KeyEvent.VK_UP, "key_up");
		bindKey(KeyEvent.VK_DOWN, "key_down");
		bindKey(KeyEvent.VK_LEFT, "key_left");
		bindKey(KeyEvent.VK_RIGHT, "key_right");
		bindKey(KeyEvent.VK_O, "key_o");
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
			System.exit(0);
		}
		
	}
}
