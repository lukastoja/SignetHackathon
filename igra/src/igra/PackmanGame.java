package igra;

import Engine.ActionCompleted;
import Engine.BasicElement;
import Engine.CenterCamera;
import Engine.CollisionGroup;
import Engine.CollisionListener;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FadeView;
import Engine.FollowingCamera;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;
import Engine.StaticCamera;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class PackmanGame extends EngineEpisode{
	public static final int MAZE_WIDTH = 28;
	public static final int MAZE_HEIGHT = 31;
	public static final float PLAYER_SPEED = 7.0f;
	public static final float DISTANCE = 1.0f;
	public static final float JEDAC_SPEED = 5.0f;
	Grid labirintGrid;
	Grid playerGrid;
	PackmanGameIgrac player;
	boolean labirint[][];
	Random rnd = new Random();
	ArrayList<PackmanGameJedac> jedaci = new ArrayList<>();
	ArrayList<PackmanGameZvijezda> zvjezde = new ArrayList<>();
	
	@Override
	public void mainLoop(long renderDeltaTime) {
		if(isKeyPressed(KeyEvent.VK_UP) == true) {
			player.moveUp(PLAYER_SPEED, null);
		}else if(isKeyPressed(KeyEvent.VK_DOWN) == true) {
			player.moveDown(PLAYER_SPEED, null);
		}else if(isKeyPressed(KeyEvent.VK_LEFT) == true) {
			player.moveLeft(PLAYER_SPEED, null);
		}else if(isKeyPressed(KeyEvent.VK_RIGHT) == true) {
			player.moveRight(PLAYER_SPEED, null);
		}else if(isKeyPressed(KeyEvent.VK_O) == true) {
			pobjeda();
		}
		
		for(int i=0; i < jedaci.size(); i++) {
			int dx = Math.abs(jedaci.get(i).getPosition().getX() - player.getPosition().getX());
			int dy = Math.abs(jedaci.get(i).getPosition().getY() - player.getPosition().getY());
			
			if(dx+dy == 0) {
				int lives = jedaci.get(i).getLives();
				new JedacPojeden().onCollision(player, jedaci.get(i));
				if(lives > 0) {
					PackmanGameJedac j1 = spawnajJedaca();
					PackmanGameJedac j2 = spawnajJedaca();
					j1.setLives(lives-1);
					j2.setLives(lives-1);
				}
				i--;
				continue;
			}
			
			boolean flag = false;
			for(int j=0; j < zvjezde.size(); j++) {
				dx = Math.abs(jedaci.get(i).getPosition().getX() - zvjezde.get(j).getPosition().getX());
				dy = Math.abs(jedaci.get(i).getPosition().getY() - zvjezde.get(j).getPosition().getY());
				
				if(dx+dy == 0) {
					zvjezde.get(j).remove();
					zvjezde.remove(j);
					flag = true;
					j--;
					continue;
				}
			}
			
			if(flag == true && zvjezde.size() == 0) {
				System.out.println("Gameover");
				GameOver gameOver = new GameOver();
				gameOver.setEngine(getEngine());
				this.getEngine().setEpisode(gameOver);
			}
			
			int smjer = dobiSmjerZaJedaca(jedaci.get(i));
			if(smjer == -1)continue;
			if(smjer == 0)jedaci.get(i).moveUp(JEDAC_SPEED, null);
			if(smjer == 1)jedaci.get(i).moveRight(JEDAC_SPEED, null);
			if(smjer == 2)jedaci.get(i).moveDown(JEDAC_SPEED, null);
			if(smjer == 3)jedaci.get(i).moveLeft(JEDAC_SPEED, null);
		}
		
	}
	
	int bfs(int x, int y) {
		ArrayList<Position> q = new ArrayList<>();
		ArrayList<Integer> distances = new ArrayList<>();
		boolean bio[][] = new boolean[playerGrid.getRows()][playerGrid.getCols()];
		
		q.add(new Position(x, y));
		distances.add(new Integer(0));
		while(q.size() > 0) {
			Position p = q.get(0);
			q.remove(0);
			
			Integer dist = distances.get(0);
			distances.remove(0);
			
			if(bio[p.getY()][p.getX()] == true)continue;
			bio[p.getY()][p.getX()] = true;
			
			if(p.getX() < 0 || p.getY() < 0)continue;
			if(p.getX() >= playerGrid.getCols())continue;
			if(p.getY() >= playerGrid.getRows())continue;
			
			if(labirint[p.getY()][p.getX()] == true)continue;
			
			GridBlock blk = labirintGrid.get(p.getX(), p.getY());
			if(blk != null && blk instanceof PackmanGameZvijezda)return dist.intValue();
			
			q.add(new Position(p.getX(), p.getY()-1));
			q.add(new Position(p.getX()+1, p.getY()));
			q.add(new Position(p.getX(), p.getY()+1));
			q.add(new Position(p.getX()-1, p.getY()));
			
			distances.add(new Integer(dist.intValue()+1));
			distances.add(new Integer(dist.intValue()+1));
			distances.add(new Integer(dist.intValue()+1));
			distances.add(new Integer(dist.intValue()+1));
		}
		
		return Integer.MAX_VALUE;
	}

	private int dobiSmjerZaJedaca(PackmanGameJedac jedac) {
		//if(jedac.isMoving() == true)return 0;
		Position pos = jedac.getPosition();
		
		int minDist=999999999;
		int minDir=0;
		
		if(pos.getY()>0) {
			int tmp = bfs(pos.getX(), pos.getY()-1);
			if(tmp < minDist) {
				minDist = tmp;
				minDir = 0;
			}
		}
		
		if(pos.getY()<playerGrid.getRows()-2) {
			int tmp = bfs(pos.getX(), pos.getY()+1);
			if(tmp < minDist) {
				minDist = tmp;
				minDir = 2;
			}
		}
		
		if(pos.getX()<playerGrid.getCols()-2) {
			int tmp = bfs(pos.getX()+1, pos.getY());
			if(tmp < minDist) {
				minDist = tmp;
				minDir = 1;
			}
		}
		
		if(pos.getX()>0) {
			int tmp = bfs(pos.getX()-1, pos.getY());
			if(tmp < minDist) {
				minDist = tmp;
				minDir = 3;
			}
		}
		
		if(minDist == Integer.MAX_VALUE)return -1;
		return minDir;
	}

	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EngineCore engineCore) {
		labirintGrid = new Grid(engineCore, 64, 64, MAZE_WIDTH, MAZE_HEIGHT);
		this.addViewComponent(labirintGrid);
		labirintGrid.setDistanceFromCamera(DISTANCE);
		
		playerGrid = new Grid(engineCore, 64, 64, MAZE_WIDTH, MAZE_HEIGHT);
		this.addViewComponent(playerGrid);
		playerGrid.setDistanceFromCamera(DISTANCE);
		
		ucitajLabirint(labirintGrid);
		dodajZvijezde();
		spawnajIgraca();
		PackmanGameJedac j = spawnajJedaca();
		j.setLives(2);
		
		
		MyFollowingCamera camera = new MyFollowingCamera(player);
		camera.setZoom(0.5f);
		camera.setCorrection(-600, -400);
		this.setCameraController(camera);
		
		bindKeys();
		
	}
	
	private PackmanGameJedac spawnajJedaca() {
		int x, y;
		while(true) {
			x = rnd.nextInt(MAZE_WIDTH);
			y = rnd.nextInt(MAZE_HEIGHT);
			if(playerGrid.get(x, y) == null && labirintGrid.get(x, y) instanceof PackmanGameZvijezda)break;
		}
		
		PackmanGameJedac jedac = new PackmanGameJedac(playerGrid, x, y);
		playerGrid.add(jedac);
		jedac.setBoundingRadius(34);
		jedaci.add(jedac);
		return jedac;
	}

	public void bindKeys() {
		bindKey(KeyEvent.VK_UP, "key_up");
		bindKey(KeyEvent.VK_DOWN, "key_down");
		bindKey(KeyEvent.VK_LEFT, "key_left");
		bindKey(KeyEvent.VK_RIGHT, "key_right");
		bindKey(KeyEvent.VK_O, "key_o");
	}
	
	private void dodajZvijezde() {
		for(int i=0; i < labirint.length; i++) {
			for(int j=0; j < labirint[i].length; j++) {
				if(rnd.nextFloat() > 0.2f)continue;
				
				if(labirint[i][j] == false) {
					PackmanGameZvijezda zvijezda = new PackmanGameZvijezda(labirintGrid, j, i);
					labirintGrid.add(zvijezda);
					zvjezde.add(zvijezda);
				}
			}
		}
		
	}
	
	private void spawnajIgraca() {
		int x, y;
		while(true) {
			x = rnd.nextInt(MAZE_WIDTH);
			y = rnd.nextInt(MAZE_HEIGHT);
			if(labirint[y][x] == false)break;
		}
		
		player = new PackmanGameIgrac(playerGrid, labirintGrid, x, y);
		playerGrid.add(player);
		player.setBoundingRadius(34);
	}

	void labirintDrawLine(boolean labirint[][], int x1, int y1, int x2, int y2) {
		if(x1 == x2) {
			if(y1 > y2) {
				int tmp = y1;
				y1 = y2;
				y2 = tmp;
			}
			for(int i=y1; i <= y2; i++) {
				labirint[i][x1] = true;
			}
		}else if(y1 == y2){
			if(x1 > x2) {
				int tmp = x1;
				x1 = x2;
				x2 = tmp;
			}
			for(int i=x1; i <= x2; i++) {
				labirint[y1][i] = true;
			}
		}
	}
	
	private void ucitajLabirint(Grid labirintGrid) {
		Random rand = new Random();
		
		int h = labirintGrid.getRows();
		int w = labirintGrid.getCols();
		
		labirint = new boolean[h][w];
		//Rubovi
		labirintDrawLine(labirint, 0, 0, 0, h-1);    // Lijevo okvir
		labirintDrawLine(labirint, 0, 0, w-1, 0);	// Gornji okvir
		labirintDrawLine(labirint, w-1, 0, w-1, h-1);	// Desni okvir
		labirintDrawLine(labirint, 0, h-1, w-1, h-1);	// Donji okvir

		//Horizontalne linije
		for(int i=2; i < h; i+=2) {
			for(int j=2; j < w-2; j++) {
				labirint[i][j] = true;
			}
		}

		//Tetris oblici
		int tetris = 5;
		for(int t=0; t < tetris; t++) {
			if (w > 4) {
				int lo = 3;
				int hi = h - 3;
				int i = rand.nextInt(hi-lo) + lo;
				while (i % 2 == 0) i = rand.nextInt(hi-lo) + lo;
				int low = 2;
				int high = w - 2;
				int point = rand.nextInt(high-low) + low;
				labirint[i][point] = true;
				if (i < h / 2) {
					labirint[i+1][point] = false;
					labirint[i+1][point+1] = false;
					labirint[i+1][point-1] = false;
				} else {
					labirint[i-1][point] = false;
					labirint[i-1][point+1] = false;
					labirint[i-1][point-1] = false;
				}
			}
		}
		
		//Okomiti prolazi
		for(int i=2; i < h; i+=2) {
			if (w > 4 && i < h-1) {
				int passages = rand.nextInt((w - 4)/2);
				for(int j=0; j < passages; j++) {
					int low = 2;
					int high = w - 2;
					int passage = rand.nextInt(high-low) + low;
					while (labirint[i][passage] == false || (labirint[i-1][passage] == true || labirint[i+1][passage] == true)) {
						passage = rand.nextInt(high-low) + low;
					}
					labirint[i][passage] = false;
				}
			}
		}
		
		for(int i=0; i < h; i++) {
			for(int j=0; j < w; j++) {
				if(labirint[i][j] == true) {
					LabirintBlock blk = new LabirintBlock(labirintGrid, j, i);
					labirintGrid.add(blk);
				}
			}
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
				CinematicSeven c = new CinematicSeven();
				c.setEngine(getEngine());
				getEngine().setEpisode(c);
			}
			
		});
	}
	
	private class JedacPojeden implements CollisionListener{

		@Override
		public void onCollision(BasicElement el1, BasicElement el2) {
			System.out.println("Pojeden");
			PackmanGameJedac jedac = (PackmanGameJedac)el2;
			jedac.remove();
			jedaci.remove(jedac);
			
			if(jedac.getLives() == 0 && jedaci.size() == 0) {
				pobjeda();
			}
		}
		
	}

}
