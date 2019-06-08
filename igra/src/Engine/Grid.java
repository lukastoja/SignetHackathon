package Engine;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Grid extends ViewComponent{
	ArrayList<GridBlock> gridBlocks = new ArrayList<>();
	GridBlock cache[][];
	int width, height;
	int cols, rows;
	
	public Grid(EngineCore core, int w, int h, int cols, int rows) {
		super(core);
		this.width = w;
		this.height = h;
		this.cols = cols;
		this.rows = rows;
		
		cache = new GridBlock[rows][];
		for(int i=0; i < rows; i++) {
			cache[i] = new GridBlock[cols];
		}
	}
	
	public void add(GridBlock blk) {
		if(get(blk.origx, blk.origy) != null)System.out.println("Vec je zauzeta ta pozicija u mrezi " + new Position(blk.origx, blk.origy));
		gridBlocks.add(blk);
	}
	
	public GridBlock get(int x, int y) {
		//return cache[y][x];
		
		for(int i=0; i < gridBlocks.size(); i++) {
			GridBlock gridBlock = gridBlocks.get(i);
			if(gridBlock.origx == x && gridBlock.origy == y)return gridBlock;
		}
		return null;
		
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	@Override
	public void draw(Graphics2D g2d, JFrame frame, CameraController cameraCtl) {
		Position offset = this.getDrawingPosition();
		Position cameraPosition = cameraCtl.getPosition();
		float zoom = cameraCtl.getZoom();
		
		int cameraOffsetX = (int) (cameraPosition.getX()*getDistanceFromCamera());
		int cameraOffsetY = (int) (cameraPosition.getY()*getDistanceFromCamera());
		
		float scaleFactor = zoom*getDistanceFromCamera();
		g2d.scale(scaleFactor, scaleFactor);
		
		for(int i=0; i < gridBlocks.size(); i++) {
			GridBlock blk = gridBlocks.get(i);
			
			if(blk.removeFlag == true) {
				gridBlocks.remove(i);
				i--;
				continue;
			}
			
			Position pos = blk.getDrawingPosition();
			
			Composite oldComposite = g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - blk.transparency);
			g2d.setComposite(ac);
			g2d.drawImage(blk.getImage(), pos.getX() + offset.x - cameraOffsetX + getEngine().getWidth()/2, pos.getY() + offset.y - cameraOffsetY + getEngine().getHeight()/2, frame);
			
			g2d.setComposite(oldComposite);
		}
		
		g2d.scale(1.0f/scaleFactor, 1.0f/scaleFactor);
	}
	
	
}
