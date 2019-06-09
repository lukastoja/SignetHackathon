package igra;

import Engine.EngineCore;

public class minigame {

	public static void main(String[] args) {
		EngineCore engine = new EngineCore(1280, 720, true);
		//EngineCore engine = new EngineCore(640, 480, true);
		engine.hideCursor();
		
		MainMenu game = new MainMenu();
		
		//LabirintGame game = new LabirintGame();
		//PackmanGame game = new PackmanGame();
		//DiggingGame game = new DiggingGame();
		//DashboardGame game = new DashboardGame();
		//
		//CinematicOne game = new CinematicOne();
		//CinematicTwo game = new CinematicTwo();
		game.setEngine(engine);
		
		engine.setEpisode(game);
		//engine.hideCursor();
		engine.start();


	}

}
