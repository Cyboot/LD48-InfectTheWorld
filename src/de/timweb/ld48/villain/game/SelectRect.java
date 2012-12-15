package de.timweb.ld48.villain.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class SelectRect {
	public static final SelectRect s = new SelectRect();
	
	private final Color color = Color.orange;

	private boolean isActive = true;
	private Point point1;
	private Point point2;

	private boolean released;

	public Point getPoint2() {
		return point2;
	}

	public void setPoint2(Point point2) {
		if(!isActive)
			return;
		this.point2 = point2;
	}

	public Point getPoint1() {
		return point1;
	}

	public void setPoint1(Point point1) {
		if(!isActive)
			return;
			
		this.point1 = point1;
		released = false;
	}

	public void render(Graphics g){
		if(!isActive)
			return;
		if(released || point1 == null || point2 == null)
			return;
		
			
		int minX =  point1.x < point2.x ? point1.x : point2.x;
		int minY =  point1.y < point2.y ? point1.y : point2.y;
		int maxX =  point1.x > point2.x ? point1.x : point2.x;
		int maxY =  point1.y > point2.y ? point1.y : point2.y;
		
		
		g.setColor(color);
		g.drawRect(minX, minY, maxX - minX, maxY - minY);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void released(){
		released = true;
		point1 = null;
		point2 = null;
	}
	
}
