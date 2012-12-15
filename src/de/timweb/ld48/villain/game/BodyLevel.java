package de.timweb.ld48.villain.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.timweb.ld48.villain.util.ImageLoader;

public class BodyLevel extends Level{
	private BufferedImage bgImg;
	
	
	public BodyLevel(int levelNr) {
		switch (levelNr) {
		case 1:
			bgImg = ImageLoader.bg_body_mouth;
			break;
		case 2:
			bgImg = ImageLoader.bg_body_liver;
			break;
		case 3:
			bgImg = ImageLoader.bg_body_heart;
			break;
		}
		
		SelectRect.s.setActive(true);
	}

	@Override
	public void update(int delta) {

		if(Controls.c.wasKeyPressed(KeyEvent.VK_ENTER))
			finish();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(bgImg, 0, 0, null);
		
		
		SelectRect.s.render(g);
	}

}
