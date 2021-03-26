package games.bomberman.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.openal.Audio;

import app.AppLoader;

import games.bomberman.Case;
import games.bomberman.Player;
import games.bomberman.World;

public class TP extends Case {

	private Case twin;
	private static Audio sound;
	private static Image img;

	static {
		img = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "try.png");
		sound = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BONUS + "tp.ogg");
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
			sound.playAsSoundEffect(1f, .4f, false);
		}
	}

}
