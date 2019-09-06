package games.bomberman;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Aside {

	private List<Player> players;
	private int hauteur;
	private int longueur;
	private int hauteurPlateau;

	public Aside(World w, int hauteurPlateau) {
		this.players = w.getPlayers();
		this.hauteurPlateau = hauteurPlateau;
		this.hauteur = w.getHeight() - hauteurPlateau;
		this.longueur = w.getWidth();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (int i = 0; i < players.size(); i++) {
			context.setColor(Color.white);
			int right = (i * longueur - 1) / (players.size());
			context.drawLine(right, hauteurPlateau, right, hauteurPlateau + hauteur);
			context.setColor(players.get(i).getFillColor());
			context.drawString("Joueur " + (i + 1) + " : ", right, hauteurPlateau + 5);
			context.drawString("Vies : " + players.get(i).getLife(), right + 10, hauteurPlateau + 25);
			context.drawString("Bombes disponibles : " + players.get(i).getBombAvailable(), right + 10, hauteurPlateau + 40);
		}
	}

}
