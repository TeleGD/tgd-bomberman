package games.bomberman.bonuses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.Bonus;
import games.bomberman.Player;
import games.bomberman.World;

public class DefinitiveAccelerate extends Bonus {

	private boolean activated;
	private boolean deleted;
	private static Image sprite;
	private static Sound sound;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES + "bonus_defAccelerate.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BONUS + "sncf.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public DefinitiveAccelerate(int caseX, int caseY) {
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
			player.setSpeed(player.getSpeed() * 1.10f);
			this.deleted = true;
			sound.play(1, .4f);
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isActivated() {
		return this.activated;
	}

}
