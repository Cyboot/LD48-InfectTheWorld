package de.timweb.ld48.villain;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.timweb.ld48.villain.game.VillainCanvas;

public class VillainMain {

	public static void main(String[] args) {
		VillainCanvas canvas = new VillainCanvas(1280,720);
		
		JFrame frame = new JFrame("Infect the World");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas);
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas.start();
		
	}
	
}
