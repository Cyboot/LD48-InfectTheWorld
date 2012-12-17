package de.timweb.ld48.villain.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.timweb.ld48.villain.entity.Antibody;
import de.timweb.ld48.villain.entity.Burn;
import de.timweb.ld48.villain.entity.Entity;
import de.timweb.ld48.villain.entity.RedCell;
import de.timweb.ld48.villain.entity.WhiteCell;
import de.timweb.ld48.villain.game.Controls;
import de.timweb.ld48.villain.game.Level;
import de.timweb.ld48.villain.game.Player;
import de.timweb.ld48.villain.game.SelectRect;
import de.timweb.ld48.villain.game.Spawner;
import de.timweb.ld48.villain.game.VillainCanvas;
import de.timweb.ld48.villain.util.Button;
import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class BodyLevel extends Level {
	private static final int FINISH_TIMELEFT = 10 * 1000;

	private int levelNr;
	private BufferedImage bgImg;

	private boolean isFinished = false;
	private int finishedTimeleft = FINISH_TIMELEFT;

	private List<WhiteCell> whiteCells;
	private List<Antibody> antibody;
	private List<RedCell> redCells;
	private List<Virus> virus;
	private List<Spawner> spawner;
	private List<Entity> specials;

	private List<Virus> selectedVirus = new ArrayList<Virus>();

	private boolean isStarted;

	public BodyLevel(int levelNr) {
		this.levelNr = levelNr;
		whiteCells = new ArrayList<WhiteCell>(100);
		antibody = new ArrayList<Antibody>(100);
		redCells = new ArrayList<RedCell>(100);
		virus = new ArrayList<Virus>(100);
		spawner = new ArrayList<Spawner>(20);
		specials = new ArrayList<Entity>(20);

		switch (levelNr) {
		case 0:
			bgImg = ImageLoader.bg_body_liver;
			break;
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

		if (levelNr == 0)
			addVirus(20);
		
		
		

		SelectRect.s.setActive(true);
		Gui.g.showScoreBoard();
	}

	private void reset() {
		Virus.setLevel(Virus.getLevel()/3);
		Player.setMoney(0);
		
		Spawner.reset();
		
		Button.spawnrate.setCost(1);
		Button.speed.setCost(1);
		Button.strenght.setCost(Virus.getLevel());
		Button.special_burn.setCost(25);
		Button.special_freeze.setCost(25);
		Button.special_maxSpawn.setCost(25);
		
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
			spawner.add(new Spawner(new Vector2d(w * 0.4, h * 0.3), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.8, h * 0.7), -1));
			break;
		case 2:
			spawner.add(new Spawner(new Vector2d(w * 0.3, h * 0.3), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.7, h * 0.2), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.9, h * 0.7), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.5, h * 0.1), -1));

			break;
		case 0:
		case 3:
			spawner.add(new Spawner(new Vector2d(w * 0.2, h * 0.2), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.3, h * 0.5), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.5, h * 0.5), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.8, h * 0.8), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.7, h * 0.2), -1));
			spawner.add(new Spawner(new Vector2d(w * 0.6, h * 0.6), -1));

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
		// for (int i = 0; i < count; i++) {
		// double x = Math.random() * VillainCanvas.WIDTH;
		// double y = Math.random() * VillainCanvas.HEIGHT;
		//
		// antibody.add(new Antibody(new Vector2d(x, y),(int)
		// (Math.random()*6)));
		// }
		// for (int i = 0; i < count; i++) {
		// double x = Math.random() * VillainCanvas.WIDTH;
		// double y = Math.random() * VillainCanvas.HEIGHT;
		//
		// redCells.add(new RedCell(new Vector2d(x, y)));
		// }
	}

	@Override
	public void update(int delta) {
		if(!isStarted){
			reset();
			isStarted = true;
		}
		
		
		// check if there are white spawner in this level
		boolean isWhiteSpawner = false;
		for (Spawner s : spawner) {
			if (s.isWhite())
				isWhiteSpawner = true;
		}

		// no more Whitespawner --> Player won
		if ((!isWhiteSpawner && !isFinished) || Controls.c.wasKeyPressed(KeyEvent.VK_F12)) {
			switch (levelNr) {
			case 1:
				Gui.g.drawText(FINISH_TIMELEFT,
						"Congratulations! You won this Level",
						"You infected this human.",
						"Move on to the next organ, the liver");
				break;
			case 2:
				Gui.g.drawText(FINISH_TIMELEFT,
						"Congratulations! You won this Level",
						"You destroyed the liver.",
						"Now go and finish the human by ",
						"destroying his heart!");
				break;
			case 3:
				Gui.g.drawText(FINISH_TIMELEFT,
						"Congratulations! You won this Level",
						"You killed the man, good job!", "",

						"Now...   Let's go bigger...    Much bigger...");
				break;
			}
			isFinished = true;
		}

		if (isFinished) {
			finishedTimeleft -= delta;
		}

		// finish the Level afer timeleft
		if (finishedTimeleft < 0) {
			finish();
		}

		// DEBUG
		if (Controls.c.wasKeyPressed(KeyEvent.VK_L)) {
			Virus.setLevel(Virus.getLevel() + 1);
		}

		if (Controls.c.wasLeftMouseClicked()) {
			deselectVirus();
		}

		if (SelectRect.s.isSelected()) {
			selectedVirus(SelectRect.s.getPointMin(),
					SelectRect.s.getPointMax());

			SelectRect.s.reset();
		}

		if (!selectedVirus.isEmpty() && Controls.c.isRightMouseDown()) {
			moveSelectedVirus(Controls.c.getMousePosRight());
		}

		updateEnities(whiteCells, delta);
		updateEnities(redCells, delta);
		updateEnities(antibody, delta);
		updateEnities(virus, delta);
		updateEnities(spawner, delta);
		updateEnities(specials, delta);

		Player.addMoneyDelta(delta);
	}

	private void updateEnities(List<? extends Entity> list, int delta) {
		List<Entity> deadEntities = new ArrayList<Entity>();

		for (Entity e : list) {
			if (!e.isAlive()) {
				deadEntities.add(e);
				continue;
			}
			e.update(delta);
		}

		// remove dead entities
		list.removeAll(deadEntities);
	}

	private void deselectVirus() {
		for (Virus v : virus) {
			v.setSelected(false);
		}

	}

	private void selectedVirus(Point min, Point max) {
		selectedVirus = new ArrayList<Virus>();

		for (Virus v : virus) {
			Vector2d pos = v.getPos();

			if (pos.x > min.x && pos.x < max.x && pos.y > min.y
					&& pos.y < max.y) {
				selectedVirus.add(v);
				v.setSelected(true);
			}

		}
	}

	private void moveSelectedVirus(Point target) {
		Vector2d targetPos = new Vector2d(target.x, target.y);
		for (Virus v : selectedVirus) {
			v.setTarget(targetPos);
			v.setSelected(false);
		}
		selectedVirus = new ArrayList<Virus>();
	}

	public void addWhiteCell(WhiteCell cell) {
		whiteCells.add(cell);
	}

	public void addVirus(Virus virus) {
		this.virus.add(virus);
	}

	public List<Virus> getVirus() {
		return virus;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(bgImg, 0, 0, null);

		renderEntities(spawner, g);
		renderEntities(redCells, g);
		renderEntities(antibody, g);
		renderEntities(whiteCells, g);
		renderEntities(virus, g);

		renderSpecials(g);

		SelectRect.s.render(g);
	}

	private void renderSpecials(Graphics g) {
		Point mousepos = Controls.c.getCurrentMousePos();

		if (Controls.c.isBurn()) {
			g.drawImage(ImageLoader.special_fire_trans, mousepos.x - 100, mousepos.y - 100, null);
		}
		if (Controls.c.isFreeze()) {
			g.drawImage(ImageLoader.special_ice_trans, mousepos.x - 100, mousepos.y - 100, null);
		}

		renderEntities(specials, g);

	}

	public void renderEntities(List<? extends Entity> list, Graphics g) {
		for (Entity e : list) {
			e.render(g);
		}
	}

	public List<Spawner> getSpawner() {
		return spawner;
	}

	@Override
	protected void finish() {
		super.finish();

		Gui.g.deleteText();
	}

	public void addSpecial(Entity special) {
		specials.add(special);

	}

	public List<WhiteCell> getWhiteCells() {
		return whiteCells;
	}
	public List<Antibody> getAntibody() {
		return antibody;
	}

	public void addAntibody(Antibody antibody2) {
		antibody.add(antibody2);
	}
}
