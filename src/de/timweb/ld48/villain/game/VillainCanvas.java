package de.timweb.ld48.villain.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import de.timweb.ld48.villain.util.ImageLoader;

@SuppressWarnings("serial")
public class VillainCanvas extends Canvas implements Runnable {
	public static int WIDTH;
	public static int HEIGHT;
	public static long TARGET_FPS = 100;
	public static long TARGET_DELTA = 1000 / TARGET_FPS;
	public static float fps = 0;

	public VillainCanvas(int width, int height) {
		WIDTH = width;
		HEIGHT = height;

		Dimension dim = new Dimension(WIDTH-10, HEIGHT-10);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.addKeyListener(Controls.c);
		this.addMouseMotionListener(Controls.c);
		this.addMouseListener(Controls.c);
	}

	public void start() {
		Thread t = new Thread(this);
		
		ImageLoader.init();
		
		t.start();
	}

	@Override
	public void run() {
		long delta = 0;

		while (true) {
			long start = System.currentTimeMillis();

			Game.g.update((int) delta);
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				continue;
			}
			render(bs.getDrawGraphics());

			if (bs != null)
				bs.show();

			long timepassed = System.currentTimeMillis() - start;
			if (timepassed < TARGET_DELTA) {
				try {
					Thread.sleep(TARGET_DELTA - timepassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			delta = System.currentTimeMillis() - start;
			
			if (delta != 0)
				fps = 1000 / delta;
		}

	}

	private void render(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);

		Game.g.render(g);

		g.setColor(Color.blue);
		g.setFont(getFont());
		g.drawString("FPS: " + fps, WIDTH - 80, 20);

		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
}
