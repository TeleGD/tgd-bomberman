package games.bomberman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import games.bomberman.board.cases.Case;
import games.bomberman.board.cases.DestructibleWall;
import games.bomberman.board.cases.Wall;
import games.bomberman.bonus.Accelerate;
import games.bomberman.bonus.Capacity;
import games.bomberman.bonus.Cooldown;
import games.bomberman.bonus.Life;
import games.bomberman.bonus.Range;
import games.bomberman.bonus.Reverse;
import games.bomberman.bonus.Shield;
import games.bomberman.bonus.Slow;
import games.bomberman.bonus.Teleport;

public class Bomb {

	private int x;
	private int y;
	private int i, j;
	private int portee;
	private int tpsRestant;
	private int numJoueur;
	private static Image sprite,bord,milieu,centre;
	private int arret[]= {0,0,0,0};
	private int tempsExplosion=700;
	private boolean detruite = false,explose=false;
	private static Sound sound;
	private World w;

	static {
		try {
			sprite = new Image(World.DIRECTORY_IMAGES+"bombe.png");
			bord = new Image(World.DIRECTORY_IMAGES+"fin_deflagration.png");
			milieu = new Image(World.DIRECTORY_IMAGES+"deflagration.png");
			centre = new Image(World.DIRECTORY_IMAGES+"bombe_explose.png");
			sound = new Sound(World.DIRECTORY_SOUNDS_BOMBS+"explo_forte.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Bomb(World world, int numJoueur,int i,int j,int porteep,int tpsRestantp) {
		w=world;
		this.i = i;
		this.j = j;
		int[] XY = convertInXY(i,j);
		x = XY[0];
		y = XY[1];
		portee=porteep+1;
		for (int k=0;k<4;k++) {arret[k]=portee;}
		tpsRestant=tpsRestantp;
	}

	public int[] convertInXY(int i, int j) {
		int sizeCase = (int) w.getBoard().getCaseSize();
		return new int[] {j * sizeCase, i * sizeCase};
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

	public void update(int delta) {
		if (!explose) {
			tpsRestant-=delta;
			if(tpsRestant<=0) {
				this.BombExplose();
			}
		} else {
			tempsExplosion-=delta;
			if (tempsExplosion<=0) {
				detruite = true;
			}
		}

	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		if (!explose) {
		context.drawImage(sprite, x, y);
		} else {

			context.drawImage(centre,x,y);
			bord.rotate(90);
			milieu.rotate(90);
			for (int k=1;k<arret[0];k++) {
				if (k==portee-1) {
					context.drawImage(bord, x, y-50*k);
				} else {
					context.drawImage(milieu, x, y-50*k);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k=1;k<arret[1];k++) {
				if (k==portee-1) {
					context.drawImage(bord, x+50*k, y);
				} else {
					context.drawImage(milieu, x+50*k, y);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k=1;k<arret[2];k++) {
				if (k==portee-1) {
					context.drawImage(bord, x, y+50*k);
				} else {
					context.drawImage(milieu, x, y+50*k);
				}
			}
			bord.rotate(90);
			milieu.rotate(90);
			for (int k=1;k<arret[3];k++) {
				if (k==portee-1) {
					context.drawImage(bord, x-50*k, y);
				} else {
					context.drawImage(milieu, x-50*k, y);
				}
			}

		}
	}

	public void BombExplose() {

		for (int dir=0;dir<4;dir++) {
			boolean stop = false;
			int d = 0;
			while (d<portee && !stop) {
				int l=i,c=j;
				switch (dir) {
				case 0 :
					l-=d;
					break;
				case 1 :
					c+=d;
					break;
				case 2 :
					l+=d;
					break;
				case 3 :
					c-=d;
				}
				explose = true;
				if ( c<0 || l<0 || c>24 || l>12 ) {
					arret[dir] = d;
					stop = true;
				} else {
					Case ca = w.getBoard().getCase(l, c);
					if (ca instanceof DestructibleWall) {
						w.getBoard().destruct(l, c);
						if (Math.random()<0.4) {
							double r = Math.random();
							/* répartition :
							 *
							 * acceleration  : 0.20
							 * life : 0.05
							 * reverse : 0.05
							 * capacity : 0.1
							 * shield : 0.15
							 * teleport : 0.05
							 * cooldown : 0.1
							 * slow : 0.05
							 * range : 0.25
							 */
							if (r<0.2) {
								w.getBoard().getCase(l, c).setBonus(new Accelerate(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.2 && r<0.25) {
								w.getBoard().getCase(l, c).setBonus(new Life(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.25 && r<0.30) {
								w.getBoard().getCase(l, c).setBonus(new Reverse(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.30 && r<0.4) {
								w.getBoard().getCase(l, c).setBonus(new Capacity(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.4 && r<0.55) {
								w.getBoard().getCase(l, c).setBonus(new Shield(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.55 && r<0.60) {
								w.getBoard().getCase(l, c).setBonus(new Teleport(w, w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.60 && r<0.70) {
								w.getBoard().getCase(l, c).setBonus(new Cooldown(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.7 && r<0.75) {
								w.getBoard().getCase(l, c).setBonus(new Slow(w, w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}
							if (r>=0.75 && r<1) {
								w.getBoard().getCase(l, c).setBonus(new Range(w.getBoard().getCase(l, c).getJ(),w.getBoard().getCase(l, c).getI()));
							}

						}
						arret[dir] = d+1;
						stop = true;
					}
					if (ca instanceof Wall) {
						arret[dir] = d;
						stop = true;
					}
					for (Player p : w.getPlayers()) {
						if (p.getI()==l && p.getJ()==c) {
							p.takeDamage();
						}
					}
					for (Bomb b : w.getBombs()) {
						if (b.getI()==l && b.getJ()==c && !b.isExplosed()) {
							b.BombExplose();
						}
					}
				}
				d++;
			}
		}
		sound.play(1, (float) 0.8);

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
