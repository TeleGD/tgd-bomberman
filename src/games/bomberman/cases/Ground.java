package games.bomberman.cases;

import org.newdawn.slick.Image;

import app.AppLoader;

import games.bomberman.Case;
import games.bomberman.World;

public class Ground extends Case {

	private static Image img;

	static {
		img = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "Ground.png");
	}

	public Ground(World w, int i, int j) {
		super(w, i, j, img, true);
	}

}
