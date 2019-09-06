package games.bomberman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Bonus {

	private int caseX;
	private int caseY;
	private Image sprite;

	public Bonus(int caseX, int caseY) {
		this.caseX = caseX;
		this.caseY = caseY;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {};

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(sprite, caseX * 50, caseY * 50);
	}

	public void setSprite(Image s) {
		this.sprite = s;
	}

	public abstract boolean isActivated();

	public abstract void activate(Player player);

	public abstract boolean isDeleted();

}
