package games.bomberman;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import app.AppGame;
import app.AppInput;
import app.AppWorld;

import games.bomberman.bonuses.Accelerate;
import games.bomberman.bonuses.Capacity;
import games.bomberman.bonuses.Cooldown;
import games.bomberman.bonuses.Life;
import games.bomberman.bonuses.Range;
import games.bomberman.bonuses.Reverse;
import games.bomberman.bonuses.Shield;
import games.bomberman.bonuses.Slow;
import games.bomberman.bonuses.Teleport;
import games.bomberman.cases.Ground;

public class World extends AppWorld {

	public final static String GAME_FOLDER_NAME = "bomberman";
	public final static String DIRECTORY_SOUNDS = "sounds" + File.separator + GAME_FOLDER_NAME + File.separator;
	public final static String DIRECTORY_MUSICS = "musics" + File.separator + GAME_FOLDER_NAME + File.separator;
	public final static String DIRECTORY_IMAGES = "images" + File.separator + GAME_FOLDER_NAME + File.separator;
	public final static String DIRECTORY_SOUNDS_BONUS = DIRECTORY_SOUNDS + "bonuses" + File.separator;
	public final static String DIRECTORY_SOUNDS_BOMBS = DIRECTORY_SOUNDS + "bombs" + File.separator;
	private Board board;
	private int time;
	private int width;
	private int height;
	private Aside gui;
	private List<Player> players;
	private List<Bomb> bombs;

	private ArrayList<Player> morts;
	private static Music music;
	private static Sound poseBombe;
	private static Sound theEnd;

	static {
		try {
			music = new Music(DIRECTORY_MUSICS + "amazon_rain_2.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			poseBombe = new Sound(DIRECTORY_SOUNDS_BOMBS + "pose_bombe_3.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			theEnd = new Sound(DIRECTORY_SOUNDS_BOMBS + "criWilhelm.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public World(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {}

	@Override
	public void play(GameContainer container, StateBasedGame game) {
		AppGame appGame = (AppGame) game;
		AppInput appInput = (AppInput) container.getInput();
		appInput.clearKeyPressedRecord();
		appInput.clearControlPressedRecord();
		time = 30000;
		music.loop(1, .5f);
		int n = appGame.appPlayers.size();
		this.width = container.getWidth();
		this.height = container.getHeight();
		board = new Board(this, 13, 25);
		players = new ArrayList<Player>();
		bombs = new ArrayList<Bomb>();
		morts = new ArrayList<Player>();
		for (int i = 0; i < n; i++) {
			players.add(new Player(this, appGame.appPlayers.get(i), i));
		}
		gui = new Aside(this, (int) (board.getCaseSize() * board.cases.length));
	}

	@Override
	public void pause(GameContainer container, StateBasedGame game) {
		music.pause();
	}

	@Override
	public void resume(GameContainer container, StateBasedGame game) {
		music.resume();
	}

	public void end() {
		HashMap<Integer, Integer> classement = new HashMap<Integer, Integer>();
		for (int i = 0; i < morts.size(); i++) {
			classement.put(morts.get(i).getControllerID(), i);
		}
		System.out.println(classement.toString());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		for (Player p: players) {
			p.update(container, game, delta);
		}
		for (Bomb b: bombs) {
			b.update(delta);
		}
		for (int i = players.size() - 1; i >= 0; i--) {
			if (players.get(i).getLife() <= 0) {
				theEnd.play(1, 1f);
				morts.add(players.get(i));
				players.remove(i);
				if (players.size() == 0) {
					end();
				}
			}
		}
		for (int i = bombs.size() - 1; i >= 0; i--) {
			if (bombs.get(i).isDetruite()) {
				board.getCase(bombs.get(i).getI(), bombs.get(i).getJ()).setBomb(null);
				bombs.remove(i);
			}
		}
		time -= delta;
		if (time <= 0) {
			generateBonus();
			time = 30000;
		}
		board.update(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		board.render(container, game, context);
		gui.render(container, game, context);
		for (Bomb b: bombs) {
			b.render(container, game, context);
		}
		for (Player p: this.players) {
			p.render(container, game, context);
		}
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Bomb> getBombs() {
		return bombs;
	}

	private void generateBonus() {
		// Find the ground without bonus and player
		ArrayList<Case> freeCases = new ArrayList<Case>();
		for (Case c: board.getAllCases()) {
			if (c instanceof Ground && c.getBonus() == null && c.getBomb() == null) {
				boolean temp = true;
				for (Player p: this.players) {
					if (p.getI() == c.getI() && p.getJ() == c.getJ()) {
						temp = false;
					}
				}
				if (temp) {
					freeCases.add(c);
				}
			}
		}

		int i = (int) (Math.random() * freeCases.size());

		// Generate the bonus
		int k = (int) (Math.random() * 9);
		switch (k) {
		case 0:
			freeCases.get(i).setBonus(new Accelerate(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 1:
			freeCases.get(i).setBonus(new Life(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 2:
			freeCases.get(i).setBonus(new Reverse(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 3:
			freeCases.get(i).setBonus(new Capacity(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 4:
			freeCases.get(i).setBonus(new Shield(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 5:
			freeCases.get(i).setBonus(new Teleport(this, freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 6:
			freeCases.get(i).setBonus(new Cooldown(freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 7:
			freeCases.get(i).setBonus(new Slow(this, freeCases.get(i).getJ(), freeCases.get(i).getI()));
			break;
		case 8:
			freeCases.get(i).setBonus(new Range(freeCases.get(i).getJ(), freeCases.get(i).getI()));
		}
	}

	public void addBomb(int numJoueur, int i, int j, int porteep, int tpsRestantp) {
		poseBombe.play(1, .4f);
		bombs.add(new Bomb(this, numJoueur, i, j, porteep, tpsRestantp));
		board.getCase(i, j).setBomb(bombs.get(bombs.size() - 1));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
