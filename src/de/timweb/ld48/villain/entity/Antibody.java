package de.timweb.ld48.villain.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;

public class Antibody extends Entity {
	private BufferedImage img;
	private Vector2d direction;
	private int size = 8;
	private double speed = 0.03;

	public Antibody(Vector2d pos) {
		super(pos);

		img = ImageLoader.anti_16_green;
		direction = Vector2d.randomNormalized();
	}

	@Override
	public void update(int delta) {
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

	@Override
	public void render(Graphics g) {
		g.drawImage(img, pos.x()-size, pos.y()-size, null);
	}

}
