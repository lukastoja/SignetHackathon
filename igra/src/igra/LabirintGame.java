package igra;

import java.util.Random;

import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.Grid;
import Engine.StaticCamera;

public class LabirintGame extends EngineEpisode{
	public static final int MAZE_WIDTH = 10;
	public static final int MAZE_HEIGHT = 10;
	Grid labirintGrid;
	
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
		
		int h = MAZE_HEIGHT * 2 + 1;
		int w = MAZE_WIDTH * 4 + 1;
		
		StaticCamera camera = new StaticCamera(0, 0);
		this.setCameraController(camera);
		
		labirintGrid = new Grid(engineCore, 64, 64, w, h);
		this.addViewComponent(labirintGrid);
		generirajLabirint(labirintGrid);
		
		labirintGrid.setDistanceFromCamera(0.2f);
		
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
				}
			}
		}
	}
}
