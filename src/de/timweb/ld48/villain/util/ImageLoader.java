package de.timweb.ld48.villain.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.timweb.ld48.villain.VillainMain;

public class ImageLoader {
	public static BufferedImage bg_body_mouth;
	public static BufferedImage bg_body_liver;
	public static BufferedImage bg_body_heart;
	public static BufferedImage cell_white_64;
	public static BufferedImage cell_white_24;
	public static BufferedImage cell_red_16;
	
	public static BufferedImage anti_16_red;
	public static BufferedImage anti_16_yellow;
	public static BufferedImage anti_16_green;
	public static BufferedImage anti_16_cyan;
	public static BufferedImage anti_16_blue;
	public static BufferedImage anti_16_purple;
	
	private static BufferedImage sprite_cell_anti_16;
	private static BufferedImage sprite_cell_anti_64;

	public static void init() {
		try {
			System.out.println("loading Images... ");
			bg_body_mouth = readImage("bg_body_mouth.png");
			bg_body_liver = readImage("bg_body_liver.png");
			bg_body_heart = readImage("bg_body_heart.png");
			cell_white_64 = readImage("cell_white_64.png");
			cell_white_24 = readImage("cell_white_24.png");
			cell_red_16 = readImage("cell_red_16.png");

			sprite_cell_anti_16 = readImage("sprite_cell_anti_16.png");
			sprite_cell_anti_64 = readImage("sprite_cell_anti_64.png");
			
			
			//Spritesheets
			anti_16_red = getSubImage(sprite_cell_anti_16, 0, 0, 16);
			anti_16_yellow = getSubImage(sprite_cell_anti_16, 1, 0, 16);
			anti_16_green = getSubImage(sprite_cell_anti_16, 2, 0, 16);
			
			anti_16_cyan = getSubImage(sprite_cell_anti_16, 0, 1, 16);
			anti_16_blue = getSubImage(sprite_cell_anti_16, 1, 1, 16);
			anti_16_purple = getSubImage(sprite_cell_anti_16, 2, 1, 16);
			
			
			
			System.out.println("finished loading Images");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage readImage(String res) throws IOException {
		return ImageIO.read(VillainMain.class.getResource("/" + res));
	}

	public static BufferedImage getSubImage(BufferedImage img, int x, int y,
			int width) {
		return img.getSubimage(x * width, y * width, width, width);
	}

	public static BufferedImage getCutImage(BufferedImage img, int width) {
		return img.getSubimage(0, 0, width, img.getHeight());
	}

}