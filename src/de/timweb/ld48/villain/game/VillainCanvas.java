package de.timweb.ld48.villain.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import de.timweb.ld48.villain.VillainMain;
import de.timweb.ld48.villain.util.Gui;
import de.timweb.ld48.villain.util.ImageLoader;
import de.timweb.ld48.villain.util.SoundEffect;

@SuppressWarnings("serial")
public class VillainCanvas extends Canvas implements Runnable {
	public static int WIDTH;
	public static int HEIGHT;
	public static long TARGET_FPS = 100;
	public static long TARGET_DELTA = 1000 / TARGET_FPS;
	public static float fps = 0;
	public static Font font;
	public static Font font_Bigger;
	public static Font font_Big;
	public static Font font_End;
	public static Font font_Start;

	public VillainCanvas(int width, int height) {
		WIDTH = width;
		HEIGHT = height;

		Dimension dim = new Dimension(WIDTH - 10, HEIGHT - 10);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.addKeyListener(Controls.c);
		this.addMouseMotionListener(Controls.c);
		this.addMouseListener(Controls.c);

	}

	public void start() {
		Thread t = new Thread(this);
		initFont();

		try {
			createBufferStrategy(3);
			Graphics g = getBufferStrategy().getDrawGraphics();

			if (g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D) g;
				try {
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				} catch (Exception e) {
					System.err
							.println("Antialias failed for displaying the Font");
				}
			}

			g.setColor(Color.black);
			g.setFont(font_Big);
			g.drawString("loading Images...", WIDTH / 2 - 50, HEIGHT / 2 - 10);

			g.dispose();
			Toolkit.getDefaultToolkit().sync();

			getBufferStrategy().show();
		} catch (Exception e) {
		}

		ImageLoader.init();
		SoundEffect.init();

		SoundEffect.MUSIC.loop();
		t.start();
	}

	private void initFont() {
		try {
			font = Font
					.createFont(Font.TRUETYPE_FONT, VillainMain.class
							.getResourceAsStream("/cinnamon_cake.ttf"));
			// GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
			font = font.deriveFont(Font.PLAIN, 14);
			font_Big = font.deriveFont(Font.BOLD, 18);
			font_Bigger = font.deriveFont(Font.BOLD, 16);
			font_End = font.deriveFont(Font.BOLD, 200);
			font_Start = font.deriveFont(Font.BOLD, 150);
		} catch (FontFormatException e) {
			System.err.println("Cannot Load Font! Using default one");
		} catch (IOException e) {
			System.err.println("Cannot find Font! Using default one");
		} finally {
			if (font == null) {
				font = getFont();
				font_Big = font.deriveFont(Font.BOLD, 18);
				font_Bigger = font.deriveFont(Font.BOLD, 16);
				font_End = font.deriveFont(Font.BOLD, 96);
				font_Start = font.deriveFont(Font.BOLD, 150);
			}
		}
	}

	@Override
	public void run() {
		long delta = 0;

		while (true) {
			long start = System.currentTimeMillis();

			update((int) delta);

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

	private void update(int delta) {

		// cover Lags (example debugging)
		if (delta >= 800)
			return;

		Game.g.update(delta);

		try {
			Gui.g.update(delta);
		} catch (Exception e) {
			System.err
					.println("Exception occured, this should NOT HAPPEN, continue anyways (to little time to test this): "
							+ e);
		}
	}

	private void render(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);

		Game.g.render(g);

		try {
			Gui.g.render(g);
		} catch (Exception e) {
			System.err
					.println("Exception occured, this should NOT HAPPEN, continue anyways (to little time to test this): "
							+ e);
		}

		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			try {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} catch (Exception e) {
				System.err.println("Antialias failed for displaying the Font");
			}
		}

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("FPS: " + (int) fps, 5, 13);

		String muted = "mute";
		if (SoundEffect.isMusicMuted())
			muted = "unmute";

		g.drawString("Press M to " + muted + " music", WIDTH - 190, HEIGHT - 30);

		muted = "mute";
		if (SoundEffect.isSoundMuted())
			muted = "unmute";
		g.drawString("Press S to " + muted + " sound", WIDTH - 190, HEIGHT - 10);

		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
}
