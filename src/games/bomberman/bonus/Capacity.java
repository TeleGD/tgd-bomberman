package games.bomberman.bonus;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.Player;
import games.bomberman.World;

public class Capacity extends Bonus{
	
	private boolean activated, deleted;
	private static Sound sound;
	private static Image sprite;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES+"bonus_capacity.png");
			sound = new Sound(World.DIRECTORY_SOUNDS+"bonus"+File.separator+"tataa.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Capacity(int caseX, int caseY) {
		super(caseX, caseY);
		super.setSprite(sprite);
		this.activated = false;
		this.deleted = false;
	}
	
	public void activate(Player player) {
		if (!isActivated()) {
			this.activated = true;
			
			player.setBombCapacity(player.getBombCapacity()+1);
			
			this.deleted = true;

			sound.play(1, (float) 0.4);
		}
	}
	
	public boolean isDeleted() {
		return this.deleted;
	}
	public boolean isActivated() {
		return this.activated;
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {

	}	
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
	}
}