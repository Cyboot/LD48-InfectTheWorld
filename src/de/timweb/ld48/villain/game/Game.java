package de.timweb.ld48.villain.game;

import java.awt.Graphics;

import de.timweb.ld48.villain.level.AnimationLevel;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.level.StartLevel;
import de.timweb.ld48.villain.level.WorldLevel;

public class Game {
	public static final Game g = new Game();

	private Level currentLevel;
	private int levelNr = 1;
	private int tutorial = 0;

	public Game() {

		// currentLevel = new BodyLevel(levelNr);
		currentLevel = new StartLevel(tutorial++);
	}

	public void update(int delta) {

		if (currentLevel.isFinished()) {
			if (tutorial < 2) {
				currentLevel = new StartLevel(tutorial++);
				return;
			}

			if (levelNr == 4) {
				currentLevel = new WorldLevel();
				return;
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
