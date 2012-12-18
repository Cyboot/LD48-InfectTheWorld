package de.timweb.ld48.villain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.timweb.ld48.villain.game.Player;
import de.timweb.ld48.villain.game.VillainCanvas;

public class Gui {
	public static final Gui g = new Gui();

	private static final int MAX_TIME = 4000;

	private static final int TEXT_DISPLAY_TIME = 14 * 1000;

	private String[] texts;
	private int[] curserPositions;

	private int length;
	private int timePassed;

	private boolean isWriting = false;
	private boolean showText = false;

	private int textTimeLeft;

	private boolean isScoreboardShown;

	public void update(int delta) {
		if (isWriting)
			writeText(delta);

		textTimeLeft -= delta;
		if (textTimeLeft < 0 && !isWriting) {
			showText = false;
			texts = null;
			curserPositions = null;
		}

		Button.spawnrate.update(delta);
		Button.speed.update(delta);
		Button.strenght.update(delta);
		Button.special_burn.update(delta);
		Button.special_freeze.update(delta);
		Button.special_maxSpawn.update(delta);
	}

	private void writeText(int delta) {
		timePassed += delta;

		int written = (int) (length * ((float) timePassed / MAX_TIME));

		for (int i = 0; i < texts.length; i++) {
			int len = texts[i].length();

			if (written < len) {
				curserPositions[i] = written;
			} else {
				curserPositions[i] = len;
			}

			written -= len;
			if (written <= 0)
				break;
		}

		if (timePassed >= MAX_TIME) {
			isWriting = false;
			showText = true;
		}

	}

	public void drawText(String... text) {
		drawText(TEXT_DISPLAY_TIME, text);
	}

	public void drawText(int time, String... text) {
		SoundEffect.TALK.play();
		isWriting = true;
		timePassed = 0;
		textTimeLeft = time;
		length = 0;
		for (String str : text) {
			length += str.length();
		}

		texts = text;
		curserPositions = new int[text.length];
	}

	public void render(Graphics g) {
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			try {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} catch (Exception e) {
				System.err.println("Antialias failed for displaying the Font");
			}
		}

		if (isScoreboardShown)
			renderScoreBoard(g);

		if (!isWriting && !showText)
			return;

		try {

		} catch (Exception e) {

		}

		g.drawImage(ImageLoader.villain, 0, 0, null);
		g.drawImage(ImageLoader.speech, 0, 0, null);

		g.setColor(Color.white);
		g.setFont(VillainCanvas.font);

		renderText(g);

	}

	private void renderScoreBoard(Graphics g) {
		g.drawImage(ImageLoader.scoreboard, VillainCanvas.WIDTH - 150, 0, null);

		g.setColor(Color.white);
		g.setFont(VillainCanvas.font_Big);
		g.drawString("$ " + Player.getMoney(), VillainCanvas.WIDTH - 130, 30);

		Button.strenght.render(g);
		Button.speed.render(g);
		Button.spawnrate.render(g);
		Button.special_burn.render(g);
		Button.special_freeze.render(g);
		Button.special_maxSpawn.render(g);
	}

	private void renderText(Graphics g) {
		if (texts == null)
			return;

		int y = 30;
		for (int i = 0; i < texts.length; i++) {
			String str = texts[i];
			g.drawString(str.substring(0, curserPositions[i]), 150, y);

			y += 16;
		}

	}

	public void hideScoreBoard() {
		isScoreboardShown = false;
	}

	public void showScoreBoard() {
		isScoreboardShown = true;
	}

	public boolean isScoreboardShown() {
		return isScoreboardShown;
	}

	public void deleteText() {
		textTimeLeft = -10;
		timePassed = MAX_TIME;
	}
}
