package igra;

import Engine.EngineCore;

public class minigame {

	public static void main(String[] args) {
		EngineCore engine = new EngineCore(1280, 720, true);
		
		//LabirintGame game = new LabirintGame();
		PackmanGame game = new PackmanGame();
		game.setEngine(engine);
		
		engine.setEpisode(game);
		//engine.hideCursor();
		engine.start();


	}

}
