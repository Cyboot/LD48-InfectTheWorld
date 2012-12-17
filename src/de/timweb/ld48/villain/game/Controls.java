package de.timweb.ld48.villain.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.timweb.ld48.villain.entity.Burn;
import de.timweb.ld48.villain.entity.Freeze;
import de.timweb.ld48.villain.level.BodyLevel;
import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.SoundEffect;
import de.timweb.ld48.villain.util.Vector2d;

public class Controls implements MouseListener, KeyListener,
		MouseMotionListener {
	public final static Controls c = new Controls();

	private Point currentMousePos;
	private boolean ok_pressed;
	private boolean space_pressed;
	private boolean f12_pressed;
	private Point mousePos_right;
	private Point mousePos_left;

	private boolean leftMouse_down;
	private boolean leftMouse_up;

	private boolean l_pressed;

	private boolean leftMouseButton;

	private boolean freeze;

	private boolean burn;

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
			
		case KeyEvent.VK_F12:
			result = f12_pressed;
			f12_pressed = false;
			return result;

		case KeyEvent.VK_L:
			result = l_pressed;
			l_pressed = false;
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
		case KeyEvent.VK_L:
			l_pressed = true;
			break;
		case KeyEvent.VK_F12:
			f12_pressed = true;
			break;
		case KeyEvent.VK_M:
			SoundEffect.muteMusic();
			break;
		case KeyEvent.VK_S:
			SoundEffect.muteSound();
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
//		System.out.println("clicked "+me.getButton());
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			leftMouse_up = true;
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
		System.out.println("mouse pressed");
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			if(burn){
				((BodyLevel)Game.g.getCurrentLevel()).addSpecial(new Burn(new Vector2d(me.getPoint().x, me.getPoint().y)));
				burn = false;
				return;
			}
			if(freeze){
				((BodyLevel)Game.g.getCurrentLevel()).addSpecial(new Freeze(new Vector2d(me.getPoint().x, me.getPoint().y)));
				freeze = false;
				return;
			}
			
			
			SelectRect.s.setPoint1(me.getPoint());
			mousePos_left = me.getPoint();
			leftMouse_down = true;
			leftMouseButton = true;
			break;

		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent me) {
//		System.out.println("released: "+me.getButton());
		switch (me.getButton()) {
		case MouseEvent.BUTTON1:
			SelectRect.s.released();
			mousePos_right = null;
			leftMouseButton = false;
			break;
		case MouseEvent.BUTTON3:
			mousePos_right = me.getPoint();
			// System.out.println("right");
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
		currentMousePos = me.getPoint();
	}

	public boolean isRightMouseDown() {
		return mousePos_right != null;
	}

	public boolean isLeftMouseDown() {
		return mousePos_left != null;
	}
	
	public boolean isLeftMouseDownButton() {
		return leftMouseButton;
	}
	
	

	public Point getMousePosRight() {
		return mousePos_right;
	}

	public Point getMousePosLeft() {
		return mousePos_left;
	}

	public boolean wasLeftMouseClicked() {
		boolean result = leftMouse_down;
		leftMouse_down = false;
		return result;
	}
	
	public boolean wasLeftMouseUp() {
		boolean result = leftMouse_up;
		leftMouse_up = false;
		return result;
	}

	public Point getCurrentMousePos() {
		return currentMousePos;
	}

	public void setFreeze() {
		freeze = true;
	}
	
	public boolean isFreeze() {
		return freeze;
	}

	public void setBurn() {
		burn = true;
	}
	
	public boolean isBurn() {
		return burn;
	}
}
