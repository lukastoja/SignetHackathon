package igra;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.Grid;
import Engine.StaticCamera;
import java.util.Random;

public class PackmanGame extends EngineEpisode{

	@Override
	public void mainLoop(long renderDeltaTime) {
		
	}

	@Override
	public void keyPressed(int key, int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EngineCore engineCore) {
		StaticCamera camera = new StaticCamera(0, 0);
		this.setCameraController(camera);
		
		Grid labirintGrid = new Grid(engineCore, 64, 64, 28, 31);
		this.addViewComponent(labirintGrid);
		ucitajLabirint(labirintGrid);
		
		labirintGrid.setDistanceFromCamera(0.2f);
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
		
		boolean labirint[][] = new boolean[h][w];
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

}
