// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.physics;

import merthew.engine.Renderer;
import merthew.engine.GameContainer;

public class DrawnObject
{
    private float x;
    private float y;
    private float width;
    private float height;
    private Type type;
    private int color;
    
    public DrawnObject(final float x, final float y, final float width, final float height, final Type type, final int color) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.type = type;
        this.setColor(color);
    }
    
    public void draw(final GameContainer gc) {
        final Renderer r = gc.getRenderer();
        switch (this.type) {
            case RECTANGLE: {
                r.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), this.getColor());
                break;
            }
            case BORDER: {
                r.drawRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), this.getColor());
            }
        }
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public enum Type
    {
        RECTANGLE("RECTANGLE", 0), 
        BORDER("BORDER", 1), 
        CIRCLE("CIRCLE", 2);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
