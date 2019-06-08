package igra;

import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.Grid;
import Engine.StaticCamera;

public class LabirintGame extends EngineEpisode{
	public static final int MAZE_WIDTH = 40;
	public static final int MAZE_HEIGHT = 40;
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
		StaticCamera camera = new StaticCamera(0, 0);
		this.setCameraController(camera);
		
		labirintGrid = new Grid(engineCore, 64, 64, MAZE_WIDTH, MAZE_HEIGHT);
		this.addViewComponent(labirintGrid);
		
		labirintGrid.setDistanceFromCamera(0.2f);
		
	}

}
