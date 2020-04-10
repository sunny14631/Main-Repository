// 
// Decompiled by Procyon v0.5.36
// 

package merthew.game.objects;

import merthew.engine.GameContainer;
import merthew.engine.physics.DrawnObject;
import merthew.engine.physics.BBox;
import merthew.engine.physics.HasPhysics;

public class Player extends HasPhysics
{
    float speed;
    int isJump;
    int jumpPower;
    int coinCount;
    
    public Player(final float x, final float y) {
        super(new BBox(x, y, 4, 4), true, true, x, y, 8.0f, 8.0f, Type.RECTANGLE, -1);
        this.speed = 2.0f;
        this.isJump = 0;
        this.jumpPower = 4;
    }
    
    public void move(final GameContainer gc) {
        if (gc.getInput().isKey(37)) {
            System.out.println("L");
            this.setLastX(this.getX());
            this.sethVel(-this.speed);
        }
        if (gc.getInput().isKey(39)) {
            System.out.println("R");
            this.setLastX(this.getX());
            this.sethVel(this.speed);
        }
        if (this.getvVel() <= 0.001 && this.getvVel() >= -0.001) {
            this.isJump = Math.min(this.isJump + 1, 2);
        }
        if (gc.getInput().isKey(38) && this.getLastY() == this.getY() && this.isJump == 2) {
            this.isJump = 0;
            this.setvVel((float)(-this.jumpPower));
            System.out.println("U");
        }
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public void setSpeed(final float speed) {
        this.speed = speed;
    }
    
    public int getJumpPower() {
        return this.jumpPower;
    }
    
    public void setJumpPower(final int jumpPower) {
        this.jumpPower = jumpPower;
    }
    
    public int getCoinCount() {
        return this.coinCount;
    }
    
    public void setCoinCount(final int coinCount) {
        this.coinCount = coinCount;
    }
}
