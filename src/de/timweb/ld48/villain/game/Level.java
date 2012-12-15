package de.timweb.ld48.villain.game;

import java.awt.Graphics;

public abstract class Level {

	private boolean finished;

	public abstract void update(int delta);

	public abstract void render(Graphics g);
	
	protected void finish(){
		finished = true;
		SelectRect.s.setActive(false);
	}
	
	public boolean isFinished() {
		return finished;
	}
}
