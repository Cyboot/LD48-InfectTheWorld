package de.timweb.ld48.villain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.timweb.ld48.villain.game.VillainCanvas;

public class Gui {
	public static final Gui g = new Gui();

	private static final int MAX_TIME = 4000;

	private static final int TEXT_DISPLAY_TIME = 10 * 1000;

	private String[] texts;
	private int[] curserPositions;

	private int length;
	private int timePassed;

	private boolean isWriting = false;
	private boolean showText = false;

	private int textTimeLeft;

	public void update(int delta) {
		if (isWriting)
			writeText(delta);

		textTimeLeft -= delta;
		if (textTimeLeft < 0 && !isWriting) {
			showText = false;
			texts = null;
			curserPositions = null;
		}

		// TODO: 05. Mutation, Buttons
		// TODO: 99. (special attacks)
	}

	private void writeText(int delta) {
		timePassed += delta;

		int written = (int) (length * ((float) timePassed / MAX_TIME));

		for (int i = 0; i < texts.length; i++) {
			int len = texts[i].length();

			if (written < len) {
				curserPositions[i] = written;
			} else {
				curserPositions[i] = len - 1;
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
		isWriting = true;
		timePassed = 0;
		textTimeLeft = TEXT_DISPLAY_TIME;
		length = 0;
		for (String str : text) {
			length += str.length();
		}

		texts = text;
		curserPositions = new int[text.length];
	}

	public void render(Graphics g) {
		g.drawImage(ImageLoader.scoreboard, VillainCanvas.WIDTH - 150, 0, null);

		if (!isWriting && !showText)
			return;

		try {

		} catch (Exception e) {

		}

		g.drawImage(ImageLoader.villain, 0, 0, null);
		g.drawImage(ImageLoader.speech, 0, 0, null);

		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			try {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} catch (Exception e) {
				System.err.println("Antialias failed for displaying the Font");
			}
		}

		g.setColor(Color.white);
		g.setFont(VillainCanvas.font);

		renderText(g);

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

}
