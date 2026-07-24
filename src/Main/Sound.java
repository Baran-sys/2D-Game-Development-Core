package Main;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        try {
            soundURL[0] = new File("res/sound/BlueBoyAdventure.wav").toURI().toURL();
            soundURL[1] = new File("res/sound/coin.wav").toURI().toURL();
            soundURL[2] = new File("res/sound/powerup.wav").toURI().toURL();
            soundURL[3] = new File("res/sound/unlock.wav").toURI().toURL();
            soundURL[4] = new File("res/sound/fanfare.wav").toURI().toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                System.out.println("Sound file not found for index " + i);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float value = min + (max - min) * volume;
            gainControl.setValue(value);
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
