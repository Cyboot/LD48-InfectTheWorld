package de.timweb.ld48.villain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.villain.entity.Entity;
import de.timweb.ld48.villain.game.VillainCanvas;

public class Virus extends Entity {
	private static final double DEFAULT_SPEED = 0.02;
	private static final double MIN_DISTANCE = 5;

	private BufferedImage img;
	private Vector2d direction;
	private int size = 8;
	private double speed = DEFAULT_SPEED;
	private boolean isSelected;
	private Vector2d target;
	private Color color;
	private int health = 1000;

	public Virus(Vector2d pos, int color, int level, int size) {
		super(pos);

		this.size = size / 2;
		img = ImageLoader.getVirusImage(color, level, size);
		direction = Vector2d.randomNormalized();

		switch (color) {
		case 0:
			this.color = Color.red;
			break;
		case 1:
			this.color = Color.yellow;
			break;
		case 2:
			this.color = Color.green;
			break;
		case 3:
			this.color = Color.cyan;
			break;
		case 4:
			this.color = Color.blue;
			break;
		case 5:
			this.color = Color.pink;
			break;
		}
		this.color = Color.yellow;
		this.color = new Color(this.color.getRed(), this.color.getGreen(),
				this.color.getBlue(), 150);
	}

	public Virus(Vector2d pos) {
		this(pos, (int) (Math.random() * 6), (int) (Math.random() * 6), 16);
	}

	@Override
	public void update(int delta) {
		if (target != null) {
			direction = target.copy().add(-pos.x, -pos.y).normalize();
			if (target.distance(pos) < MIN_DISTANCE) {
				target = null;
				speed = DEFAULT_SPEED;
			}
		} else {
			if (Math.random() < 0.02) {
				direction.flipX();
			}
			if (Math.random() < 0.02) {
				direction.flipY();
			}

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

	@Override
	public void render(Graphics g) {
		if (isSelected) {
			g.setColor(color);
			g.fillOval(pos.x() - size, pos.y() - size, size * 2, size * 2);
		}

		g.drawImage(img, pos.x() - size, pos.y() - size, null);
	}

	public void setSelected(boolean b) {
		isSelected = b;
	}

	public void setTarget(Vector2d target) {
		this.target = target;

		speed = 10 * DEFAULT_SPEED;
	}

	@Override
	protected void onKilled() {
		// TODO: 91.a Sound + Effekt
		System.out.println("Virus killed");
	}
	
	public void hurt(int delta) {
		health  -= delta;
		if(health <= 0){
			kill();
		}
	}

}
