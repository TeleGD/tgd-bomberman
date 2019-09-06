package games.bomberman;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.cases.DestructibleWall;
import games.bomberman.cases.Ground;
import games.bomberman.cases.TP;
import games.bomberman.cases.Wall;

public class Board {

	// taille case 50x50
	private float caseSize = 50;
	public Case[][] cases;
	private World w;

	// Dans un permier temps, generation non dependante d'un niveau
	public Board(World w, int imax, int jmax) {
		this.w = w;
		cases = new Case[imax][jmax];
		// j sur x et i sur y
		// bords safes
		cases[0][0] = new Ground(w, 0, 0);
		cases[0][1] = new Ground(w, 0, 1);
		cases[1][0] = new Ground(w, 1, 0);
		cases[0][jmax - 1] = new Ground(w, 0, jmax - 1);
		cases[1][jmax - 1] = new Ground(w, 1, jmax - 1);
		cases[0][jmax - 2] = new Ground(w, 0, jmax - 2);
		cases[imax - 1][0] = new Ground(w, imax - 1, 0);
		cases[imax - 1][1] = new Ground(w, imax - 1, 1);
		cases[imax - 2][0] = new Ground(w, imax - 2, 0);
		cases[imax - 1][jmax - 1] = new Ground(w, imax - 1, jmax - 1);
		cases[imax - 2][jmax - 1] = new Ground(w, imax - 2, jmax - 1);
		cases[imax - 1][jmax - 2] = new Ground(w, imax - 1, jmax - 2);
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				if (i % 2 == 1 && j % 2 == 1 && cases[i][j] == null) {
					cases[i][j] = new Wall(w, i, j);
				} else {
					if (cases[i][j] == null) {
						if (Math.random() > .7) {
							cases[i][j] = new Ground(w, i, j);
						}
						else cases[i][j] = new DestructibleWall(w, i, j);
					}
				}
			}
		}
		// For each Board created their is a pair of tp cases
		int i = (int) (Math.random() * imax);
		int j = (int) (Math.random() * jmax);
		int k = (int) (Math.random() * imax);
		int l = (int) (Math.random() * jmax);
		cases[i][j] = new TP(w, i, j, cases[k][l]);
		cases[k][l] = new TP(w, k, l, cases[i][j]);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Case[] c: cases) {
			for (Case ca: c) {
				ca.update(container, game, delta);
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Case[] c: cases) {
			for (Case ca: c) {
				ca.render(container, game, context);
			}
		}
	}

	/*
	 * 0 : min x 1 : max x 2 : min y 3 : max y
	 */
	public float[] getlimits() {
		float[] result = new float[4];
		result[0] = 0;
		result[1] = cases.length * caseSize;
		result[2] = 0;
		result[3] = cases[0].length * caseSize;
		return result;
	}

	public Case getCase(int i, int j) {
		return cases[i][j];
	}

	public Case[][] getCases() {
		return cases;
	}

	public Integer[] getDim() {
		return new Integer[] {
			cases.length,
			cases[0].length
		};
	}

	public void destruct(int i, int j) {
		if (cases[i][j] instanceof DestructibleWall) {
			cases[i][j] = new Ground(w, i, j);
		}
	}

	public ArrayList<Case> getAllCases() {
		ArrayList<Case> result = new ArrayList<Case>();
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[0].length; j++) {
				result.add(cases[i][j]);
			}
		}
		return result;
	}

	public float getCaseSize() {
		return caseSize;
	}

}
