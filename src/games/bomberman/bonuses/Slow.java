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

public class Slow extends Bonus {

	private boolean activated;
	private boolean deleted;
	private Player player;
	private int duration;
	private static Sound sound;
	private static Image sprite;
	private World w;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES + "bonus_slow.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BONUS + "gary.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Slow(World world, int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.w = world;
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
		if (!activated) {
			this.activated = true;
			for (Player p: w.getPlayers()) {
				if (!p.equals(player)) {
					p.setSpeed(.5f);
				}
			}
			duration = 7000;
			this.player = player;
			sound.play(1, .4f);
		}
	}

	public void desactivate() {
		for (Player p: w.getPlayers()) {
			if (!p.equals(player)) {
				p.setSpeed(1);
			}
		}
		deleted = true;
	}

	public boolean isActivated() {
		return this.activated;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

}
