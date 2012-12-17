package de.timweb.ld48.villain.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import de.timweb.ld48.villain.game.Controls;
import de.timweb.ld48.villain.game.SelectRect;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Virus;

public class StartLevel extends BodyLevel {

	private int tutorial;

	public StartLevel(int tutorial) {
		super(0);

		this.tutorial = tutorial;

		Gui.g.hideScoreBoard();
		SelectRect.s.setActive(false);
	}

	@Override
	public void update(int delta) {
		if (Controls.c.wasKeyPressed(KeyEvent.VK_ENTER)) {
			finish();
			return;
		}

		super.update(delta);
		Virus.setLevel(30);

	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		g.drawImage(ImageLoader.shade, 0, 0, null);
		g.drawImage(ImageLoader.shade, 0, 0, null);

		g.drawImage(ImageLoader.goat, 0,
				VillainCanvas.HEIGHT - 81, null);

		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			try {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} catch (Exception e) {
				System.err.println("Antialias failed for displaying the Font");
			}
		}

		g.setColor(Color.orange);

		switch (tutorial) {
		case 0:
			g.setFont(VillainCanvas.font_Start);
			g.drawString("Infect the World", 80, VillainCanvas.HEIGHT / 2 + 50);
			break;
		case 1:
			g.drawImage(ImageLoader.tutorial, 0, 0, null);
			break;
		}
		g.setColor(Color.orange);
		g.setFont(VillainCanvas.font_Big);
		g.drawString("Press <Enter> to continue", VillainCanvas.WIDTH / 2 - 80,
				VillainCanvas.HEIGHT - 30);
	}

	@Override
	protected void finish() {
		super.finish();

		Virus.setLevel(0);
	}

}
