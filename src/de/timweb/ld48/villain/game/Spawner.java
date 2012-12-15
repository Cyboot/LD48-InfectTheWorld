package de.timweb.ld48.villain.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.villain.entity.Entity;
import de.timweb.ld48.villain.entity.WhiteCell;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.Vector2d;
import de.timweb.ld48.villain.util.Virus;

public class Spawner extends Entity{
	public static final int MAX_SPAWN = 10*1000;
	public static final int HEALTH = 10*1000;
	
	private int health = 10*  1000;
	private BufferedImage img;
	private int lastSpawn;
	private int color = -1;

	public Spawner(Vector2d pos,int color) {
		super(pos);
		setColor(color);
		
		lastSpawn = (int) (Math.random() * MAX_SPAWN);
	}
	
	@Override
	public void update(int delta) {
		
		
		lastSpawn += delta;
		if(lastSpawn > MAX_SPAWN){
			lastSpawn = 0;
			
			BodyLevel level = (BodyLevel) Game.g.getCurrentLevel();
		
			if(color == -1){
				level.addWhiteCell(new WhiteCell(getPos().copy()));
			}else{
				level.addVirus(new Virus(getPos().copy(),color,3,16));
			}
			
		}
		
	}
	
	public void setColor(int color){
		this.color = color;
		if(color == -1){
			img = ImageLoader.spawner_white;
			return;
		}
		int x = color % 3;
		int y = color/3;
		
		img = ImageLoader.getSubImage(ImageLoader.sprite_spawner, x, y, 32);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, pos.x() - 16, pos.y() - 16, null);
	}

	public boolean isWhite() {
		return color == -1;
	}

	public void attack(int delta, int color) {
		health -= delta;
		
		if(health <= 0){
			health = HEALTH;
			setColor(color);
		}
	}

}
