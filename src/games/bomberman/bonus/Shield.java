package games.bomberman.bonus;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.Player;
import games.bomberman.World;

public class Shield extends Bonus{
	private boolean activated, deleted;
	private Player player;
	private int duration;
	private static Image sprite;
	private static Sound sound;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES+"bonus_shield.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BONUS+"tataa.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Shield(int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.activated = false;
		this.deleted = false;
	}

	public void activate(Player player) {
		if (!isActivated()) {
			this.activated = true;

			player.setBouclier(true);

			this.player = player;
			duration=30000;

			sound.play(1, (float) 0.4);
		}
	}

	public void desactivate() {
		if (this.player.isBouclier())
			this.player.setBouclier(false);
		this.deleted = true;
	}

	public boolean isActivated() {
		return this.activated;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		duration-=delta;
		if (activated && (duration<=0)) {
			activated=false;
			this.desactivate();
		}
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
	}
}
