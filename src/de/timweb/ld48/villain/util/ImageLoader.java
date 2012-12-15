package de.timweb.ld48.villain.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.timweb.ld48.villain.VillainMain;

public class ImageLoader {
	public static BufferedImage bg_body_mouth;
	public static BufferedImage bg_body_liver;
	public static BufferedImage bg_body_heart;

	public static void init() {
		try {
			System.out.println("loading Images... ");
			bg_body_mouth = readImage("bg_body_mouth.png");
			bg_body_liver = readImage("bg_body_liver.png");
			bg_body_heart = readImage("bg_body_heart.png");
			System.out.println("loading Images finished ");
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