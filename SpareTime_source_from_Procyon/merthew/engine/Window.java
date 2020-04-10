// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Window
{
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;
    
    public Window(final GameContainer gc) {
        this.image = new BufferedImage(gc.getWidth(), gc.getHeight(), 1);
        this.canvas = new Canvas();
        final Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        this.canvas.setPreferredSize(s);
        this.canvas.setMaximumSize(s);
        this.canvas.setMinimumSize(s);
        (this.frame = new JFrame(gc.getTitle())).setDefaultCloseOperation(3);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.canvas, "Center");
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.canvas.createBufferStrategy(2);
        this.bs = this.canvas.getBufferStrategy();
        this.g = this.bs.getDrawGraphics();
    }
    
    public void update() {
        this.g.drawImage(this.image, 0, 0, this.canvas.getWidth(), this.canvas.getHeight(), null);
        this.bs.show();
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public Canvas getCanvas() {
        return this.canvas;
    }
    
    public JFrame getFrame() {
        return this.frame;
    }
}
