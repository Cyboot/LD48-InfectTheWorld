package de.timweb.ld48.villain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import de.timweb.ld48.villain.game.Controls;
import de.timweb.ld48.villain.game.Game;
import de.timweb.ld48.villain.game.Player;
import de.timweb.ld48.villain.game.Spawner;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.level.BodyLevel;

public class Button {
	public static Button strenght = new Button("Strength", 50);
	public static Button speed = new Button("Speed", 90);
	public static Button spawnrate = new Button("Rate", 130);
	public static Button special_freeze = new Button("FREEZE", 170, 25);
	public static Button special_burn = new Button("BURN", 210, 25);
	public static Button special_maxSpawn = new Button("SPAWN", 250, 25);

	private String text;
	private int height;

	private boolean isActive = true;
	private boolean isPressed;
	private boolean isHover;

	private Rectangle rect;
	private int cost;
	private int costDelta = 1;

	public Button(String string, int height, int cost) {
		this(string, height);

		this.cost = cost;
		costDelta = 5;
	}

	public Button(String string, int height) {
		text = string;
		this.height = height;

		rect = new Rectangle(VillainCanvas.WIDTH - 150, height, 150, 35);
		setCost(1);
	}

	public void update(int delta) {
		// Button is now shown --> don't update
		if (!Gui.g.isScoreboardShown())
			return;

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
		} else if (text.equals("FREEZE")) {
			Controls.c.setFreeze();
		} else if (text.equals("BURN")) {
			Controls.c.setBurn();
		} else if (text.equals("SPAWN")) {
			List<Spawner> spawner = ((BodyLevel) Game.g.getCurrentLevel())
					.getSpawner();
			for(Spawner s : spawner){
				if(s.isWhite())
					continue;
				
				s.spawn();
				s.spawn();
				s.spawn();
			}
		}

		Player.removeMoney(cost);
		cost += costDelta;
	}

	public void setCost(int cost) {
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
		else {
			if (costDelta > 1)
				img = ImageLoader.button_special;
			else
				img = ImageLoader.button_normal;
		}

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
