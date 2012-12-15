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
	private int levelNr;
	private BufferedImage bgImg;
	private List<WhiteCell> whiteCells;
	private List<Antibody> antibody;
	private List<RedCell> redCells;
	private List<Virus> virus;
	private List<Spawner> spawner;

	public BodyLevel(int levelNr) {
		this.levelNr = levelNr;
		whiteCells = new ArrayList<WhiteCell>(100);
		antibody = new ArrayList<Antibody>(100);
		redCells = new ArrayList<RedCell>(100);
		virus = new ArrayList<Virus>(100);
		spawner = new ArrayList<Spawner>(20);

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

		addSpawner();

		SelectRect.s.setActive(true);
	}

	private void addVirus(int count) {
		for (int i = 0; i < count; i++) {
			double x = Math.random() * VillainCanvas.WIDTH;
			double y = Math.random() * VillainCanvas.HEIGHT;

			virus.add(new Virus(new Vector2d(x, y)));
		}

	}

	private void addSpawner() {
		int w = VillainCanvas.WIDTH;
		int h = VillainCanvas.HEIGHT;

		switch (levelNr) {
		case 1:
			spawner.add(new Spawner(new Vector2d(w * 0.3, h * 0.3), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.9, h * 0.2), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.7, h * 0.7), -1));

			break;
		case 2:

			break;
		case 3:

			break;
		}
		spawner.add(new Spawner(new Vector2d(w * 0.1, h * 0.8), 1));

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
		if (Controls.c.wasKeyPressed(KeyEvent.VK_ENTER)) {
			finish();

		}

		for (Entity e : whiteCells) {
			e.update(delta);
		}
		for (Entity e : antibody) {
			e.update(delta);
		}
		for (Entity e : redCells) {
			e.update(delta);
		}
		for (Entity e : virus) {
			e.update(delta);
		}
		for (Entity e : spawner) {
			e.update(delta);
		}

	}

	public void addWhiteCell(WhiteCell cell) {
		whiteCells.add(cell);
	}

	public void addVirus(Virus virus) {
		this.virus.add(virus);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(bgImg, 0, 0, null);

		for (Entity e : spawner) {
			e.render(g);
		}
		for (Entity e : redCells) {
			e.render(g);
		}
		for (Entity e : antibody) {
			e.render(g);
		}
		for (Entity e : whiteCells) {
			e.render(g);
		}
		for (Entity e : virus) {
			e.render(g);
		}

		SelectRect.s.render(g);
	}

}
