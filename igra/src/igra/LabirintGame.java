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
	Grid labirintGrid;
	LabirintIgrac player;
	LabirintShip ship;
	ArrayList<LabirintBlock> blocks;

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
		
		int h = MAZE_HEIGHT * 2 + 1;
		int w = MAZE_WIDTH * 4 + 1;
		
		labirintGrid = new Grid(engineCore, 64, 64, w, h);
		this.addViewComponent(labirintGrid);
		generirajLabirint(labirintGrid);
		
		player = new LabirintIgrac(labirintGrid, 1, 1);
		labirintGrid.add(player);
		
		FollowingCamera camera = new FollowingCamera(player);
		this.setCameraController(camera);
		camera.setZoom(0.5f);
		
		bindKeys();
		
		ship = new LabirintShip(labirintGrid, w-2, h-2);
		labirintGrid.add(ship);
	}
	
	private void generirajLabirint(Grid labirintGrid) {	
		
		int h = MAZE_HEIGHT * 2 + 1;
		int w = MAZE_WIDTH * 4 + 1;
		
		MyMaze maze = new MyMaze(MAZE_WIDTH, MAZE_HEIGHT);
	    char[][] grid = maze.getGrid();
	    
	    for(int i=0; i < grid.length; i++) {
			for(int j=0; j < grid[i].length; j++) {
				if(grid[i][j] == 'X') {
					LabirintBlock blk = new LabirintBlock(labirintGrid, j, i);
					labirintGrid.add(blk);
					//blocks.add(blk);
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
	
	private class PlayerStigao implements ActionCompleted{
		@Override
		public void actionCompleted() {
			Position pos = player.getPosition();
			Position posShip = ship.getPosition();
			if (pos.getX() == posShip.getX()-1 && pos.getY() == posShip.getY()
					|| pos.getX() == posShip.getX() && pos.getY() == posShip.getY()-1) {
				System.out.println("Stigao!");
				System.exit(0); 
			}
		}
	}
}
