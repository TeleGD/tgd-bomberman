package games.bomberman.bonuses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.bomberman.Bonus;
import games.bomberman.Player;
import games.bomberman.World;

public class Range extends Bonus {

	private boolean activated;
	private boolean deleted;
	private boolean add;
	private static Image sprite0;
	private static Image sprite1;
	private static Audio sound0;
	private static Audio sound1;

	static {
		sprite0 = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bonus_range0.png");
		sprite1 = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bonus_range1.png");
		sound0 = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BONUS + "charger.ogg");
		sound1 = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BONUS + "bemol.ogg");
	}

	public Range(int caseX, int caseY) {
		super(caseX, caseY);
		add = Math.random() < .7;
		super.setSprite(add ? sprite0: sprite1);
		this.activated = false;
		this.deleted = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
	}

	public void activate(Player player) {
		if (!activated) {
			this.activated = true;
			if (add) {
				player.addRange(1);
				sound0.playAsSoundEffect(1f, 1f, false);
			} else {
				player.addRange(-1);
				sound1.playAsSoundEffect(1f, .4f, false);
			}
			this.deleted = true;
		}
	}

	public boolean isDeleted() {
		return this.deleted;
	}
	public boolean isActivated() {
		return this.activated;
	}

}
