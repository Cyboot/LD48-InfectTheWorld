package de.timweb.ld48.villain.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.ImageLoader;

public class WorldLevel extends Level {
	private static final int HEIGHT = 80;
	private static final int TIME = 8 * 5 * 1000;

	private int timePassed = 0;
	private int finishedTimeleft = 7 * 1000;
	private Image[] continents;
	private boolean isFinished;
	private boolean showEnd;

	public WorldLevel() {
		continents = new Image[8];

		continents[0] = ImageLoader.world_madagaskar;
		continents[1] = ImageLoader.world_africa;
		continents[2] = ImageLoader.world_china;
		continents[3] = ImageLoader.world_australia;
		continents[4] = ImageLoader.world_russland;
		continents[5] = ImageLoader.world_europa;
		continents[6] = ImageLoader.world_usa;
		continents[7] = ImageLoader.world_brasilien;

		Gui.g.drawText("And now let's destroy the whole world with",
				"our infectious virus!", "", "HARR HARR HARR");
		Gui.g.hideScoreBoard();
	}

	@Override
	public void update(int delta) {

		timePassed += delta;
		if (timePassed > TIME) {
			finishedTimeleft -= delta;
			
			if (!isFinished) {
				Gui.g.drawText(Integer.MAX_VALUE, "Well... ",
						"That was a lot easier than expected",
						"Hmm ......             now what?  ",
						"...        ",
						"Maybe that was not so well thought out after all...");
			}

			isFinished = true;
		}

		if(finishedTimeleft < 0){
			showEnd = true;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, VillainCanvas.WIDTH, VillainCanvas.HEIGHT);
		
		g.drawImage(ImageLoader.bg_world, 0, HEIGHT, null);

		int maxImg = (int) ((float) timePassed / TIME * 8);
		if (maxImg > 8)
			maxImg = 8;

		for (int i = 0; i < maxImg; i++) {
			g.drawImage(continents[i], 0, HEIGHT, null);
		}

		g.drawImage(ImageLoader.top_world, 0, HEIGHT, null);
		
		if(showEnd){
			if (g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D) g;
				try {
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				} catch (Exception e) {
					System.err.println("Antialias failed for displaying the Font");
				}
			}
			
			g.setColor(Color.black);
			
			g.setFont(VillainCanvas.font_End);
			g.drawString("The End", VillainCanvas.WIDTH/2-350, VillainCanvas.HEIGHT/2+50);
			
			g.setFont(VillainCanvas.font_Big);
			g.drawString("Thanks for Playing!", VillainCanvas.WIDTH/2-50, VillainCanvas.HEIGHT-50);
		}
	}

}
