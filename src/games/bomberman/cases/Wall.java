package games.bomberman.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import games.bomberman.Case;
import games.bomberman.World;

public class Wall extends Case {

	private static Image img;

	static {
		try {
			img = new Image(World.DIRECTORY_IMAGES + "Wall.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Wall(World w, int i, int j) {
		super(w, i, j, img, false);
	}

}
