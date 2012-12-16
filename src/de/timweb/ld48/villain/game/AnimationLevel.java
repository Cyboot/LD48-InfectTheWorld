package de.timweb.ld48.villain.game;

import java.awt.Graphics;
import java.awt.Image;

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
					"Yeah, take a deep breath", "HARR HARR HARRR");
			break;
		case 2:
			img1 = ImageLoader.human_level2_1;
			img2 = ImageLoader.human_level2_2;
			timeleft = 5*1000;
			Gui.g.drawText(timeleft, "The liver is next");
			break;
		case 3:
			img1 = ImageLoader.human_level3_1;
			img2 = ImageLoader.human_level3_2;
			timeleft = 5*1000;
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

	}

	@Override
	public void render(Graphics g) {
		if (timeleft % 1000 > 500)
			g.drawImage(img1, VillainCanvas.WIDTH / 2 - 140, 0, null);
		else
			g.drawImage(img2, VillainCanvas.WIDTH / 2 - 140, 0, null);

	}

}
