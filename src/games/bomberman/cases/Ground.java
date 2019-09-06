package games.bomberman.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import games.bomberman.Case;
import games.bomberman.World;

public class Ground extends Case {

	private static Image img;

	static {
		try {
			img = new Image(World.DIRECTORY_IMAGES + "Ground.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Ground(World w, int i, int j) {
		super(w, i, j, img, true);
	}

}
