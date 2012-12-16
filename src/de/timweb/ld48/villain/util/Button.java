package de.timweb.ld48.villain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import de.timweb.ld48.villain.game.Controls;
import de.timweb.ld48.villain.game.Player;
import de.timweb.ld48.villain.game.Spawner;
import de.timweb.ld48.villain.game.VillainCanvas;

public class Button {
	public static Button strenght = new Button("Strength", 50);
	public static Button speed = new Button("Speed", 90);
	public static Button spawnrate = new Button("Rate", 130);

	private String text;
	private int height;

	private boolean isActive = true;
	private boolean isPressed;
	private boolean isHover;

	private Rectangle rect;
	private int cost;

	public Button(String string, int height) {
		text = string;
		this.height = height;

		rect = new Rectangle(VillainCanvas.WIDTH - 150, height, 150, 35);
		setCost(1);
	}

	public void update(int delta) {
		Point pos = Controls.c.getCurrentMousePos();

		if (cost > Player.getMoney()) {
			isActive = false;
			return;
		} else {
			isActive = true;
		}

		boolean contain = rect.contains(pos);

		boolean wasPressed = isPressed;

		if (contain) {
			if (Controls.c.isLeftMouseDownButton())
				isPressed = true;
			else {
				isHover = true;
				isPressed = false;
			}
		} else {
			isPressed = false;
			isHover = false;
		}

		if (wasPressed && !isPressed) {
			waspressed();
		}

	}

	private void waspressed() {
		// Dirty dirty (no time)
		if (text.equals("Strength")) {
			Virus.setLevel(Virus.getLevel() + 1);
		} else if (text.equals("Speed")) {
			Virus.increaseSpeed();
		} else if (text.equals("Rate")) {
			Spawner.increaseSpawnRate();
		}

		Player.removeMoney(cost);
		cost++;
	}

	private void setCost(int cost) {
		this.cost = cost;
	}

	public void render(Graphics g) {
		Image img = null;
		if (!isActive)
			img = ImageLoader.button_grey;
		else if (isPressed)
			img = ImageLoader.button_pressed;
		else if (isHover)
			img = ImageLoader.button_hover;
		else
			img = ImageLoader.button_normal;

		g.drawImage(img, VillainCanvas.WIDTH - 150, height, null);

		g.setColor(Color.black);
		g.setFont(VillainCanvas.font_Bigger);
		g.drawString(text, VillainCanvas.WIDTH - 132, height + 22);
		g.drawString("$ " + cost, VillainCanvas.WIDTH - 50, height + 22);

	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isPressed() {
		return isPressed;
	}
}
