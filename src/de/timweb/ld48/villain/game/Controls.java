package de.timweb.ld48.villain.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Controls implements MouseListener, KeyListener,
		MouseMotionListener {
	public final static Controls c = new Controls();

	private boolean ok_pressed;
	private boolean space_pressed;
	private Point mousePos_right;
	private Point mousePos_left;

	private boolean leftMouse_clicked;
	
	
	public boolean wasKeyPressed(int code) {
		boolean result = false;
		switch (code) {
		
		case KeyEvent.VK_ENTER:
			result = ok_pressed;
			ok_pressed = false;
			return result;
			
		case KeyEvent.VK_SPACE:
			result = space_pressed;
			space_pressed = false;
			return result;
			
		default:
			return false;
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {

	}

	@Override
	public void keyReleased(KeyEvent ke) {

		switch (ke.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			ok_pressed = true;
			break;
		case KeyEvent.VK_SPACE:
			space_pressed = true;
			break;

		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			break;

			
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {

	}

	@Override
	public void mouseExited(MouseEvent me) {

	}

	@Override
	public void mousePressed(MouseEvent me) {
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			SelectRect.s.setPoint1(me.getPoint());
			mousePos_left = me.getPoint();
			leftMouse_clicked = true;
			break;

		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			SelectRect.s.released();
			mousePos_right = null;
			break;
		case MouseEvent.BUTTON3:
			mousePos_right = me.getPoint();
//			System.out.println("right");
			break;

		default:
			break;
		}

	}

	@Override
	public void mouseDragged(MouseEvent me) {
		SelectRect.s.setPoint2(me.getPoint());

	}

	@Override
	public void mouseMoved(MouseEvent me) {

	}

	public boolean isRightMouseDown() {
		return mousePos_right != null;
	}
	public boolean isLeftMouseDown() {
		return mousePos_left != null;
	}
	public Point getMousePosRight(){
		return mousePos_right;
	}
	public Point getMousePosLeft(){
		return mousePos_left;
	}

	public boolean wasLeftMouseClicked() {
		boolean result = leftMouse_clicked;
		leftMouse_clicked = false;
		return result;
	}
	
}
