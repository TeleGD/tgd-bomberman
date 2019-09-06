package games.bomberman.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import games.bomberman.Case;
import games.bomberman.Player;
import games.bomberman.World;

public class TP extends Case {

	private Case twin;
	private static Sound sound;
	private static Image img;

	static {
		try {
			img = new Image(World.DIRECTORY_IMAGES + "try.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BONUS + "tp.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public TP(World w, int i, int j, Case twin) {
		super(w, i, j, img, true);
		this.twin = twin;
	}

	public void getAction(Player p) {
		super.getAction(p);
		if (p.isTPable()) {
			p.setTPable(false);
			p.setIJ(twin.getI(), twin.getJ());
			sound.play(1, .4f);
		}
	}

}
