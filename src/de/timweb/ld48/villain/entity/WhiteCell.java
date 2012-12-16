package de.timweb.ld48.villain.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.timweb.ld48.villain.game.Game;
import de.timweb.ld48.villain.game.Player;
import de.timweb.ld48.villain.game.Spawner;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class WhiteCell extends Entity {
	private static final int ALARM_RADIUS = 100;
	private static final int MAX_NOENEMEY_TIMELEFT = 3000;
	private static final double MIN_DISTANCE = 5;
	private static final double DEFAULT_SPEED = 0.05;
	private static final int START_HEALTH = 100 * 1000;

	private BufferedImage img = ImageLoader.cell_white_24;
	private Vector2d direction;
	private int size = 12;
	private double speed = DEFAULT_SPEED;
	private int health = START_HEALTH;

	private Entity target;
	private boolean isDefender;

	public WhiteCell(Vector2d pos) {
		super(pos);

		isDefender = Math.random() > 0.3;
		direction = Vector2d.randomNormalized();
	}

	@Override
	public void update(int delta) {
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

		if (targetPos.distance(pos) < MIN_DISTANCE) {
			speed = DEFAULT_SPEED;
			// direction = Vector2d.randomNormalized();
			target = null;
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
		List<Spawner> spawner = ((BodyLevel) Game.g.getCurrentLevel())
				.getSpawner();

		double minDist = Double.MAX_VALUE;

		Entity nearest = null;
		for (Virus v : virus) {
			double dist = v.getPos().distance(pos);
			if (dist < ALARM_RADIUS && dist < minDist) {
				minDist = dist;
				nearest = v;
				speed = 3 * DEFAULT_SPEED;
			}
			if (dist < MIN_DISTANCE) {
				v.hurt(delta);

				// if Virus is frozen don't hurt Whitecell
				if (!v.isFrozen())
					health -= Virus.getLevel() * delta;

				if (health < 0)
					kill();

			}
		}

		// no Virus found --> check for enemy spawner
		if (nearest == null) {
			minDist = Double.MAX_VALUE;
			for (Spawner e : spawner) {
				double dist = e.getPos().distance(pos);
				if (!e.isWhite() && dist < ALARM_RADIUS * 1.5 && dist < minDist) {
					minDist = dist;
					nearest = e;
					speed = 2 * DEFAULT_SPEED;
				}
				if (dist < MIN_DISTANCE) {
					e.attack(delta, -1);
				}
			}
		}

		Spawner ownspawner = null;
		// if have no target for a time --> defend own spawner
		if (nearest == null && isDefender && target == null) {
			minDist = Double.MAX_VALUE;
			for (Spawner e : spawner) {
				double dist = e.getPos().distance(pos);
				if (e.isWhite() && dist < minDist) {
					minDist = dist;
					ownspawner = e;
				}
				if (dist < MIN_DISTANCE) {
					ownspawner = null;
					direction = Vector2d.randomNormalized();
					speed = DEFAULT_SPEED;
				}
			}

			// only goto spawner if it is far away enough
			if (ownspawner == null
					|| ownspawner.getPos().distance(pos) < ALARM_RADIUS * 2) {
				ownspawner = null;
			} else {
				speed = 2 * DEFAULT_SPEED;
				nearest = ownspawner;
			}
		}

		// Virus got away, random Direction again
		if (nearest == null && target != null) {
			direction = Vector2d.randomNormalized();
		}

		// check if is defending a spawner:
		// if yes --> don't delete the target
		if (nearest == null && target instanceof Spawner) {
			if (((Spawner) target).isWhite())
				return;
		}

		target = nearest;
	}

	@Override
	protected void onKilled() {
		Player.addMoney(50);
	}

	@Override
	public void render(Graphics g) {

		// DEBUG: drawRadius
		// g.setColor(Color.white);
		// g.drawOval(pos.x() - ALARM_RADIUS, pos.y() - ALARM_RADIUS,
		// ALARM_RADIUS * 2, ALARM_RADIUS * 2);
		g.drawImage(img, pos.x() - size, pos.y() - size, null);
	}

}
