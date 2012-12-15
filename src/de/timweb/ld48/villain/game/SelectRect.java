package de.timweb.ld48.villain.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class SelectRect {
	public static final SelectRect s = new SelectRect();

	private final Color color = Color.orange;

	private boolean isActive = true;
	private boolean isFixed = false;;
	private Point point1;
	private Point point2;

	public synchronized void setPoint1(Point point1) {
		if (!isActive || isFixed)
			return;
		this.point1 = point1;
		if (point2 == null)
			this.point2 = this.point1;
	}

	public synchronized void setPoint2(Point point2) {
		if (!isActive || isFixed)
			return;
		this.point2 = point2;
	}

	/**
	 * @return the upper left Point
	 */
	public synchronized Point getPointMin() {
		if (!isFixed) {
			throw new IllegalStateException("Rectangle is not ready");
		}
		int minX = point1.x < point2.x ? point1.x : point2.x;
		int minY = point1.y < point2.y ? point1.y : point2.y;

		return new Point(minX, minY);
	}

	/**
	 * @return the bottom right Point
	 */
	public synchronized Point getPointMax() {
		if (!isFixed) {
			throw new IllegalStateException("Rectangle is not ready");
		}
		int maxX = point1.x > point2.x ? point1.x : point2.x;
		int maxY = point1.y > point2.y ? point1.y : point2.y;

		return new Point(maxX, maxY);
	}

	public synchronized void render(Graphics g) {
		if (!isActive)
			return;
		if (isFixed || point1 == null || point2 == null)
			return;

		int minX = point1.x < point2.x ? point1.x : point2.x;
		int minY = point1.y < point2.y ? point1.y : point2.y;
		int maxX = point1.x > point2.x ? point1.x : point2.x;
		int maxY = point1.y > point2.y ? point1.y : point2.y;

		g.setColor(color);
		g.drawRect(minX, minY, maxX - minX, maxY - minY);
	}

	public synchronized boolean isActive() {
		return isActive;
	}

	public synchronized void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Fix the currently selected Rectangle, now it is ready to be read out
	 */
	public synchronized void released() {
		if (point1 != null && point2 != null)
			isFixed = true;
	}

	public synchronized boolean isSelected() {
		return isFixed;
	}

	public synchronized boolean isWorking(){
		return !isFixed && point1 != null;
	}
	
	/**
	 * should be called after the Rectangle was read out
	 */
	public synchronized void reset() {
		point1 = null;
		point2 = null;
		isFixed = false;
	}

}
