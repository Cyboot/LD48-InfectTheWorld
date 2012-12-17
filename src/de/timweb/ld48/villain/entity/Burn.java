package de.timweb.ld48.villain.entity;

import java.awt.Graphics;
import java.util.List;

import de.timweb.ld48.villain.game.Game;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.SoundEffect;
import de.timweb.ld48.villain.util.Vector2d;

public class Burn extends Entity {
	private static final int RADIUS = 100;
	private int timeLeft = 3 * 1000;

	public Burn(Vector2d pos) {
		super(pos);
		SoundEffect.SPECIAL.play();
	}

	@Override
	public void update(int delta) {
		if (!isAlive())
			return;

		List<WhiteCell> whitecells = ((BodyLevel) Game.g.getCurrentLevel())
				.getWhiteCells();
		List<Antibody> antibody = ((BodyLevel) Game.g.getCurrentLevel())
				.getAntibody();

		for (WhiteCell w : whitecells) {
			double dist = w.getPos().distance(pos);
			if (dist < RADIUS) {
				w.killByFire();
			}
		}
		for (Antibody a : antibody) {
			double dist = a.getPos().distance(pos);
			if (dist < RADIUS) {
				a.kill();
			}
		}

		timeLeft -= delta;
		if (timeLeft < 0)
			kill();
	}

	@Override
	public void render(Graphics g) {
		if ((timeLeft % 100) > 50)
			g.drawImage(ImageLoader.special_fire, pos.x() - 100, pos.y() - 100,
					null);
		else
			g.drawImage(ImageLoader.special_fire_trans, pos.x() - 100,
					pos.y() - 100, null);
	}

}
