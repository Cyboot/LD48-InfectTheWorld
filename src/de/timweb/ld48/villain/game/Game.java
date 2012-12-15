package de.timweb.ld48.villain.game;

import java.awt.Graphics;

public class Game {
	public static final Game g = new Game();

	private Level currentLevel;
	private int levelNr = 1;

	public Game() {
		currentLevel = new BodyLevel(levelNr);

	}

	public void update(int delta) {
		//TODO: 09. Animation zwischen Leveln
		
		if (currentLevel.isFinished()) {
			levelNr++;

			if (levelNr == 2 || levelNr == 3)
				currentLevel = new BodyLevel(levelNr);
			else
				currentLevel = new WorldLevel();
		}
		currentLevel.update(delta);

	}

	public void render(Graphics g) {

		currentLevel.render(g);
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}
	
}
