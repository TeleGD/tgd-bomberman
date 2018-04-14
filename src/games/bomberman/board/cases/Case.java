package games.bomberman.board.cases;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.Player;
import games.bomberman.bonus.Bonus;

public abstract class Case {

	protected boolean passable;
	protected Image img;
	protected int i,j;
	protected float size=50;
	protected Bonus bonus;

	public Case(int i,int j,Image img,boolean passable) {
		this.i=i;
		this.j=j;
		this.img=img;
		this.passable=passable;
		this.bonus=null;
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(img, j*50, i*50);
		if(bonus!=null) {
			if(!bonus.isActivated()) {
				bonus.render(container, game, context);
			}
		}
	}

	public void getBonusAction(Player p) {
		if(bonus!=null)
			bonus.activate(p);
	}

	public void getAction(Player p) {
		getBonusAction(p);
	}

	public Bonus getBonus() {
		return bonus;
	}

	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		if (bonus!=null) {
			bonus.update(container, game, delta);
			if (bonus.isDeleted()) {
				bonus = null;
			}
		}
	}


	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}



}
