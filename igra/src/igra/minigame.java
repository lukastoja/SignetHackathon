package igra;

import Engine.EngineCore;

public class minigame {

	public static void main(String[] args) {
		EngineCore engine = new EngineCore(640, 480, true);
		
		LabirintGame game = new LabirintGame();
		game.setEngine(engine);
		
		engine.setEpisode(game);
		//engine.hideCursor();
		engine.start();


	}

}
