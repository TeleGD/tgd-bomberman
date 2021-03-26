package games.bomberman.cases;

import org.newdawn.slick.Image;

import app.AppLoader;

import games.bomberman.Case;
import games.bomberman.World;

public class DestructibleWall extends Case {

	private static Image img;

	static {
		img = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "Rocher.png");
	}

	public DestructibleWall(World w, int i, int j) {
		super(w, i, j, img, false);
	}

}
