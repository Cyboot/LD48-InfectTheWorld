package de.timweb.ld48.villain.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.timweb.ld48.villain.entity.Antibody;
import de.timweb.ld48.villain.entity.Entity;
import de.timweb.ld48.villain.entity.WhiteCell;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class Spawner extends Entity {
	public static final int MAX_SPAWN = 20 * 1000;
	public static final int HEALTH = 10 * 1000;

	private static int maxVirusspawn = MAX_SPAWN / 3;

	private int health = 10 * 1000;
	private BufferedImage img;
	private int lastSpawn;
	private int color = -1;

	public Spawner(Vector2d pos, int color) {
		super(pos);
		setColor(color);

		if (isWhite())
			lastSpawn = 0;
		else
			lastSpawn = MAX_SPAWN;

	}

	@Override
	public void update(int delta) {

		lastSpawn += delta;

		BodyLevel level = (BodyLevel) Game.g.getCurrentLevel();
		if (isWhite()) {
			// use default time for White
			if (lastSpawn > MAX_SPAWN) {
				level.addWhiteCell(new WhiteCell(getPos().copy()));

				if (Math.random() > 0.5) {
					List<Spawner> spawner = level.getSpawner();

					int colorAnti = 0;
					for (Spawner s : spawner) {
						if (!s.isWhite()) {
							colorAnti = s.color;
						}
					}

					level.addAntibody(new Antibody(getPos().copy(), colorAnti));
				}

				lastSpawn = 0;
			}
		} else {
			// Use mutable maxVirusspawn for virus
			if (lastSpawn > maxVirusspawn) {
				level.addVirus(new Virus(getPos().copy(), color));
				lastSpawn = 0;
			}

		}

	}

	public void setColor(int color) {
		this.color = color;
		if (color == -1) {
			img = ImageLoader.spawner_white;
			return;
		}
		int x = color % 3;
		int y = color / 3;

		img = ImageLoader.getSubImage(ImageLoader.sprite_spawner, x, y, 32);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, pos.x() - 16, pos.y() - 16, null);
	}

	public boolean isWhite() {
		return color == -1;
	}

	public void attack(int delta, int color) {
		health -= delta;

		if (health <= 0) {
			health = HEALTH;
			setColor(color);
		}
	}

	public static void increaseSpawnRate() {
		maxVirusspawn -= 500;
		if (maxVirusspawn < 1000) {
			maxVirusspawn = 1000;
		}
	}

	public void spawn() {
		BodyLevel level = (BodyLevel) Game.g.getCurrentLevel();

		if (isWhite())
			level.addWhiteCell(new WhiteCell(getPos().copy()));
		else
			level.addVirus(new Virus(getPos().copy(), color));
	}

	public static void reset() {
		maxVirusspawn = MAX_SPAWN / 3;
	}

}
