// 
// Decompiled by Procyon v0.5.36
// 

package merthew.game.objects;

import merthew.engine.physics.DrawnObject;
import merthew.engine.physics.BBox;
import merthew.engine.physics.HasPhysics;

public class Coin extends HasPhysics
{
    public Coin(final float x, final float y) {
        super(new BBox(x, y, 2, 4), true, false, x, y, 2.0f, 4.0f, Type.RECTANGLE, -3362816);
    }
}
