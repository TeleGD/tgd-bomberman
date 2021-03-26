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

public class Shield extends Bonus {

	private boolean activated;
	private boolean deleted;
	private Player player;
	private int duration;
	private static Image sprite;
	private static Audio sound;

	static {
		sprite = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bonus_shield.png");
		sound = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BONUS + "tataa.ogg");
	}

	public Shield(int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.activated = false;
		this.deleted = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		duration -= delta;
		if (activated && (duration <= 0)) {
			activated = false;
			this.desactivate();
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
	}

	public void activate(Player player) {
		if (!isActivated()) {
			this.activated = true;
			player.setBouclier(true);
			this.player = player;
			duration = 30000;
			sound.playAsSoundEffect(1f, .4f, false);
		}
	}

	public void desactivate() {
		if (this.player.isBouclier()) {
			this.player.setBouclier(false);
		}
		this.deleted = true;
	}

	public boolean isActivated() {
		return this.activated;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

}
