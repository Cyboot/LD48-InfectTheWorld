package de.timweb.ld48.villain.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;

public class RedCell extends Entity {
	private BufferedImage img = ImageLoader.cell_red_16;
	private Vector2d direction;
	private int size = 8;
	private double speed = 0.2;

	public RedCell(Vector2d pos) {
		super(pos);

		direction = Vector2d.randomNormalized();
		if(direction.x < 0)
			direction.flipX();
		if(direction.y < 0)
			direction.flipY();
		
	}

	@Override
	public void update(int delta) {
		double dx = direction.x * delta * speed;
		double dy = direction.y * delta * speed;

		pos.add(dx, dy);

		if (pos.x > VillainCanvas.WIDTH) {
			pos.set(-size, pos.y);
		}
		if (pos.y > VillainCanvas.HEIGHT) {
			pos.set(pos.x, -size);
		}

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, pos.x()-size, pos.y()-size, null);
	}

}
