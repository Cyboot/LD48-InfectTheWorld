package de.timweb.ld48.villain.util;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.timweb.ld48.villain.VillainMain;

public enum SoundEffect {
	HURT("hurt.wav"), //
	LEVELUP("levelUp.wav"), //
	MUSIC("music.wav"), //
	SPECIAL("special.wav"), //
	TALK("talk.wav");

	private static boolean isMuted = false;
	private static boolean isMusicMuted = false;
	private Clip[] clips;
	private int curser;

	SoundEffect(String file) {
		try {
			URL url = VillainMain.class.getResource("/" + file);

			if (file != "music.wav")
				clips = new Clip[5];
			else
				clips = new Clip[2];

			for (int i = 0; i < clips.length; i++) {

				AudioInputStream ais = AudioSystem.getAudioInputStream(url);

				AudioFormat format = ais.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				clips[i] = (Clip) AudioSystem.getLine(info);
				clips[i].open(ais);

				ais.close();
			}

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (isMuted)
			return;
		new Thread() {
			@Override
			public void run() {
				try {
					clips[curser].stop();
					clips[curser].setFramePosition(0);
					clips[curser].start();
				} catch (Exception e) {
				}
			}
		}.start();

		curser = ++curser % clips.length;
	}

	public void loop() {
		clips[0].loop(-1);
	}

	private void stop() {
		for (Clip c : clips) {
			c.stop();
			c.setFramePosition(0);
		}
	}

	public static void init() {
		values();
	}

	public static void stopMusic() {
		SoundEffect.MUSIC.stop();
		isMusicMuted = true;
	}

	public static void muteMusic() {
		if (!isMusicMuted) {
			SoundEffect.MUSIC.stop();
			isMusicMuted = true;
		} else {
			SoundEffect.MUSIC.loop();
			isMusicMuted = false;
		}
	}

	public static void muteSound() {
		if (!isMuted) {
			isMuted = true;
		} else {
			isMuted = false;
		}
	}

	public static boolean isMusicMuted() {
		return isMusicMuted;
	}

	public static boolean isSoundMuted() {
		return isMuted;
	}

}