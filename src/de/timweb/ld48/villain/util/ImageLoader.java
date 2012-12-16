package de.timweb.ld48.villain.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.timweb.ld48.villain.VillainMain;

public class ImageLoader {
	public static BufferedImage bg_body_mouth;
	public static BufferedImage bg_body_liver;
	public static BufferedImage bg_body_heart;
	public static BufferedImage cell_white_64;
	public static BufferedImage cell_white_24;
	public static BufferedImage cell_red_16;
	public static BufferedImage spawner_white;
	public static BufferedImage scoreboard;
	public static BufferedImage villain;
	public static BufferedImage speech;
	public static BufferedImage selected_32;
	public static BufferedImage button_grey;
	public static BufferedImage button_hover;
	public static BufferedImage button_normal;
	public static BufferedImage button_pressed;

	public static BufferedImage sprite_cell_anti_16;
	// private static BufferedImage sprite_cell_anti_64;

	private static BufferedImage sprite_virus_16;
	private static BufferedImage sprite_virus_24;
	private static BufferedImage sprite_virus_32;
	public static BufferedImage sprite_spawner;

	private static HashMap<Integer, BufferedImage> virusMap = new HashMap<Integer, BufferedImage>();

	public static void init() {
		try {
			System.out.println("loading Images... ");
			bg_body_mouth = readImage("bg_body_mouth.png");
			bg_body_liver = readImage("bg_body_liver.png");
			bg_body_heart = readImage("bg_body_heart.png");
			cell_white_64 = readImage("cell_white_64.png");
			cell_white_24 = readImage("cell_white_24.png");
			cell_red_16 = readImage("cell_red_16.png");
			spawner_white = readImage("spawner_white.png");
			scoreboard = readImage("scoreboard.png");
			villain = readImage("villain.png");
			speech = readImage("speech.png");
			selected_32 = readImage("selected_32.png");
			button_grey = readImage("button_grey.png");
			button_hover = readImage("button_hover.png");
			button_normal = readImage("button_normal.png");
			button_pressed = readImage("button_pressed.png");

			sprite_cell_anti_16 = readImage("sprite_cell_anti_16.png");
			// sprite_cell_anti_64 = readImage("sprite_cell_anti_64.png");
			sprite_virus_16 = readImage("sprite_virus_16.png");
			sprite_virus_24 = readImage("sprite_virus_24.png");
			sprite_virus_32 = readImage("sprite_virus_32.png");
			sprite_spawner = readImage("sprite_spawner.png");

			System.out.println("finished loading Images");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage readImage(String res) throws IOException {
		return ImageIO.read(VillainMain.class.getResource("/" + res));
	}

	/**
	 * 
	 * @param color
	 *            (0 - red; 1 - yellow; 2 - green; 3 - cyan; 4 - blue; 5 -
	 *            purple)
	 * @param level
	 *            0-5
	 * @param size
	 *            16,24,32
	 * @return
	 */
	public static BufferedImage getVirusImage(int color, int level) {
		int size = 16;
		
		//Dirty diry hack
		if (level >= 6){
			size = 24;
			level -= 6;
		}
		if (level >= 6){
			size = 32;
			level -= 6;
		}
		
		int hash = (size << 16) + (color << 8) + level;

		BufferedImage img = virusMap.get(hash);

		// no Image of this Virus was found --> put in Map and return created
		// Image
		if (img == null) {
			int x = level % 3;
			int y = level / 3 + color * 2;

			switch (size) {
			case 16:
				img = getSubImage(sprite_virus_16, x, y, 16);
				break;
			case 24:
				img = getSubImage(sprite_virus_24, x, y, 24);
				break;
			case 32:
				img = getSubImage(sprite_virus_32, x, y, 32);
				break;
			}

			virusMap.put(hash, img);
		}

		return img;
	}

	public static BufferedImage getSubImage(BufferedImage img, int x, int y,
			int width) {
		return img.getSubimage(x * width, y * width, width, width);
	}

	public static BufferedImage getCutImage(BufferedImage img, int width) {
		return img.getSubimage(0, 0, width, img.getHeight());
	}

}