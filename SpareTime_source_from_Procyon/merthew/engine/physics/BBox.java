// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.physics;

public class BBox
{
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    
    public BBox(final float x1, final float y1, final float x2, final float y2) {
        this.setX1(x1);
        this.setY1(y1);
        this.setX2(x2);
        this.setY2(y2);
    }
    
    public BBox(final float x, final float y, final int width, final int height) {
        this.setX1(x);
        this.setY1(y);
        this.setX2(x + width);
        this.setY2(y + height);
    }
    
    public boolean collides(final BBox check) {
        return this.getX1() <= check.getX2() && this.getX1() >= check.getX2();
    }
    
    public float getX1() {
        return this.x1;
    }
    
    public void setX1(final float x1) {
        this.x1 = x1;
    }
    
    public float getX2() {
        return this.x2;
    }
    
    public void setX2(final float x2) {
        this.x2 = x2;
    }
    
    public float getY1() {
        return this.y1;
    }
    
    public void setY1(final float y1) {
        this.y1 = y1;
    }
    
    public float getY2() {
        return this.y2;
    }
    
    public void setY2(final float y2) {
        this.y2 = y2;
    }
}
