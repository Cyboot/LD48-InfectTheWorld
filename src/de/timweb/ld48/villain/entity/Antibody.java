package de.timweb.ld48.villain.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.timweb.ld48.villain.game.Game;
import de.timweb.ld48.villain.game.Spawner;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class Antibody extends Entity {
	private static final int MAX_FLICKER = 500;
	private BufferedImage img;
	private int size = 8;
	private int flicker;
	private int color;

	public Antibody(Vector2d pos, int color) {
		super(pos);

		this.color = color;

		flicker = (int) (Math.random() * 1000);

		int x = color % 3;
		int y = color / 3;
		img = ImageLoader
				.getSubImage(ImageLoader.sprite_cell_anti_16, x, y, 16);

		direction = Vector2d.randomNormalized();
	}

	private static final int ALARM_RADIUS = 100;
	private static final double MIN_DISTANCE = 5;
	private static final double DEFAULT_SPEED = 0.05;

	private Vector2d direction;
	private double speed = DEFAULT_SPEED;

	private Entity target;
	private boolean isFrozen = false;

	@Override
	public void update(int delta) {
		flicker += delta;
		if (flicker > MAX_FLICKER) {
			flicker = -MAX_FLICKER / 8;
		}
		
		if(isFrozen)
			return;
		
		
		checkForEnemies(delta);
		if (target != null) {
			moveToTarget(delta);
			return;
		}

		double dx = direction.x * delta * speed;
		double dy = direction.y * delta * speed;

		pos.add(dx, dy);

		if (pos.x < 0 || pos.x > VillainCanvas.WIDTH) {
			direction.flipX();
			dx = direction.x * delta;
			pos.add(dx, 0);
		}
		if (pos.y < 0 || pos.y > VillainCanvas.HEIGHT) {
			direction.flipY();
			dy = direction.y * delta;
			pos.add(0, dy);
		}

	}

	private void moveToTarget(int delta) {
		Vector2d targetPos = target.getPos();

		direction = targetPos.copy().add(-pos.x, -pos.y).normalize();

		if (targetPos.distance(targetPos) < MIN_DISTANCE) {
			target = null;
			speed = DEFAULT_SPEED;
		}

		double dx = direction.x * delta * speed;
		double dy = direction.y * delta * speed;

		pos.add(dx, dy);

		if (pos.x < 0 || pos.x > VillainCanvas.WIDTH) {
			pos.add(dx, 0);
		}
		if (pos.y < 0 || pos.y > VillainCanvas.HEIGHT) {
			pos.add(0, dy);
		}
	}

	private void checkForEnemies(int delta) {
		List<Virus> virus = ((BodyLevel) Game.g.getCurrentLevel()).getVirus();

		double minDist = Double.MAX_VALUE;

		// TODO: 92. Antibody nur an Virus anhefen, nicht drauf stehen

		Entity nearest = null;
		for (Virus v : virus) {
			// only search for right color
			if (color != v.getColor())
				continue;

			double dist = v.getPos().distance(pos);
			if (dist < ALARM_RADIUS && dist < minDist) {
				minDist = dist;
				nearest = v;
				speed = 3 * DEFAULT_SPEED;
			}
			if (dist < MIN_DISTANCE) {
				v.freeze();
			}
		}

		// Virus got away, random Direction again
		if (nearest == null && target != null) {
			direction = Vector2d.randomNormalized();
		}
		target = nearest;
	}

	@Override
	public void render(Graphics g) {
		if (flicker < 0)
			return;

		g.drawImage(img, pos.x() - size, pos.y() - size, null);
	}

	public void setFrozen(boolean b) {
		isFrozen = b;
	}

}
