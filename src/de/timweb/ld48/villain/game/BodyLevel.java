package de.timweb.ld48.villain.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.timweb.ld48.villain.entity.Antibody;
import de.timweb.ld48.villain.entity.Entity;
import de.timweb.ld48.villain.entity.RedCell;
import de.timweb.ld48.villain.entity.WhiteCell;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class BodyLevel extends Level {
	private BufferedImage bgImg;
	private List<Entity> whiteCells;
	private List<Entity> antibody;
	private List<Entity> redCells;
	private List<Entity> virus;

	public BodyLevel(int levelNr) {
		whiteCells = new ArrayList<Entity>(100);
		antibody = new ArrayList<Entity>(100);
		redCells = new ArrayList<Entity>(100);
		virus = new ArrayList<Entity>(100);

		switch (levelNr) {
		case 1:
			bgImg = ImageLoader.bg_body_mouth;
			addEnemys(8);
			break;
		case 2:
			bgImg = ImageLoader.bg_body_liver;
			addEnemys(16);
			break;
		case 3:
			bgImg = ImageLoader.bg_body_heart;
			addEnemys(32);
			break;
		}

		addVirus(50);
		
		SelectRect.s.setActive(true);
	}

	private void addVirus(int count) {
		for (int i = 0; i < count; i++) {
			double x = Math.random() * VillainCanvas.WIDTH;
			double y = Math.random() * VillainCanvas.HEIGHT;
			
			virus.add(new Virus(new Vector2d(x, y)));
		}
		
	}

	private void addEnemys(int count) {
		for (int i = 0; i < count; i++) {
			double x = Math.random() * VillainCanvas.WIDTH;
			double y = Math.random() * VillainCanvas.HEIGHT;

			whiteCells.add(new WhiteCell(new Vector2d(x, y)));
		}
		for (int i = 0; i < count; i++) {
			double x = Math.random() * VillainCanvas.WIDTH;
			double y = Math.random() * VillainCanvas.HEIGHT;
			
			antibody.add(new Antibody(new Vector2d(x, y)));
		}
		for (int i = 0; i < count; i++) {
			double x = Math.random() * VillainCanvas.WIDTH;
			double y = Math.random() * VillainCanvas.HEIGHT;
			
			redCells.add(new RedCell(new Vector2d(x, y)));
		}
	}

	@Override
	public void update(int delta) {
		if (Controls.c.wasKeyPressed(KeyEvent.VK_ENTER)){
			finish();
			
		}
		
		
		for(Entity e : whiteCells){
			e.update(delta);
		}
		for(Entity e : antibody){
			e.update(delta);
		}
		for(Entity e : redCells){
			e.update(delta);
		}
		for(Entity e : virus){
			e.update(delta);
		}
		
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(bgImg, 0, 0, null);

		for(Entity e : redCells){
			e.render(g);
		}
		for(Entity e : antibody){
			e.render(g);
		}
		for(Entity e : whiteCells){
			e.render(g);
		}
		for(Entity e : virus){
			e.render(g);
		}
		
		SelectRect.s.render(g);
	}

}
