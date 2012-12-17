package de.timweb.ld48.villain.entity;

import java.awt.Graphics;

import de.timweb.ld48.villain.util.Vector2d;

public abstract class Entity {
	protected Vector2d pos;

	private boolean isAlive = true;

	public Entity() {
		this(new Vector2d());
	}

	public Entity(Vector2d pos) {
		this.pos = pos;
	}

	public abstract void update(int delta);

	public abstract void render(Graphics g);

	protected void onKilled() {
		// TODO: 90. sound here
		// TODO: 91. grafikeffekte
	}

	public void kill() {
		isAlive = false;
		onKilled();
	}

	public boolean isAlive() {
		return isAlive;
	}

	public Vector2d getPos() {
		return pos;
	}

}
