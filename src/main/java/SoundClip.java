import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

class SoundClip extends JFrame {

    SoundClip(String message) {
        try {
            File soundFile = new File("C:/Users/user/IdeaProjects/SignDetection/src/main/resources/" + message + ".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
