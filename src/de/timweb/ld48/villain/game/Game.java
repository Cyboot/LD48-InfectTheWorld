package de.timweb.ld48.villain.game;

import java.awt.Graphics;

public class Game {
	public static final Game g = new Game();

	private Level currentLevel;
	private int levelNr = 1;

	public Game() {

		currentLevel = new WorldLevel();
		// currentLevel = new AnimationLevel(levelNr);
	}

	public void update(int delta) {

		if (currentLevel.isFinished()) {
			// Player won --> no more Level
			if (currentLevel instanceof WinLevel) {
				return;
			}

			// Player finished Worldlevel --> win
			if (currentLevel instanceof WorldLevel) {
				currentLevel = new WinLevel();
			}

			if (currentLevel instanceof AnimationLevel) {
				if (levelNr > 3) {
					currentLevel = new WorldLevel();
				} else {
					currentLevel = new BodyLevel(levelNr++);
				}
			} else {
				currentLevel = new AnimationLevel(levelNr);
				return;
			}

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
