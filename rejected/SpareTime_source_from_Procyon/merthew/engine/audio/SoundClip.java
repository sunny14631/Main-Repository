// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.audio;

import javax.sound.sampled.AudioInputStream;
import java.io.InputStream;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Control;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;

public class SoundClip
{
    private Clip clip;
    private FloatControl gainControl;
    
    public SoundClip(final String path) {
        this.clip = null;
        try {
            final InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            final InputStream bufferedIn = new BufferedInputStream(audioSrc);
            final AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            final AudioFormat baseFormat = ais.getFormat();
            final AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            final AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            (this.clip = AudioSystem.getClip()).open(dais);
            this.gainControl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public void play() {
        if (this.clip == null) {
            return;
        }
        this.stop();
        this.clip.setFramePosition(0);
        while (!this.clip.isRunning()) {
            this.clip.start();
        }
    }
    
    public void stop() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }
    
    public void close() {
        this.stop();
        this.clip.drain();
        this.clip.close();
    }
    
    public void loop() {
        this.clip.loop(-1);
        this.play();
    }
    
    public void setVolume(final float val) {
        this.gainControl.setValue(val);
    }
    
    public boolean isRunning() {
        return this.clip.isRunning();
    }
}
