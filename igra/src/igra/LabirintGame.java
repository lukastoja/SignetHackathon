package igra;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Engine.ActionCompleted;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.FollowingCamera;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;
import Engine.StaticCamera;

public class LabirintGame extends EngineEpisode{
	public static final int MAZE_WIDTH = 10;
	public static final int MAZE_HEIGHT = 10;
	final static float SPEED = 5.5f;
	private int w, h;
	Grid labirintGrid;
	LabirintIgrac player;
	LabirintShip ship;
	ArrayList<LabirintBlock> blocks = new ArrayList();

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
		
	}

	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EngineCore engineCore) {
		
		h = MAZE_HEIGHT * 2 + 1;
		w = MAZE_WIDTH * 4 + 1;
		
		labirintGrid = new Grid(engineCore, 64, 64, w, h);
		this.addViewComponent(labirintGrid);
		generirajLabirint(labirintGrid);
		
		player = new LabirintIgrac(labirintGrid, 1, 1);
		labirintGrid.add(player);
		
		FollowingCamera camera = new FollowingCamera(player);
		this.setCameraController(camera);
		camera.setZoom(0.4f);
		
		bindKeys();
		
		ship = new LabirintShip(labirintGrid, w-2, h-2);
		labirintGrid.add(ship);
	}
	
	private void generirajLabirint(Grid labirintGrid) {	
		
		MyMaze maze = new MyMaze(MAZE_WIDTH, MAZE_HEIGHT);
	    char[][] grid = maze.getGrid();
	    
	    for(int i=0; i < grid.length; i++) {
			for(int j=0; j < grid[i].length; j++) {
				if(grid[i][j] == 'X') {
					LabirintBlock blk = new LabirintBlock(labirintGrid, j, i);
					labirintGrid.add(blk);
					blocks.add(blk);
				}
			}
		}
	}
	
	public void bindKeys() {
		bindKey(KeyEvent.VK_UP, "key_up");
		bindKey(KeyEvent.VK_DOWN, "key_down");
		bindKey(KeyEvent.VK_LEFT, "key_left");
		bindKey(KeyEvent.VK_RIGHT, "key_right");
	}
	
	private double distanceBetweenPoints(
	  double x1, 
	  double y1, 
	  double x2, 
	  double y2) {       
	    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
	private class PlayerStigao implements ActionCompleted{
		@Override
		public void actionCompleted() {
			Position posPlayer = player.getPosition();
			Position posShip = ship.getPosition();
			int playerX = posPlayer.getX();
			int playerY = posPlayer.getY();
			if (playerX == posShip.getX()-1 && playerY == posShip.getY()
					|| playerX == posShip.getX() && playerY == posShip.getY()-1) {
				System.out.println("Stigao!");
				System.exit(0); 
			}
			for (LabirintBlock blk: blocks) {
				Position blkPos = blk.getPosition();
				int blkPosX = blkPos.getX();
				int blkPosY = blkPos.getY();
				float dist = (float) distanceBetweenPoints(blkPosX, blkPosY, playerX, playerY);
				float maxDist = (float) distanceBetweenPoints(0, 0, w, h);
				System.out.println(dist / maxDist);
				blk.setTransparancy((float) Math.min(1.0 - (float) Math.pow(0.00002, dist / maxDist), 1.0f) );
			}
		}
	}
}
