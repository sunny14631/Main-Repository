// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine;

import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private GameContainer gc;
    private final int NUM_KEYS = 256;
    private boolean[] keys;
    private boolean[] keysLast;
    private final int NUM_BUTTONS = 5;
    private boolean[] buttons;
    private boolean[] buttonsLast;
    private int mouseX;
    private int mouseY;
    private int scroll;
    
    public Input(final GameContainer gc) {
        this.keys = new boolean[256];
        this.keysLast = new boolean[256];
        this.buttons = new boolean[5];
        this.buttonsLast = new boolean[5];
        this.gc = gc;
        this.mouseX = 0;
        this.mouseY = 0;
        this.scroll = 0;
        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }
    
    public void update() {
        this.scroll = 0;
        for (int i = 0; i < 256; ++i) {
            this.keysLast[i] = this.keys[i];
        }
        for (int i = 0; i < 5; ++i) {
            this.buttonsLast[i] = this.buttons[i];
        }
    }
    
    public boolean isKey(final int keyCode) {
        return this.keys[keyCode];
    }
    
    public boolean isKeyUp(final int keyCode) {
        return !this.keys[keyCode] && this.keysLast[keyCode];
    }
    
    public boolean isKeyDown(final int keyCode) {
        return !this.keysLast[keyCode] && this.keys[keyCode];
    }
    
    public boolean isButton(final int button) {
        return this.buttons[button];
    }
    
    public boolean isButtonUp(final int button) {
        return !this.buttons[button] && this.buttonsLast[button];
    }
    
    public boolean isButtonDown(final int button) {
        return !this.buttonsLast[button] && this.buttons[button];
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
        this.mouseX = (int)(e.getX() / this.gc.getScale());
        this.mouseY = (int)(e.getY() / this.gc.getScale());
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        this.mouseX = (int)(e.getX() / this.gc.getScale());
        this.mouseY = (int)(e.getY() / this.gc.getScale());
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        this.buttons[e.getButton()] = true;
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        this.buttons[e.getButton()] = false;
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        this.scroll = e.getWheelRotation();
    }
    
    public int getMouseX() {
        return this.mouseX;
    }
    
    public int getMouseY() {
        return this.mouseY;
    }
    
    public int getScroll() {
        return this.scroll;
    }
}
