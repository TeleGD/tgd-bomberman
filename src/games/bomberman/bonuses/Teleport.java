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

public class Teleport extends Bonus {

	private boolean activated;
	private boolean deleted;
	private static Sound sound;
	private static Image sprite;
	private World w;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES + "bonus_teleport.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BONUS + "tp.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Teleport(World w, int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.w = w;
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
			int i;
			int j;
			do {
				i = (int) (Math.random() * w.getBoard().getDim()[0]);
				j = (int) (Math.random() * w.getBoard().getDim()[1]);
			} while (!w.getBoard().getCase(i, j).isPassable());
			player.setIJ(i, j);
			sound.play(1, .4f);
			this.deleted = true;
		}
	}

	public boolean isActivated() {
		return this.activated;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

}
