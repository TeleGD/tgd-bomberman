package games.bomberman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.bomberman.bonuses.Accelerate;
import games.bomberman.bonuses.Capacity;
import games.bomberman.bonuses.Cooldown;
import games.bomberman.bonuses.Life;
import games.bomberman.bonuses.Range;
import games.bomberman.bonuses.Reverse;
import games.bomberman.bonuses.Shield;
import games.bomberman.bonuses.Slow;
import games.bomberman.bonuses.Teleport;
import games.bomberman.cases.DestructibleWall;
import games.bomberman.cases.Wall;

public class Bomb {

	private int x;
	private int y;
	private int i;
	private int j;
	private int portee;
	private int tpsRestant;
	private int numJoueur;
	private static Image sprite;
	private static Image bord;
	private static Image milieu;
	private static Image centre;
	private int arret[] = {
		0,
		0,
		0,
		0
	};
	private int tempsExplosion = 700;
	private boolean detruite = false;
	private boolean explose = false;
	private static Audio sound;
	private World w;

	static {
		sprite = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bombe.png");
		bord = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "fin_deflagration.png");
		milieu = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "deflagration.png");
		centre = AppLoader.loadPicture(World.DIRECTORY_IMAGES + "bombe_explose.png");
		sound = AppLoader.loadAudio(World.DIRECTORY_SOUNDS_BOMBS + "explo_forte.ogg");
	}

	public Bomb(World world, int numJoueur, int i, int j, int porteep, int tpsRestantp) {
		w = world;
		this.i = i;
		this.j = j;
		int[] XY = convertInXY(i, j);
		x = XY[0];
		y = XY[1];
		portee = porteep + 1;
		for (int k = 0; k < 4; k++) {
			arret[k] = portee;
		}
		tpsRestant = tpsRestantp;
	}

	public void update(int delta) {
		if (!explose) {
			tpsRestant -= delta;
			if (tpsRestant <= 0) {
				this.bombExplose();
			}
		} else {
			tempsExplosion -= delta;
			if (tempsExplosion <= 0) {
				detruite = true;
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		if (!explose) {
			context.drawImage(sprite, x, y);
		} else {
			context.drawImage(centre, x, y);
			bord.rotate(90);
			milieu.rotate(90);
			for (int k = 1; k < arret[0]; k++) {
				if (k == portee - 1) {
					context.drawImage(bord, x, y - 50 * k);
				} else {
					context.drawImage(milieu, x, y - 50 * k);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k = 1; k < arret[1]; k++) {
				if (k == portee - 1) {
					context.drawImage(bord, x + 50 * k, y);
				} else {
					context.drawImage(milieu, x + 50 * k, y);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k = 1; k < arret[2]; k++) {
				if (k == portee - 1) {
					context.drawImage(bord, x, y + 50 * k);
				} else {
					context.drawImage(milieu, x, y + 50 * k);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k = 1; k < arret[3]; k++) {
				if (k == portee - 1) {
					context.drawImage(bord, x - 50 * k, y);
				} else {
					context.drawImage(milieu, x - 50 * k, y);
				}
			}
		}
	}

	public int[] convertInXY(int i, int j) {
		int sizeCase = (int) w.getBoard().getCaseSize();
		return new int[] {
			j * sizeCase,
			i * sizeCase
		};
	}

	public int getNumJoueur() {
		return numJoueur;
	}

	public int getTpsRestant() {
		return tpsRestant;
	}

	public boolean isDetruite() {
		return detruite;
	}

	public void bombExplose() {
		for (int dir = 0; dir < 4; dir++) {
			boolean stop = false;
			int d = 0;
			while (d < portee && !stop) {
				int l = i;
				int c = j;
				switch (dir) {
				case 0:
					l -= d;
					break;
				case 1:
					c += d;
					break;
				case 2:
					l += d;
					break;
				case 3:
					c -= d;
				}
				explose = true;
				if (c < 0 || l < 0 || c > 24 || l > 12) {
					arret[dir] = d;
					stop = true;
				} else {
					Case ca = w.getBoard().getCase(l, c);
					if (ca instanceof DestructibleWall) {
						w.getBoard().destruct(l, c);
						if (Math.random() < .4) {
							double r = Math.random();
							/* répartition :
							 *
							 * acceleration : .20
							 * life : .05
							 * reverse : .05
							 * capacity : .1
							 * shield : .15
							 * teleport : .05
							 * cooldown : .1
							 * slow : .05
							 * range : .25
							 */
							if (r < .2) {
								w.getBoard().getCase(l, c).setBonus(new Accelerate(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .2 && r < .25) {
								w.getBoard().getCase(l, c).setBonus(new Life(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .25 && r < .30) {
								w.getBoard().getCase(l, c).setBonus(new Reverse(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .30 && r < .4) {
								w.getBoard().getCase(l, c).setBonus(new Capacity(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .4 && r < .55) {
								w.getBoard().getCase(l, c).setBonus(new Shield(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .55 && r < .60) {
								w.getBoard().getCase(l, c).setBonus(new Teleport(w, w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .60 && r < .70) {
								w.getBoard().getCase(l, c).setBonus(new Cooldown(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .7 && r < .75) {
								w.getBoard().getCase(l, c).setBonus(new Slow(w, w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
							if (r >= .75 && r < 1) {
								w.getBoard().getCase(l, c).setBonus(new Range(w.getBoard().getCase(l, c).getJ(), w.getBoard().getCase(l, c).getI()));
							}
						}
						arret[dir] = d + 1;
						stop = true;
					}
					if (ca instanceof Wall) {
						arret[dir] = d;
						stop = true;
					}
					for (Player p: w.getPlayers()) {
						if (p.getI() == l && p.getJ() == c) {
							p.takeDamage();
						}
					}
					for (Bomb b: w.getBombs()) {
						if (b.getI() == l && b.getJ() == c && !b.isExplosed()) {
							b.bombExplose();
						}
					}
				}
				d++;
			}
		}
		sound.playAsSoundEffect(1f, .8f, false);
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

	public boolean isExplosed() {
		return explose;
	}

}
