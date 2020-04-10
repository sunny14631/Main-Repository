// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.physics;

import merthew.engine.GameContainer;

public class HasPhysics extends DrawnObject
{
    private BBox box;
    private boolean isGravity;
    private boolean isSolid;
    private float gravity;
    float hVel;
    float vVel;
    float lastX;
    float lastY;
    
    public HasPhysics(final BBox b, final boolean isGravity, final boolean isSolid, final float x, final float y, final float width, final float height, final Type type, final int color) {
        super(x, y, width, height, type, color);
        this.gravity = 0.1f;
        this.setBox(b);
        this.setGravity(isGravity);
        this.setSolid(isSolid);
    }
    
    public void calcPhysics(final GameContainer gc) {
        if (this.isGravity()) {
            this.vVel += this.gravity;
        }
        if (this.hVel != 0.0f) {
            this.hVel *= (float)0.9;
        }
        if (this.hVel < 1.0E-4 && this.hVel > -1.0E-4) {
            this.hVel = 0.0f;
        }
        this.setX(this.getX() + this.hVel);
        this.setY(this.getY() + this.vVel);
        if (this.getX() >= gc.getWidth() - (this.getWidth() + 1.0f)) {
            this.setX(gc.getWidth() - (this.getWidth() + 1.0f));
        }
        if (this.getX() <= 0.0f) {
            this.setX(0.0f);
        }
        if (this.getY() >= gc.getHeight() - (this.getHeight() + 1.0f)) {
            this.setY(gc.getHeight() - (this.getHeight() + 1.0f));
        }
        if (this.getY() <= 0.0f) {
            this.setY(0.0f);
        }
        if (this.lastY == this.getY()) {
            this.vVel = 0.0f;
        }
        this.lastX = this.getX();
        this.lastY = this.getY();
        this.box.setX1(this.getX());
        this.box.setY1(this.getY());
        this.box.setX2(this.getX() + this.getWidth());
        this.box.setY2(this.getY() + this.getHeight());
    }
    
    public boolean isCollide(final HasPhysics o) {
        final BBox other = o.getBox();
        return this.box.getX1() < other.getX2() && this.box.getX2() > other.getX1() && this.box.getY1() < other.getY2() && this.box.getY2() > other.getY1();
    }
    
    public void drawBB(final GameContainer gc) {
        gc.getRenderer().drawRect((int)this.box.getX1(), (int)this.box.getY1(), (int)(this.box.getX2() - this.box.getX1()), (int)(this.box.getY2() - this.box.getY1()), -16776961);
    }
    
    public BBox getBox() {
        return this.box;
    }
    
    public void setBox(final BBox box) {
        this.box = box;
    }
    
    public boolean isGravity() {
        return this.isGravity;
    }
    
    public void setGravity(final boolean isGravity) {
        this.isGravity = isGravity;
    }
    
    public float gethVel() {
        return this.hVel;
    }
    
    public void sethVel(final float hVel) {
        this.hVel = hVel;
    }
    
    public float getvVel() {
        return this.vVel;
    }
    
    public void setvVel(final float vVel) {
        this.vVel = vVel;
    }
    
    public float getLastX() {
        return this.lastX;
    }
    
    public void setLastX(final float lastX) {
        this.lastX = lastX;
    }
    
    public float getLastY() {
        return this.lastY;
    }
    
    public void setLastY(final float lastY) {
        this.lastY = lastY;
    }
    
    public boolean isSolid() {
        return this.isSolid;
    }
    
    public void setSolid(final boolean isSolid) {
        this.isSolid = isSolid;
    }
}
