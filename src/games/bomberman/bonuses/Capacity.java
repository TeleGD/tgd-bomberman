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

public class Capacity extends Bonus {

	private boolean activated;
	private boolean deleted;
	private static Audio sound;
	private static Image sprite;

	static {
		sprite = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bonus_capacity.png");
		sound = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BONUS + "tataa.ogg");
	}

	public Capacity(int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.activated = false;
		this.deleted = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
	}

	public void activate(Player player) {
		if (!isActivated()) {
			this.activated = true;
			player.setBombCapacity(player.getBombCapacity() + 1);
			this.deleted = true;
			sound.playAsSoundEffect(1f, .4f, false);
		}
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public boolean isActivated() {
		return this.activated;
	}

}
