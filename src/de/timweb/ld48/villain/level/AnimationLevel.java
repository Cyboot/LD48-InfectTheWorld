package de.timweb.ld48.villain.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import de.timweb.ld48.villain.game.Controls;
import de.timweb.ld48.villain.game.Level;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.ImageLoader;

public class AnimationLevel extends Level {
	private static final int TIME = 10 * 1000;

	private int timeleft = TIME;

	private Image img1;
	private Image img2;

	public AnimationLevel(int levelNr) {
		switch (levelNr) {
		case 1:
			img1 = ImageLoader.human_level1_1;
			img2 = ImageLoader.human_level1_2;

			Gui.g.drawText(timeleft, "First we have to infect the human",
					"The viruses are in the Air", "",
					"Yeah, take a deep breath", "HARR HARR HARRR");
			break;
		case 2:
			img1 = ImageLoader.human_level2_1;
			img2 = ImageLoader.human_level2_2;
			timeleft = 5 * 1000;
			Gui.g.drawText(timeleft, "The liver is next", "                ",
					"                    ");
			break;
		case 3:
			img1 = ImageLoader.human_level3_1;
			img2 = ImageLoader.human_level3_2;
			timeleft = 5 * 1000;
			Gui.g.drawText(timeleft, "Almost done",
					"Destroy that humans heart and the fight will be over");
			break;
		}
	}

	@Override
	public void update(int delta) {
		Gui.g.hideScoreBoard();

		timeleft -= delta;

		if (timeleft < 0)
			finish();

		if (Controls.c.wasKeyPressed(KeyEvent.VK_ENTER)) {
			finish();
		}
	}

	@Override
	public void render(Graphics g) {
		if (timeleft % 1000 > 500)
			g.drawImage(img1, VillainCanvas.WIDTH / 2 - 140, 0, null);
		else
			g.drawImage(img2, VillainCanvas.WIDTH / 2 - 140, 0, null);

		g.setColor(Color.orange);
		g.setFont(VillainCanvas.font_Big);
		g.drawString("Press <Enter> to continue", VillainCanvas.WIDTH / 2 - 80,
				VillainCanvas.HEIGHT - 30);
	}

	@Override
	protected void finish() {
		super.finish();

		Gui.g.deleteText();
	}
}
