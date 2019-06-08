package igra;

import Engine.CenterCamera;
import Engine.EngineCore;
import Engine.EngineEpisode;
import Engine.Grid;
import Engine.StaticCamera;

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
		boolean labirint[][] = new boolean[31][28];
		labirintDrawLine(labirint, 0, 0, 0, 30);    // Lijevo okvir
		labirintDrawLine(labirint, 0, 0, 27, 0);	// Gornji okvir
		labirintDrawLine(labirint, 27, 0, 27, 30);	// Desni okvir
		labirintDrawLine(labirint, 0, 30, 27, 30);	// Donji okvir
		
		//Gornja lijeva kucica
		labirintDrawLine(labirint, 2, 2, 5, 2);
		labirintDrawLine(labirint, 2, 3, 5, 3);
		
		// Crta gore lijevo
		labirintDrawLine(labirint, 2, 5, 5, 5);
		
		labirintDrawLine(labirint, 0, 7, 5, 7);
		labirintDrawLine(labirint, 5, 7, 5, 21);
		
		//Gornja lijeva kucica 2
		labirintDrawLine(labirint, 7, 2, 11, 2);
		labirintDrawLine(labirint, 7, 3, 11, 3);
		
		for(int i=0; i < 31; i++) {
			for(int j=0; j < 28; j++) {
				if(labirint[i][j] == true) {
					LabirintBlock blk = new LabirintBlock(labirintGrid, j, i);
					labirintGrid.add(blk);
				}
			}
		}
	}

}
