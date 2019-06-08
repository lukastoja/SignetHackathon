package igra;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import Engine.ActionCompleted;
import Engine.BasicElement;
import Engine.Grid;
import Engine.GridBlock;
import Engine.Position;

public class DiggingGameKamen extends GridBlock {
	public static final float BRZINA_PADANJA = 4.0f;
	Grid playerGrid;
	boolean rezervacije[][];
	ArrayList<Position> mojeRezervacije = new ArrayList<>();

	/*
	 * TODO: ideja za sprijeciti bug sa rezervacijama
	 * 
	 */

	public DiggingGameKamen(Grid grid, Grid playerGrid, boolean rezervacije[][], int x, int y) {
		super(grid, x, y);
		this.playerGrid = playerGrid;
		Image img = Toolkit.getDefaultToolkit().getImage("/home/ecokeco/Asseti/rock.png");
		this.rezervacije = rezervacije;
		this.setImage(img);
	}

	public void padaj() {
		if (isMoving())
			return;
		Position mojaPozicija = this.getPosition();
		if (mojaPozicija.getY() == this.getGrid().getRows() - 1)
			return;
		BasicElement ispodMene = getGrid().get(mojaPozicija.getX(), mojaPozicija.getY() + 1);

		if (playerGrid.get(mojaPozicija.getX(), mojaPozicija.getY() + 1) == null) {
			if (ispodMene == null && rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX()] == false) {

				rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX()] = true;
				mojeRezervacije.add(new Position(mojaPozicija.getX(), mojaPozicija.getY() + 1));
				this.moveTo(mojaPozicija.getX(), mojaPozicija.getY() + 1, BRZINA_PADANJA, new ZavrsioPadati());
				return;
			}
		}

		if (mojaPozicija.getX() > 0 && ispodMene != null && ispodMene instanceof DiggingGameKamen) {
			if (playerGrid.get(mojaPozicija.getX() - 1, mojaPozicija.getY()) == null
					&& playerGrid.get(mojaPozicija.getX() - 1, mojaPozicija.getY() + 1) == null
					&& getGrid().get(mojaPozicija.getX() - 1, mojaPozicija.getY()) == null
					&& getGrid().get(mojaPozicija.getX() - 1, mojaPozicija.getY() + 1) == null
					&& rezervacije[mojaPozicija.getY()][mojaPozicija.getX() - 1] == false
					&& rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX() - 1] == false) {

				ArrayList<Position> pathToMove = new ArrayList<>();
				pathToMove.add(new Position(mojaPozicija.getX() - 1, mojaPozicija.getY()));
				pathToMove.add(new Position(mojaPozicija.getX() - 1, mojaPozicija.getY() + 1));

				rezervacije[mojaPozicija.getY()][mojaPozicija.getX() - 1] = true;
				rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX() - 1] = true;
				mojeRezervacije.add(new Position(mojaPozicija.getX() - 1, mojaPozicija.getY()));
				mojeRezervacije.add(new Position(mojaPozicija.getX() - 1, mojaPozicija.getY() + 1));
				this.moveAlongPath(pathToMove, BRZINA_PADANJA, new ZavrsioPadati());
				return;
			}
		}

		if (mojaPozicija.getX() < getGrid().getCols() - 1 && ispodMene != null
				&& ispodMene instanceof DiggingGameKamen) {
			if (playerGrid.get(mojaPozicija.getX() + 1, mojaPozicija.getY()) == null
					&& playerGrid.get(mojaPozicija.getX() + 1, mojaPozicija.getY() + 1) == null
					&& getGrid().get(mojaPozicija.getX() + 1, mojaPozicija.getY()) == null
					&& getGrid().get(mojaPozicija.getX() + 1, mojaPozicija.getY() + 1) == null
					&& rezervacije[mojaPozicija.getY()][mojaPozicija.getX() + 1] == false
					&& rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX() + 1] == false) {

				ArrayList<Position> pathToMove = new ArrayList<>();
				pathToMove.add(new Position(mojaPozicija.getX() + 1, mojaPozicija.getY()));
				pathToMove.add(new Position(mojaPozicija.getX() + 1, mojaPozicija.getY() + 1));

				rezervacije[mojaPozicija.getY()][mojaPozicija.getX() + 1] = true;
				rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX() + 1] = true;
				mojeRezervacije.add(new Position(mojaPozicija.getX() + 1, mojaPozicija.getY()));
				mojeRezervacije.add(new Position(mojaPozicija.getX() + 1, mojaPozicija.getY() + 1));
				this.moveAlongPath(pathToMove, BRZINA_PADANJA, new ZavrsioPadati());
				return;
			}
		}
	}

	private class ZavrsioPadati implements ActionCompleted {

		@Override
		public void actionCompleted() {
			for (Position pos : mojeRezervacije) {
				if (rezervacije[pos.getY()][pos.getX()] == false)
					System.out.println("Vec je osloboden");
				rezervacije[pos.getY()][pos.getX()] = false;
			}
			mojeRezervacije.clear();

			Position mojaPozicija = DiggingGameKamen.this.getPosition();
			if (mojaPozicija.getY() == getGrid().getRows() - 1)
				return;

			if (getGrid().get(mojaPozicija.getX(), mojaPozicija.getY() + 1) == null
					&& rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX()] == false) {

				rezervacije[mojaPozicija.getY() + 1][mojaPozicija.getX()] = true;
				mojeRezervacije.add(new Position(mojaPozicija.getX(), mojaPozicija.getY() + 1));
				DiggingGameKamen.this.moveTo(mojaPozicija.getX(), mojaPozicija.getY() + 1, BRZINA_PADANJA,
						new ZavrsioPadati());
			}
		}

	}

}
